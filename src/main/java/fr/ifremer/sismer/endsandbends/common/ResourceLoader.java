package fr.ifremer.sismer.endsandbends.common;

import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * @author ATLANTIDE - ABT Classe utilitaire pour r�cup�rer des fichiers de
 *         ressources
 */
public class ResourceLoader
{
    /**
     * Method getStream, renvoie un fichier stocker avec les classes java sous
     * la forme d'un InputStream;
     * 
     * @param path
     * @param ref
     *            objet java r�f�rence
     * @return InputSource
     */
    public static InputStream getStream(Object ref, String path)
    {
        if (ref == null)
            ref = new ResourceLoader();
        return ref.getClass().getResourceAsStream(path);
    }

    public static URL getURL(Object ref, String path)
    {
        if (ref == null)
            ref = new ResourceLoader();
        return ref.getClass().getResource(path);
    }

    public static ImageIcon getImage(Object ref, String path)
    {
        URL url = getURL(ref, path);
        return new ImageIcon(url);
    }
}
