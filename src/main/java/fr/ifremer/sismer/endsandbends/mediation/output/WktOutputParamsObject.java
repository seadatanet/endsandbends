/* ============================================================================
 * Nom du fichier	: NmeaParamsObject.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.mediation.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.enums.OutputFilesTypes;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class WktOutputParamsObject extends OutputParamsObject
{

    protected int resolution;

    public WktOutputParamsObject(WktOutputParamsObject outputObject)
    {
        super(outputObject);
        this.resolution = outputObject.resolution;
    }

    /**
    * 
    */
    public WktOutputParamsObject()
    {
        super.setFormat(OutputFilesTypes.WKT_FORMAT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromResultDialog(ResultsDialog dialog)
    {
        resolution =
            ((Integer) dialog.getWktResolutionValue().getValue()).intValue();
    }

    @Override
    public void convertToXML(File file) throws ParserConfigurationException,
            TransformerConfigurationException, TransformerException,
            IOException
    {
        FileOutputStream fos;

        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element outputModel = doc.createElement("OutputModel");
        doc.appendChild(outputModel);
        outputModel.setAttribute("format", "WKT_FORMAT");

        Element wktResolution = doc.createElement("WktResolution");
        wktResolution.setTextContent(String.valueOf(resolution));

        outputModel.appendChild(wktResolution);

        try
        {
            fos = new FileOutputStream(file.getAbsolutePath());

            // XERCES 1 or 2 additionnal classes.
            OutputFormat of =
                new OutputFormat(Globals.XML, Globals.UTF_8, true);
            of.setIndent(1);
            of.setIndenting(true);
            XMLSerializer serializer = new XMLSerializer(fos, of);
            serializer.serialize(doc);
            serializer.asDOMSerializer();
            fos.close();
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

    }

    public int getResolution()
    {
        return resolution;
    }

    public void setResolution(int resolution)
    {
        this.resolution = resolution;
    }

}
