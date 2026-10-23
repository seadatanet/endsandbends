/* ============================================================================
 * Nom du fichier	: GMLFileWriter.java
 * Auteur			: Altran
 * Date				: 11 juin 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.PrecisionModel;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints;
import fr.ifremer.sismer.endsandbends.mediation.output.GmlOutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.NavigationData;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class GMLFileWriter extends AEndsAndBendsWriter implements
        IEndsAndBendsWriter
{


    /**
     * XML Nodes names 19139
     */
    private static final String EXTENT_19139 = "extent";
    private static final String IDENT_INFO = "identificationInfo";
    private static final String DATA_EXT_19139 = "EX_Extent";
    private static final String GEO_ELE_19139 = "geographicElement";
    private static final String GEO_BND_BOX_19139 = "EX_GeographicBoundingBox";
    private static final String WEST_BL_19139 = "westBoundLongitude";
    private static final String EAST_BL_19139 = "eastBoundLongitude";
    private static final String SOUTH_BL_19139 = "southBoundLatitude";
    private static final String NORTH_BL_19139 = "northBoundLatitude";
    private static final String DECIMAL_BL_19139 = "gco:Decimal";
    private static final String BOUND_POLY_19139 = "EX_BoundingPolygon";
    private static final String POLYGON2_19139 = "polygon";

    /**
     * Param�tres � utiliser lors de l'ex�cution de l'algorithme.
     */
    GmlOutputParamsObject _gmlParams = null;

    private Node nodeToKeepBefore;
    private final List<Node> nodesToKeepBefore = new ArrayList<Node>();

    private Node nodeToKeepAfter;
    private final List<Node> nodesToKeepAfter = new ArrayList<Node>();


    /**
     * @param file
     * @throws FileNotFoundException
     */
    public GMLFileWriter(JFrame main, NavigationData data)
    {
        super(main, data);
    }

    /**
     * Constructeur.
     * 
     * @param file
     * @throws FileNotFoundException
     */
    public GMLFileWriter(JFrame main, NavigationData data,
            final GmlOutputParamsObject gmlParams)
    {
        super(main, data);
        _gmlParams = gmlParams;
    }

    // // M�thodes directes pour �crire dans le fichier...

    @Override
    public void printString(int nWidth, String value)
    {
        _printWriter.print(formatString(nWidth, value));
    }

    @Override
    public void printStringLeft(int nWidth, String value)
    {
        _printWriter.print(formatStringLeft(nWidth, value));
    }

    @Override
    public void printInteger(int nWidth, char leftPad, int value)
    {
        _printWriter.print(formatInteger(nWidth, leftPad, value));
    }

    @Override
    public void printReal(int nWidth, int nDecimal, char leftPad, double value)
    {
        _printWriter.print(formatReal(nWidth, nDecimal, leftPad, value));
    }

    public static int CEIL = 0;
    public static int ROUND = 1;
    public static int FLOOR = 2;

    @Override
    public void printPosition(int degChars, int minChars, int fracChars,
            double lat, int round)
    {
        if (lat < 0)
        {
            _printWriter.print('-');
            lat = Math.abs(lat);
            if (round == CEIL)
                round = FLOOR;
            else if (round == FLOOR)
                round = CEIL;
        }
        else
            _printWriter.print('+');
        int deg = (int) lat;
        double fmin = (lat - deg) * 60;
        int min = 0;
        if (fracChars > 0)
        {
            long mult = 1;
            for (int i = 0; i < fracChars; i++)
                mult *= 10;
            if (round == CEIL)
                fmin = Math.ceil(fmin * mult);
            else if (round == ROUND)
                fmin = Math.round(fmin * mult);
            else if (round == FLOOR)
                fmin = Math.floor(fmin * mult);
            min = (int) (fmin / mult);
            int frac = (int) (fmin - min * mult);
            if (min >= 60)
            {
                min -= 60;
                deg += 1;
                if (deg >= 360)
                    deg -= 360;
            }
            printInteger(degChars, '0', deg);
            printInteger(minChars, '0', min);
            _printWriter.print('.');
            printInteger(fracChars, '0', frac);
        }
        else
        {
            if (round == CEIL)
                min = (int) Math.ceil(fmin);
            else if (round == ROUND)
                min = (int) Math.round(fmin);
            else if (round == FLOOR)
                min = (int) Math.floor(fmin);
            if (min >= 60)
            {
                min -= 60;
                deg += 1;
                if (deg >= 360)
                    deg -= 360;
            }
            printInteger(degChars, '0', deg);
            printInteger(minChars, '0', min);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void writeFile(NavPathPoints path, int optimPoints)
    {
        File file = null;
        Jts2GmlDOM converter = convertGMLtoDOM(path);
        file = new File(_gmlParams.getSelectedFile().getAbsolutePath());
        FileOutputStream fos;
        Transformer transformer;
        try
        {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // SVO le 18/03/2014 : Modif :use xalan}, indent =4
            transformer.setOutputProperty(
                "{http://xml.apache.org/xalan}indent-amount", "4");

            // initialize StreamResult with File object to save to file
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(converter.getDoc());
            transformer.transform(source, result);

            String xmlString = result.getWriter().toString();

            fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(xmlString);
            out.close();
        }
        catch (FileNotFoundException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        // As a DOM Serializer

        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (TransformerException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

   

    // SVO le 10/03/2014 : Creation GML ISO 19139
    // Mantis 20237 fichier CSR
    private void mergeGMLwithMikado19139(Jts2GmlDOM converter, Element[] geoEle)
    {
        Document mikadoDoc = converter.getDoc();

        // traitement de l'arbre dataExts
        // TODO voir pour eviter concat ici
        NodeList identInfo =
            mikadoDoc.getElementsByTagName(Jts2GmlDOM.GMD_PREFIX.concat(
                Jts2GmlDOM.PREFIX_SEPARATOR).concat(IDENT_INFO));
        // we know that we have only one identificationInfo
        Element identInfoElement = (Element) identInfo.item(0);

        NodeList dataExts =
            identInfoElement.getElementsByTagName(Jts2GmlDOM.GMD_PREFIX.concat(
                Jts2GmlDOM.PREFIX_SEPARATOR).concat(DATA_EXT_19139));

        Node dataExtNode = dataExts.item(0);

        if (containsInsertionTags(
            dataExtNode.getChildNodes(),
            Jts2GmlDOM.GMD_PREFIX.concat(Jts2GmlDOM.PREFIX_SEPARATOR).concat(
                GEO_ELE_19139)))
        {
            deleteInsertionTags(dataExtNode);// clone
            // GeographicDescription,temporalElement,verticalElement and delete
// geoElements and
            // GeographicDescription, temporalElement,verticalElement

            for (Node nodeToKeepBefore : nodesToKeepBefore)
            {
                dataExtNode.appendChild(nodeToKeepBefore);
            }
            dataExtNode.appendChild(geoEle[0]);
            dataExtNode.appendChild(geoEle[1]);

            for (Node nodeToKeepAfter : nodesToKeepAfter)
            {
                dataExtNode.appendChild(nodeToKeepAfter);
            }
        }
    }

    private boolean containsInsertionTags(NodeList childNodes, String tag)
    {
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            if (childNodes.item(i).getNodeName().equals(tag))
            {
                return true;
            }
        }
        return false;
    }

    private void deleteInsertionTags(Node parent)
    {
        NodeList nodeList = parent.getChildNodes();
        int nbChildren = nodeList.getLength();
        for (int i = 0; i < nbChildren; i++)
        {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                if (currentNode.getNodeName().equals(
                    Jts2GmlDOM.GMD_PREFIX.concat(Jts2GmlDOM.PREFIX_SEPARATOR)
                        .concat(GEO_ELE_19139)))
                {
                    if (containsInsertionTags(currentNode.getChildNodes(),
                        "gmd:EX_GeographicDescription"))
                    {

                        nodeToKeepBefore = currentNode.cloneNode(true);
                        nodesToKeepBefore.add(nodeToKeepBefore);
                    }
                }
                else
                {
                    nodeToKeepAfter = currentNode.cloneNode(true);
                    nodesToKeepAfter.add(nodeToKeepAfter);
                }
            }
        }
        // Delete Child
        Node child;
        while (parent.hasChildNodes())
        {
            child = parent.getFirstChild();
            parent.removeChild(parent.getFirstChild());
        }
    }

    private Jts2GmlDOM convertGMLtoDOM(NavPathPoints path)
    {
        Jts2GmlDOM converter = null;

        double precision;

        if (_gmlParams.getResolution() > 0)
        {
            precision = Math.pow(10.0, _gmlParams.getResolution() - 1);
        }
        else
        {
            precision = 0;
        }

        // Cr�ation du GeometryFactory
        GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(precision));

        try
        {
            if (!_gmlParams.getGenerationType().equalsIgnoreCase("mikado"))
            {
                converter = new Jts2GmlDOM(Jts2GmlDOM.GMD_NAMESPACE);
            }
            else
            {
                DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
                DocumentBuilder parser = factory.newDocumentBuilder();
                Document mikadoDoc =
                    parser
                        .parse(_gmlParams.getSelectedFile().getAbsolutePath());
                converter = new Jts2GmlDOM(mikadoDoc);
            }

            // Traitement d'un profil :
            int modelp;
            int model;

            List<LineString> lineStringList = new ArrayList<LineString>();
            double maxLatitude = 0, minLatitude = 0, maxLongitude = 0, minLongitude =
                0;

            for (int i = 0; i < _data._paths.length; i++)
            {
                // R�cup�rer le parcours :
                path = _data._paths[i];
                model = path.getSelectedModel();
                // Parcours des points :
                int count = path.getPointsCount();
                ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

                // Initialisation des valeurs min et max pour le premier segment
                if (i == 0)
                {
                    maxLatitude = path.getLatitude(0);
                    minLatitude = path.getLatitude(0);
                    maxLongitude = path.getLongitude(0);
                    minLongitude = path.getLongitude(0);
                }

                for (int index = 0; index < count; index++)
                {
                    // Sauter s'il n'est pas dans le mod�le :
                    modelp = path.getModel(index);
                    if ((model != 0) && ((modelp == 0) || (modelp > model)))
                        continue;

                    if (path.getPoint(index) != null)
                    {
                        coordinates.add(new Coordinate(
                            path.getLongitude(index), path.getLatitude(index)));

                        // Comparaison des min max avec le point courant
                        if (Double.compare(path.getLongitude(index),
                            maxLongitude) > 0)
                        {
                            maxLongitude = path.getLongitude(index);
                        }
                        if (Double.compare(path.getLongitude(index),
                            minLongitude) < 0)
                        {
                            minLongitude = path.getLongitude(index);
                        }

                        if (Double
                            .compare(path.getLatitude(index), maxLatitude) > 0)
                        {
                            maxLatitude = path.getLatitude(index);
                        }
                        if (Double
                            .compare(path.getLatitude(index), minLatitude) < 0)
                        {
                            minLatitude = path.getLatitude(index);
                        }
                    }
                }
                lineStringList.add(geometryFactory.createLineString(coordinates
                    .toArray(new Coordinate[coordinates.size()])));

            }

            MultiLineString multiCurve =
                geometryFactory.createMultiLineString(lineStringList
                    .toArray(new LineString[lineStringList.size()]));

            Element elementMultiCurve =
                converter.multiLineStringToGmlElement(multiCurve);

           
            Element geoBndBox =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    GEO_BND_BOX_19139);
            Element westBL =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    WEST_BL_19139);
            Element eastBL =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    EAST_BL_19139);
            Element southBL =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    SOUTH_BL_19139);
            Element northBL =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    NORTH_BL_19139);

            Element polygon =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    POLYGON2_19139);
            Element boundPoly =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    BOUND_POLY_19139);

            Element[] decimalBLVal = new Element[4];
            decimalBLVal[0] = converter.createElement(DECIMAL_BL_19139);
            decimalBLVal[1] = converter.createElement(DECIMAL_BL_19139);
            decimalBLVal[2] = converter.createElement(DECIMAL_BL_19139);
            decimalBLVal[3] = converter.createElement(DECIMAL_BL_19139);

            Element[] geoEle = new Element[2];
            geoEle[0] =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    GEO_ELE_19139);
            geoEle[1] =
                converter.createElementNS(Jts2GmlDOM.GMD_NAMESPACE,
                    GEO_ELE_19139);

            decimalBLVal[0].setTextContent(roundToString(minLongitude,
                BigDecimal.ROUND_DOWN));
            decimalBLVal[1].setTextContent(roundToString(maxLongitude,
                BigDecimal.ROUND_UP));
            decimalBLVal[2].setTextContent(roundToString(minLatitude,
                BigDecimal.ROUND_DOWN));
            decimalBLVal[3].setTextContent(roundToString(maxLatitude,
                BigDecimal.ROUND_UP));

            geoEle[0].appendChild(geoBndBox);
            geoBndBox.appendChild(westBL);
            westBL.appendChild(decimalBLVal[0]);
            geoBndBox.appendChild(eastBL);
            eastBL.appendChild(decimalBLVal[1]);
            geoBndBox.appendChild(southBL);
            southBL.appendChild(decimalBLVal[2]);
            geoBndBox.appendChild(northBL);
            northBL.appendChild(decimalBLVal[3]);

            geoEle[1].appendChild(boundPoly);
            boundPoly.appendChild(polygon);
            polygon.appendChild(elementMultiCurve);

            // Append GeoEle
            if (!_gmlParams.getGenerationType().equalsIgnoreCase("mikado"))
            {
                converter.getMetaData().appendChild(geoEle[0]);
                converter.getMetaData().appendChild(geoEle[1]);

            }
            else
            {
                mergeGMLwithMikado19139(converter, geoEle);
            }


        }

        catch (ParserConfigurationException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        catch (FactoryConfigurationError e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        catch (SAXException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (EABException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.displayFatalError();
        }
        return converter;
    }

    public String roundToString(double myDouble, int roundingMethod)
    {
        BigDecimal bd = new BigDecimal(myDouble);
        bd = bd.setScale(_gmlParams.getResolution(), roundingMethod);
        String myRoundedString = bd.toString();



        return myRoundedString;
    }
}
