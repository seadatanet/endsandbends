/* ============================================================================
 * Nom du fichier	: GgaFrame.java
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
public class GgaFrame extends ANmeaFrame implements INmeaFrame
{

    private Date _time;

    private double _latitude, _longitude;

    private double _hdop;

    /**
     * Constructeur
     * 
     * @param elements
     */
    public GgaFrame(String frame)
    {
        super(frame);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinElementNumber()
    {
        return 9;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     */
    @Override
    protected void setElements() throws EABException
    {
        // $GPGGA,064036.289,4836.5375,N,00740.9373,E,1,04,3.2,200.2,M,,,,0000*0E
        try
        {
            _time = Config.GGA_TIME_FORMAT.parse(_elements[1]);
            _latitude = parseLatitude(_elements[2], _elements[3]);
            _longitude = parseLongitude(_elements[4], _elements[5]);
            _hdop = Double.parseDouble(_elements[8]);
        }
        catch (Exception e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e);
        }
    }
}
