package fr.ifremer.sismer.endsandbends.algorithm;

import java.awt.geom.Point2D;

/**
 * @author ATLANTIDE - ABT Interface qui d�fini une projection
 */
public abstract class GeoProjection
{
    // indique s'il s'agit d'une projection globale (pour representer TOUS les
    // segments)
    // dans ce cas, on ne la recalculera pas � chaque fois
    protected boolean _globalProjection;

    /**
     * Getter sur globalProjection.
     * 
     * @return the globalProjection
     */
    public boolean isGlobalProjection()
    {
        return _globalProjection;
    }

    /**
     * Setter sur globalProjection.
     * 
     * @param globalProjection
     *            the globalProjection to set
     */
    public void setGlobalProjection(boolean globalProjection)
    {
        this._globalProjection = globalProjection;
    }

    /**
     * Teste si la projection d'un tableau de point est possible
     * 
     * @param indexStart
     *            indice de d�part dans les tableaux
     * @param latitudes
     *            tableau des latitudes
     * @param longitudes
     *            tableau des longitudes
     * @return boolean, true si le projection peut se faire sur tous les points,
     *         false sinon, dans ce cas il faut d�composer les trac�s...
     */
    public abstract boolean canProject(int indexStart, double[] latitudes,
            double[] longitudes);

    /**
     * Renvoie l'indice du premier point qui sort des crit�res de projection,
     * c'est � dire que cette projection peut projeter d'un seul coup tous les
     * points entre indexStart inclu et indexStop exclu.
     * 
     * @param indexStart
     *            indice de d�part dans les tableaux
     * @param latitudes
     *            tableau des latitudes
     * @param longitudes
     *            tableau des longitudes
     * @return int, indice � partir duquel il faut cr�er une nouvelle navigation
     */
    public abstract int getProjectableIndex(int indexStart, double[] latitudes,
            double[] longitudes);

    /**
     * Initialisation de la projection, calibrage.
     * 
     * @param latitudes
     *            tableau des latitudes � projeter
     * @param longitudes
     *            tableau des longitudes � projeter
     * @return boolean, false si la projection n'est pas possible...
     */
    public abstract boolean initialize(double[] latitudes, double[] longitudes);

    /**
     * Methode de projection
     * 
     * @param latitude
     *            latitude
     * @param longitude
     *            longitude
     * @return Point2D, coordonn�es x,y du point projet�
     */
    public abstract Point2D project(double latitude, double longitude)
            throws ProjectionException;

    // Donn�es internes :
    protected String _name;
    protected String _description;

    /**
     * Returns the name.
     * 
     * @return String
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Returns the description.
     * 
     * @return String
     */
    public String getDescription()
    {
        return _description;
    }

    /**
     * Methode statique permettant de r�cup�rer une projection par d�faut
     * 
     * @return GeoProjection
     */
    public static GeoProjection getDefault(boolean isGlobal)
    {
        return new GaussKruegerProjection(isGlobal);
    }

    private static double _Rt = 6378000.0;

    /**
     * Calcul de la distance sp�rique entre deux points repr�sent�s par leurs
     * latitude et longitude. Soient 2 lieux donn�s par leur coordonn�es
     * g�ographiques, longitude et latitude, B=( Lo, lo ), C=( L1, l1 ). Le
     * rayon terrestre �tant not� Rt, le lecteur �tablira, en utilisant un
     * triangle constitu� de B, C, et du p�le nord A, que la plus courte
     * distance entre B et C, mesur�e sur un grand cercle (distance loxodromique
     * ou g�od�sique) est : D = Rt*arccos(sin(lat1)*sin(lat2) +
     * cos(lat1)*cos(lat2)*cos(long2 - long1))
     * 
     * @param long1
     *            longitude point 1
     * @param lat1
     *            latitude point 1
     * @param long2
     *            longitude point 2
     * @param lat2
     *            latitude point 2
     * @return double
     */
    public static double distance(double long1, double lat1, double long2,
            double lat2)
    {
        double l1 = (long1 % 360) * StrictMath.PI / 360;
        double p1 = (lat1 % 360) * StrictMath.PI / 360;
        double l2 = (long2 % 360) * StrictMath.PI / 360;
        double p2 = (lat2 % 360) * StrictMath.PI / 360;
        double D =
            _Rt
                    * StrictMath.acos(StrictMath.sin(p1) * StrictMath.sin(p2)
                            + StrictMath.cos(p1) * StrictMath.cos(p2)
                            * StrictMath.cos(l2 - l1));
        return D;
    }

}
