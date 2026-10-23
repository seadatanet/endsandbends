/* ============================================================================
 * Nom du fichier	: Formatters.java
 * Auteur			: Altran
 * Date				: 11 juin 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import java.text.ParseException;

import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.common.format.LatitudeFormat;
import fr.ifremer.sismer.endsandbends.common.format.LongitudeFormat;

/**
 * Description: TODO
 * 
 * @author Pablo
 */
public class Formatters implements IFormatters
{

    private final UTCDateFormat _dateTimeFormat;
    private final UTCDateFormat _dateFormat;
    private final UTCDateFormat _timeFormat;

    private final LatitudeFormat _latitudeFormat;
    private final LongitudeFormat _longitudeFormat;

    public Formatters(String dateTimeFormat, String latitudeFormat,
            String longitudeFormat) throws ParseException
    {
        _dateTimeFormat = new UTCDateFormat(dateTimeFormat);
        _dateFormat = null;
        _timeFormat = null;

        _latitudeFormat = new LatitudeFormat(latitudeFormat);

        _longitudeFormat = new LongitudeFormat(longitudeFormat);
    }

    public Formatters(String dateFormat, String timeFormat,
            String latitudeFormat, String longitudeFormat)
            throws ParseException
    {
        _dateTimeFormat = null;
        _dateFormat = new UTCDateFormat(dateFormat);
        _timeFormat = new UTCDateFormat(timeFormat);

        _latitudeFormat = new LatitudeFormat(latitudeFormat);

        _longitudeFormat = new LongitudeFormat(longitudeFormat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UTCDateFormat getDateTimeFormat()
    {
        return _dateTimeFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LatitudeFormat getLatitudeFormat()
    {
        return _latitudeFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LongitudeFormat getLongitudeFormat()
    {
        return _longitudeFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UTCDateFormat getDateFormat()
    {
        return _dateFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UTCDateFormat getTimeFormat()
    {
        return _timeFormat;
    }

}
