package fr.ifremer.sismer.endsandbends.algorithm;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Calcul de la distance � un point verticalement...
 */
public class VerticalDistanceToLine extends DistanceToLine
{
    // Param�tres :
    private static double _tangLimit = 1.0;
    // !! ATTENTION !! Si x varie lentement �a arrive tr�s souvent, donc
    // ce n'est pas n�cessaire, ou peut mettre 0.0 � la place. Mais pour
    // l'instant on reste le plus proche possible de l'ancien code.

    // Donn�es :
    private double _x1 = 0.0;
    private double _y1 = 0.0;
    private double _x2 = 0.0;
    private double _y2 = 0.0;
    private double _tang = 0.0;

    /**
     * Constructeur
     */
    public VerticalDistanceToLine()
    {
        _name = Messages.getString(Messages.MESSAGES, "dist_vert_name"); //$NON-NLS-1$
        _description = Messages.getString(Messages.MESSAGES, "dist_vert_desc"); //$NON-NLS-1$
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine#defineLine(double,
     *      double, double, double)
     */
    @Override
    public void defineLine(double x1, double y1, double x2, double y2)
    {
        _x1 = x1;
        _y1 = y1;
        _x2 = x2;
        _y2 = y2;
        _tang = 0.0;
        if (StrictMath.abs(x2 - x1) > _tangLimit)
            _tang = (y2 - y1) / (x2 - x1);
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine#getDistance(double,
     *      double)
     */
    @Override
    public double getDistance(double x, double y)
    {
        double dy = 0.0;
        if (StrictMath.abs(_x2 - _x1) > _tangLimit)
        {
            double yt = _y1 + _tang * (x - _x1);
            dy = (y - yt);
        }
        else
            dy = (x - _x2);
        return dy;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine#getSquareDistance(double,
     *      double)
     */
    @Override
    public double getSquareDistance(double x, double y)
    {
        double dy = getDistance(x, y);
        return dy * dy;
    }

}
