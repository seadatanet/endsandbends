/* ============================================================================
 * Nom du fichier	: NmeaTrameFactory.java
 * Auteur			: Altran
 * Date				: Jun 7, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input.nmea;

import fr.ifremer.sismer.endsandbends.common.Config;
import fr.ifremer.sismer.endsandbends.exception.EABException;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public class NmeaFrameFactory
{
    /**
     * Singleton
     */
    private static NmeaFrameFactory _instance;

    /**
     * Constructeur
     */
    private NmeaFrameFactory()
    {
    }

    /**
     * R�cup�ration du singleton
     * 
     * @return
     */
    public static NmeaFrameFactory getInstance()
    {
        if (null == _instance)
        {
            _instance = new NmeaFrameFactory();
        }

        return _instance;
    }

    /**
     * Construction d'une trame � partir d'une cha�ne
     * 
     * @param line
     * @return
     * @throws EABException
     */
    public INmeaFrame getNewFrameFrom(String line) throws EABException
    {
        INmeaFrame frame = null;

        int index = line.indexOf(',');

        if (index > 0)
        {
            String frameType = line.substring(0, index);

            if (Config.GGA_FRAME.equals(frameType))
            {
                frame = new GgaFrame(line);
            }
            else if (Config.RMC_FRAME.equals(frameType))
            {
                frame = new RmcFrame(line);
            }

            if (null != frame)
            {
                frame.initialize();
            }
        }

        return frame;
    }
}
