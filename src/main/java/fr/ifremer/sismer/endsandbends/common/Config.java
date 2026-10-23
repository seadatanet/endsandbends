/* ============================================================================
 * Nom du fichier	: Config.java
 * Auteur			: Altran
 * Date				: Jun 7, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

import java.awt.Color;
import java.text.Format;
import java.text.MessageFormat;

import fr.ifremer.sismer.endsandbends.common.format.LatitudeFormat;
import fr.ifremer.sismer.endsandbends.common.format.LongitudeFormat;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
@SuppressWarnings("nls")
public abstract class Config extends AProperties
{
    static
    {
        BUNDLE = Messages.CONFIG;
        URL = "fr.ifremer.sismer.endsandbends.resources.config";
    }

    public static final String CR = "\n";

    public static final String GGA_FRAME = getString("GGA_FRAME");

    public static final String RMC_FRAME = getString("RMC_FRAME");

    public static final UTCDateFormat GGA_TIME_FORMAT =
        getDateFormat("GGA_TIME_FORMAT");

    public static final UTCDateFormat RMC_DATE_FORMAT =
        getDateFormat("RMC_DATE_FORMAT");

    public static final MessageFormat WRONG_NUMBER_OF_ELEMENTS =
        getMessageFormat("WRONG_NUMBER_OF_ELEMENTS");

    public static final Format P50_LINE_FORMAT =
        getMessageFormat("P50_LINE_FORMAT");

    public static final LatitudeFormat PATHVIEWER_LATITUDE_FORMAT =
        getLatitudeFormat("PATHVIEWER_LATITUDE_FORMAT");

    public static final LongitudeFormat PATHVIEWER_LONGITUDE_FORMAT =
        getLongitudeFormat("PATHVIEWER_LONGITUDE_FORMAT");

    public static final Color PATHVIEWER_HIGHLIGHTED_SEGMENT_COLOR =
        getColor("PATHVIEWER_HIGHLIGHTED_SEGMENT_COLOR");

    public static final int PATHVIEWER_HIGHLIGHTED_SEGMENT_WIDTH =
        getInt("PATHVIEWER_HIGHLIGHTED_SEGMENT_WIDTH");

    public static final int PATHVIEWER_MIN_FREE_SPACE =
        getInt("PATHVIEWER_MIN_FREE_SPACE");

    public static final int PATHVIEWER_OFFSET_X = getInt("PATHVIEWER_OFFSET_X");

    public static final int PATHVIEWER_OFFSET_Y = getInt("PATHVIEWER_OFFSET_Y");

}
