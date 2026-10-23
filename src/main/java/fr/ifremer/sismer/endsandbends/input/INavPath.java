/* ============================================================================
 * Nom du fichier	: INavPath.java
 * Auteur			: Altran
 * Date				: Jun 2, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import java.util.Date;

import fr.ifremer.sismer.endsandbends.exception.EABException;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public interface INavPath
{
    double getMaxLatitude();

    double getMaxLongitude();

    double getMinLatitude();

    double getMinLongitude();

    long getTimeEnded();

    long getTimeStarted();

    /**
     * Chargement d'un fichier NetCDF .nvi contenant les points de navigation
     * 
     * @param filepath
     *            chemin d'acc�s au fichier
     * @return boolean, true si le chargement s'est bien fait, false sinon
     * @throws EABException
     */
    boolean loadNavFile(String filePath) throws EABException;

    NavPathPoints[] splitIntoContinuousPaths();

    boolean checkGapTypeCompatibilityWithFile();

    void filterDates(Date startDate, Date endDate) throws EABException;

    public double[] getLatitudes();

    public double[] getLongitudes();

    /**
     * 
     */
    // pour evol mail19/09/2012 (non confirm�e)
//    void setGapToDistance();

}
