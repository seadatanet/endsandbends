package fr.ifremer.sismer.endsandbends.input;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine;
import fr.ifremer.sismer.endsandbends.algorithm.GeoProjection;
import fr.ifremer.sismer.endsandbends.algorithm.ProjectionException;
import fr.ifremer.sismer.endsandbends.common.Globals;

/**
 * @author ATLANTIDE - ABT Classe tenant en m�moire un parcours de bateau
 */
public class NavPathPoints
{
    /**
     * logger
     */
    protected static final Logger myLogger = Logger
        .getLogger(NavPathPoints.class);

    /**
     * Description: defines the start and en type of each segment
     * ABSOLUTE_START: start of the hole trajectory (first point of first
     * segment); TIME_GAP: TIME between this segment and next or previous one is
     * greater the TIME threshold; DISTANCE_GAP: DISTANCE between this segment
     * and next one is greater the DISTANCE threshold; PROJECTION_SPLIT: an
     * artifical cut has been forced because the segment area was too big for
     * projection ABSOLUTE_END: end of the hole trajectory (last point of last
     * segment)
     * 
     * @author sbregent
     */
    public static enum START_END_TYPE
    {
        ABSOLUTE_START,
        TIME_GAP,
        DISTANCE_GAP,
        PROJECTION_SPLIT,
        ABSOLUTE_END
    };

    // default values for start and end: absolute
    private START_END_TYPE startType = START_END_TYPE.ABSOLUTE_START;
    private START_END_TYPE endType = START_END_TYPE.ABSOLUTE_END;

    /**
     * Getter sur endType.
     * 
     * @return the endType
     */
    public START_END_TYPE getEndType()
    {
        return endType;
    }

    /**
     * Setter sur endType.
     * 
     * @param endType
     *            the endType to set
     */
    public void setEndType(START_END_TYPE endType)
    {
        this.endType = endType;
    }

    /**
     * Getter sur startType.
     * 
     * @return the startType
     */
    public START_END_TYPE getStartType()
    {
        return startType;
    }

    /**
     * Getter sur startType.
     * 
     * @return the startType
     */
    public void setStartType(START_END_TYPE startType)
    {
        this.startType = startType;
    }

    // Statiques :
    // nbModels : nombre maxi de mod�les
    private static int __nbModels = 10;

    /**
     * Getter sur __nbModels.
     * 
     * @return the _nbModels
     */
    public static int get_nbModels()
    {
        return __nbModels;
    }

    /**
     * Getter sur __pathLimit.
     * 
     * @return the _pathLimit
     */
    public static int get_pathLimit()
    {
        return __pathLimit;
    }

    /**
     * Getter sur _nbPoints.
     * 
     * @return the nbPoints
     */
    public int getNbPoints()
    {
        return _nbPoints;
    }

    /**
     * Getter sur _x.
     * 
     * @return the x
     */
    public double[] getX()
    {
        return _x;
    }

    /**
     * Getter sur _y.
     * 
     * @return the y
     */
    public double[] getY()
    {
        return _y;
    }

    /**
     * Getter sur _tags.
     * 
     * @return the tags
     */
    public short[] getTags()
    {
        return _tags;
    }

    /**
     * Getter sur _error.
     * 
     * @return the error
     */
    public double[] getError()
    {
        return _error;
    }

    /**
     * Getter sur _count.
     * 
     * @return the count
     */
    public short[] getCount()
    {
        return _count;
    }

    /**
     * Getter sur _cause.
     * 
     * @return the cause
     */
    public String[] getCause()
    {
        return _cause;
    }

    /**
     * Getter sur _modelSelected.
     * 
     * @return the modelSelected
     */
    public int getModelSelected()
    {
        return _modelSelected;
    }

    // pathLimit : maximum de points dans un chemin export�
    private static int __pathLimit = 999;

    /**
     * Setter sur __pathLimit.
     * 
     * @param _pathLimit
     *            the _pathLimit to set
     */
    public static void set_pathLimit(int _pathLimit)
    {
        __pathLimit = _pathLimit;
    }

    // Donn�es d'un chemin de navigation :
    // nbPoints : nombre de points dans le trac� de navigation
    private int _nbPoints = 0;

    /**
     * Setter sur _nbPoints.
     * 
     * @param nbPoints
     *            the nbPoints to set
     */
    public void setNbPoints(int nbPoints)
    {
        _nbPoints = nbPoints;
    }

    // dates : dates � chaque point
    private long[] _dates = null;
    // latitudes : latitudes � chaque point
    private double[] _latitudes = null;
    // longitudes : longitudes � chaque point
    private double[] _longitudes = null;
    // x : abscisses du point projet� selon la projection locale (adapt�e � ce
// segment)
    private double[] _x = null;
    // y : ordonn�es du point projet� selon la projection locale (adapt�e � ce
// segment)
    private double[] _y = null;

    // xGlobal : abscisses du point projet� selon la projection globale
    private double[] _xGlobal = null;
    // yGlobal : ordonn�es du point projet� selon la projection globale
    private double[] _yGlobal = null;

    // tags : n� de �od�les
    private short[] _tags = null;
    // error : erreurs cumul�es par mod�le
    private final double[] _error = new double[__nbModels];
    // count : nombre de points par mod�le
    private final short[] _count = new short[__nbModels];
    // cause : cause de passage � ce mod�le...
    private final String[] _cause = new String[__nbModels];
    // modelSelected : n� du mod�le choisi
    private int _modelSelected = 0;

    /**
     * Constructeur par d�faut
     */
    public NavPathPoints()
    {
    }

    public void initPoints(boolean hasDate)
    {
        if (hasDate)
        {
            this._dates = new long[this._nbPoints];
        }
        else
        {
            this._dates = null;
        }

        this._latitudes = new double[this._nbPoints];
        this._longitudes = new double[this._nbPoints];

        this._x = new double[this._nbPoints];
        this._y = new double[this._nbPoints];
        this._xGlobal = new double[this._nbPoints];
        this._yGlobal = new double[this._nbPoints];
        this._tags = new short[this._nbPoints];

        for (int i = 0; i < this._nbPoints; i++)
        {
            this._x[i] = 0.0;
            this._y[i] = 0.0;
            this._xGlobal[i] = 0.0;
            this._yGlobal[i] = 0.0;
            this._tags[i] = 0;
        }
    }

    /**
     * Constructeur de copie par rapport � un autre trac�
     * 
     * @param start
     *            indice de debut des points � copier
     * @param length
     *            nombre de points � copier
     * @param isSplittedByThresholds
     *            true si le segment a �t� coup� � cause des gaps (points dont
     *            l'�cart est sup�rieur aux seuils d�finis)
     * @param isSplittedByProjection
     *            true si le segment a �t� coup� � cause de l'�tendue
     *            g�ographique, pour la projection
     * @return
     */
    public NavPathPoints extract(int start, int length)
    {
        NavPathPoints navPathToReturn;

        if (length > 1)
        {
            navPathToReturn = new NavPathPoints();

            // Stocker le nombre de points :
            navPathToReturn._nbPoints = length;

            // Allouer les tableaux :
            if (this._dates != null)
            {
                navPathToReturn._dates = new long[navPathToReturn._nbPoints];
            }
            else
            {
                navPathToReturn._dates = null;
            }

            navPathToReturn._latitudes = new double[navPathToReturn._nbPoints];
            navPathToReturn._longitudes = new double[navPathToReturn._nbPoints];
            navPathToReturn._x = new double[navPathToReturn._nbPoints];
            navPathToReturn._y = new double[navPathToReturn._nbPoints];
            navPathToReturn._xGlobal = new double[navPathToReturn._nbPoints];
            navPathToReturn._yGlobal = new double[navPathToReturn._nbPoints];
            navPathToReturn._tags = new short[navPathToReturn._nbPoints];

            // Copier les points :
            for (int index = 0; index < length; index++)
            {
                if (navPathToReturn._dates != null)
                {
                    navPathToReturn._dates[index] = this._dates[start + index];
                }
                navPathToReturn._latitudes[index] =
                    this._latitudes[start + index];
                navPathToReturn._longitudes[index] =
                    this._longitudes[start + index];
                navPathToReturn._x[index] = this._x[start + index];
                navPathToReturn._y[index] = this._y[start + index];
                navPathToReturn._xGlobal[index] = this._xGlobal[start + index];
                navPathToReturn._yGlobal[index] = this._yGlobal[start + index];
                navPathToReturn._tags[index] = this._tags[start + index];
            }
        }
        else
        {
            navPathToReturn = null;
        }

        return navPathToReturn;
    }

    // // M�thodes d'acc�s aux donn�es et statistiques :

    /**
     * Renvoie le nombre de point constituants le trac�
     * 
     * @return int, nombre de points
     */
    public int getPointsCount()
    {
        return _nbPoints;
    }

    /**
     * Renvoie un point en coordonn�es projet�es selon la projection locale
     * 
     * @param index
     *            indice demand�
     * @return Point2D, point contenant les coordonn�es.
     */
    public Point2D getPoint(int index)
    {
        return new Point2D.Double(_x[index], _y[index]);
    }

    /**
     * renvoie un point en coordonn�es projet�es selon la projection locale ou
     * globale, fonction de la valeur de isGlobaleProj
     * 
     * @param index
     *            index du point
     * @param isGlobaleProj
     *            true si on veut les coordonn�es projet�es selon proj globale,
     *            false si on veut les coordonn�es projet�es en proj locale
     * @return Point2D, point contenant les coordonn�es.
     */
    public Point2D getPoint(int index, boolean isGlobaleProj)
    {
        Point2D point = null;
        if (isGlobaleProj)
        {
            point = new Point2D.Double(_xGlobal[index], _yGlobal[index]);
        }
        else
        {
            point = getPoint(index);
        }
        return point;
    }

    /**
     * Returns the latitudes.
     * 
     * @return double[]
     */
    public double[] getLatitudes()
    {
        return _latitudes;
    }

    /**
     * Returns the longitudes.
     * 
     * @return double[]
     */
    public double[] getLongitudes()
    {
        return _longitudes;
    }

    /**
     * Renvoie une coordonn�es de latitude
     * 
     * @param index
     *            indice demand�
     * @return double, latitude du point � l'indice demand�
     */
    public double getLatitude(int index)
    {
        return _latitudes[index];
    }

    /**
     * Renvoie une coordonn�es de longitude
     * 
     * @param index
     *            indice demand�
     * @return double, longitude du point � l'indice demand�
     */
    public double getLongitude(int index)
    {
        return _longitudes[index];
    }

    /**
     * Renvoie le n� de mod�le associ� � un point
     * 
     * @param index
     *            indice demand�
     * @return short, n� du mod�le (0=original, jusqu'� 5)
     */
    public short getModel(int index)
    {
        return _tags[index];
    }

    /**
     * Renvoie la date d'un point
     * 
     * @param index
     *            indice demand�
     * @return long, date epoch du point demand�
     */
    public long getEpochDate(int index)
    {
        if (_dates == null)
            return 0;
        return _dates[index];
    }

    /**
     * Renvoie la date d'un point
     * 
     * @param index
     *            indice demand�
     * @return long, date du point demand�
     */
    public Date getDate(int index)
    {
        if (_dates == null)
            return null;
        return new Date(_dates[index]);
    }

    /**
     * Method getModelError.
     * 
     * @param model
     * @return double
     */
    public double getModelError(int model)
    {
        return _error[model];
    }

    /**
     * Method getModelCount.
     * 
     * @param model
     * @return double
     */
    public short getModelCount(int model)
    {
        return _count[model];
    }

    /**
     * Method getModelCause.
     * 
     * @param model
     * @return String
     */
    public String getModelCause(int model)
    {
        return _cause[model];
    }

    /**
     * Method getSelectedModel.
     * 
     * @return int
     */
    public int getSelectedModel()
    {
        return _modelSelected;
    }

    /**
     * Method selectModel.
     * 
     * @param model
     */
    public void selectModel(int model)
    {
        _modelSelected = model;
    }

    /**
     * @author ATLANTIDE - ABT Conteneur des donn�es statistiques renvoy�es par
     *         les m�thodes
     */
    public static class Statistics
    {
        public int count = 0;
        public double minValue = Double.MAX_VALUE;
        public double maxValue = -Double.MAX_VALUE;
        public double meanValue = 0.0;
        public double variance = 0.0;

        public void open()
        {
            count = 0;
            minValue = Double.MAX_VALUE;
            maxValue = -Double.MAX_VALUE;
            meanValue = 0.0;
            variance = 0.0;
        }

        public void close()
        {
            meanValue /= count;
            variance /= count;
            variance -= meanValue * meanValue;
        }

        public void add(double value)
        {
            if (value < minValue)
                minValue = value;
            if (value > maxValue)
                maxValue = value;
            meanValue += value;
            variance += value * value;
            count++;
        }
    }

    /**
     * @author ATLANTIDE - ABT Conteneur des donn�es statistiques renvoy�es par
     *         les m�thodes, sp�cialis� dans les stats de longitude... Pour
     *         d�terminer quelle borne est �tendue, on suppose que le point
     *         ajout� est pr�t d'une des bornes. Il �tend la borne dont il est
     *         le plus proche s'il va � l'ext�rieur de l'interval est- ouest. -
     *         minValue est l'ouest - maxValue est l'est La valeur moyenne ne
     *         prend pas en compte le passage par 180�
     */
    public static class LongitudeStatistics extends Statistics
    {
        private double dist(double value1, double value2)
        {
            if (value1 >= 0)
            {
                if (value2 >= 0)
                {
                    // Ils sont du m�me c�t�, donc prendre la distance
                    // courante
                    return (Math.abs(value1 - value2));
                }

                // Ils sont de signe oppos�s : prendre la distance
                // qui passe par 0, puis l'angle compl�mentaire, la
                // distance renvoy�e est le minimum des deux...
                double dist1 = Math.abs(value1) + Math.abs(value2);
                double dist2 = 360 - dist1;
                return (Math.min(dist1, dist2));
            }

            if (value2 >= 0)
            {
                double dist1 = Math.abs(value1) + Math.abs(value2);
                double dist2 = 360 - dist1;
                return (Math.min(dist1, dist2));
            }

            return (Math.abs(value1 - value2));
        }

        private boolean isBetween(double value, double east, double west,
                double middle)
        {
            // Cas trivial, � l'initialisation
            if ((east == middle) && (west == middle))
            {
                return (value == east);
            }

            // S'ils sont bien orient�s, c'est le cas simple, on utilise
            // les coordonn�es "� plat", en projection cylindrique
            if (east > west)
            {
                return ((value >= west) && (value <= east));
            }

            // Sinon c'est l'inverse, c'est � dire que le segment de cercle
            // passe la limite de 180�, on prend donc l'inverse (le segment
            // "naturel"
            // en projection cylindrique passe tjs par 0
            return ((value >= east) && (value <= west));
        }

        private boolean isAtEast(double value, double east, double west,
                double middle)
        {
            if ((east == middle) && (west == middle))
            {
                return (value > middle); // que le cas normal... disons qu'on ne
                // passe pas la limite de 180� d�s les premiers points...
            }
            else if (isBetween(value, east, west, middle))
            {
                return false;
            }
            else
            {
                double distEast = dist(value, east);
                double distWest = dist(value, west);
                return (distEast < distWest);
            }
        }

        private boolean isAtWest(double value, double east, double west,
                double middle)
        {
            if ((east == middle) && (west == middle))
            {
                return (value < middle); // que le cas normal... disons qu'on ne
                // passe pas la limite de 180� d�s les premiers points...
            }
            else if (isBetween(value, east, west, middle))
            {
                return false;
            }
            else
            {
                double distEast = dist(value, east);
                double distWest = dist(value, west);
                return (distWest < distEast);
            }
        }

        @Override
        public void add(double value)
        {
            // Normaliser entre 0 et 180
            while (value > 180)
                value -= 360;
            while (value < -180)
                value += 360;

            // Calculer le min max est prenant en compte le passage
            // par 180� :
            if (count == 0)
            {
                minValue = value;
                maxValue = value;
            }
            else
            {
                if (isAtEast(value, maxValue, minValue, meanValue / count))
                    maxValue = value;
                else if (isAtWest(value, maxValue, minValue, meanValue / count))
                    minValue = value;
            }

            // Calculer le reste normalement :
            meanValue += value;
            variance += value * value;
            count++;
        }
    }

    /**
     * Calcule des statistiques sur la latitude
     * 
     * @return Statistics
     */
    public Statistics getLatitudeStatistics()
    {
        Statistics stats = new Statistics();
        stats.open();
        for (int index = 0; index < _nbPoints; index++)
        {
            stats.add(_latitudes[index]);
        }
        stats.close();
        return stats;
    }

    /**
     * Calcule des statistiques sur la longitude
     * 
     * @return Statistics
     */
    public Statistics getLongitudeStatistics()
    {
        Statistics stats = new LongitudeStatistics();
        stats.open();
        for (int index = 0; index < _nbPoints; index++)
        {
            stats.add(_longitudes[index]);
        }
        stats.close();
        return stats;
    }

    /**
     * Calcule des statistiques sur l'�cart en temps entre deux mesures
     * successives
     * 
     * @return Statistics
     */
    public Statistics getTimeIntervalStatistics()
    {
        if (_dates == null)
            return null;
        Statistics stats = new Statistics();
        stats.open();
        for (int index = 1; index < _nbPoints; index++)
        {
            stats.add(_dates[index] - _dates[index - 1]);
        }
        stats.close();
        return stats;
    }

    // // M�thodes de manipulation globale :

    /**
     * Supprime les donn�es charg�es en m�moire.
     */
    public void clear()
    {
        _nbPoints = 0;
        _dates = null;
        _latitudes = null;
        _longitudes = null;
        _x = null;
        _y = null;
        _xGlobal = null;
        _yGlobal = null;
        _tags = null;

        // [TNU] Code explicitly invokes garbage collection. Except for
        // specific use in benchmarking, this is very dubious.
// System.gc();
// [/TNU]
    }

    /**
     * R�initialise les informations de calcul (tags et distances)
     */
    public void clearTemporaryInformation()
    {
        for (int index = 0; index < _nbPoints; index++)
            _tags[index] = 0;
        for (int index = 0; index < __nbModels; index++)
        {
            _error[index] = 0.0;
            _count[index] = 0;
            _cause[index] = null;
        }
    }

    /**
     * Calcule de la longueur du trac�
     * 
     * @return double
     */
    public double getPathLength()
    {
        double x1;
        double y1;
        double x2 = _x[0];
        double y2 = _y[0];
        double length = 0.0;
        for (int i = 1; i < _nbPoints; i++)
        {
            x1 = x2;
            y1 = y2;
            x2 = _x[i];
            y2 = _y[i];
            length +=
                StrictMath.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        }
        return length;
    }

    /**
     * Method setError.
     * 
     * @param model
     * @param error
     */
    public void setModelError(int model, double error)
    {
        _error[model] = error;
    }

    /**
     * Method setModelCause.
     * 
     * @param model
     * @param cause
     */
    public void setModelCause(int model, String cause)
    {
        _cause[model] = cause;
    }

    /**
     * Initialisation du calcul
     * 
     * @param algoDistance
     *            distance utilis�e
     * @return double[], tableau des distances, sur nbPoints...
     */
    public double[] initComputation(DistanceToLine algoDistance, short model)
    {
        // R�initialiser :
        clearTemporaryInformation();
        double[] distances = new double[_nbPoints];

        // Indices des extr�mit�s
        int m1 = 0;
        int m2 = _nbPoints - 1;

        // Le premier et le dernier point appartiennent au mod�le 1
        // et avec des distances �gales � 0
        _tags[m1] = model;
        _tags[m2] = model;
        _count[model] = 2;
        distances[m1] = 0.0;
        distances[m2] = 0.0;

        // Calcul des distances des points entre les deux :
        algoDistance.defineLine(_x[m1], _y[m1], _x[m2], _y[m2]);
        for (int j = m1 + 1; j <= m2 - 1; j++)
            distances[j] = algoDistance.getSquareDistance(_x[j], _y[j]);

        // Retour :
        return distances;
    }

    /**
     * It�ration dans le calcul
     * 
     * @param algoDistance
     *            distance utilis�e
     * @param distances
     *            tableau des distances
     * @param model
     *            n� du mod�le en cours
     * @return int, indice du point ajout�, -1 s'il y a une erreur
     */
    public int stepComputation(DistanceToLine algoDistance, double[] distances,
            short model)
    {
        // Indices des extr�mit�s
        int m1 = 0;
        int m2 = _nbPoints - 1;

        // Recherche du maximum de la distance :
        int kn = findMostDistantPoint(distances);
        if (kn < 0)
            return -1;

        // Ajout du point maxi dans le mod�le n :
        _tags[kn] = model;
        _count[model]++;
        distances[kn] = 0.0;

        // Recherche des indices m1 et m2 des points imm�diatement
        // inf�rieurs et sup�rieurs qui apprtiennent � un mod�le
        for (int i = kn - 1; i >= 0; i--)
        {
            if (_tags[i] > 0)
            {
                m1 = i;
                break;
            }
        }
        for (int i = kn + 1; i < _nbPoints; i++)
        {
            if (_tags[i] > 0)
            {
                m2 = i;
                break;
            }
        }

        // Calcul de la pente et des distances entre m1 et kn
        algoDistance.defineLine(_x[m1], _y[m1], _x[kn], _y[kn]);
        for (int i = m1 + 1; i < kn; i++)
            distances[i] = algoDistance.getSquareDistance(_x[i], _y[i]);

        // Calcul de la pente et des distances entre kn et m2 :
        algoDistance.defineLine(_x[kn], _y[kn], _x[m2], _y[m2]);
        for (int i = kn + 1; i < m2; i++)
            distances[i] = algoDistance.getSquareDistance(_x[i], _y[i]);

        // Renvoyer l'erreur :
        return kn;
    }

    /**
     * Calcul de l'erreur
     * 
     * @param distances
     * @param length
     * @return double
     */
    public static double computeError(double[] distances, double length)
    {
        double error = 0.0;
        for (int i = 0; i < distances.length; i++)
            error += distances[i];
        error = StrictMath.sqrt(error / (distances.length - 1)) / length;
        return error;
    }

    /**
     * Recherche du point le plus �loign�
     * 
     * @param distances
     * @return int
     */
    public static int findMostDistantPoint(double[] distances)
    {
        double dmax = 0.0;
        int kn = -1;
        for (int i = 0; i < distances.length; i++)
        {
            if (distances[i] > dmax)
            {
                dmax = distances[i];
                kn = i;
            }
        }
        return kn;
    }

    /**
     * Recherche du point le plus �loign�
     * 
     * @param distances
     * @return double
     */
    public static double findGreatestDistance(double[] distances)
    {
        double dmax = 0.0;
        for (int i = 0; i < distances.length; i++)
        {
            if (distances[i] > dmax)
                dmax = distances[i];
        }
        return dmax;
    }

    /**
     * M�thode de calcul principale. Calcule les tableaux _tags et _distances
     * qui donnent les trac�s optimis�s...
     * 
     * @return boolean
     */
    public boolean compute(DistanceToLine algoDistance)
    {
        // Il doit y avoir des points :
        if (_nbPoints == 0)
            return false;
        if (algoDistance == null)
            algoDistance = DistanceToLine.getDefault();

        // Num�ro de mod�le en cours :
        short model = 1;

        // D�but de calcul :
        double[] distances = initComputation(algoDistance, model);

        // Longueur du trac� :
        double length = getPathLength();
        if (length == 0.0)
            return false;

        // Boucle principale sur les mod�les.
        // Le calcul continue tant que l'erreur globale est sup�rieure
        // � 10^5
        double error = 1.0;
        int nextErrorPrecision = 1;
        int nextPathLimit = __pathLimit;
        int nextModel = 0;
        String strCause;
        do
        {
            // Ajouter un point :
            stepComputation(algoDistance, distances, model);

            // Calculer l'erreur :
            error = computeError(distances, length);

            // Test pour passer au mod�le suivant
            nextModel = 0;
            strCause = null;
            // Passage d'un mod�le � l'autre par puissance de 10...
            if (error == 0.0)
            {
                nextModel++;
                strCause = Globals.MODEL_CAUSE_EXACT_TRACE;
            }
            else
            {
                while (error < StrictMath.pow(0.1, nextErrorPrecision))
                {
                    nextModel++;
                    strCause =
                        Globals.MODEL_CAUSE_ERROR.format(new String[] {String
                            .valueOf(nextErrorPrecision)});
                    nextErrorPrecision++;
                }
            }
            // Passage d'un mod�le � l'autre par limite de points...
            if ((nextModel == 0) && (_count[model] >= nextPathLimit))
            {
                nextModel++;
                strCause =
                    Globals.MODEL_CAUSE_POINT_NUMBER
                        .format(new String[] {String.valueOf(nextPathLimit)});
                nextPathLimit += __pathLimit;
            }

            // Passage au mod�le suivant si n�cessaire :
            if (nextModel > 0)
            {
                // Stocker l'erreur de ce mod�le :
                _error[model] = error;
                _cause[model] = strCause;

                // Incr�menter le n� de mod�le :
                model++;

                // Recopier le compte du mod�le pr�c�dent dans celui-ci et
                // remettre
                // � 0 le compteur de nombre de profiles export�s :
                if (model < __nbModels)
                    _count[model] = _count[model - 1];
            }

        }
        while ((model < __nbModels) && (error > 0.0));

        // S�lectionner un mod�le par d�faut :
        selectDefaultModel();

        // Retour sans erreur :
        return true;
    }

    /**
     * Calcul d'une approximation jusqu'� un nombre de points
     * 
     * @param algoDistance
     * @param nbPoints
     * @return boolean
     */
    public boolean computeUser(DistanceToLine algoDistance, int nbPoints)
    {
        // Il doit y avoir des points :
        if (_nbPoints == 0)
            return false;
        if (algoDistance == null)
            algoDistance = DistanceToLine.getDefault();

        // Num�ro de mod�le en cours :
        short model = 1;

        // D�but de calcul :
        double[] distances = initComputation(algoDistance, model);

        // Longueur du trac� :
        double length = getPathLength();
        if (length == 0.0)
            return false;

        // Boucle principale jusqu'� ce qu'on ait le bon nombre de points.
// double error = 0.0;
        do
        {
            stepComputation(algoDistance, distances, model);
        }
        while (_count[model] < nbPoints);

        // S�lectionner ce mod�le :
        selectModel(model);

        // Stocker l'info :
        _error[model] = computeError(distances, length);
        _cause[model] = Globals.MODEL_CAUSE_CUSTOM;

        // Retour sans erreur :
        return true;
    }

    /**
     * Method selectDefaultModel.
     */
    public void selectDefaultModel()
    {
        _modelSelected = 0;
        for (int model = 1; model < __nbModels; model++)
        {
            if (_count[model] > __pathLimit)
                break;
            if (_error[model] < 0.000001)
                break;
            if (_cause[model] == null)
                break;
            _modelSelected = model;
        }
    }

    /**
     * S�lectionne le premier mod�le dont l'erreur est inf�rieure � un seuil
     * exprim� en puissance de dix.
     * 
     * @param error
     *            puissance de dix de l'erreur (1 pour 10-1, 2 pour 10-2)
     */
    public void selectErrorModel(int error)
    {
        _modelSelected = 0;
        double level = StrictMath.pow(10.0, (-error));
        for (int model = __nbModels - 1; model > 0; model--)
        {
            if (_cause[model] == null)
                continue;
            if (_error[model] >= level)
                break;
            _modelSelected = model;
        }
    }

    /**
     * D�composition des trac�s en petites unit�s pour le cas o� la projection
     * ne pourrait supporter de larges zones...
     * 
     * @param projection
     *            projection utilis�e
     * @return NavPath[], liste des trac�s, null si erreur.
     */
    public NavPathPoints[] splitProjection(GeoProjection projection)
    {
        // Initialiser un tableau avec un seul chemin :
        List<NavPathPoints> list = new ArrayList<NavPathPoints>();
        NavPathPoints navPathPoints = null;
        // int count = 0;

        // Tester si on peut tout calculer :
        int indexStart = 0;
        int indexStop = 0;
        while (indexStart < _nbPoints)
        {
            if (!projection.canProject(indexStart, _latitudes, _longitudes))
            {
                indexStop =
                    projection.getProjectableIndex(indexStart, _latitudes,
                        _longitudes);
            }
            else
            {
                indexStop = _nbPoints; // donc sortie de la boucle, ce pourrait
            }
            // �tre un break...
            // segment n'a pas besoin d'�tre splitt� pour le projection.
            if ((indexStart == 0) && (indexStop == _nbPoints))
            {
                list.add(this);
            }
            else
            {
                navPathPoints = extract(indexStart, indexStop - indexStart);
                navPathPoints.setEndType(START_END_TYPE.PROJECTION_SPLIT);
                list.add(navPathPoints);
            }
            indexStart = indexStop;
        }

        // Ajouter la fin :
        NavPathPoints[] paths = new NavPathPoints[list.size()];
        for (int i = 0; i < list.size(); i++)
        {
            // si on a plus de 1 segment c'est qu'on a splitt� le segment en
// cours (this), or on a mis les
// types de start, mais pas de end
// le premier sous segment commence par le m�me type que le segment
// courant splitt�
            list.get(0).setStartType(this.getStartType());
            // chaque segment qui n'est pas le premier commence par un
            // split
            if (i > 0 && i < list.size())
            {
                list.get(i).setStartType(START_END_TYPE.PROJECTION_SPLIT);
            }
            // le dernier sous segment se termine par le m�me type que le
// segment courant splitt�
            list.get(list.size() - 1).setEndType(this.getEndType());
            paths[i] = list.get(i);
        }

        // Renvoyer la liste des trac�s :
        return paths;
    }

    /**
     * D�composition des trac�s en petites unit�s pour le cas o� la projection
     * ne pourrait supporter de larges zones...
     * 
     * @param projection
     *            projection utilis�e
     * @return NavPath[], liste des trac�s, null si erreur.
     */
// public NavPathPoints[] splitProjection(GeoProjection projection, int index)
// {
//
// // Initialiser un tableau avec un seul chemin :
// List<NavPathPoints> list = new ArrayList<NavPathPoints>();
// // int count = 0;
//
// // Tester si on peut tout calculer :
// int indexStart = 0;
// int indexStop = 0;
// while (indexStart < _nbPoints)
// {
// if (!projection.canProject(indexStart, _latitudes, _longitudes))
// {
// indexStop =
// projection.getProjectableIndex(indexStart, _latitudes,
// _longitudes);
// this.endType = START_END_TYPE.PROJECTION_SPLIT;
// }
// else
// {
// indexStop = _nbPoints; // donc sortie de la boucle, ce pourrait
// }
// // �tre un break...
// if ((indexStart == 0) && (indexStop == _nbPoints))
// list.add(this);
// else
// list.add(extract(indexStart, indexStop - indexStart));// ,
// // false, true));
// indexStart = indexStop;
// }
//
// // Ajouter la fin :
// NavPathPoints[] paths = new NavPathPoints[list.size()];
// for (int i = 0; i < list.size(); i++)
// paths[i] = list.get(i);
//
// // Renvoyer la liste des trac�s :
// return paths;
// }

    /**
     * Calcule la projection des points selon la projection globale
     * 
     * @param projection
     *            globale!! ne pas utiliser de projection locale (propre � un
     *            seul segment ici)!!
     * @return
     * @throws ProjectionException
     */
    public boolean projectInGlobalProjection(GeoProjection projection)
            throws ProjectionException
    {

        // Parcours de tous les points pour faire la projection :
        Point2D point;
        for (int index = 0; index < _nbPoints; index++)
        {
            point = projection.project(_latitudes[index], _longitudes[index]);
            _xGlobal[index] = point.getX();
            _yGlobal[index] = point.getY();
        }
        // Retour OK :
        return true;
    }

    /**
     * Calcule la projection des points selon la projection locale
     * 
     * @param projection
     *            globale!! ne pas utiliser de projection locale (propre � un
     *            seul segment ici)!!
     * @return
     * @throws ProjectionException
     */
    public boolean projectInLocaleProjection(GeoProjection projection)
            throws ProjectionException
    {

        // Parcours de tous les points pour faire la projection :
        Point2D point;
        for (int index = 0; index < _nbPoints; index++)
        {
            point = projection.project(_latitudes[index], _longitudes[index]);
            _x[index] = point.getX();
            _y[index] = point.getY();
        }
        // Retour OK :
        return true;
    }

    /**
     * M�thode de calcul optimale : elle permet d'obtenir exactement le nombre
     * de points voulu. La t�chnique utilis�e est d'ajouter les points un � un,
     * en travaillant sur le segment qui a le point le plus �loign�.
     * 
     * @param algoDistance
     * @param paths
     *            liste de trac�
     * @param nbPoints
     *            nombre de point voulus
     */
    public static void computeAcrossPathsOnDistance(
            DistanceToLine algoDistance, NavPathPoints[] paths, int nbPoints)
    {
        // V�rifications :
        if ((paths == null) || (paths.length == 0))
            return;

        // Allouer un tableau pour les distances de chaque segment :
        double[][] distances = new double[paths.length][];

        // Premier passage pour initialiser le nombre de points obtenus
        // nbUsed : nombre de points atteints, en sommant les points de chaque
        // segment...
        int nbUsed = 0;
        NavPathPoints path = null;
        for (int index = 0; index < paths.length; index++)
        {
            path = paths[index];
            distances[index] = path.initComputation(algoDistance, (short) 1);
            nbUsed += path.getModelCount(1);
        }

        // Boucle tant qu'on n'a pas le bon nombre de points :
        while (nbUsed < nbPoints)
        {
            // Chercher le segment � traiter :
            NavPathPoints pathSelected = null;
            int indSelected = 0;
            double maxDist = 0.0;
            double dist;
            for (int index = 0; index < paths.length; index++)
            {
                dist = NavPathPoints.findGreatestDistance(distances[index]);
                if (dist > maxDist)
                {
                    pathSelected = paths[index];
                    indSelected = index;
                    maxDist = dist;
                }
            }

            // Traiter ce segment :
            if (pathSelected == null)
                break;
            else
            {
                if (pathSelected.stepComputation(algoDistance,
                    distances[indSelected], (short) 1) >= 0)
                    nbUsed++; // �a ajoute forc�ment un seul point...
            }
        }

        // Remplir le mod�le s�lectionn� et la cause pour tous les segments :
        for (int index = 0; index < paths.length; index++)
        {
            // S�lectionner le mod�le 1 :
            path = paths[index];
            path.selectModel(1);

            // Stocker l'info :
            double length = path.getPathLength();
            path.setModelError(1, computeError(distances[index], length));
            path.setModelCause(1, Globals.MODEL_CAUSE_DIST_OPTIM);
        }
    }

    /**
     * M�thode de calcul optimale : elle permet d'obtenir exactement le nombre
     * de points voulu. La t�chnique utilis�e est d'ajouter les points un � un,
     * en travaillant sur le segment qui a l'erreur la plus importante.
     * 
     * @param paths
     *            liste de trac�
     * @param nbPoints
     *            nombre de point voulus
     */
    public static void computeAcrossPathsOnError(DistanceToLine algoDistance,
            NavPathPoints[] paths, int nbPoints)
    {
        // V�rifications :
        if ((paths == null) || (paths.length == 0))
            return;

        // Allouer un tableau pour les distances de chaque segment :
        double[] lengths = new double[paths.length];
        double[] errors = new double[paths.length];
        double[][] distances = new double[paths.length][];

        // Premier passage pour initialiser le nombre de points obtenus
        // nbUsed : nombre de points atteints, en sommant les points de chaque
        // segment...
        int nbUsed = 0;
        NavPathPoints path = null;
        for (int index = 0; index < paths.length; index++)
        {
            path = paths[index];
            lengths[index] = path.getPathLength();
            distances[index] = path.initComputation(algoDistance, (short) 1);
            errors[index] = path.computeError(distances[index], lengths[index]);
            nbUsed += path.getModelCount(1);
        }

        // Boucle tant qu'on n'a pas le bon nombre de points :
        while (nbUsed < nbPoints)
        {
            // Chercher le segment � traiter :
            NavPathPoints pathSelected = null;
            int indSelected = 0;
            double maxError = 0.0;
            for (int index = 0; index < paths.length; index++)
            {
                if (errors[index] > maxError)
                {
                    pathSelected = paths[index];
                    indSelected = index;
                    maxError = errors[index];
                }
            }

            // Traiter ce segment :
            if (pathSelected == null)
                break;
            else
            {
                if (pathSelected.stepComputation(algoDistance,
                    distances[indSelected], (short) 1) >= 0)
                    nbUsed++; // �a ajoute forc�ment un seul point...
                lengths[indSelected] = pathSelected.getPathLength();
                errors[indSelected] =
                    pathSelected.computeError(distances[indSelected],
                        lengths[indSelected]);
            }
        }

        // Remplir le mod�le s�lectionn� et la cause pour tous les segments :
        for (int index = 0; index < paths.length; index++)
        {
            // S�lectionner le mod�le 1 :
            path = paths[index];
            path.selectModel(1);

            // Stocker l'info :
            path.setModelError(1, errors[index]);
            path.setModelCause(1, Globals.MODEL_CAUSE_ERROR_OPTIM);
        }
    }

    /**
     * Getter sur _dates.
     * 
     * @return the dates
     */
    public long[] getDates()
    {
        return _dates;
    }

    public void setX(int index, double value)
    {
        _x[index] = value;
    }

    public void setY(int index, double value)
    {
        _y[index] = value;
    }

    public boolean needStartIndicator()
    {
        return this.startType == START_END_TYPE.ABSOLUTE_START
                || this.startType == START_END_TYPE.DISTANCE_GAP
                || this.startType == START_END_TYPE.TIME_GAP;
    }

    public boolean needEndIndicator()
    {
        return this.endType == START_END_TYPE.ABSOLUTE_END
                || this.endType == START_END_TYPE.DISTANCE_GAP
                || this.endType == START_END_TYPE.TIME_GAP;
    }
}
