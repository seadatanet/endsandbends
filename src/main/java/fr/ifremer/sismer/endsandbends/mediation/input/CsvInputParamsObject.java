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
public class CsvInputParamsObject extends InputParamsObject
{

    protected String dateFormat, timeFormat, latitudeFormat, longitudeFormat,
            checkValidityOperator, dateTimeFormat, delimiterUsed;

    protected int firstLine, dateFormatColumnNumber, timeFormatColumnNumber,
            latitudeFormatColumnNumber, longitudeFormatColumnNumber,
            checkValidityColumnNumber, dateTimeColumnNumber, lastLine;

    protected boolean isCheckValidityEnabled, isDateTimeEnabled,
            isJoinDateTimeParamsEnabled, isLastLineDefined;

    protected double validityValue;

    public CsvInputParamsObject(CsvInputParamsObject inputObject)
    {
        super(inputObject);
        this.dateFormat = inputObject.dateFormat;
        this.timeFormat = inputObject.timeFormat;
        this.latitudeFormat = inputObject.latitudeFormat;
        this.longitudeFormat = inputObject.longitudeFormat;
        this.checkValidityOperator = inputObject.checkValidityOperator;
        this.lastLine = inputObject.lastLine;
        this.firstLine = inputObject.firstLine;
        this.dateFormatColumnNumber = inputObject.dateFormatColumnNumber;
        this.timeFormatColumnNumber = inputObject.timeFormatColumnNumber;
        this.latitudeFormatColumnNumber =
            inputObject.latitudeFormatColumnNumber;
        this.longitudeFormatColumnNumber =
            inputObject.longitudeFormatColumnNumber;
        this.checkValidityColumnNumber = inputObject.checkValidityColumnNumber;
        this.isCheckValidityEnabled = inputObject.isCheckValidityEnabled;
        this.isDateTimeEnabled = inputObject.isDateTimeEnabled;
        this.isJoinDateTimeParamsEnabled =
            inputObject.isJoinDateTimeParamsEnabled;
        this.validityValue = inputObject.validityValue;
        this.dateTimeColumnNumber = inputObject.dateTimeColumnNumber;
        this.dateTimeFormat = inputObject.dateTimeFormat;
        this.delimiterUsed = inputObject.delimiterUsed;
    }

    @Override
    public void updateFromInputDialog(InputDialog dialog) throws EABException
    {
        super.updateFromInputDialog(dialog);
        if (dialog.getFirstLine().getText() != null
                && !dialog.getFirstLine().getText().isEmpty())
        {
            firstLine = Integer.valueOf(dialog.getFirstLine().getText());
        }
        if (dialog.getLastLine().getText() != null
                && !dialog.getLastLine().getText().isEmpty())
        {
            lastLine = Integer.valueOf(dialog.getLastLine().getText());
        }

        isCheckValidityEnabled =
            dialog.getIsCheckValidityEnabled().isSelected();

        if (dialog.getCheckValidityColumnNumber().getText() != null
                && !dialog.getCheckValidityColumnNumber().getText().isEmpty())
        {
            checkValidityColumnNumber =
                Integer
                    .valueOf(dialog.getCheckValidityColumnNumber().getText());
        }
        checkValidityOperator =
            (String) dialog.getCheckValidityoperatorCombo().getSelectedItem();

        if (dialog.getValidityValue().getText() != null
                && !dialog.getValidityValue().getText().isEmpty())
        {
            validityValue = Double.valueOf(dialog.getValidityValue().getText());
        }

        isDateTimeEnabled = dialog.getIsDateTimeEnabled().isSelected();
        isJoinDateTimeParamsEnabled = dialog.getJoinDateTime().isSelected();
        dateFormat = (String) dialog.getDateFormatCombo().getSelectedItem();
        timeFormat = (String) dialog.getTimeFormatCombo().getSelectedItem();
        delimiterUsed = (String) dialog.getDelimiterUsed().getSelectedItem();

        if (dialog.getDateFormatColumnNumber().getText() != null
                && !dialog.getDateFormatColumnNumber().getText().isEmpty())
        {
            dateFormatColumnNumber =
                Integer.valueOf(dialog.getDateFormatColumnNumber().getText());
        }
        if (dialog.getTimeFormatColumnNumber().getText() != null
                && !dialog.getTimeFormatColumnNumber().getText().isEmpty())
        {
            timeFormatColumnNumber =
                Integer.valueOf(dialog.getTimeFormatColumnNumber().getText());
        }
        if (dialog.getDateTimeColumnNumber().getText() != null
                && !dialog.getDateTimeColumnNumber().getText().isEmpty())
        {
            dateTimeColumnNumber =
                Integer.valueOf(dialog.getDateTimeColumnNumber().getText());
        }
        dateTimeFormat = (String) dialog.getDateTimeFormat().getSelectedItem();

        latitudeFormat = (String) dialog.getLatitudeFormat().getSelectedItem();
        longitudeFormat =
            (String) dialog.getLongitudeFormat().getSelectedItem();

        if (dialog.getLatitudeFormatColumnNumber().getText() != null
                && !dialog.getLatitudeFormatColumnNumber().getText().isEmpty())
        {
            latitudeFormatColumnNumber =
                Integer.valueOf(dialog.getLatitudeFormatColumnNumber()
                    .getText());
        }
        if (dialog.getLongitudeFormatColumnNumber().getText() != null
                && !dialog.getLongitudeFormatColumnNumber().getText().isEmpty())
        {
            longitudeFormatColumnNumber =
                Integer.valueOf(dialog.getLongitudeFormatColumnNumber()
                    .getText());
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
        DocumentBuilder docBuilder;

        docBuilder = dbfac.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element inputModel = doc.createElement("InputModel");
        doc.appendChild(inputModel);
        inputModel.setAttribute("format", "CSV_FORMAT");

        Element timeFilter = doc.createElement("TimeFilter");
        timeFilter.setAttribute("enabled",
            String.valueOf(this.isTimeFilterEnabled()));
        if (null != startDate && null != endDate)
        {
            Element startDateEl = doc.createElement("StartDate");
            startDateEl.setTextContent(dateSDF.format(this.getStartDate()));
            Element endDateEl = doc.createElement("EndDate");
            endDateEl.setTextContent(dateSDF.format(this.getEndDate()));
            timeFilter.appendChild(startDateEl);
            timeFilter.appendChild(endDateEl);
        }

        Element dateTime = doc.createElement("DateTime");
        dateTime.setAttribute("enabled",
            String.valueOf(!this.isDateTimeEnabled()));
        dateTime.setAttribute("joinEnabled",
            String.valueOf(this.isJoinDateTimeParamsEnabled()));
        if (!isJoinDateTimeParamsEnabled())
        {
            Element cvsElDate = doc.createElement("CsvElement");
            cvsElDate.setAttribute("type", "date");
            cvsElDate.setAttribute("format", this.getDateFormat());
            cvsElDate.setAttribute("column",
                String.valueOf(this.getDateFormatColumnNumber()));
            Element cvsElTime = doc.createElement("CsvElement");
            cvsElTime.setAttribute("type", "time");
            cvsElTime.setAttribute("format", this.getTimeFormat());
            cvsElTime.setAttribute("column",
                String.valueOf(this.getTimeFormatColumnNumber()));
            dateTime.appendChild(cvsElDate);
            dateTime.appendChild(cvsElTime);
        }
        else
        {
            Element cdfElDateTime = doc.createElement("CsvElement");
            cdfElDateTime.setAttribute("type", "dateAndtime");
            cdfElDateTime.setAttribute("format", this.getDateTimeFormat());
            cdfElDateTime.setAttribute("column",
                String.valueOf(this.getDateTimeColumnNumber()));
            dateTime.appendChild(cdfElDateTime);
        }

        Element coordinates = doc.createElement("Coordinates");
        Element cvsElLon = doc.createElement("CsvElement");
        cvsElLon.setAttribute("type", "longitude");
        cvsElLon.setAttribute("format", this.getLongitudeFormat());
        cvsElLon.setAttribute("column",
            String.valueOf(this.getLongitudeFormatColumnNumber()));
        Element cvsElLat = doc.createElement("CsvElement");
        cvsElLat.setAttribute("type", "latitude");
        cvsElLat.setAttribute("format", this.getLatitudeFormat());
        cvsElLat.setAttribute("column",
            String.valueOf(this.getLatitudeFormatColumnNumber()));
        coordinates.appendChild(cvsElLat);
        coordinates.appendChild(cvsElLon);

        Element firstLineEl = doc.createElement("FirstLine");
        firstLineEl.setTextContent(String.valueOf(this.getFirstLine()));
        Element lastLineEl = doc.createElement("LastLine");
        lastLineEl.setTextContent(String.valueOf(this.getLastLine()));

        Element checkValidity = doc.createElement("CheckValidity");
        checkValidity.setAttribute("enabled",
            String.valueOf(this.isCheckValidityEnabled()));
        Element columnNumber = doc.createElement("ColumnNumber");
        columnNumber.setTextContent(String.valueOf(this
            .getCheckValidityColumnNumber()));
        Element operator = doc.createElement("Operator");
        operator.setTextContent(this.getCheckValidityOperator());
        Element validityValueEl = doc.createElement("ValidityValue");
        validityValueEl.setTextContent(String.valueOf(this.getValidityValue()));
        checkValidity.appendChild(columnNumber);
        checkValidity.appendChild(operator);
        checkValidity.appendChild(validityValueEl);

        Element delimiterUsed = doc.createElement("DelimiterUsed");
        delimiterUsed.setTextContent(this.getDelimiterUsed());

        inputModel.appendChild(timeFilter);
        inputModel.appendChild(dateTime);
        inputModel.appendChild(firstLineEl);
        inputModel.appendChild(lastLineEl);
        inputModel.appendChild(coordinates);
        inputModel.appendChild(checkValidity);
        inputModel.appendChild(delimiterUsed);

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
     * Getter sur delimiterUsed.
     * 
     * @return the delimiterUsed
     */
    public String getDelimiterUsed()
    {
        return delimiterUsed;
    }

    /**
     * Setter sur delimiterUsed.
     * 
     * @param delimiterUsed
     *            the delimiterUsed to set
     */
    public void setDelimiterUsed(String delimiterUsed)
    {
        this.delimiterUsed = delimiterUsed;
    }

    /**
     * 
     */
    public CsvInputParamsObject()
    {
        super.setFormat(InputFilesTypes.CSV_FORMAT);
    }

    public String getDateFormat()
    {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat()
    {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat)
    {
        this.timeFormat = timeFormat;
    }

    public String getLatitudeFormat()
    {
        return latitudeFormat;
    }

    public void setLatitudeFormat(String latitudeFormat)
    {
        this.latitudeFormat = latitudeFormat;
    }

    public String getLongitudeFormat()
    {
        return longitudeFormat;
    }

    public void setLongitudeFormat(String longitudeFormat)
    {
        this.longitudeFormat = longitudeFormat;
    }

    public boolean isCheckValidityEnabled()
    {
        return isCheckValidityEnabled;
    }

    public void setIsCheckValidityEnabled(boolean isCheckValidityEnabled)
    {
        this.isCheckValidityEnabled = isCheckValidityEnabled;
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

    public String getCheckValidityOperator()
    {
        return checkValidityOperator;
    }

    public void setCheckValidityOperator(String checkValidityOperator)
    {
        this.checkValidityOperator = checkValidityOperator;
    }

    public int getFirstLine()
    {
        return firstLine;
    }

    public void setFirstLine(int firstLine)
    {
        this.firstLine = firstLine;
    }

    public int getLastLine()
    {
        return lastLine;
    }

    public void setLastLine(int lastLine)
    {
        this.lastLine = lastLine;
    }

    public void setDateFormatColumnNumber(int dateFormatColumnNumber)
    {
        this.dateFormatColumnNumber = dateFormatColumnNumber;
    }

    public int getDateFormatColumnNumber()
    {
        return dateFormatColumnNumber;
    }

    public void setTimeFormatColumnNumber(int timeFormatColumnNumber)
    {
        this.timeFormatColumnNumber = timeFormatColumnNumber;
    }

    public int getTimeFormatColumnNumber()
    {
        return timeFormatColumnNumber;
    }

    public void setLatitudeFormatColumnNumber(int latitudeFormatColumnNumber)
    {
        this.latitudeFormatColumnNumber = latitudeFormatColumnNumber;
    }

    public int getLatitudeFormatColumnNumber()
    {
        return latitudeFormatColumnNumber;
    }

    public void setLongitudeFormatColumnNumber(int longitudeFormatColumnNumber)
    {
        this.longitudeFormatColumnNumber = longitudeFormatColumnNumber;
    }

    public int getLongitudeFormatColumnNumber()
    {
        return longitudeFormatColumnNumber;
    }

    public void setCheckValidityColumnNumber(int checkValidityColumnNumber)
    {
        this.checkValidityColumnNumber = checkValidityColumnNumber;
    }

    public int getCheckValidityColumnNumber()
    {
        return checkValidityColumnNumber;
    }

    public void setValidityValue(double validityValue)
    {
        this.validityValue = validityValue;
    }

    public double getValidityValue()
    {
        return validityValue;
    }

    public boolean isDateTimeEnabled()
    {
        return isDateTimeEnabled;
    }

    public void setIsDateTimeEnabled(boolean isDateTimeEnabled)
    {
        this.isDateTimeEnabled = isDateTimeEnabled;
    }

    /**
     * Getter sur dateTimeFormat.
     * 
     * @return the dateTimeFormat
     */
    public String getDateTimeFormat()
    {
        return dateTimeFormat;
    }

    /**
     * Setter sur dateTimeFormat.
     * 
     * @param dateTimeFormat
     *            the dateTimeFormat to set
     */
    public void setDateTimeFormat(String dateTimeFormat)
    {
        this.dateTimeFormat = dateTimeFormat;
    }

    /**
     * Getter sur dateTimeColumnNumber.
     * 
     * @return the dateTimeColumnNumber
     */
    public int getDateTimeColumnNumber()
    {
        return dateTimeColumnNumber;
    }

    /**
     * Setter sur dateTimeColumnNumber.
     * 
     * @param dateTimeColumnNumber
     *            the dateTimeColumnNumber to set
     */
    public void setDateTimeColumnNumber(int dateTimeColumnNumber)
    {
        this.dateTimeColumnNumber = dateTimeColumnNumber;
    }

    /**
     * Setter sur isCheckValidityEnabled.
     * 
     * @param isCheckValidityEnabled
     *            the isCheckValidityEnabled to set
     */
    public void setCheckValidityEnabled(boolean isCheckValidityEnabled)
    {
        this.isCheckValidityEnabled = isCheckValidityEnabled;
    }

    /**
     * Setter sur isDateTimeEnabled.
     * 
     * @param isDateTimeEnabled
     *            the isDateTimeEnabled to set
     */
    public void setDateTimeEnabled(boolean isDateTimeEnabled)
    {
        this.isDateTimeEnabled = isDateTimeEnabled;
    }

    /**
     * Setter sur isJoinDateTimeParamsEnabled.
     * 
     * @param isJoinDateTimeParamsEnabled
     *            the isJoinDateTimeParamsEnabled to set
     */
    public void setJoinDateTimeParamsEnabled(boolean isJoinDateTimeParamsEnabled)
    {
        this.isJoinDateTimeParamsEnabled = isJoinDateTimeParamsEnabled;
    }

    /**
     * Getter sur isLastLineDefined.
     * 
     * @return the isLastLineDefined
     */
    public boolean isLastLineDefined()
    {
        return isLastLineDefined;
    }

    /**
     * Setter sur isLastLineDefined.
     * 
     * @param isLastLineDefined
     *            the isLastLineDefined to set
     */
    public void setLastLineDefined(boolean isLastLineDefined)
    {
        this.isLastLineDefined = isLastLineDefined;
    }

}