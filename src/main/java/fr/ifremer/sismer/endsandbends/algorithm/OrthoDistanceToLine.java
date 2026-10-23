package fr.ifremer.sismer.endsandbends.algorithm;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Calcul de la distance orthogonale d'un point � une
 *         droite D'apr�s une formule trouv�e sur :
 *         http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html
 *         Soit la droite d�finie par P1(x1,y1) et P2(x2,y2), la distance du
 *         point P(x,y) orthognalement � cette droite est d = (-(y2-y1)*x +
 *         (x2-x1)*y + x1*y2 - x2*y1) / sqrt((x2-x1)^2 + (y2 - y1)^2)
 */
public class OrthoDistanceToLine extends DistanceToLine
{

    // Donn�es :
    private double _dxl = 0.0; // (x2-x1)
    private double _dyl = 0.0; // (y2-y1)
    private double _len = 0.0; // sqrt((x2-x1)^2 + (y2 - y1)^2)
    private double _cross = 0.0; // x1*y2 - x2*y1

    /**
     * Constructeur
     */
    public OrthoDistanceToLine()
    {
        _name = Messages.getString(Messages.MESSAGES, "dist_ortho_name"); //$NON-NLS-1$
        _description = Messages.getString(Messages.MESSAGES, "dist_ortho_desc"); //$NON-NLS-1$
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine#defineLine(double,
     *      double, double, double)
     */
    @Override
    public void defineLine(double x1, double y1, double x2, double y2)
    {
        _dxl = x2 - x1;
        _dyl = y2 - y1;
        _len = StrictMath.sqrt(_dxl * _dxl + _dyl * _dyl);
        _cross = x1 * y2 - x2 * y1;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine#getDistance(double,
     *      double)
     */
    @Override
    public double getDistance(double x, double y)
    {
        double dy = (_dxl * y - _dyl * x + _cross) / _len;
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
