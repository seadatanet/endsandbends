/* ============================================================================
 * Nom du fichier	: NmeaParamsObject.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.mediation.input;

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
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.ui.MainForm;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class NmeaInputParamsObject extends InputParamsObject
{

    protected int lossOfAccuracy;

    public NmeaInputParamsObject(NmeaInputParamsObject inputObject)
    {
        super(inputObject);
        this.lossOfAccuracy = inputObject.lossOfAccuracy;
    }

    /**
* 
*/
    public NmeaInputParamsObject()
    {
        super.setFormat(InputFilesTypes.NMEA_183_FORMAT);
    }

    public int getLossOfAccuracy()
    {
        return lossOfAccuracy;
    }

    public void setLossOfAccuracy(int lossOfAccuracy)
    {
        this.lossOfAccuracy = lossOfAccuracy;
    }

    @Override
    public void updateFromInputDialog(InputDialog dialog) throws EABException
    {
        super.updateFromInputDialog(dialog);
        if (dialog.getLossOfAccuracyField().getText() != null)
        {
            lossOfAccuracy =
                Integer.valueOf(dialog.getLossOfAccuracyField().getText());
        }
    }

    @Override
    public void convertToXML(File file) throws ParserConfigurationException,
            TransformerConfigurationException, TransformerException,
            IOException
    {
        FileOutputStream fos;
        UTCDateFormat dateSDF =
            new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_ISO);
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element inputModel = doc.createElement("InputModel");
        doc.appendChild(inputModel);
        inputModel.setAttribute("format", "NMEA_183_FORMAT");

        Element timeFilter = doc.createElement("TimeFilter");
        timeFilter.setAttribute("enabled", String.valueOf(this
            .isTimeFilterEnabled()));
        Element startDate = doc.createElement("StartDate");
        startDate.setTextContent(dateSDF.format(this.getStartDate()));
        Element endDate = doc.createElement("EndDate");
        endDate.setTextContent(dateSDF.format(this.getEndDate()));
        timeFilter.appendChild(startDate);
        timeFilter.appendChild(endDate);

        Element lossOfAccuracy = doc.createElement("LossOfAccuracy");
        lossOfAccuracy.setTextContent(String.valueOf(this.getLossOfAccuracy()));

        inputModel.appendChild(timeFilter);
        inputModel.appendChild(lossOfAccuracy);

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
}
