/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author mbettich
 */
public class AsciiColumnOutputParamsObject extends OutputParamsObject
{
    protected String dateFormat, dateTimeFormat, timeFormat, latitudeFormat,
            longitudeFormat, tokenizer, startIndicator, endIndicator;

    protected int timeCorrection, dateFormatColumnNumber,
            dateTimeFormatColumnNumber, timeFormatColumnNumber,
            latitudeFormatColumnNumber, longitudeFormatColumnNumber,
            startEndIndicatorColumnNumber;

    protected boolean isJoinDateTimeParamsEnabled, withSeconds;

    public AsciiColumnOutputParamsObject(
            AsciiColumnOutputParamsObject outputObject)
    {
        super(outputObject);
        this.dateFormat = outputObject.dateFormat;
        this.dateTimeFormat = outputObject.dateTimeFormat;
        this.timeFormat = outputObject.timeFormat;
        this.latitudeFormat = outputObject.latitudeFormat;
        this.longitudeFormat = outputObject.longitudeFormat;
        this.dateFormatColumnNumber = outputObject.dateFormatColumnNumber;
        this.dateTimeFormatColumnNumber =
            outputObject.dateTimeFormatColumnNumber;
        this.timeFormatColumnNumber = outputObject.timeFormatColumnNumber;
        this.latitudeFormatColumnNumber =
            outputObject.latitudeFormatColumnNumber;
        this.longitudeFormatColumnNumber =
            outputObject.longitudeFormatColumnNumber;
        this.tokenizer = outputObject.tokenizer;
        this.timeCorrection = outputObject.timeCorrection;
        this.isJoinDateTimeParamsEnabled =
            outputObject.isJoinDateTimeParamsEnabled;
        this.startEndIndicatorColumnNumber =
            outputObject.startEndIndicatorColumnNumber;
        this.endIndicator = outputObject.endIndicator;
        this.startIndicator = outputObject.startIndicator;
    }

    /**
     * 
     */
    public AsciiColumnOutputParamsObject()
    {
        super.setFormat(OutputFilesTypes.COLUMNS_ASCII_FORMAT);
    }

    @Override
    public void updateFromResultDialog(ResultsDialog dialog)
    {
        isJoinDateTimeParamsEnabled =
            dialog.getColumnsAsciiGroupSeparateDateTimeFormatsCheckBox()
                .isSelected();
        dateFormat =
            (String) dialog.getColumnsAsciiDateFormats().getSelectedItem();
        dateTimeFormat =
            (String) dialog.getColumnsAsciiDatetimeFormats().getSelectedItem();
        timeFormat =
            (String) dialog.getColumnsAsciiTimeFormats().getSelectedItem();
        tokenizer = dialog.getColumnsAsciiTokenizer().getText();
        timeCorrection = (Integer) dialog.getTimeToApply().getValue();
        endIndicator = dialog.getEndIndicatorField().getText();
        startIndicator = dialog.getStartIndicatorField().getText();

        if (dialog.getColumnsAsciiLatitudeFormat().getSelectedItem() != null
                && !((String) dialog.getColumnsAsciiLatitudeFormat()
                    .getSelectedItem()).isEmpty())
        {
            latitudeFormat =
                (String) dialog.getColumnsAsciiLatitudeFormat()
                    .getSelectedItem();
        }

        if (dialog.getColumnsAsciiLongitudeFormat().getSelectedItem() != null
                && !((String) dialog.getColumnsAsciiLongitudeFormat()
                    .getSelectedItem()).isEmpty())
        {
            longitudeFormat =
                (String) dialog.getColumnsAsciiLongitudeFormat()
                    .getSelectedItem();
        }

        if (dialog.getColumnsAsciiDateColumnNumber().getText() != null
                && !(dialog.getColumnsAsciiDateColumnNumber().getText())
                    .isEmpty())
        {
            dateFormatColumnNumber =
                Integer.valueOf(dialog.getColumnsAsciiDateColumnNumber()
                    .getText());
        }

        if (dialog.getColumnsAsciiDatetimeColumnNumber().getText() != null
                && !dialog.getColumnsAsciiDatetimeColumnNumber().getText()
                    .isEmpty())
        {
            dateTimeFormatColumnNumber =
                Integer.valueOf(dialog.getColumnsAsciiDatetimeColumnNumber()
                    .getText());
        }

        if (dialog.getColumnsAsciiTimeColumnNumber().getText() != null
                && !dialog.getColumnsAsciiTimeColumnNumber().getText()
                    .isEmpty())
        {
            timeFormatColumnNumber =
                Integer.valueOf(dialog.getColumnsAsciiTimeColumnNumber()
                    .getText());
        }
        if (dialog.getColumnsAsciiLatitudeColumnNumber().getText() != null
                && !dialog.getColumnsAsciiLatitudeColumnNumber().getText()
                    .isEmpty())
        {
            latitudeFormatColumnNumber =
                Integer.valueOf(dialog.getColumnsAsciiLatitudeColumnNumber()
                    .getText());
        }
        if (dialog.getColumnsAsciiLongitudeColumnNumber().getText() != null
                && !dialog.getColumnsAsciiLongitudeColumnNumber().getText()
                    .isEmpty())
        {
            longitudeFormatColumnNumber =
                Integer.valueOf(dialog.getColumnsAsciiLongitudeColumnNumber()
                    .getText());
        }
        if (dialog.getStartEndIndicatorColumnNumber().getText() != null
                && !dialog.getStartEndIndicatorColumnNumber().getText()
                    .isEmpty())
        {
            startEndIndicatorColumnNumber =
                Integer.valueOf(dialog.getStartEndIndicatorColumnNumber()
                    .getText());
        }
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
        outputModel.setAttribute("format", "COLUMNS_ASCII_FORMAT");

        Element timeCorrectionEl = doc.createElement("TimeCorrection");
        timeCorrectionEl.setTextContent(String
            .valueOf(this.getTimeCorrection()));

        Element tokenizerEl = doc.createElement("Tokenizer");
        tokenizerEl.setTextContent(this.getTokenizer());

        Element dateTime = doc.createElement("DateTime");
        dateTime.setAttribute("joinEnabled", String.valueOf(this
            .isJoinDateTimeParamsEnabled()));
        if (!isJoinDateTimeParamsEnabled())
        {
            Element asciiElDate = doc.createElement("AsciiElement");
            asciiElDate.setAttribute("type", "date");
            asciiElDate.setAttribute("format", this.getDateFormat());
            asciiElDate.setAttribute("column", String.valueOf(this
                .getDateFormatColumnNumber()));
            Element asciiElTime = doc.createElement("AsciiElement");
            asciiElTime.setAttribute("type", "time");
            asciiElTime.setAttribute("format", this.getTimeFormat());
            asciiElTime.setAttribute("column", String.valueOf(this
                .getTimeFormatColumnNumber()));
            dateTime.appendChild(asciiElDate);
            dateTime.appendChild(asciiElTime);
        }
        else
        {
            Element cdfElDateTime = doc.createElement("AsciiElement");
            cdfElDateTime.setAttribute("type", "dateAndtime");
            cdfElDateTime.setAttribute("format", this.getDateTimeFormat());
            cdfElDateTime.setAttribute("column", String.valueOf(this
                .getDateFormatColumnNumber()));
            dateTime.appendChild(cdfElDateTime);
        }

        Element coordinates = doc.createElement("Coordinates");
        Element asciiElLon = doc.createElement("AsciiElement");
        asciiElLon.setAttribute("type", "longitude");
        asciiElLon.setAttribute("format", this.getLongitudeFormat());
        asciiElLon.setAttribute("column", String.valueOf(this
            .getLongitudeFormatColumnNumber()));

        Element asciiElLat = doc.createElement("AsciiElement");
        asciiElLat.setAttribute("type", "latitude");
        asciiElLat.setAttribute("format", this.getLatitudeFormat());
        asciiElLat.setAttribute("column", String.valueOf(this
            .getLatitudeFormatColumnNumber()));
        coordinates.appendChild(asciiElLat);
        coordinates.appendChild(asciiElLon);

        Element startEndIndicator = doc.createElement("StartEndIndicator");
        startEndIndicator.setAttribute("startIndicator", this
            .getStartIndicator());
        startEndIndicator.setAttribute("endIndicator", this.getEndIndicator());
        startEndIndicator.setAttribute("column", String.valueOf(this
            .getStartEndIndicatorColumnNumber()));

        outputModel.appendChild(timeCorrectionEl);
        outputModel.appendChild(tokenizerEl);
        outputModel.appendChild(dateTime);
        outputModel.appendChild(coordinates);
        outputModel.appendChild(startEndIndicator);

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

    public boolean isJoinDateTimeParamsEnabled()
    {
        return isJoinDateTimeParamsEnabled;
    }

    public void setIsJoinDateTimeParamsEnabled(
            boolean isJoinDateTimeParamsEnabled)
    {
        this.isJoinDateTimeParamsEnabled = isJoinDateTimeParamsEnabled;
    }

    /**
     * Getter sur tokenizer.
     * 
     * @return the tokenizer
     */
    public String getTokenizer()
    {
        return tokenizer;
    }

    /**
     * Setter sur tokenizer.
     * 
     * @param tokenizer
     *            the tokenizer to set
     */
    public void setTokenizer(String tokenizer)
    {
        this.tokenizer = tokenizer;
    }

    /**
     * Getter sur timeCorrection.
     * 
     * @return the timeCorrection
     */
    public int getTimeCorrection()
    {
        return timeCorrection;
    }

    /**
     * Setter sur timeCorrection.
     * 
     * @param timeCorrection
     *            the timeCorrection to set
     */
    public void setTimeCorrection(int timeCorrection)
    {
        this.timeCorrection = timeCorrection;
    }

    /**
     * Getter sur dateFormatColumnNumber.
     * 
     * @return the dateFormatColumnNumber
     */
    public int getDateFormatColumnNumber()
    {
        return dateFormatColumnNumber;
    }

    /**
     * Setter sur dateFormatColumnNumber.
     * 
     * @param dateFormatColumnNumber
     *            the dateFormatColumnNumber to set
     */
    public void setDateFormatColumnNumber(int dateFormatColumnNumber)
    {
        this.dateFormatColumnNumber = dateFormatColumnNumber;
    }

    /**
     * Getter sur timeFormatColumnNumber.
     * 
     * @return the timeFormatColumnNumber
     */
    public int getTimeFormatColumnNumber()
    {
        return timeFormatColumnNumber;
    }

    /**
     * Setter sur timeFormatColumnNumber.
     * 
     * @param timeFormatColumnNumber
     *            the timeFormatColumnNumber to set
     */
    public void setTimeFormatColumnNumber(int timeFormatColumnNumber)
    {
        this.timeFormatColumnNumber = timeFormatColumnNumber;
    }

    /**
     * Getter sur latitudeFormatColumnNumber.
     * 
     * @return the latitudeFormatColumnNumber
     */
    public int getLatitudeFormatColumnNumber()
    {
        return latitudeFormatColumnNumber;
    }

    /**
     * Setter sur latitudeFormatColumnNumber.
     * 
     * @param latitudeFormatColumnNumber
     *            the latitudeFormatColumnNumber to set
     */
    public void setLatitudeFormatColumnNumber(int latitudeFormatColumnNumber)
    {
        this.latitudeFormatColumnNumber = latitudeFormatColumnNumber;
    }

    /**
     * Getter sur longitudeFormatColumnNumber.
     * 
     * @return the longitudeFormatColumnNumber
     */
    public int getLongitudeFormatColumnNumber()
    {
        return longitudeFormatColumnNumber;
    }

    /**
     * Setter sur longitudeFormatColumnNumber.
     * 
     * @param longitudeFormatColumnNumber
     *            the longitudeFormatColumnNumber to set
     */
    public void setLongitudeFormatColumnNumber(int longitudeFormatColumnNumber)
    {
        this.longitudeFormatColumnNumber = longitudeFormatColumnNumber;
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
     * Getter sur dateTimeFormatColumnNumber.
     * 
     * @return the dateTimeFormatColumnNumber
     */
    public int getDateTimeFormatColumnNumber()
    {
        return dateTimeFormatColumnNumber;
    }

    /**
     * Setter sur dateTimeFormatColumnNumber.
     * 
     * @param dateTimeFormatColumnNumber
     *            the dateTimeFormatColumnNumber to set
     */
    public void setDateTimeFormatColumnNumber(int dateTimeFormatColumnNumber)
    {
        this.dateTimeFormatColumnNumber = dateTimeFormatColumnNumber;
    }

    /**
     * Getter sur startIndicator.
     * 
     * @return the startIndicator
     */
    public String getStartIndicator()
    {
        return startIndicator;
    }

    /**
     * Setter sur startIndicator.
     * 
     * @param startIndicator
     *            the startIndicator to set
     */
    public void setStartIndicator(String startIndicator)
    {
        this.startIndicator = startIndicator;
    }

    /**
     * Getter sur endIndicator.
     * 
     * @return the endIndicator
     */
    public String getEndIndicator()
    {
        return endIndicator;
    }

    /**
     * Setter sur endIndicator.
     * 
     * @param endIndicator
     *            the endIndicator to set
     */
    public void setEndIndicator(String endIndicator)
    {
        this.endIndicator = endIndicator;
    }

    /**
     * Getter sur startEndIndicatorColumnNumber.
     * 
     * @return the startEndIndicatorColumnNumber
     */
    public int getStartEndIndicatorColumnNumber()
    {
        return startEndIndicatorColumnNumber;
    }

    /**
     * Setter sur startEndIndicatorColumnNumber.
     * 
     * @param startEndIndicatorColumnNumber
     *            the startEndIndicatorColumnNumber to set
     */
    public void setStartEndIndicatorColumnNumber(
            int startEndIndicatorColumnNumber)
    {
        this.startEndIndicatorColumnNumber = startEndIndicatorColumnNumber;
    }

    /**
     * Getter sur withSeconds.
     * 
     * @return the withSeconds
     */
    public boolean isWithSeconds()
    {
        return withSeconds;
    }

    /**
     * Setter sur withSeconds.
     * 
     * @param withSeconds
     *            the withSeconds to set
     */
    public void setWithSeconds(boolean withSeconds)
    {
        this.withSeconds = withSeconds;
    }
}