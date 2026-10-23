/* ============================================================================
 * Nom du fichier	: ColumnWriter.java
 * Auteur			: Altran
 * Date				: Jun 16, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.output;

import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import fr.ifremer.sismer.endsandbends.common.AppException;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.enums.ColumnType;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.input.Formatters;
import fr.ifremer.sismer.endsandbends.input.IFormatters;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints;
import fr.ifremer.sismer.endsandbends.mediation.output.AsciiColumnOutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.NavigationData;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public class ColumnWriter extends AEndsAndBendsWriter
{
    private final AsciiColumnOutputParamsObject _columnParameters;

    private final IFormatters _formatters;

    /**
     * @param main
     * @param columnParameters
     * @throws EABException
     */
    public ColumnWriter(JFrame main, NavigationData data,
            AsciiColumnOutputParamsObject columnParameters) throws EABException
    {
        super(main, data);

        _columnParameters = columnParameters;

        try
        {
            if (_columnParameters.isJoinDateTimeParamsEnabled())
            {
                _formatters =
                    new Formatters(_columnParameters.getDateTimeFormat(),
                        _columnParameters.getLatitudeFormat(),
                        _columnParameters.getLongitudeFormat());
            }
            else
            {
                _formatters =
                    new Formatters(_columnParameters.getDateFormat(),
                        _columnParameters.getTimeFormat(),
                        _columnParameters.getLatitudeFormat(),
                        _columnParameters.getLongitudeFormat());
            }
        }
        catch (ParseException e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     */
    @Override
    public void writeFile(NavPathPoints path, int optimPoints)
            throws EABException
    {
        // R�cup�rer le fichier :
        File file = _columnParameters.getSelectedFile();

        if (null != file)
        {
            try
            {
                _printWriter = new PrintWriter(new FileOutputStream(file));
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Calculer la progression :
            int nProgressRange = optimPoints;
            int nProgressPosition = 1;

            // Initialiser la progression :
            ProgressMonitor progress =
                new ProgressMonitor(_mainFrame, Messages.getString(
                    Messages.MESSAGES, "prog_save"), "", 0, nProgressRange);
            Cursor oldone = _mainFrame.getCursor();
            _mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            _mainFrame.setEnabled(false);

            // Protection pour sortir correctement dans tous les cas
            // d'erreur...
            try
            {
                Map<Integer, ColumnType> columnMap =
                    new TreeMap<Integer, ColumnType>();

                if (!_data._isDateIgnored)
                {
                    if (_columnParameters.isJoinDateTimeParamsEnabled())
                    {
                        columnMap.put(
                            _columnParameters.getDateTimeFormatColumnNumber(),
                            ColumnType.DATETIME);
                    }
                    else
                    {
                        columnMap.put(
                            _columnParameters.getDateFormatColumnNumber(),
                            ColumnType.DATE);

                        columnMap.put(
                            _columnParameters.getTimeFormatColumnNumber(),
                            ColumnType.TIME);
                    }
                }

                columnMap.put(
                    _columnParameters.getLatitudeFormatColumnNumber(),
                    ColumnType.LATITUDE);

                columnMap.put(
                    _columnParameters.getLongitudeFormatColumnNumber(),
                    ColumnType.LONGITUDE);

                columnMap.put(
                    _columnParameters.getStartEndIndicatorColumnNumber(),
                    ColumnType.STARTEND);

                Collection<ColumnType> columns = columnMap.values();

                String separator = _columnParameters.getTokenizer();

                // Traitement d'un profil :
                int modelp;
                int number = 0;
                int model;
                for (int i = 0; i < _data._paths.length; i++)
                {
                    // R�cup�rer le parcours :
                    path = _data._paths[i];
                    model = path.getSelectedModel();


                    // Parcours des points :
                    int count = path.getPointsCount();
                    for (int index = 0; index < count; index++)
                    {
                        // Sauter s'il n'est pas dans le mod�le :
                        modelp = path.getModel(index);
                        if ((model != 0) && ((modelp == 0) || (modelp > model)))
                            continue;
                        number++;

                        String startEndValue;

                        if ((index == 0) && path.needStartIndicator())
                        {
                            startEndValue =
                                formatString(10,
                                    _columnParameters.getStartIndicator());
                        }
                        else if ((index == count - 1)
                                && path.needEndIndicator())
                        {
                            startEndValue =
                                formatString(10,
                                    _columnParameters.getEndIndicator());
                        }
                        else
                        {
                            startEndValue = formatString(10, ""); //$NON-NLS-1$
                        }

                        for (ColumnType columnType : columns)
                        {
                            writeColumn(columnType, path, index, separator,
                                startEndValue);
                        }

                        // Ecrire une ligne :

// _printWriter.print("41");
// printInteger(3, ' ', number);
// _printWriter.print("     ");
// printDate("AAMMJJHHmmss", path.getDate(index));
// printPosition(2, 2, 2, path.getLatitude(index),
// AsciiWriter.ROUND);
// printPosition(3, 2, 2, path.getLongitude(index),
// AsciiWriter.ROUND);
// _printWriter.print("   ");

                        _printWriter.println();

                        // Progression :
                        progress.setProgress(nProgressPosition++);
                        if (progress.isCanceled())
                            throw new AppException(Messages.getString(
                                Messages.MESSAGES, "error_canceled"));
                    }
                }

            }
            catch (AppException ue)
            {
                JOptionPane.showMessageDialog(_mainFrame, ue.getMessage(),
                    Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                    JOptionPane.ERROR_MESSAGE);
            }
            finally
            {
                // Fin de traitement :
                progress.close();
                _mainFrame.setCursor(oldone);
                _mainFrame.setEnabled(true);

                // Fermer le writer :
                _printWriter.close();
            }
        }
    }

    /**
     * @param value
     * @param path
     * @param index
     * @param separator
     * @param startEndValue
     * @throws EABException
     */
    private void writeColumn(ColumnType columnType, NavPathPoints path,
            int index, String separator, String startEndValue)
            throws EABException
    {
        String stringToWrite;

        switch (columnType)
        {
            case DATE:
                stringToWrite = getDateStringValue(path, index);
            break;

            case TIME:
                stringToWrite = getTimeStringValue(path, index);
            break;

            case DATETIME:
                stringToWrite = getDateTimeStringValue(path, index);
            break;

            case LATITUDE:
                stringToWrite = getLatitudeStringValue(path, index);
            break;

            case LONGITUDE:
                stringToWrite = getLongitudeStringValue(path, index);
            break;

            case STARTEND:
                stringToWrite = startEndValue;
            break;

            default:
                throw new EABException(ExceptionCode.ARGUMENT_NULL_OR_EMPTY);
        }

        _printWriter.print(stringToWrite);
        _printWriter.print(separator);
    }

    /**
     * @param path
     * @param index
     * @return
     */
    private String getLongitudeStringValue(NavPathPoints path, int index)
    {
        return _formatters.getLongitudeFormat()
            .format(path.getLongitude(index));
    }

    /**
     * @param path
     * @param index
     * @return
     */
    private String getLatitudeStringValue(NavPathPoints path, int index)
    {
        return _formatters.getLatitudeFormat().format(path.getLatitude(index));
    }

    /**
     * @param path
     * @param index
     * @return
     */
    private String getDateTimeStringValue(NavPathPoints path, int index)
    {
        if (path.getDate(index) != null)
        {
            return _formatters.getDateTimeFormat().format(
                getCorrectedDate(path, index));
        }
        else
        {
            return new String();
        }
    }

    /**
     * @param path
     * @param index
     * @return
     */
    private String getTimeStringValue(NavPathPoints path, int index)
    {
        if (path.getDate(index) != null)
        {
            return _formatters.getTimeFormat().format(
                getCorrectedDate(path, index));
        }
        else
        {
            return new String();
        }
    }

    /**
     * @param path
     * @param index
     */
    private String getDateStringValue(NavPathPoints path, int index)
    {
        if (path.getDate(index) != null)
        {
            return _formatters.getDateFormat().format(
                getCorrectedDate(path, index));
        }
        else
        {
            return new String();
        }
    }

    /**
     * @param path
     * @param index
     * @return
     */
    private Date getCorrectedDate(NavPathPoints path, int index)
    {
        return new Date(path.getDate(index).getTime() + 3600000
                * _columnParameters.getTimeCorrection());
    }
}
