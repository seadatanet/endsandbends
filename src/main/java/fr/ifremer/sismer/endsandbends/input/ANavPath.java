/* ============================================================================
 * Nom du fichier	: ANavPath.java
 * Auteur			: Altran
 * Date				: Jun 2, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ifremer.sismer.endsandbends.algorithm.GeoProjection;
import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.Time;
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints.START_END_TYPE;
import fr.ifremer.sismer.endsandbends.mediation.input.DateSegment;
import fr.ifremer.sismer.endsandbends.ui.OptionConstants;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public abstract class ANavPath implements INavPath
{
    protected long _timeStarted = 0;
    protected long _timeEnded = 0;
    protected double _minLongitude = 0.0;
    protected double _maxLongitude = 0.0;
    protected double _minLatitude = 0.0;
    protected double _maxLatitude = 0.0;

    /**
     * valeurs par defaut qui seront affich�es dans le panneau Options de
     * calcul, onglet Seuil
     */
    // type de gap pour calculer separation en 2 segments(en temps ou en
// distance)
    protected static int _gapType = OptionConstants.GAP_TYPE_TIME;
    // Ecart de distance maxi pour que deux trac�s soient cons�cutifs
    protected static long _distLimit = 3000; // 3km, donc 10mn de parcours...
    // Ecart de temps maxi pour que deux trac�s soient cons�cutifs
    protected static long _timeLimit = Time.timeToEpoch(0, 10); // 10
// minutes...

    protected final NavPathPoints _navPathPoints = new NavPathPoints();

    protected final List<DateSegment> _dateSegmentList;

    protected ANavPath(List<DateSegment> dateSegmentList)
    {
        _dateSegmentList = dateSegmentList;
    }

    // pour evol mail19/09/2012 (non confirm�e)
    //@Override
    public boolean checkGapTypeCompatibilityWithFile()
    {
        boolean isCompatible = true;
        if (_gapType == OptionConstants.GAP_TYPE_TIME)
        {
            if (_navPathPoints.getDates() == null)
            {
                isCompatible = false;
            }
        }
        return isCompatible;
    }

    /**
     * Method splitIntoContinuousPaths.
     * 
     * @param distLimit
     * @param timeLimit
     * @return NavPath[]
     */
    public NavPathPoints[] splitIntoContinuousPaths()
    {
        // Allouer un vecteur temporaire :
        List<NavPathPoints> navPathPointsTmp = new ArrayList<NavPathPoints>();

        boolean checkSegment =
            (null != _dateSegmentList && !_dateSegmentList.isEmpty());

        // Parcours :
        int start = -1;
        int stop = -1;
        long prevdate = -1; // pas de pr�c�dent
        double prevlat = 0.0;
        double prevlong = 0.0;



        int lastSegmentIndex = -1;
        int currentSegmentIndex;

        for (int index = 0; index < _navPathPoints.getNbPoints(); index++)
        {
            // Initialisation (premier points d'un trac�) :
            if (start < 0)
            {
                if (checkSegment)
                {
                    currentSegmentIndex =
                        DateSegment.indexOfSegmentContainingDate(
                            _dateSegmentList,
                            new Date(_navPathPoints.getDates()[index]));

                    if (currentSegmentIndex >= 0)
                    {
                        start = index;
                        lastSegmentIndex = currentSegmentIndex;
                    }
                }
                else
                {
                    start = index;
                }
            }
            else
            {
                // Crit�re de passage au trac� suivant :
                if (prevdate > 0) // sert � indiquer qu'il n'y a pas de
                // pr�c�dent...
                {
                    if (_gapType == OptionConstants.GAP_TYPE_TIME)
                    {
                        if ((_navPathPoints.getDates()[index] - prevdate >= _timeLimit))
                        {
                            {
                                stop = index;
                                _navPathPoints
                                    .setEndType(START_END_TYPE.TIME_GAP);
                            }
                        }
                    }
                    else if (_gapType == OptionConstants.GAP_TYPE_DIST)
                    {
                        double dist =
                            GeoProjection.distance(prevlong, prevlat,
                                _navPathPoints.getLongitudes()[index],
                                _navPathPoints.getLatitudes()[index]);
                        if (dist >= _distLimit)
                        {
                            stop = index;
                            _navPathPoints
                                .setEndType(START_END_TYPE.DISTANCE_GAP);
                        }
                    }
                }

                if (checkSegment && (stop < 0))
                {
                    currentSegmentIndex =
                        DateSegment.indexOfSegmentContainingDate(
                            _dateSegmentList,
                            new Date(_navPathPoints.getDates()[index]));

                    if (currentSegmentIndex < 0
                            || currentSegmentIndex != lastSegmentIndex)
                    {
                        stop = index;
                    }
                }

                // Stocker les pr�c�dents...
                if (_navPathPoints.getDates() != null)
                {
                    prevdate = _navPathPoints.getDates()[index];
                }
                else
                {
                    prevdate = 1;
                }
                prevlat = _navPathPoints.getLatitudes()[index];
                prevlong = _navPathPoints.getLongitudes()[index];

                // Terminer un trac�, si n�cessaire :
                if (stop >= 0 && start >= 0)
                {
                    NavPathPoints navPath =
                        _navPathPoints.extract(start, stop - start);

                    if (null != navPath)
                    {
                        navPath.setStartType(_navPathPoints.getStartType());
                        navPath.setEndType(_navPathPoints.getEndType());
                        navPathPointsTmp.add(navPath);
                    }

                    if (checkSegment)
                    {
                        currentSegmentIndex =
                            DateSegment.indexOfSegmentContainingDate(
                                _dateSegmentList,
                                new Date(_navPathPoints.getDates()[index]));

                        if (currentSegmentIndex >= 0)
                        {
                            start = index;
                            lastSegmentIndex = currentSegmentIndex;
                        }
                        else
                        {
                            start = -1;
                        }
                    }
                    else
                    {
                        start = index;
                    }

// start = index; // on recommence l� puisque les limites sont
                    // d�pass�es...

                    prevdate = -1; // pas de pr�c�dent...
                    stop = -1;
                }
            }
        }// fin boucle sur les points

        // Ajouter le dernier trac� :
        if (_navPathPoints.getNbPoints() > 0 && start >= 0)
        {
            NavPathPoints navPath =
                _navPathPoints.extract(start, _navPathPoints.getNbPoints()
                        - start);

            if (null != navPath)
            {
                navPathPointsTmp.add(navPath);
            }
        }

        // V�rifier qu'il y en a :
        if (navPathPointsTmp.isEmpty())
        {
            return null;
        }
        // correction des starts en fonction des ends des precedents
        for (int navIndex = 1; navIndex < navPathPointsTmp.size(); navIndex++)
        {
            navPathPointsTmp.get(navIndex).setStartType(
                navPathPointsTmp.get(navIndex - 1).getEndType());
        }
        // Renvoyer un tableau :
        NavPathPoints[] paths = new NavPathPoints[navPathPointsTmp.size()];
        for (int index = 0; index < navPathPointsTmp.size(); index++)
        {
            paths[index] = navPathPointsTmp.get(index);
        }
        navPathPointsTmp = null;
        return paths;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMaxLatitude()
    {
        return _maxLatitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMaxLongitude()
    {
        return _maxLongitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMinLatitude()
    {
        return _minLatitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMinLongitude()
    {
        return _minLongitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTimeEnded()
    {
        return _timeEnded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTimeStarted()
    {
        return _timeStarted;
    }

    /**
     * Changement du param�tre de diff�renciation des segments
     * 
     * @param minuts
     *            nombre de minutes
     */
    public static void setGapTime(int minuts)
    {
        _timeLimit = Time.timeToEpoch(0, minuts);
    }

    /**
     * Returns the timeLimit.
     * 
     * @return long
     */
    public static long getGapTimeInMinuts()
    {
        return Time.epochToMinuts(_timeLimit);
    }

    public static long getGapDistance()
    {
        return _distLimit;
    }

    public static void setGapDistance(int distLimit)
    {
        _distLimit = distLimit;
    }

    public static void setGapType(int gapType)
    {
        _gapType = gapType;
    }

    public static int getGapType()
    {
        return _gapType;
    }

    // pour evol mail19/09/2012 (non confirm�e)
    // public void setGapToDistance()
    // {
    // _gapType = OptionConstants.GAP_TYPE_DIST;
    // }

    /**
     * R�cup�ration des dates de d�but et de fin et v�rification de la
     * chronologie des dates
     * 
     * @param dates
     * @throws EABException
     */
    protected void analyseDates() throws EABException
    {
        if (null != _navPathPoints.getDates())
        {
            int length = _navPathPoints.getDates().length;

            _timeStarted = _navPathPoints.getDates()[0];
            _timeEnded = _navPathPoints.getDates()[length - 1];

            for (int i = 1; i < length; i++)
            {
                if (_navPathPoints.getDates()[i] < _navPathPoints.getDates()[i - 1])
                {
                    throw new EABException(ExceptionCode.CHRONOLOGICAL_ERROR);
                }
            }
        }
    }

    public void filterDates(Date startDate, Date endDate) throws EABException
    {
        if (null != _navPathPoints.getDates())
        {
            int length = _navPathPoints.getDates().length;

            if ((length != _navPathPoints.getLatitudes().length)
                    || (length != _navPathPoints.getLongitudes().length))
            {
                throw new EABException(ExceptionCode.WRONG_NUMBER_OF_ELEMENTS);
            }

            List<Long> dateList = new ArrayList<Long>(length);
            List<Double> latitudeList = new ArrayList<Double>(length);
            List<Double> longitudeList = new ArrayList<Double>(length);

            long startDateLong = startDate.getTime();
            long endDateLong = endDate.getTime();

            for (int i = 0; i < length; i++)
            {
                if ((_navPathPoints.getDates()[i] >= startDateLong)
                        && (_navPathPoints.getDates()[i] <= endDateLong))
                {
                    dateList.add(_navPathPoints.getDates()[i]);
                    latitudeList.add(_navPathPoints.getLatitudes()[i]);
                    longitudeList.add(_navPathPoints.getLongitudes()[i]);
                }
            }

            int newLength = dateList.size();
            if (newLength != length)
            {
                _navPathPoints.setNbPoints(newLength);

                initializeArrays(true);

                for (int i = 0; i < _navPathPoints.getNbPoints(); i++)
                {
                    _navPathPoints.getDates()[i] = dateList.get(i);
                    _navPathPoints.getLatitudes()[i] = latitudeList.get(i);
                    _navPathPoints.getLongitudes()[i] = longitudeList.get(i);
                }

                if (0 == _navPathPoints.getNbPoints())
                {
                    throw new EABException(
                        ExceptionCode.WRONG_NUMBER_OF_ELEMENTS,
                        noPointFoundMessage(startDate, endDate));
                }
            }

        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     */
    private String noPointFoundMessage(Date startDate, Date endDate)
    {
        String format =
            new StringBuffer(Messages.getString(Messages.MESSAGES,
                "SIMPLEDATEFORMATMASK")).append(" ").append( //$NON-NLS-1$ //$NON-NLS-2$
                Messages.getString(Messages.MESSAGES, "SIMPLETIMEFORMATMASK")) //$NON-NLS-1$
                .toString();

        UTCDateFormat dateFormat = new UTCDateFormat(format);

        String start = dateFormat.format(startDate);
        String end = dateFormat.format(endDate);

        return Globals.FILTER_NO_POINT_FOUND.format(new String[] {start, end});
    }

    protected void initializeArrays(boolean hasDate)
    {
        _navPathPoints.initPoints(hasDate);
    }

    public double[] getLatitudes()
    {
        return _navPathPoints.getLatitudes();
    }

    public double[] getLongitudes()
    {
        return _navPathPoints.getLongitudes();
    }

}
