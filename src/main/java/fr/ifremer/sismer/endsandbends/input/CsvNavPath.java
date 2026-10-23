/* ============================================================================
 * Nom du fichier	: CsvNavPath.java
 * Auteur			: Altran
 * Date				: Jun 9, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.input.csv.CsvEntry;
import fr.ifremer.sismer.endsandbends.input.csv.CsvEntryFactory;
import fr.ifremer.sismer.endsandbends.mediation.input.CsvInputParamsObject;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public class CsvNavPath extends ANavPath implements INavPath
{
    protected final CsvInputParamsObject _parameters;

    /**
     * @param parameters
     */
    public CsvNavPath(CsvInputParamsObject parameters)
    {
        super(null);

        _parameters = parameters;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     */
    @Override
    public boolean loadNavFile(String filePath) throws EABException
    {
        BufferedReader buff = null;

        int counter = 0;
        boolean isMinMaxLatLonSet = false;
        try
        {
            buff = new BufferedReader(new FileReader(filePath));

            CsvEntryFactory factory = new CsvEntryFactory(_parameters);
            factory.initialize();

            List<CsvEntry> entries = new ArrayList<CsvEntry>();

            Date lastDate = null;

            String line;
            CsvEntry entry;

            while ((line = buff.readLine()) != null)
            {
                counter++;

                if (isLineToRead(counter))
                {
                    entry =
                        factory.getNewEntryFrom(line,
                            _parameters.getDelimiterUsed());

                    if (entry.isValid())
                    {
                        entries.add(entry);

                        // on teste la chronologie des lignes
                        if (null != lastDate && lastDate.after(entry.getDate()))
                        {
                            throw new EABException(
                                ExceptionCode.CHRONOLOGICAL_ERROR,
                                getErrorLine(counter));
                        }

                        lastDate = entry.getDate();

                        if (!isMinMaxLatLonSet)
                        {
                            _minLatitude = entry.getLatitude();
                            _minLongitude = entry.getLongitude();
                            _maxLatitude = entry.getLatitude();
                            _maxLongitude = entry.getLongitude();
                            isMinMaxLatLonSet = true;
                        }
                        // on conserve les positions extr�mes
                        if (entry.getLatitude() < _minLatitude)
                        {
                            _minLatitude = entry.getLatitude();
                        }

                        if (entry.getLatitude() > _maxLatitude)
                        {
                            _maxLatitude = entry.getLatitude();
                        }

                        if (entry.getLongitude() < _minLongitude)
                        {
                            _minLongitude = entry.getLongitude();
                        }

                        if (entry.getLongitude() > _maxLongitude)
                        {
                            _maxLongitude = entry.getLongitude();
                        }
                    }
                }
            }

            int size = entries.size();

            if (size > 0)
            {
                _navPathPoints.setNbPoints(size);

                if (!_parameters.isDateTimeEnabled())
                {
                    _timeStarted = entries.get(0).getDate().getTime();
                    _timeEnded = entries.get(size - 1).getDate().getTime();
                }

                initializeArrays(!_parameters.isDateTimeEnabled());

                for (int i = 0; i < size; i++)
                {
                    entry = entries.get(i);

                    if (!_parameters.isDateTimeEnabled())
                    {
                        _navPathPoints.getDates()[i] = entry.getDate().getTime();
                    }

                    _navPathPoints.getLatitudes()[i] = entry.getLatitude();
                    _navPathPoints.getLongitudes()[i] = entry.getLongitude();
                }
            }
            else
            {
                throw new EABException(ExceptionCode.PARSING_ERROR,
                    Globals.NO_POINTS_FOUND);
            }
        }
        catch (EABException e)
        {
            throw e;
        }
        catch (FileNotFoundException e)
        {
            throw new EABException(ExceptionCode.IO_ERROR, e);
        }
        catch (IOException e)
        {
            throw new EABException(ExceptionCode.IO_ERROR, e);
        }
        catch (Exception e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e,
                getErrorLine(counter));
        }
        finally
        {
            if (null != buff)
            {
                try
                {
                    buff.close();
                }
                catch (IOException e)
                {
                    throw new EABException(ExceptionCode.IO_ERROR, e);
                }
            }
        }

        return true;
    }

    /**
     * Test s'il s'agit d'une ligne de donn�es
     * 
     * @param counter
     * @return
     */
    private boolean isLineToRead(int counter)
    {
        boolean valueToReturn = true;

        if (counter < _parameters.getFirstLine()
                || (_parameters.getLastLine() != 0 && counter > _parameters
                    .getLastLine()))
        {
            valueToReturn = false;
        }

        return valueToReturn;
    }

    private String getErrorLine(int counter)
    {
        return Globals.CSV_LINE_NUMBER.format(new String[] {String
            .valueOf(counter)});
    }
}
