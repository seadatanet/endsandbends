/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author mbettich
 */
public class NetCdfInputParamsObject extends InputParamsObject
{

    protected String dateVariableName, timeVariableName, datetimeVariableName,
            latitudeVariableName, longitudeVariableName;

    protected boolean isJoinDateTimeParamsEnabled;

    public NetCdfInputParamsObject(NetCdfInputParamsObject inputObject)
    {
        super(inputObject);
        this.dateVariableName = inputObject.dateVariableName;
        this.datetimeVariableName = inputObject.datetimeVariableName;
        this.timeVariableName = inputObject.timeVariableName;
        this.latitudeVariableName = inputObject.latitudeVariableName;
        this.longitudeVariableName = inputObject.longitudeVariableName;
        this.isJoinDateTimeParamsEnabled =
            inputObject.isJoinDateTimeParamsEnabled;
    }

    public NetCdfInputParamsObject()
    {
        super.setFormat(InputFilesTypes.GENERAL_NETCDF_FORMAT);
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
        inputModel.setAttribute("format", "GENERAL_NETCDF_FORMAT");

        Element timeFilter = doc.createElement("TimeFilter");
        timeFilter.setAttribute("enabled", String.valueOf(this
            .isTimeFilterEnabled()));
        Element startDate = doc.createElement("StartDate");
        if (null != this.getStartDate())
        {
            startDate.setTextContent(dateSDF.format(this.getStartDate()));
        }
        Element endDate = doc.createElement("EndDate");
        if (null != this.getEndDate())
        {
            endDate.setTextContent(dateSDF.format(this.getEndDate()));
        }
        timeFilter.appendChild(startDate);
        timeFilter.appendChild(endDate);

        Element dateTime = doc.createElement("DateTime");
        dateTime.setAttribute("joinEnabled", String.valueOf(this
            .isJoinDateTimeParamsEnabled()));
        if (!isJoinDateTimeParamsEnabled())
        {
            Element cdfElDate = doc.createElement("CdfElement");
            cdfElDate.setAttribute("type", "date");
            cdfElDate.setAttribute("column", this.getDateVariableName());
            dateTime.appendChild(cdfElDate);
            Element cdfElTime = doc.createElement("CdfElement");
            cdfElTime.setAttribute("type", "time");
            cdfElTime.setAttribute("column", this.getTimeVariableName());
            dateTime.appendChild(cdfElTime);

        }
        else
        {
            Element cdfElDateTime = doc.createElement("CdfElement");
            cdfElDateTime.setAttribute("type", "dateAndtime");
            cdfElDateTime
                .setAttribute("column", this.getDateTimeVariableName());
            dateTime.appendChild(cdfElDateTime);
        }

        Element coordinates = doc.createElement("Coordinates");
        Element cdfElLon = doc.createElement("CdfElement");
        cdfElLon.setAttribute("type", "longitude");
        cdfElLon.setAttribute("column", String.valueOf(this
            .getLongitudeVariableName()));
        Element cdfElLat = doc.createElement("CdfElement");
        cdfElLat.setAttribute("type", "latitude");
        cdfElLat.setAttribute("column", String.valueOf(this
            .getLatitudeVariableName()));
        coordinates.appendChild(cdfElLat);
        coordinates.appendChild(cdfElLon);

        inputModel.appendChild(timeFilter);
        inputModel.appendChild(dateTime);
        inputModel.appendChild(coordinates);

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

    @Override
    public void updateFromInputDialog(InputDialog dialog) throws EABException
    {
        super.updateFromInputDialog(dialog);
        datetimeVariableName = dialog.getDateTimeVariableName().getText();
        dateVariableName = dialog.getDateVariableName().getText();
        timeVariableName = dialog.getTimeVariableName().getText();
        latitudeVariableName = dialog.getLatitudeVariableName().getText();
        longitudeVariableName = dialog.getLongitudeVariableName().getText();
        isJoinDateTimeParamsEnabled = dialog.getJoinDateTime1().isSelected();
    }

    public boolean isJoinDateTimeParamsEnabled()
    {
        return isJoinDateTimeParamsEnabled;
    }

    public void setIsJoinDateTimeParamsEnabled(
            boolean isJoinDateTimeParamsEnabled)
    {
        this.isJoinDateTimeParamsEnabled = isJoinDateTimeParamsEnabled;
    }

    public void setDateVariableName(String dateFormatColumnName)
    {
        this.dateVariableName = dateFormatColumnName;
    }

    public String getDateVariableName()
    {
        return dateVariableName;
    }

    public void setTimeVariableName(String timeFormatColumnName)
    {
        this.timeVariableName = timeFormatColumnName;
    }

    public String getTimeVariableName()
    {
        return timeVariableName;
    }

    public void setLatitudeVariableName(String latitudeFormatColumnName)
    {
        this.latitudeVariableName = latitudeFormatColumnName;
    }

    public String getLatitudeVariableName()
    {
        return latitudeVariableName;
    }

    public void setLongitudeVariableName(String longitudeFormatColumnName)
    {
        this.longitudeVariableName = longitudeFormatColumnName;
    }

    public String getLongitudeVariableName()
    {
        return longitudeVariableName;
    }

    /**
     * Getter sur dateTimeColumnName.
     * 
     * @return the dateTimeColumnName
     */
    public String getDateTimeVariableName()
    {
        return datetimeVariableName;
    }

    /**
     * Setter sur dateTimeColumnName.
     * 
     * @param dateTimeColumnName
     *            the dateTimeColumnName to set
     */
    public void setDateTimeVariableName(String dateTimeColumnName)
    {
        this.datetimeVariableName = dateTimeColumnName;
    }

}
