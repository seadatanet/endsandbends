/* ============================================================================
 * Nom du fichier	: Globals.java
 * Auteur			: Altran
 * Date				: Jun 10, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

import java.text.Format;
import java.text.MessageFormat;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
@SuppressWarnings("nls")
public abstract class Globals extends AProperties
{
    static
    {
        BUNDLE = Messages.MESSAGES;
        URL = "fr.ifremer.sismer.endsandbends.resources.messages";
    }

    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String POINT = ".";
    public static final String SEMICOLON = ";";

    public static final String NO = "N";
    public static final String YES = "Y";

    public static final String PLUS = "+";
    public static final String MINUS = "-";

    public static final String NORTH = "N";
    public static final String SOUTH = "S";
    public static final String EAST = "E";
    public static final String WEST = "W";

    public static final String UTC = "UTC";

    public static final String XML = "XML";

    public static final String UTF_8 = "UTF-8";

    public static final String errorParseCoordN =
        "Invalid character, expecting 'N' or 'S'";

    public static final String errorParseCoordW =
        "Invalid character, expecting 'E' or 'W'";

    public static final String errorParseSign =
        "Invalid character, expecting '+' or '-'";

    public static final MessageFormat errorParseChar =
        new MessageFormat(
            "Invalid character, expecting: \"{0}\", found: \"{1}\"");

    public static final MessageFormat errorParseDegres =
        new MessageFormat("Expected format for degrees: {0}");

    public static final MessageFormat errorParseMinutes =
        new MessageFormat("Expected format for minutes: {0}");

    public static final MessageFormat errorParseSeconds =
        new MessageFormat("Expected format for seconds: {0}");

    public static final MessageFormat INVALID_COLUMN_NUMBER =
        new MessageFormat(Messages.getString(Messages.MESSAGES,
            "INVALID_COLUMN_NUMBER"));

    public static final MessageFormat INVALID_CHARACTER =
        new MessageFormat(Messages.getString(Messages.MESSAGES,
            "INVALID_CHARACTER"));

    public static final MessageFormat ERROR_POSITION =
        new MessageFormat(Messages.getString(Messages.MESSAGES,
            "ERROR_POSITION"));

    public static final String UNCAUGHT_EXCEPTION =
        Messages.getString(Messages.MESSAGES, "UNCAUGHT_EXCEPTION");

    public static final String MAIN_TITLE =
        Messages.getString(Messages.MESSAGES, "main_title");

    public static final Format MAIN_TITLE_INPUT =
        new MessageFormat(Messages.getString(Messages.MESSAGES,
            "MAIN_TITLE_INPUT"));

    public static final Format OPTIONS_SUMMARY_TEMPLATE =
        getMessageFormat("OPTIONS_SUMMARY_TEMPLATE");

    public static final Format FILTER_NO_POINT_FOUND =
        getMessageFormat("FILTER_NO_POINT_FOUND");

    public static final Format SIMPLEDATEFORMATMASK =
        getDateFormat("SIMPLEDATEFORMATMASK");

    public static final Format SIMPLETIMEFORMATMASK =
        getDateFormat("SIMPLETIMEFORMATMASK");

    public static final Format PROPERTIES_CONTENT_ERROR_MESSAGE =
        getMessageFormat("PROPERTIES_CONTENT_ERROR_MESSAGE");

    public static final Format NETCDF_GEN_VARIABLE_NOT_FOUND =
        getMessageFormat("NETCDF_GEN_VARIABLE_NOT_FOUND");

    public static final UTCDateFormat INPUT_DIALOG_SEGMENT_DATE_FORMAT =
        getDateFormat("INPUT_DIALOG_SEGMENT_DATE_FORMAT");

    public static final UTCDateFormat PARAMETER_FILE_ISO_DATE_FORMAT =
        getDateFormat("PARAMETER_FILE_ISO_DATE_FORMAT");

    public static final String NO_POINTS_FOUND = getString("NO_POINTS_FOUND");

    public static final Format CSV_LINE_NUMBER =
        getMessageFormat("CSV_LINE_NUMBER");

    public static final Format NVI_POINT_NUMBER =
        getMessageFormat("NVI_POINT_NUMBER");

    public static final String MODEL_CAUSE_EXACT_TRACE =
        getString("MODEL_CAUSE_EXACT_TRACE");

    public static final Format MODEL_CAUSE_ERROR =
        getMessageFormat("MODEL_CAUSE_ERROR");

    public static final Format MODEL_CAUSE_POINT_NUMBER =
        getMessageFormat("MODEL_CAUSE_POINT_NUMBER");

    public static final String MODEL_CAUSE_CUSTOM =
        getString("MODEL_CAUSE_CUSTOM");

    public static final String MODEL_CAUSE_DIST_OPTIM =
        getString("MODEL_CAUSE_DIST_OPTIM");

    public static final String MODEL_CAUSE_ERROR_OPTIM =
        getString("MODEL_CAUSE_ERROR_OPTIM");

    public static final Format PATH_VIEWER_SCALE =
        getMessageFormat("PATH_VIEWER_SCALE");

    public static final String INCONSISTENT_XML_FILE =
        getString("INCONSISTENT_XML_FILE");

    public static final String LOAD_PARAMETER_FILE_TITLE =
        getString("LOAD_PARAMETER_FILE_TITLE");

    public static final String LOAD_PARAMETER_FILE_QUESTION =
        getString("LOAD_PARAMETER_FILE_QUESTION");

    public static final String SAVE_PARAMETER_FILE_TITLE =
        getString("SAVE_PARAMETER_FILE_TITLE");

    public static final String SAVE_PARAMETER_FILE_QUESTION =
        getString("SAVE_PARAMETER_FILE_QUESTION");

    public static final String WRONG_FILEPATH = getString("WRONG_FILEPATH");
}
