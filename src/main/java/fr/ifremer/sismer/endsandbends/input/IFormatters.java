/* ============================================================================
 * Nom du fichier	: IFormatters.java
 * Auteur			: Altran
 * Date				: 11 juin 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.common.format.LatitudeFormat;
import fr.ifremer.sismer.endsandbends.common.format.LongitudeFormat;

/**
 * Description: TODO
 * 
 * @author Pablo
 */
public interface IFormatters
{
    UTCDateFormat getDateTimeFormat();

    UTCDateFormat getDateFormat();

    UTCDateFormat getTimeFormat();

    LatitudeFormat getLatitudeFormat();

    LongitudeFormat getLongitudeFormat();

}
