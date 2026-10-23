/* ============================================================================
 * Nom du fichier	: Messages.java
 * Auteur			: Altran
 * Date				: 20 mai 2011
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Description: TODO
 * @author sbregent
 *
 */
public class Messages
{
    private static final String BUNDLE_NAME =
        "ifremer.sismer.endsandbends.input.ukooa"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
        .getBundle(BUNDLE_NAME);

    private Messages()
    {
    }

    public static String getString(String key)
    {
        try
        {
            return RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }
}
