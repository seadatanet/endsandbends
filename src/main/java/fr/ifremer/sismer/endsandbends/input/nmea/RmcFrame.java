/* ============================================================================
 * Nom du fichier	: RmcFrame.java
 * Auteur			: Altran
 * Date				: Jun 7, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input.nmea;

import java.util.Date;

import fr.ifremer.sismer.endsandbends.common.Config;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public class RmcFrame extends ANmeaFrame implements INmeaFrame
{
    private Date _date;

    private double _latitude;

    private double _longitude;

    private boolean _isValid;

    /**
     * Constructeur
     * 
     * @param elements
     */
    protected RmcFrame(String frame)
    {
        super(frame);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinElementNumber()
    {
        return 10;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     */
    @Override
    protected void setElements() throws EABException
    {
        // $GPRMC,212949,A,4915.61,N,12310.55,W,000.0,360.0,111198,020.3,E*6B

        _isValid = "A".equals(_elements[2]); //$NON-NLS-1$

        StringBuilder dateTime =
            new StringBuilder(_elements[9]).append(_elements[1]);

        if (dateTime.length() == 12)
        {
            dateTime.append(".000"); //$NON-NLS-1$
        }

        try
        {
            _date = Config.RMC_DATE_FORMAT.parse(dateTime.toString());
            _latitude = parseLatitude(_elements[3], _elements[4]);
            _longitude = parseLongitude(_elements[5], _elements[6]);
        }
        catch (Exception e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e);
        }
    }
}
