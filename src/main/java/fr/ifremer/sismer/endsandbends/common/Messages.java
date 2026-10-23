package fr.ifremer.sismer.endsandbends.common;

import java.text.Format;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

// Utilisation :
// System.out.println(StringTable.get("File_unfound", a_configFile.toString()));

/**
 * @author ATLANTIDE - ABT Classe StringTable, permettant d'isoler les textes
 *         d'interface
 */
public abstract class Messages
{
    public static final ResourceBundle MESSAGES =
        ResourceBundle
            .getBundle("fr.ifremer.sismer.endsandbends.resources.messages"); //$NON-NLS-1$

    public static final ResourceBundle CONFIG =
        ResourceBundle
            .getBundle("fr.ifremer.sismer.endsandbends.resources.config"); //$NON-NLS-1$

    /**
     * Method get, statique, permet de r�cup�rer une cha�ne de caract�re
     * 
     * @param key
     *            clef identifiant la cha�ne
     * @return String chaine demand�e
     */
    public static String getString(ResourceBundle bundle, String key)
    {
        try
        {
            return bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }

    /**
     * Method get, statique, permet de r�cup�rer une cha�ne de caract�re
     * 
     * @param key
     *            clef identifiant la cha�ne
     * @return String chaine demand�e
     * @param aMessageArguments
     *            arguments du message
     */
    public static String getString(ResourceBundle bundle, String key,
            String[] aMessageArguments)
    {
        try
        {
            MessageFormat messageForm =
                new MessageFormat(bundle.getString(key));
            Format[] formats = new Format[aMessageArguments.length];
            messageForm.setFormats(formats);
            String res = messageForm.format(aMessageArguments);
            return res;
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }

    /**
     * Method get, statique, permet de r�cup�rer une cha�ne de caract�re
     * 
     * @param key
     *            clef identifiant la cha�ne
     * @return String chaine demand�e
     * @param aMessageArguments
     *            arguments du message
     */
    public static String getString(ResourceBundle bundle, String key,
            String aMessageArgument)
    {
        try
        {
            MessageFormat messageForm = new MessageFormat("");
            String pattern = bundle.getString(key);
            messageForm.applyPattern(pattern);
            Format[] formats = {null};
            messageForm.setFormats(formats);
            Object[] messageArguments = new Object[1];
            messageArguments[0] = aMessageArgument;
            String res = messageForm.format(messageArguments);
            return res;
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }
}
