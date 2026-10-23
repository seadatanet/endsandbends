/* ============================================================================
 * Nom du fichier	: IAsciiWriter.java
 * Auteur			: Altran
 * Date				: Jun 2, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.output;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public interface IEndsAndBendsWriter
{
    void writeFile(NavPathPoints path, int optimPoints) throws EABException;

}
