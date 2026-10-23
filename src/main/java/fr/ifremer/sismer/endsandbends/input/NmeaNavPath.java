/* ============================================================================
 * Nom du fichier	: NmeaNavPath.java
 * Auteur			: Altran
 * Date				: Jun 7, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.input.nmea.INmeaFrame;
import fr.ifremer.sismer.endsandbends.input.nmea.NmeaFrameFactory;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public class NmeaNavPath extends ANavPath implements INavPath
{

    /**
     * @param dateSegmentList
     */
    protected NmeaNavPath()
    {
        super(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loadNavFile(String filePath)
    {
        boolean processOK = true;

        List<INmeaFrame> frames = new ArrayList<INmeaFrame>();

        try
        {
            // Cr�ation du flux buff�ris� sur un FileReader, imm�diatement suivi
            // par un
            // try/finally, ce qui permet de ne fermer le flux QUE s'il le
            // reader
            // est correctement instanci� (�vite les NullPointerException)
            BufferedReader buff = new BufferedReader(new FileReader(filePath));

            int counter = 0;

            try
            {
                INmeaFrame frame;

                String line;

                // Lecture du fichier ligne par ligne. Cette boucle se termine
                // quand la m�thode retourne la valeur null.
                while ((line = buff.readLine()) != null)
                {
                    counter++;

                    frame =
                        NmeaFrameFactory.getInstance().getNewFrameFrom(line);

                    if (null != frame)
                    {
                        frames.add(frame);
                    }
                }
            }
            catch (EABException e)
            {
                processOK = false;

                e.printStackTrace();
            }
            finally
            {
                // dans tous les cas, on ferme nos flux
                buff.close();
            }
        }
        catch (IOException ioe)
        {
            processOK = false;
            // erreur de fermeture des flux
// System.out.println("Erreur --" + ioe.toString());
        }

        return processOK;
    }

}
