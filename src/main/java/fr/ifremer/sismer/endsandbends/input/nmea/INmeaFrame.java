/* ============================================================================
 * Nom du fichier	: INmeaTrame.java
 * Auteur			: Altran
 * Date				: Jun 7, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input.nmea;

import fr.ifremer.sismer.endsandbends.exception.EABException;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public interface INmeaFrame
{
    /**
     * Initialisation d'une trame � partir de ses �l�ments
     * 
     * @throws EABException
     */
    void initialize() throws EABException;

    int getMinElementNumber();
}
