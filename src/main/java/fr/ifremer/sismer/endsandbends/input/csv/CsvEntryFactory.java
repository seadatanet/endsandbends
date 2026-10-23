/* ============================================================================
 * Nom du fichier	: CsvEntryFactory.java
 * Auteur			: Altran
 * Date				: Jun 9, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input.csv;

import java.text.ParseException;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.input.Formatters;
import fr.ifremer.sismer.endsandbends.input.IFormatters;
import fr.ifremer.sismer.endsandbends.mediation.input.CsvInputParamsObject;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public class CsvEntryFactory
{
    private final CsvInputParamsObject _parameters;

    private IFormatters _formatters;

    public CsvEntryFactory(CsvInputParamsObject parameters)
    {
        _parameters = parameters;
    }

    public void initialize() throws EABException
    {
        String dateFormat = ""; //$NON-NLS-1$

        if (!_parameters.isDateTimeEnabled())
        {
            if (_parameters.isJoinDateTimeParamsEnabled())
            {
                dateFormat = _parameters.getDateTimeFormat();
            }
            else
            {
                dateFormat =
                    new StringBuilder(_parameters.getDateFormat()).append(
                        _parameters.getTimeFormat()).toString();
            }
        }

        try
        {
            _formatters =
                new Formatters(dateFormat, _parameters.getLatitudeFormat(),
                    _parameters.getLongitudeFormat());
        }
        catch (ParseException e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e);
        }
    }

    public CsvEntry getNewEntryFrom(String line, String separator)
            throws EABException
    {
        CsvEntry entry = new CsvEntry();

        entry.initialize(line, _parameters, _formatters, separator);

        return entry;
    }
}
