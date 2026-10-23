package fr.ifremer.sismer.endsandbends.algorithm;

import java.awt.geom.Point2D;

import fr.ifremer.sismer.endsandbends.common.Complex;
import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Projection de GaussKrueger. Reprise du code
 *         Ends&Bends en VisualBasic, m�thode ellgau
 */
public class GaussKruegerProjection extends GeoProjection
{

    // Limites de validit� de la projection :
    // distLimit : distance angulaire (en degr�s) au del� de laquelle la
    // projection n'est pas valide
    private static double __distLimit = 11.0;
    // distCut : distance angulaire (en degr�) � laquelle un trac� est
    // coup� s'il est sup�rieure � distLimit
    private static double __distCut = 10.0;

    private boolean distLimitExceeded = false;

    /**
     * Getter sur distLimitExceeded.
     * 
     * @return the distLimitExceeded
     */
    public boolean isDistLimitExceeded()
    {
        return distLimitExceeded;
    }

    /**
     * Changement du param�tre de distance de coupure
     * 
     * @param dist
     *            distance
     */
    public static void setCuttingDistance(double dist)
    {
        __distCut = dist;
    }

    /**
     * Changement du param�tre de distance limite
     * 
     * @param dist
     *            distance
     */
    public static void setLimitDistance(double dist)
    {
        __distLimit = dist;
    }

    /**
     * Returns the _distCut.
     * 
     * @return double
     */
    public static double getCuttingDistance()
    {
        return __distCut;
    }

    /**
     * Returns the _distLimit.
     * 
     * @return double
     */
    public static double getLimitDistance()
    {
        return __distLimit;
    }

    // Constantes d�finissant l'ellipso�de :
    private static double g_a_bessel = 6377397.155;
    private static double g_f_bessel = 1.0 / 299.1528129;
// private static double g_b_bessel = g_a_bessel * (1.0 - g_f_bessel);
    private static double g_e2_bessel = g_f_bessel * (2.0 - g_f_bessel);

    // Autres constantes (passage de degr� � radian) :
    private static double g_rho = 57.2957795130822; // 180.0/PI;
// private static double g_rad = 1.0 / g_rho;

    // M�ridien central :
    private double _longCentral = 1000.0; // d�termin� automatiquement...

    /**
     * Getter sur _longCentral.
     * 
     * @return the longCentral
     */
    public double getLongCentral()
    {
        return _longCentral;
    }

    /**
     * Constructeur
     */
    public GaussKruegerProjection(boolean isGlobal)
    {
        _name = Messages.getString(Messages.MESSAGES, "proj_gk_name"); //$NON-NLS-1$
        _description = Messages.getString(Messages.MESSAGES, "proj_gk_desc"); //$NON-NLS-1$
        _globalProjection = isGlobal;
    }

    /**
     * Constructeur
     */
    public GaussKruegerProjection()
    {
        this(false);
    }

    /**
     * Transformation d'un angle en d�gr�s dans l'interval [0, 360[
     * 
     * @param angle
     * @return double
     */
    private static double mod360(double angle)
    {
        while (angle < 0)
            angle += 360.0;
        while (angle >= 360.0)
            angle -= 360.0;
        return angle;
    }

    /**
     * Transformation d'un angle en d�gr�s dans l'interval ]-180, 180]
     * 
     * @param angle
     * @return double
     */
    private static double mod180(double angle)
    {
        while (angle <= -180.0)
            angle += 360.0;
        while (angle > 180.0)
            angle -= 360.0;
        return angle;
    }

    /**
     * Calcul de la distance angulaire entre deux angles, exprim�es en degr�s.
     * 
     * @param angle1
     *            premier angle (longitude)
     * @param angle2
     *            deuxi�me angle (longitude)
     * @return double, angle d'�cart entre les deux angles, c'est � dire l'angle
     *         qu'il faut ajouter � angle1 pour obtenir angle2... L'angle
     *         renvoy� est toujours compris entre ]-180, 180] degr�s.
     */
    private static double angleDist(double angle1, double angle2)
    {
        double dist = angle2 - angle1;
        if (dist > 180.0)
            dist = dist - 360.0;
        if (dist <= -180.0)
            dist = dist + 360.0;
        return dist;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.GeoProjection#canProject(int,
     *      double[], double[])
     */
    @Override
    public boolean canProject(int indexStart, double[] latitudes,
            double[] longitudes)
    {
        // V�rification de la taille des tableaux :
        if ((latitudes.length != longitudes.length)
                || (latitudes.length <= indexStart))
            return false;

        // Tester si l'�cart est inf�rieur � __distLimit :
        double longMin = longitudes[indexStart];
        double longMax = longitudes[indexStart];
        double longitude;
        double dist = 0.0;
        for (int i = indexStart + 1; i < longitudes.length; i++)
        {
            // R�cup�rer la longitude :
            longitude = longitudes[i];

            // G�rer l'�cart par rapport au min :
            dist = angleDist(longMin, longitude);
            if (StrictMath.abs(dist) > __distLimit)
                return false;
            if (dist < 0) // il est � gauche du min, donc un nouveau min
                longMin += dist;

            // G�rer l'�cart par rapport au max :
            dist = angleDist(longMax, longitude);
            if (StrictMath.abs(dist) > __distLimit)
                return false;
            if (dist > 0) // il est � droite du max, donc un nouveau max
                longMax += dist;
        }

        // Retour OK :
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
        // V�rification de la taille des tableaux :
        if ((latitudes.length != longitudes.length)
                || (latitudes.length <= indexStart))
            return 0;

        // Tester si l'�cart est inf�rieur � __distLimit :
        double longMin = longitudes[indexStart];
        double longMax = longitudes[indexStart];
        double longitude;
        double dist = 0.0;
        for (int i = indexStart + 1; i < longitudes.length; i++)
        {
            // R�cup�rer la longitude :
            longitude = longitudes[i];

            // G�rer l'�cart par rapport au min :
            dist = angleDist(longMin, longitude);
            if (StrictMath.abs(dist) > __distCut)
                return i;
            if (dist < 0) // il est � gauche du min, donc un nouveau min
                longMin += dist;

            // G�rer l'�cart par rapport au max :
            dist = angleDist(longMax, longitude);
            if (StrictMath.abs(dist) > __distCut)
                return i;
            if (dist > 0) // il est � droite du max, donc un nouveau max
                longMax += dist;
        }

        // Retour maxi :
        return longitudes.length;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.GeoProjection#initialize(double[],
     *      double[])
     */
    @Override
    public boolean initialize(double[] latitudes, double[] longitudes)
    {
        distLimitExceeded = false;
        // V�rification de la taille des tableaux :
        if ((latitudes.length != longitudes.length) || (latitudes.length <= 0))
            return false;

        // Il s'agit de calculer un m�ridien moyen, multiple de 3, qui serve
        // de m�ridien central dans la projection de Gauss-Krueger.
        // Pour calculer le m�ridien central on va calculer une moyenne des
        // longitude, puis on va refaire un second parcours pour calculer
        // la distance min entre le m�ridien choisi et les points, on entre
        // le m�ridien choisi + 180 et les points...

        // Premier parcours pour trouver une moyenne :
        double long360, long180;
        double moy360 = 0.0;
        double moy180 = 0.0;
        for (int i = 0; i < longitudes.length; i++)
        {
            long360 = mod360(longitudes[i]);
            moy360 += long360;
            long180 = mod180(longitudes[i]);
            moy180 += long180;
        }
        moy360 = moy360 / longitudes.length;
        moy180 = moy180 / longitudes.length;

        // Deuxi�me parcours, pour calculer les distances :
        double dist180 = 0.0;
        double dist360 = 0.0;
        for (int i = 0; i < longitudes.length; i++)
        {
            long360 = mod360(longitudes[i]);
            dist360 += StrictMath.abs(long360 - moy360);
            long180 = mod180(longitudes[i]);
            dist180 += StrictMath.abs(long180 - moy180);
        }

        // Convertir en 360 si c'est le 180 qui est le plus pr�s...
        if (dist180 < dist360)
            moy360 = mod360(moy180);

        // Le m�ridien central est moy360 en multiple de 3...
        _longCentral = StrictMath.rint(moy360 / 3.0) * 3.0;

        // OK, projection valide.
        return true;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.algorithm.GeoProjection#project(double,
     *      double)
     */
    @Override
    public Point2D project(double latitude, double longitude)
            throws ProjectionException
    {

        // // Pr�paration des donn�es :

        // R�cup�ration des donn�es de l'ellipso�de :
        double RHO = g_rho;
        double A = g_a_bessel;
        double f = g_f_bessel;
        double e2 = g_e2_bessel;

        // Reprise des param�tres de l'ancienne m�thode :
        double rfi = latitude;
        double rla = longitude;
        // rla0 : m�ridien central utilis� dans la projection
        double rla0 = _longCentral;

        // Correction de la longitude :
        double dla = rla;
        if (dla < 0.0)
            dla = dla + 360.0;
        if (dla > 360.0)
            dla = dla - 360.0;

        // Correction du m�ridien central :
        double DLA00 = rla0;
        double kz = 0.0;
        if (DLA00 < 1000.0)
        {
            if (DLA00 < 0.0)
                DLA00 = DLA00 + 360.0;
            if (DLA00 > 360.0)
                DLA00 = DLA00 - 360.0;
            kz = StrictMath.rint(DLA00 / 3.0);
            if (StrictMath.abs(3.0 * kz - DLA00) > 0.000000005)
            {
                throw new ProjectionException(Messages.getString(
                    Messages.MESSAGES, "error_gauskrueger_zentralmeridian"));
            }
        }
        else
        {
            kz = StrictMath.rint(dla / 3.0);
            DLA00 = 3.0 * kz;
        }
        kz = kz * 1000000 + 500000;

        // Correction de la latitude :
        double DPHI1 = rfi / RHO;
        double DLA1 = dla - DLA00;
        if (DLA1 > 300.0)
            DLA1 = DLA1 - 360.0;
        if (DLA1 < -300.0)
            DLA1 = DLA1 + 360.0;
        if (StrictMath.abs(DLA1) >= __distLimit)
        {
            distLimitExceeded = true;
// throw new ProjectionException(Messages.getString(Messages.MESSAGES,
//                "error_gauskrueger_5grad")); //$NON-NLS-1$
        }
        DLA1 = DLA1 / RHO;

        // // Transformation :

        double SINB = StrictMath.sin(DPHI1);
        double dn = A / StrictMath.sqrt(1.0 - e2 * SINB * SINB);
        double ES2 = e2 / (1.0 - e2);
        double COSB = StrictMath.cos(DPHI1);
        double COSB2 = COSB * COSB;
        double COSB3 = COSB2 * COSB;
        double COSB4 = COSB3 * COSB;
        double COSB5 = COSB4 * COSB;
        double COSB6 = COSB5 * COSB;
        double COSB7 = COSB6 * COSB;
        double ETA2 = ES2 * COSB2;
        double T = SINB / COSB;
        double t2 = T * T;
        double T4 = t2 * t2;

        Complex XEL = new Complex(0.0, DLA1);
        Complex XEL2 = XEL.multiply(XEL);
        Complex XEL3 = XEL2.multiply(XEL);
        Complex XEL4 = XEL3.multiply(XEL);
        Complex XEL5 = XEL4.multiply(XEL);
        Complex XEL6 = XEL5.multiply(XEL);
        Complex XEL7 = XEL6.multiply(XEL);

        double A1 = dn * COSB;
        double A2 = -dn / 2.0 * COSB2 * T;
        double a3 = -dn / 6.0 * COSB3 * (1.0 - t2 + ETA2);
        double a4 =
            dn / 24.0 * COSB4 * T * (5.0 - t2 + 9.0 * ETA2 + 4.0 * ETA2 * ETA2);
        double a5 =
            dn / 120.0 * COSB5
                    * (5.0 - 18.0 * t2 + T4 + ETA2 * (14.0 - 58.0 * t2));
        double a6 =
            -dn / 720.0 * COSB6 * T
                    * (61.0 - 58.0 * t2 + T4 + ETA2 * (270.0 - 330.0 * t2));
        double A7 =
            -dn / 5040.0 * COSB7 * (61.0 - 479.0 * t2 + 179.0 * T4 - T4 * t2);

        // Werte des Realteils ergeben Hochwert, Imagin�rteil Rechtswert
        Complex XEB = new Complex();
        XEB.setReal(A1 * XEL.real() + A2 * XEL2.real() + a3 * XEL3.real() + a4
                * XEL4.real() + a5 * XEL5.real() + a6 * XEL6.real() + A7
                * XEL7.real());
        XEB.setImag(A1 * XEL.imag() + A2 * XEL2.imag() + a3 * XEL3.imag() + a4
                * XEL4.imag() + a5 * XEL5.imag() + a6 * XEL6.imag() + A7
                * XEL7.imag());

        // COMPUTE LENGTH OF THE ARC OF MERIDIAN
        double b = A - A * f;
        dn = (A - b) / (A + b);
        double DN2 = dn * dn;
        double DN3 = DN2 * dn;
        double DN4 = DN3 * dn;

        double B0 = 1.0 + DN2 / 4.0 + DN4 / 64.0;
        double B2 = (dn - DN3 / 8.0) * 3.0 / 2.0;
        double B4 = (DN2 - DN4 / 4.0) * 15.0 / 16.0;
        double B6 = DN3 * 35.0 / 48.0;
        double B8 = DN4 * 315.0 / 512.0;
        double G1 =
            B0 * DPHI1 - B2 * StrictMath.sin(2.0 * DPHI1) + B4
                    * StrictMath.sin(4.0 * DPHI1);
        double G2 =
            B6 * StrictMath.sin(6.0 * DPHI1) + B8 * StrictMath.sin(8.0 * DPHI1);
        double g = A / (1.0 + dn) * (G1 - G2);

        // // Extraction et v�rification des r�sultats :

        double H = g + XEB.real();
        // Le test est supprim� puisque la limite reste param�trable...
        /*		if (StrictMath.abs(XEB.imag()) >= 500000.0)
        		{
        			kz = 999;
        			XEB.setImag(0.0);
        			throw new ProjectionException(StringTable.get("error_gauskrueger_500km"));
        		}
        */
        double R = XEB.imag() + kz;

        // Cr�er un nouveau point et le renvoyer:
        Point2D result = new Point2D.Double();
        result.setLocation(R, H);
        return result;
    }

    /**
     * Setter sur distLimitExceeded.
     * 
     * @param distLimitExceeded
     *            the distLimitExceeded to set
     */
    public void setDistLimitExceeded(boolean distLimitExceeded)
    {
        this.distLimitExceeded = distLimitExceeded;
    }

}
