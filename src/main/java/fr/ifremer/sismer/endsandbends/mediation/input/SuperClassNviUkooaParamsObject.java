/* ============================================================================
 * Nom du fichier	: SuperClassNviUkooaParamsObject.java
 * Auteur			: Altran
 * Date				: 12 mai 2011
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.mediation.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
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
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public abstract class SuperClassNviUkooaParamsObject extends InputParamsObject
{

    protected boolean _isSplitFileEnabled;

    protected List<DateSegment> _dateSegmentList = new ArrayList<DateSegment>();

    /**
     * Constructeur
     * 
     * @param inputObject
     */
    public SuperClassNviUkooaParamsObject(InputParamsObject inputObject)
    {
        super(inputObject);
    }

    /**
     * Constructeur
     */
    public SuperClassNviUkooaParamsObject()
    {
    }

    protected List<DateSegment> populateSegmentsTable(JTable segTable)
            throws EABException
    {
        List<DateSegment> result = new ArrayList<DateSegment>();

        DateSegment dateSegment;
        String startDateStringValue, endDateStringValue;

        try
        {
            for (int i = 0; i < segTable.getRowCount(); i++)
            {
                dateSegment = new DateSegment();

                startDateStringValue = ((String) segTable.getValueAt(i, 0));

                if (null != startDateStringValue
                        && !startDateStringValue.isEmpty())
                {
                    dateSegment
                        .setStartDate(Globals.INPUT_DIALOG_SEGMENT_DATE_FORMAT
                            .parse(startDateStringValue));
                }
                else
                {
                    throw new EABException(ExceptionCode.ARGUMENT_NULL_OR_EMPTY);
                }

                endDateStringValue = (String) segTable.getValueAt(i, 1);

                if (null != endDateStringValue && !endDateStringValue.isEmpty())
                {
                    dateSegment
                        .setEndDate(Globals.INPUT_DIALOG_SEGMENT_DATE_FORMAT
                            .parse(endDateStringValue));
                }
                else
                {
                    throw new EABException(ExceptionCode.ARGUMENT_NULL_OR_EMPTY);
                }

                result.add(dateSegment);
            }
        }
        catch (ParseException e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e);
        }

        return result;
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

        Element inputModel = doc.createElement("InputModel");
        doc.appendChild(inputModel);
        if (this instanceof NviInputParamsObject)
            inputModel.setAttribute("format", "NETCDF_NVI_FORMAT");
        if (this instanceof UkooaInputParamsObject)
            inputModel.setAttribute("format", "UKOOA_P1_90");

        Element timeFilter = doc.createElement("TimeFilter");
        timeFilter.setAttribute("enabled",
            String.valueOf(this.isTimeFilterEnabled()));
        if (null != startDate && null != endDate)
        {
            Element startDate = doc.createElement("StartDate");
            startDate.setTextContent(Globals.PARAMETER_FILE_ISO_DATE_FORMAT
                .format(this.getStartDate()));
            Element endDate = doc.createElement("EndDate");
            endDate.setTextContent(Globals.PARAMETER_FILE_ISO_DATE_FORMAT
                .format(this.getEndDate()));
            timeFilter.appendChild(startDate);
            timeFilter.appendChild(endDate);
        }

        Element forceSplit = doc.createElement("ForceSplit");
        forceSplit.setAttribute("enabled",
            String.valueOf(this.isSplitFileEnabled()));
        for (DateSegment dateSegment : getDateSegmentList())
        {
            Element segment = doc.createElement("Segment");
            segment.setAttribute("startDate",
                Globals.PARAMETER_FILE_ISO_DATE_FORMAT.format(dateSegment
                    .getStartDate()));
            segment.setAttribute("endDate",
                Globals.PARAMETER_FILE_ISO_DATE_FORMAT.format(dateSegment
                    .getEndDate()));
            forceSplit.appendChild(segment);
        }

        inputModel.appendChild(timeFilter);
        if (this instanceof UkooaInputParamsObject)
        {
            Element isDegreeTheAngularUnit = doc.createElement("AngularUnit");

            if (((UkooaInputParamsObject) this).isDegreeTheAngularUnit())
                isDegreeTheAngularUnit.setTextContent("degrees");
            else
                isDegreeTheAngularUnit.setTextContent("grades");

            inputModel.appendChild(isDegreeTheAngularUnit);

        }
        inputModel.appendChild(forceSplit);

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

    /**
     * Getter sur _isSplitFileEnabled.
     * 
     * @return the isSplitFileEnabled
     */
    public boolean isSplitFileEnabled()
    {
        return _isSplitFileEnabled;
    }

    /**
     * Setter sur _isSplitFileEnabled.
     * 
     * @param isSplitFileEnabled
     *            the isSplitFileEnabled to set
     */
    public void setSplitFileEnabled(boolean isSplitFileEnabled)
    {
        _isSplitFileEnabled = isSplitFileEnabled;
    }

    /**
     * Getter sur _dateSegmentList.
     * 
     * @return the dateSegmentList
     */
    public List<DateSegment> getDateSegmentList()
    {
        return _dateSegmentList;
    }

    /**
     * Setter sur _dateSegmentList.
     * 
     * @param dateSegmentList
     *            the dateSegmentList to set
     */
    public void setDateSegmentList(List<DateSegment> dateSegmentList)
    {
        _dateSegmentList = dateSegmentList;
    }
}
