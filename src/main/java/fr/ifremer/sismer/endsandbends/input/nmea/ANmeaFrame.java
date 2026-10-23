/* ============================================================================
 * Nom du fichier	: ANmeaFrame.java
 * Auteur			: Altran
 * Date				: Jun 7, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input.nmea;

import fr.ifremer.sismer.endsandbends.common.Config;
import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public abstract class ANmeaFrame implements INmeaFrame
{
    /**
     * Trame
     */
    protected final String _frameLine;

    /**
     * El�ments de la trame
     */
    protected final String[] _elements;

    /**
     * Constructeur
     * 
     * @param elements
     */
    protected ANmeaFrame(String frame)
    {
        _frameLine = frame;

        _elements = frame.split(Globals.COMMA);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     * @throws
     * @throws FrameParsingException
     */
    @Override
    public void initialize() throws EABException
    {
        checkElementNumber();

        checkSum();

        setElements();
    }

    /**
     * @throws EABException
     */
    protected void setElements() throws EABException
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not Implemented yet !"); //$NON-NLS-1$
    }

    /**
     * @throws EABException
     */
    protected void checkElementNumber() throws EABException
    {
        if (_elements.length < getMinElementNumber())
        {
            throw new EABException(ExceptionCode.WRONG_NUMBER_OF_ELEMENTS,
                Config.WRONG_NUMBER_OF_ELEMENTS.format(new String[] {
                        String.valueOf(getMinElementNumber()),
                        String.valueOf(_elements.length)}));
        }
    }

    /**
     * @throws ChecksumException
     */
    protected void checkSum() throws EABException
    {
        int index = _frameLine.lastIndexOf('*');

        if (index > 1)
        {
            int readSum;

            try
            {
                readSum =
                    Integer
                        .parseInt(_frameLine.substring(index + 1).trim(), 16);
            }
            catch (NumberFormatException e)
            {
                String exMsg = new StringBuilder("\"").append( //$NON-NLS-1$
                    _frameLine.substring(index + 1)).append("\"").append( //$NON-NLS-1$
                    " cannot be parsed as a hexadecimal number").toString(); //$NON-NLS-1$
                throw new EABException(ExceptionCode.CHECKSUM_EXCEPTION,
                    "\" cannot be parsed as a hexadecimal number", exMsg); //$NON-NLS-1$
            }

            byte[] bytes = _frameLine.substring(1, index).getBytes();

            int computeSum = 0;
            for (byte b : bytes)
            {
                computeSum = computeSum ^ b;
            }

            if (readSum != computeSum)
            {
                String exMsg =
                    new StringBuilder("Checksum error : expected:").append( //$NON-NLS-1$
                        readSum).append(" - computed:").append(computeSum) //$NON-NLS-1$
                        .toString();
                throw new EABException(ExceptionCode.CHECKSUM_EXCEPTION, exMsg);
            }
        }
    }

    public static void main(String[] args) throws EABException
    {
        INmeaFrame frame =
// new GgaFrame(
// "$GPGGA,064036.289,4836.5375,N,00740.9373,E,1,04,3.2,200.2,M,,,,0000*0R");
            new RmcFrame(
                "$GPRMC,053740.000,A,2503.6319,N,12136.0099,E,2.69,79.65,100106,,,,,A*53"); //$NON-NLS-1$

        frame.initialize();
    }

    protected double parseLatitude(String latitude, String sign)
    {
        double valueToReturn = 0;

        int degrees = Integer.parseInt(latitude.substring(0, 2));
        double minutes = Double.parseDouble(latitude.substring(2));

        valueToReturn = degrees + minutes / 60;

        if (Globals.SOUTH.equals(sign))
        {
            valueToReturn = -valueToReturn;
        }

        return valueToReturn;
    }

    protected double parseLongitude(String longitude, String sign)
    {
        double valueToReturn = 0;

        int degrees = Integer.parseInt(longitude.substring(0, 3));
        double minutes = Double.parseDouble(longitude.substring(3));

        valueToReturn = degrees + minutes / 60;

        if (Globals.WEST.equals(sign))
        {
            valueToReturn = -valueToReturn;
        }

        return valueToReturn;
    }
}
