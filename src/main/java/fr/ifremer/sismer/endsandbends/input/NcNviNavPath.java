package fr.ifremer.sismer.endsandbends.input;

import java.io.IOException;
import java.util.List;

import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.NetCDFTools;
import fr.ifremer.sismer.endsandbends.common.Time;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.mediation.input.DateSegment;
import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * @author ATLANTIDE - ABT Chemin de navigation principal, c'est � dire qu'il
 *         faut d�compos� en trac�s continus...
 */
public class NcNviNavPath extends ANavPath implements INavPath
{

    /**
     * Constructeur
     * 
     * @param dateSegmentList
     */
    public NcNviNavPath(List<DateSegment> dateSegmentList)
    {
        super(dateSegmentList);
    }

    /**
     * Constructeur
     */
    public NcNviNavPath()
    {
        super(null);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     */
    @Override
    public boolean loadNavFile(String filepath) throws EABException
    {
        NetcdfFile nc = null;

        // Protection pour les erreurs d'entr�e / sorties :
        try
        {
            // Ouverture du fichier :
            nc = NetcdfFile.open(filepath); // ouverture
            // en
            // lecture
            // seule

            // Lire les informations d'ent�te :
            Attribute att = null;
            Number num = null;
            long date;
            long time;
            // - Date de d�but
            date = time = 0;
            att = nc.findGlobalAttribute("mbStartDate");
            if (att != null)
            {
                num = att.getNumericValue();
                if (num != null)
                    date = num.longValue() - 2440588;
            }
            att = nc.findGlobalAttribute("mbStartTime");
            if (att != null)
            {
                num = att.getNumericValue();
                if (num != null)
                    time = num.longValue();
            }
            _timeStarted = Time.julianToEpoch(date, time);
            // - Date de fin
            date = time = 0;
            att = nc.findGlobalAttribute("mbEndDate");
            if (att != null)
            {
                num = att.getNumericValue();
                if (num != null)
                    date = num.longValue() - 2440588;
            }
            att = nc.findGlobalAttribute("mbEndTime");
            if (att != null)
            {
                num = att.getNumericValue();
                if (num != null)
                    time = num.longValue();
            }
            _timeEnded = Time.julianToEpoch(date, time);
            // Longitude min :
            att = nc.findGlobalAttribute("mbWestLongitude");
            if (att != null)
            {
                num = att.getNumericValue();
                if (num != null)
                    _minLongitude = num.doubleValue();
            }
            // Longitude max :
            att = nc.findGlobalAttribute("mbEastLongitude");
            if (att != null)
            {
                num = att.getNumericValue();
                if (num != null)
                    _maxLongitude = num.doubleValue();
            }
            // Latitude min :
            att = nc.findGlobalAttribute("mbSouthLatitude");
            if (att != null)
            {
                num = att.getNumericValue();
                if (num != null)
                    _minLatitude = num.doubleValue();
            }
            // Latitude max :
            att = nc.findGlobalAttribute("mbNorthLatitude");
            if (att != null)
            {
                num = att.getNumericValue();
                if (num != null)
                    _maxLatitude = num.doubleValue();
            }
            // Lib�rer l'attribut
            att = null;
            num = null;

            // R�cup�rer le nombre de points :
            // Dimension mbPositionNbr dans un fichier SMF de navigation...
            Dimension nbPositions = nc.findDimension("mbPositionNbr");
            int nbPointsInFile = nbPositions.getLength();
            _navPathPoints.setNbPoints(nbPointsInFile);

            // Lire les flags de validit� :
            Variable ncFlags = NetCDFTools.getVariable(nc, "mbPFlag");
            Array arFlags = ncFlags.read();
            char[] flags = (char[]) arFlags.copyTo1DJavaArray();

            // Premier parcourt pour compter le nombre de points valides :
            int cpt = 0;
            _navPathPoints.setNbPoints(cpt);
            for (int index = 0; index < nbPointsInFile; index++)
            {
                if ((flags[index] >= 2) && (flags[index] <= 8))
                    cpt++;
            }
            _navPathPoints.setNbPoints(cpt);
            if (_navPathPoints.getNbPoints() == 0)
                return false;

            // R�cup�rer les variables de temps, elles peuvent �tre absentes :
            Variable ncDates = nc.findVariable("mbDate");
            Variable ncTimes = nc.findVariable("mbTime");
            boolean hasTime = ((ncDates != null) && (ncTimes != null));

            initializeArrays(hasTime);

            // Acc�der � la liste des points :
            // Variables mbDate, mbTime, mbOrdinate et mbAbsissa d'un fichier
            // SMF de navigation...
            double scale, offset;

            // Dates :
            // La variable mbDate contient des dates juliennes...
            // Il n'est pas n�cessaire de demander le facteur d'�chelle et
            // l'offset
            // - pour la date l'offset est de 2 440 588, c'est � dire le
            // 01/01/1970 en
            // date julienne, l'�chelle est � 1
            // - pour l'heure elle est en ms, l'offset est 0 et l'�chelle 1.
            if (hasTime)
            {
                // Stocker les dates :
                Array arDates = ncDates.read();
                Array arTimes = ncTimes.read();
                Index indDate = arDates.getIndex();
                Index indTime = arTimes.getIndex();

                long lastDate;

                int indReal = 0;
                for (int index = 0; index < nbPointsInFile; index++)
                {
                    if ((flags[index] >= 2) && (flags[index] <= 8))
                    {
                        indDate.set(index);
                        indTime.set(index);
                        _navPathPoints.getDates()[indReal] =
                            Time.julianToEpoch(arDates.getLong(indDate),
                                arTimes.getLong(indTime));

                        if (indReal > 0
                                && (_navPathPoints.getDates()[indReal] < _navPathPoints
                                    .getDates()[indReal - 1]))
                        {
                            throw new EABException(
                                ExceptionCode.CHRONOLOGICAL_ERROR,
                                Globals.NVI_POINT_NUMBER
                                    .format(new String[] {String.valueOf(index)}));
                        }

                        indReal++;
                    }
                }

                // Lib�rer la m�moire de ce qui n'est plus n�cessaie :
                arDates = null;
                arTimes = null;
                indDate = null;
                indTime = null;
            }
            ncDates = null;
            ncTimes = null;

            // Latitudes :
            // Maintenant il faut prendre en compte les offsets et l'�chelle
            Variable ncPos = NetCDFTools.getVariable(nc, "mbOrdinate");
            att = ncPos.findAttribute("scale_factor");
            scale = att.getNumericValue().doubleValue();
            att = ncPos.findAttribute("add_offset");
            offset = att.getNumericValue().doubleValue();
            Array arPos = ncPos.read();
            int[] pts = (int[]) arPos.copyTo1DJavaArray();
            arPos = null;
            int indReal = 0;
            for (int index = 0; index < nbPointsInFile; index++)
            {
                if ((flags[index] >= 2) && (flags[index] <= 8))
                {
                    _navPathPoints.getLatitudes()[indReal] =
                        (pts[index] + offset) * scale;
                    indReal++;
                }
            }

            // Longitudes :
            ncPos = NetCDFTools.getVariable(nc, "mbAbscissa");
            att = ncPos.findAttribute("scale_factor");
            scale = att.getNumericValue().doubleValue();
            att = ncPos.findAttribute("add_offset");
            offset = att.getNumericValue().doubleValue();
            arPos = ncPos.read();
            pts = (int[]) arPos.copyTo1DJavaArray();
            arPos = null;
            indReal = 0;
            for (int index = 0; index < nbPointsInFile; index++)
            {
                if ((flags[index] >= 2) && (flags[index] <= 8))
                {
                    _navPathPoints.getLongitudes()[indReal] =
                        (pts[index] + offset) * scale;
                    indReal++;
                }
            }
            // Retour OK :
            return true;
        }
        catch (IOException e)
        {
            throw new EABException(ExceptionCode.IO_ERROR, e);
        }
        catch (Throwable e)
        {
            throw new EABException(ExceptionCode.UNKOWN_ERROR, e,
                Messages
                    .getString(Messages.MESSAGES, "WRONG_INPUT_FILE_FORMAT")); //$NON-NLS-1$
        }
        finally
        {
            if (null != nc)
            {
                try
                {
                    nc.close();
                }
                catch (IOException e)
                {
                    throw new EABException(ExceptionCode.IO_ERROR, e);
                }
            }
        }
    }
}
