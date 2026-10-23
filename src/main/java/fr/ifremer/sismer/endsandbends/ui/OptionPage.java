package fr.ifremer.sismer.endsandbends.ui;

import javax.swing.JPanel;

/**
 * @author ATLANTIDE - ABT Interface partag�e par toutes les pages de
 *         OptionSheet...
 */
public abstract class OptionPage extends JPanel
{
    /**
     * Method UpdateData.
     * 
     * @param bSaveAndValidate
     *            true pour sauver, false pour r�initialiser...
     * @return boolean true si OK pour sauver, false s'il y a une erreur
     */
    public boolean updateData(boolean bSaveAndValidate)
    {
        return true;
    }

    /**
     * Method onActivated, appel�e quand le panneau est activ�
     */
    public void onActivated()
    {
    }

    /**
     * Method onDestroy, appel�e quand le panneau est d�truit
     */
    public void onDestroy()
    {
    }
}
