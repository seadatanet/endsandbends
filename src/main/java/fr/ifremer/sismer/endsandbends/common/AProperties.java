/* ============================================================================
 * Nom du fichier	: AProperties.java
 * Auteur			: Altran
 * Date				: 28 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

import java.awt.Color;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

import fr.ifremer.sismer.endsandbends.common.format.LatitudeFormat;
import fr.ifremer.sismer.endsandbends.common.format.LongitudeFormat;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;

/**
 * Classe abstraite contenant les m�thodes de r�cup�ration des �l�ments d'un
 * Properties � partir d'une cl�
 * 
 * @author htaniou
 */
public abstract class AProperties
{
    /**
     * ResourceBundle
     */
    protected static ResourceBundle BUNDLE;
    protected static String URL;

    /**
     * Retourne une cha�ne � partir d'une cl�
     * 
     * @param key
     * @return
     */
    protected static String getString(String key)
    {
        return Messages.getString(BUNDLE, key);
    }

    /**
     * Retourne un SimpleDateFormat � partir d'une cl�
     * 
     * @param key
     * @return
     */
    protected static UTCDateFormat getDateFormat(String key)
    {
        return new UTCDateFormat(getString(key));
    }

    /**
     * Retourne un MessageFormat � partir d'une cl�
     * 
     * @param key
     * @return
     */
    protected static MessageFormat getMessageFormat(String key)
    {
        return new MessageFormat(getString(key));
    }

    /**
     * Retourne un LatitudeFormat � partir d'une cl�
     * 
     * @param key
     * @return
     * @throws ParseException
     */
    protected static LatitudeFormat getLatitudeFormat(String key)
    {
        LatitudeFormat format = new LatitudeFormat();

        try
        {
            format.setFormatString(getString(key));
        }
        catch (ParseException e)
        {
            EABException.displayFatalError(getException(e, key));
        }

        return format;
    }

    /**
     * Retourne un LongitudeFormat � partir d'une cl�
     * 
     * @param key
     * @return
     * @throws ParseException
     */
    protected static LongitudeFormat getLongitudeFormat(String key)
    {
        LongitudeFormat format = new LongitudeFormat();

        try
        {
            format.setFormatString(getString(key));
        }
        catch (ParseException e)
        {
            EABException.displayFatalError(getException(e, key));
        }

        return format;
    }

    /**
     * Retourne une Color � partir d'une cl�<BR>
     * En cas de probl�me, la couleur noire est retourn�e
     * 
     * @param key
     * @return
     */
    protected static Color getColor(String key)
    {
        Color color = null;

        try
        {
            String rgb = getString(key);
            String[] components = rgb.split(","); //$NON-NLS-1$

            if (3 == components.length)
            {
                int r, g, b;
                r = Integer.parseInt(components[0]);
                g = Integer.parseInt(components[1]);
                b = Integer.parseInt(components[2]);
                color = new Color(r, g, b);
            }
        }
        catch (Exception e)
        {
            EABException.displayFatalError(getException(e, key));
        }

        if (null == color)
        {
            color = Color.BLACK;
        }

        return color;
    }

    /**
     * Retourne un entier � partir d'une cl�
     * 
     * @param key
     * @return
     */
    protected static int getInt(String key)
    {
        int integer = 0;

        try
        {
            integer = Integer.parseInt(getString(key));
        }
        catch (Exception e)
        {
            EABException.displayFatalError(getException(e, key));
        }

        return integer;
    }

    protected static EABException getException(Exception e, String key)
    {
        return new EABException(ExceptionCode.PROPERTIES_CONTENT_ERROR, e,
            Globals.PROPERTIES_CONTENT_ERROR_MESSAGE.format(new String[] {URL,
                    key}));
    }
}
