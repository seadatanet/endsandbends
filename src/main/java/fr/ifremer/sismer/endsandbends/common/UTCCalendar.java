/* ============================================================================
 * Nom du fichier	: UTCCalendar.java
 * Auteur			: Altran
 * Date				: 4 oct. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

/**
 * Calendrier au fuseau UTC
 * 
 * @author htaniou
 */
public class UTCCalendar extends GregorianCalendar
{
    /**
     * 
     */
    private static final long serialVersionUID = -2358444125749119074L;

    /**
     * Constructeur
     */
    public UTCCalendar()
    {
        super(new SimpleTimeZone(0, Globals.UTC));
    }
}
