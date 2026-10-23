/* ============================================================================
 * Nom du fichier	: UTCDateFormat.java
 * Auteur			: Altran
 * Date				: 4 oct. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * SimpleDateFormat au fuseau UTC
 * 
 * @author htaniou
 */
public class UTCDateFormat extends SimpleDateFormat
{
    /**
     * 
     */
    private static final long serialVersionUID = 6664243799998879537L;

    /**
     * Constructeur
     */
    public UTCDateFormat()
    {
        super();
        super.setTimeZone(TimeZone.getTimeZone(Globals.UTC));
    }

    /**
     * Constructeur
     * 
     * @param dateFormat
     */
    public UTCDateFormat(String dateFormat)
    {
        super(dateFormat);
        super.setTimeZone(TimeZone.getTimeZone(Globals.UTC));
    }

}
