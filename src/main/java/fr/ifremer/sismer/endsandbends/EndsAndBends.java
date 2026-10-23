package fr.ifremer.sismer.endsandbends;

import java.util.Locale;

import fr.ifremer.sismer.endsandbends.ui.MainForm;

/**
 * @author ATLANTIDE - ABT Point d'entr�e de l'application Ends & Bends Java
 */
public class EndsAndBends
{

    /**
     * Point d'entr�e de l'application
     * 
     * @param args
     *            arguments de la ligne de commande, aucun n'est exploit�
     */
    public static void main(String[] args)
    {
        if (null != args && args.length > 1 && "-nl".equals(args[0]) //$NON-NLS-1$
                && ("fr".equalsIgnoreCase(args[1]))) //$NON-NLS-1$
        {
            Locale.setDefault(Locale.FRENCH);
        }
        else
        {
            Locale.setDefault(Locale.ENGLISH);
        }

        MainForm form = new MainForm();
        form.showMain();
    }

}
