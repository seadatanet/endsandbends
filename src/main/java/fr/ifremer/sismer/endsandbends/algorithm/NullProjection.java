package fr.ifremer.sismer.endsandbends.algorithm;

import java.awt.geom.Point2D;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Projection virtuelle qui renvoie directement les
 *         coordonn�es lat/long
 */
public class NullProjection extends GeoProjection
{
    /**
     * Constructeur
     */
    public NullProjection()
    {
        _name = Messages.getString(Messages.MESSAGES, "proj_null_name"); //$NON-NLS-1$
        _description = Messages.getString(Messages.MESSAGES, "proj_null_desc"); //$NON-NLS-1$
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.GeoProjection#canProject(int,
     *      double[], double[])
     */
    @Override
    public boolean canProject(int indexStart, double[] latitudes,
            double[] longitudes)
    {
        return true;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.GeoProjection#getProjectableIndex(int,
     *      double[], double[])
     */
    @Override
    public int getProjectableIndex(int indexStart, double[] latitudes,
            double[] longitudes)
    {
        if (longitudes == null)
            return 0;
        return longitudes.length;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.GeoProjection#initialize(double[],
     *      double[])
     */
    @Override
    public boolean initialize(double[] latitudes, double[] longitudes)
    {
        return true;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.GeoProjection#project(double,
     *      double)
     */
    @Override
    public Point2D project(double latitude, double longitude)
    {
        return new Point2D.Double(latitude * 1000.0, longitude * 1000.0);
    }

}
