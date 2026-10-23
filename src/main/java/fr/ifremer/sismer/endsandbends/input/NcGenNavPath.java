package fr.ifremer.sismer.endsandbends.input;

import java.io.IOException;
import java.text.ParseException;

import fr.ifremer.sismer.endsandbends.common.NetCDFTools;
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.common.format.LatitudeFormat;
import fr.ifremer.sismer.endsandbends.common.format.LongitudeFormat;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.mediation.input.NetCdfInputParamsObject;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * @author ATLANTIDE - ABT Chemin de navigation principal, c'est � dire qu'il
 *         faut d�compos� en trac�s continus...
 */
public class NcGenNavPath extends ANavPath implements INavPath
{

    private final NetCdfInputParamsObject _ncGenParams;

    /**
     * Constructor for MainNavPath.
     * 
     * @param ncGenParams
     */
    public NcGenNavPath(NetCdfInputParamsObject ncGenParams)
    {
        super(null);

        _ncGenParams = ncGenParams;
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

            Variable ncLatitudes =
                NetCDFTools.getVariable(nc,
                    _ncGenParams.getLatitudeVariableName());

            Variable ncLongitudes =
                NetCDFTools.getVariable(nc,
                    _ncGenParams.getLongitudeVariableName());

            Array arrLatitudes = ncLatitudes.read();
            Array arrLongitudes = ncLongitudes.read();

            Long[] latitudesAsString =
                (Long[]) arrLatitudes.get1DJavaArray(Long.class);

            Long[] longitudesAsString =
                (Long[]) arrLongitudes.get1DJavaArray(Long.class);

            // R�cup�rer les variables de temps, elles peuvent �tre absentes :
            String[] dateTimesAsStrings = null;
            UTCDateFormat dateFormat = new UTCDateFormat();

            if (_ncGenParams.isJoinDateTimeParamsEnabled())
            {
// dateFormat.applyPattern(_ncGenParams.getDateTimeFormat());

                Variable ncDateTimes =
                    NetCDFTools.getVariable(nc,
                        _ncGenParams.getDateTimeVariableName());

                Array arDateTimes = ncDateTimes.read();

                dateTimesAsStrings =
                    (String[]) arDateTimes.get1DJavaArray(String.class);
            }
            else
            {
// dateFormat.applyPattern(_ncGenParams.getDateFormat()
// + _ncGenParams.getTimeFormat());

                Variable ncDates =
                    NetCDFTools.getVariable(nc,
                        _ncGenParams.getDateVariableName());

                Variable ncTimes =
                    NetCDFTools.getVariable(nc,
                        _ncGenParams.getTimeVariableName());

                Array arDates = ncDates.read();
                Array arTimes = ncTimes.read();

                String[] datesAsStrings =
                    (String[]) arDates.get1DJavaArray(String.class);
                String[] timesAsStrings =
                    (String[]) arTimes.get1DJavaArray(String.class);

                int length = datesAsStrings.length;

                dateTimesAsStrings = new String[length];

                StringBuilder buffer = new StringBuilder();

                for (int i = 0; i < length; i++)
                {
                    dateTimesAsStrings[i] =
                        buffer.append(datesAsStrings[i])
                            .append(timesAsStrings[i]).toString();
                    buffer.setLength(0);
                }
            }

            _navPathPoints.setNbPoints(latitudesAsString.length);
            initializeArrays(null != dateTimesAsStrings);

            if (null != dateTimesAsStrings)
            {
                try
                {
                    for (int i = 0; i < _navPathPoints.getNbPoints(); i++)
                    {
                        _navPathPoints.getDates()[i] =
                            dateFormat.parse(dateTimesAsStrings[i]).getTime();
                    }
                }
                catch (ParseException e)
                {
                    throw new EABException(ExceptionCode.PARSING_ERROR, e);
                }

                analyseDates();
            }

            LatitudeFormat latitudeFormat =
// new LatitudeFormat(_ncGenParams.getLatitudeFormat());
                new LatitudeFormat();
            LongitudeFormat longitudeFormat =
// new LongitudeFormat(_ncGenParams.getLongitudeFormat());
                new LongitudeFormat();

            double latitude, longitude;

            for (int i = 0; i < _navPathPoints.getNbPoints(); i++)
            {
                // latitude
                latitude = 0;
// ((Number) latitudeFormat.parseObject(latitudesAsString[i]))
// .doubleValue();

                _navPathPoints.getLatitudes()[i] = latitude;

                if (latitude < _minLatitude)
                {
                    _minLatitude = latitude;
                }

                if (latitude > _maxLatitude)
                {
                    _maxLatitude = latitude;
                }

                // longitude
                longitude = 0;
// ((Number) longitudeFormat
// .parseObject(longitudesAsString[i])).doubleValue();

                _navPathPoints.getLongitudes()[i] = longitude;

                if (longitude < _minLongitude)
                {
                    _minLongitude = longitude;
                }

                if (longitude > _maxLongitude)
                {
                    _maxLongitude = longitude;
                }
            }

            // Retour OK :
            return true;
        }
        catch (IOException e)
        {
            throw new EABException(ExceptionCode.IO_ERROR, e);
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
// catch (ParseException e)
// {
// throw new EABException(ExceptionCode.PARSING_ERROR, e);
// }
    }
}
