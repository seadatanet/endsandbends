package com.ago.xml;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.enums.OutputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.mediation.input.CsvInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.DateSegment;
import fr.ifremer.sismer.endsandbends.mediation.input.InputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NetCdfInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NmeaInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NviInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.UkooaInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.AsciiColumnOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.AsciiOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.GmlOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.ShapeOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.WktOutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.MainForm;

public class SAXParserEndsAndBends extends DefaultHandler
{

    private static final String SIMPLEDATEFORMATMASK = Messages.getString(
        Messages.MESSAGES, "SIMPLEDATEFORMATMASK");
    private static final String SIMPLETIMEFORMATMASK = Messages.getString(
        Messages.MESSAGES, "SIMPLETIMEFORMATMASK");
    private static final String SIMPLEDATE_TIMEFORMATMASK =
        SIMPLEDATEFORMATMASK + " " + SIMPLETIMEFORMATMASK;

    private String tempVal;
    // to maintain context
    private InputParamsObject inputParamsObject;
    private OutputParamsObject outputParamsObject;

    boolean inDateTimeNode = false;
    boolean inCoordinatesNode = false;

    private File xmlFile = null;

    public SAXParserEndsAndBends(File file)
    {
        xmlFile = file;
    }

    public void parseDocument() throws EABException
    {
        // get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try
        {
            // get a new instance of parser
            SAXParser sp = spf.newSAXParser();
            // parse the file and also register this class for call backs
            sp.parse(xmlFile, this);

        }
        catch (SAXException se)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, se,
                Globals.INCONSISTENT_XML_FILE);
        }
        catch (ParserConfigurationException pce)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, pce,
                Globals.INCONSISTENT_XML_FILE);
        }
        catch (IOException ie)
        {
            throw new EABException(ExceptionCode.IO_ERROR, ie);
        }
    }

    // Event Handlers

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException
    {
        try
        {
            // reset
            tempVal = ""; //$NON-NLS-1$
            if (qName.equalsIgnoreCase("InputModel"))
            {
                handleInputFileType(attributes);
            }
            else if (qName.equalsIgnoreCase("OutputModel"))
            {
                handleOutputFileType(attributes);
            }
            else if (qName.equalsIgnoreCase("TimeFilter"))
            {
                // create a new instance of employee
                String isTimeFilterEnabled = attributes.getValue("enabled");
                inputParamsObject.setIsTimeFilterEnabled(Boolean
                    .valueOf(isTimeFilterEnabled));
            }
            else if (qName.equalsIgnoreCase("DateTime"))
            {
                inDateTimeNode = true;
                inCoordinatesNode = false;
                if (inputParamsObject instanceof CsvInputParamsObject)
                {
                    String isDateTimeEnabled = attributes.getValue("enabled");
                    ((CsvInputParamsObject) inputParamsObject)
                        .setIsDateTimeEnabled(Boolean
                            .valueOf(isDateTimeEnabled));
                    String isJoinDateTimeEnabled =
                        attributes.getValue("joinEnabled");
                    ((CsvInputParamsObject) inputParamsObject)
                        .setIsJoinDateTimeParamsEnabled(Boolean
                            .valueOf(isJoinDateTimeEnabled));
                }
                if (outputParamsObject instanceof AsciiColumnOutputParamsObject)
                {
                    String isJoinDateTimeEnabled =
                        attributes.getValue("joinEnabled");
                    ((AsciiColumnOutputParamsObject) outputParamsObject)
                        .setIsJoinDateTimeParamsEnabled(Boolean
                            .valueOf(isJoinDateTimeEnabled));
                }
                if (inputParamsObject instanceof NetCdfInputParamsObject)
                {
                    String isJoinDateTimeEnabled =
                        attributes.getValue("joinEnabled");
                    ((NetCdfInputParamsObject) inputParamsObject)
                        .setIsJoinDateTimeParamsEnabled(Boolean
                            .valueOf(isJoinDateTimeEnabled));
                }
            }
            else if (qName.equalsIgnoreCase("Coordinates"))
            {
                inDateTimeNode = false;
                inCoordinatesNode = true;
            }
            else if (qName.equalsIgnoreCase("CsvElement"))
            {
                handleCsvElement(attributes);
            }
            else if (qName.equalsIgnoreCase("StartEndIndicator"))
            {
                String startIndicator = attributes.getValue("startIndicator");
                String endIndicator = attributes.getValue("endIndicator");
                String startEndIndicatorColumnNumber =
                    attributes.getValue("column");
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setEndIndicator(endIndicator);
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setStartIndicator(startIndicator);
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setStartEndIndicatorColumnNumber(Integer
                        .valueOf(startEndIndicatorColumnNumber));
            }
            else if (qName.equalsIgnoreCase("Segment"))
            {
                if (inputParamsObject instanceof NviInputParamsObject)
                    handleNviElement(attributes);
                else if (inputParamsObject instanceof UkooaInputParamsObject)
                    handleUKOOAElement(attributes);
            }
            else if (qName.equalsIgnoreCase("CdfElement"))
            {
                handleNetCdfElement(attributes);
            }
            else if (qName.equalsIgnoreCase("checkValidity"))
            {
                String isCheckValidityEnabled = attributes.getValue("enabled");
                ((CsvInputParamsObject) inputParamsObject)
                    .setIsCheckValidityEnabled(Boolean
                        .valueOf(isCheckValidityEnabled));
            }
            else if (qName.equalsIgnoreCase("ForceSplit"))
            {
                if (inputParamsObject instanceof NviInputParamsObject)
                {
                    String isSplitFileEnabled = attributes.getValue("enabled");
                    ((NviInputParamsObject) inputParamsObject)
                        .setSplitFileEnabled(Boolean
                            .valueOf(isSplitFileEnabled));
                }
                else if (inputParamsObject instanceof UkooaInputParamsObject)
                {
                    String isSplitFileEnabled = attributes.getValue("enabled");
                    ((UkooaInputParamsObject) inputParamsObject)
                        .setSplitFileEnabled(Boolean
                            .valueOf(isSplitFileEnabled));
                }
            }
            else if (qName.equalsIgnoreCase("AsciiElement"))
            {
                handleAsciiElement(attributes);
            }
        }
        catch (EABException e)
        {
            e.displayFatalError();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException
    {
        tempVal = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        UTCDateFormat dateSDF =
            new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_ISO);
        if (qName.equalsIgnoreCase("StartDate"))
        {
            if (null != tempVal && !tempVal.isEmpty())
            {
                try
                {
                    inputParamsObject.setStartDate(dateSDF.parse(tempVal));
                }
                catch (ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else if (qName.equalsIgnoreCase("AngularUnit"))
        {
            ((UkooaInputParamsObject) inputParamsObject)
                .setDegreeTheAngularUnit(tempVal != null
                        && tempVal.equals("DEGREES"));
        }
        else if (qName.equalsIgnoreCase("LossOfAccuracy"))
        {
            ((NmeaInputParamsObject) inputParamsObject)
                .setLossOfAccuracy(Integer.valueOf(tempVal));
        }
        else if (qName.equalsIgnoreCase("EndDate"))
        {
            if (null != tempVal && !tempVal.isEmpty())
            {
                try
                {
                    inputParamsObject.setEndDate(dateSDF.parse(tempVal));
                }
                catch (ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else if (qName.equalsIgnoreCase("FirstLine"))
        {
            ((CsvInputParamsObject) inputParamsObject).setFirstLine(Integer
                .valueOf(tempVal));
        }
        else if (qName.equalsIgnoreCase("DelimiterUsed"))
        {
            ((CsvInputParamsObject) inputParamsObject)
                .setDelimiterUsed(tempVal);
        }
        else if (qName.equalsIgnoreCase("LastLine"))
        {
            if (tempVal != null)
            {
                ((CsvInputParamsObject) inputParamsObject).setLastLine(Integer
                    .valueOf(tempVal));
            }
            else
            {
                ((CsvInputParamsObject) inputParamsObject).setLastLine(-1);
            }
        }
        else if (qName.equalsIgnoreCase("ColumnNumber"))
        {
            ((CsvInputParamsObject) inputParamsObject)
                .setCheckValidityColumnNumber(Integer.valueOf(tempVal));
        }
        else if (qName.equalsIgnoreCase("Operator"))
        {
            ((CsvInputParamsObject) inputParamsObject)
                .setCheckValidityOperator(tempVal);
        }
        else if (qName.equalsIgnoreCase("ValidityValue"))
        {
            ((CsvInputParamsObject) inputParamsObject).setValidityValue(Double
                .valueOf(tempVal));
        }
        else if (qName.equalsIgnoreCase("Type"))
        {
            ((AsciiOutputParamsObject) outputParamsObject).setType(tempVal);
        }
        else if (qName.equalsIgnoreCase("AsciiOperator"))
        {
            ((AsciiOutputParamsObject) outputParamsObject).setOperator(tempVal);
        }
        else if (qName.equalsIgnoreCase("WriteSeconds"))
        {
            ((AsciiOutputParamsObject) outputParamsObject)
                .setWithSeconds(Boolean.valueOf(tempVal));
        }
        else if (qName.equalsIgnoreCase("Comments"))
        {
            ((AsciiOutputParamsObject) outputParamsObject).setComments(tempVal);
        }
        else if (qName.equalsIgnoreCase("CampaignId"))
        {
            ((AsciiOutputParamsObject) outputParamsObject)
                .setCampaignId(tempVal);
        }
        else if (qName.equalsIgnoreCase("CampaignTitle"))
        {
            ((AsciiOutputParamsObject) outputParamsObject)
                .setCampaignTitle(tempVal);
        }
        else if (qName.equalsIgnoreCase("BoatName"))
        {
            ((AsciiOutputParamsObject) outputParamsObject).setBoatName(tempVal);
        }
        else if (qName.equalsIgnoreCase("Generation"))
        {
            ((GmlOutputParamsObject) outputParamsObject)
                .setGenerationType(tempVal);
        }
        else if (qName.equalsIgnoreCase("GmlResolution"))
        {
            ((GmlOutputParamsObject) outputParamsObject).setResolution(Integer
                .valueOf(tempVal));
        }
        else if (qName.equalsIgnoreCase("WktResolution"))
        {
            ((WktOutputParamsObject) outputParamsObject).setResolution(Integer
                .valueOf(tempVal));
        }
        else if (qName.equalsIgnoreCase("TimeCorrection"))
        {
            ((AsciiColumnOutputParamsObject) outputParamsObject)
                .setTimeCorrection(Integer.valueOf(tempVal));
        }
        else if (qName.equalsIgnoreCase("Tokenizer"))
        {
            ((AsciiColumnOutputParamsObject) outputParamsObject)
                .setTokenizer(tempVal);
        }
    }

    private void handleInputFileType(Attributes attributes)
    {
        String format = attributes.getValue("format");
        if (InputFilesTypes.CSV_FORMAT.getInputFileType().equals(format))
        {
            inputParamsObject = new CsvInputParamsObject();
            inputParamsObject.setFormat(InputFilesTypes.CSV_FORMAT);
        }
        else if (InputFilesTypes.NETCDF_NVI_FORMAT.getInputFileType().equals(
            format))
        {
            inputParamsObject = new NviInputParamsObject();
            inputParamsObject.setFormat(InputFilesTypes.NETCDF_NVI_FORMAT);
        }
        else if (InputFilesTypes.GENERAL_NETCDF_FORMAT.getInputFileType()
            .equals(format))
        {
            inputParamsObject = new NetCdfInputParamsObject();
            inputParamsObject.setFormat(InputFilesTypes.GENERAL_NETCDF_FORMAT);
        }
        else if (InputFilesTypes.NMEA_183_FORMAT.getInputFileType().equals(
            format))
        {
            inputParamsObject = new NmeaInputParamsObject();
            inputParamsObject.setFormat(InputFilesTypes.NMEA_183_FORMAT);
        }
        else if (InputFilesTypes.UKOOA_P1_90.getInputFileType().equals(format))
        {
            inputParamsObject = new UkooaInputParamsObject();
            inputParamsObject.setFormat(InputFilesTypes.UKOOA_P1_90);
        }

    }

    private void handleOutputFileType(Attributes attributes)
    {
        String format = attributes.getValue("format");
        if (OutputFilesTypes.ASCII_IFREMER_FORMAT.getOutputFileType().equals(
            format))
        {
            outputParamsObject = new AsciiOutputParamsObject();
            outputParamsObject.setFormat(OutputFilesTypes.ASCII_IFREMER_FORMAT);
        }
        else if (OutputFilesTypes.COLUMNS_ASCII_FORMAT.getOutputFileType()
            .equals(format))
        {
            outputParamsObject = new AsciiColumnOutputParamsObject();
            outputParamsObject.setFormat(OutputFilesTypes.COLUMNS_ASCII_FORMAT);
        }
        else if (OutputFilesTypes.WKT_FORMAT.getOutputFileType().equals(format))
        {
            outputParamsObject = new WktOutputParamsObject();
            outputParamsObject.setFormat(OutputFilesTypes.WKT_FORMAT);
        }
        else if (OutputFilesTypes.SHAPE_FORMAT.getOutputFileType().equals(
            format))
        {
            outputParamsObject = new ShapeOutputParamsObject();
            outputParamsObject.setFormat(OutputFilesTypes.SHAPE_FORMAT);
        }
        else if (OutputFilesTypes.GML_FORMAT.getOutputFileType().equals(format))
        {
            outputParamsObject = new GmlOutputParamsObject();
            outputParamsObject.setFormat(OutputFilesTypes.GML_FORMAT);
        }

    }

    private void handleCsvElement(Attributes attributes)
    {
        if (inDateTimeNode)
        {
            String type = attributes.getValue("type");
            if ("Date".equalsIgnoreCase(type))
            {
                String dateFormat = attributes.getValue("format");
                String dateFormatColumnNumber = attributes.getValue("column");
                ((CsvInputParamsObject) inputParamsObject)
                    .setDateFormat(dateFormat);
                ((CsvInputParamsObject) inputParamsObject)
                    .setDateFormatColumnNumber(Integer
                        .valueOf(dateFormatColumnNumber));
            }
            if ("Time".equalsIgnoreCase(type))
            {
                String timeFormat = attributes.getValue("format");
                String timeFormatColumnNumber = attributes.getValue("column");
                ((CsvInputParamsObject) inputParamsObject)
                    .setTimeFormat(timeFormat);
                ((CsvInputParamsObject) inputParamsObject)
                    .setTimeFormatColumnNumber(Integer
                        .valueOf(timeFormatColumnNumber));
            }
            else if ("DateAndTime".equalsIgnoreCase(type))
            {
                String dateTimeFormat = attributes.getValue("format");
                String dateTimeFormatColumnNumber =
                    attributes.getValue("column");
                ((CsvInputParamsObject) inputParamsObject)
                    .setDateTimeFormat(dateTimeFormat);
// ((CsvInputParamsObject) inputParamsObject)
// .setIsJoinDateTimeParamsEnabled(true);
                ((CsvInputParamsObject) inputParamsObject)
                    .setDateTimeColumnNumber(Integer
                        .valueOf(dateTimeFormatColumnNumber));
            }
        }
        else if (inCoordinatesNode)
        {
            String type = attributes.getValue("type");
            if ("Longitude".equalsIgnoreCase(type))
            {
                String longitudeFormat = attributes.getValue("format");
                String longitudeFormatColumnNumber =
                    attributes.getValue("column");
                ((CsvInputParamsObject) inputParamsObject)
                    .setLongitudeFormat(longitudeFormat);
                ((CsvInputParamsObject) inputParamsObject)
                    .setLongitudeFormatColumnNumber(Integer
                        .valueOf(longitudeFormatColumnNumber));
            }
            if ("Latitude".equalsIgnoreCase(type))
            {
                String latitudeFormat = attributes.getValue("format");
                String latitudeFormatColumnNumber =
                    attributes.getValue("column");
                ((CsvInputParamsObject) inputParamsObject)
                    .setLatitudeFormat(latitudeFormat);
                ((CsvInputParamsObject) inputParamsObject)
                    .setLatitudeFormatColumnNumber(Integer
                        .valueOf(latitudeFormatColumnNumber));
            }
        }

    }

    private void handleAsciiElement(Attributes attributes)
    {
        String timeFormat = null;
        String dateFormat = null;
        if (inDateTimeNode)
        {
            String type = attributes.getValue("type");
            if ("Date".equalsIgnoreCase(type))
            {
                dateFormat = attributes.getValue("format");
                String dateFormatColumnNumber = attributes.getValue("column");
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setDateFormat(dateFormat);
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setDateFormatColumnNumber(Integer
                        .valueOf(dateFormatColumnNumber));
            }
            if ("Time".equalsIgnoreCase(type))
            {
                timeFormat = attributes.getValue("format");
                String timeFormatColumnNumber = attributes.getValue("column");
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setTimeFormat(timeFormat);
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setTimeFormatColumnNumber(Integer
                        .valueOf(timeFormatColumnNumber));
            }
            if ("DateAndTime".equalsIgnoreCase(type))
            {
                String dateTimeFormat = attributes.getValue("format");
                String dateTimeFormatColumnNumber =
                    attributes.getValue("column");
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setDateTimeFormat(dateTimeFormat);
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setDateTimeFormatColumnNumber(Integer
                        .valueOf(dateTimeFormatColumnNumber));
            }
        }
        else if (inCoordinatesNode)
        {
            String type = attributes.getValue("type");
            if ("Longitude".equalsIgnoreCase(type))
            {
                String longitudeFormat = attributes.getValue("format");
                String longitudeFormatColumnNumber =
                    attributes.getValue("column");
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setLongitudeFormat(longitudeFormat);
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setLongitudeFormatColumnNumber(Integer
                        .valueOf(longitudeFormatColumnNumber));
            }
            if ("Latitude".equalsIgnoreCase(type))
            {
                String latitudeFormat = attributes.getValue("format");
                String latitudeFormatColumnNumber =
                    attributes.getValue("column");
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setLatitudeFormat(latitudeFormat);
                ((AsciiColumnOutputParamsObject) outputParamsObject)
                    .setLatitudeFormatColumnNumber(Integer
                        .valueOf(latitudeFormatColumnNumber));
            }
        }

    }

    private void handleNviElement(Attributes attributes) throws EABException
    {
        String strtDate = attributes.getValue("startDate");
        String endDate = attributes.getValue("endDate");

        if (strtDate != null && !strtDate.isEmpty() && endDate != null
                && !endDate.isEmpty())
        {

            try
            {
                ((NviInputParamsObject) inputParamsObject).getDateSegmentList()
                    .add(
                        new DateSegment(Globals.PARAMETER_FILE_ISO_DATE_FORMAT
                            .parse(strtDate),
                            Globals.PARAMETER_FILE_ISO_DATE_FORMAT
                                .parse(endDate)));
            }
            catch (ParseException e)
            {
                throw new EABException(ExceptionCode.PARSING_ERROR, e);
            }
        }
        else
        {
            throw new EABException(ExceptionCode.ARGUMENT_NULL_OR_EMPTY);
        }
    }

    private void handleUKOOAElement(Attributes attributes) throws EABException
    {
        String strtDate = attributes.getValue("startDate");
        String endDate = attributes.getValue("endDate");

        if (strtDate != null && !strtDate.isEmpty() && endDate != null
                && !endDate.isEmpty())
        {

            try
            {
                ((UkooaInputParamsObject) inputParamsObject)
                    .getDateSegmentList().add(
                        new DateSegment(Globals.PARAMETER_FILE_ISO_DATE_FORMAT
                            .parse(strtDate),
                            Globals.PARAMETER_FILE_ISO_DATE_FORMAT
                                .parse(endDate)));
            }
            catch (ParseException e)
            {
                throw new EABException(ExceptionCode.PARSING_ERROR, e);
            }
        }
        else
        {
            throw new EABException(ExceptionCode.ARGUMENT_NULL_OR_EMPTY);
        }
    }

    private void handleNetCdfElement(Attributes attributes)
    {
        if (inDateTimeNode)
        {
            String type = attributes.getValue("type");
            if ("Date".equalsIgnoreCase(type))
            {
                String dateFormatColumnNumber = attributes.getValue("column");
                ((NetCdfInputParamsObject) inputParamsObject)
                    .setDateVariableName(dateFormatColumnNumber);
                ((NetCdfInputParamsObject) inputParamsObject)
                    .setIsJoinDateTimeParamsEnabled(false);
            }
            if ("Time".equalsIgnoreCase(type))
            {
                String timeFormatColumnNumber = attributes.getValue("column");
                ((NetCdfInputParamsObject) inputParamsObject)
                    .setTimeVariableName(timeFormatColumnNumber);
                ((NetCdfInputParamsObject) inputParamsObject)
                    .setIsJoinDateTimeParamsEnabled(false);
            }
            if ("DateAndTime".equalsIgnoreCase(type))
            {
                String dateTimeFormatColumnName = attributes.getValue("column");
                ((NetCdfInputParamsObject) inputParamsObject)
                    .setDateTimeVariableName(dateTimeFormatColumnName);
                ((NetCdfInputParamsObject) inputParamsObject)
                    .setIsJoinDateTimeParamsEnabled(true);
            }
        }
        else if (inCoordinatesNode)
        {
            String type = attributes.getValue("type");
            if ("Longitude".equalsIgnoreCase(type))
            {
                String longitudeFormatColumnNumber =
                    attributes.getValue("column");
                ((NetCdfInputParamsObject) inputParamsObject)
                    .setLongitudeVariableName(longitudeFormatColumnNumber);
            }
            if ("Latitude".equalsIgnoreCase(type))
            {
                String latitudeFormatColumnNumber =
                    attributes.getValue("column");
                ((NetCdfInputParamsObject) inputParamsObject)
                    .setLatitudeVariableName(latitudeFormatColumnNumber);
            }
        }

    }

    /**
     * Getter sur outputParamsObject.
     * 
     * @return the outputParamsObject
     */
    public OutputParamsObject getOutputParamsObject()
    {
        return outputParamsObject;
    }

    public InputParamsObject getInputParamsObject()
    {
        return inputParamsObject;
    }

}
