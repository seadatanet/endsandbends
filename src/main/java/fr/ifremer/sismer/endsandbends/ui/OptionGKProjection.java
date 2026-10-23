package fr.ifremer.sismer.endsandbends.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import fr.ifremer.sismer.endsandbends.algorithm.GaussKruegerProjection;
import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Page de propri�t�s pour tous les seuils utilis�s dans
 *         le programme
 */
public class OptionGKProjection extends OptionPage
{
    // Contr�les :
    private final JLabel _ctrlLabelDistCut = new JLabel();
    private final JFormattedTextField _ctrlDistCut = new JFormattedTextField();
    private final JLabel _ctrlLabelDistLimit = new JLabel();
    private final JFormattedTextField _ctrlDistLimit =
        new JFormattedTextField();
    private final JLabel _ctrlLabelHelp = new JLabel();

    // Donn�es internes :
    private double _distCut = GaussKruegerProjection.getCuttingDistance();
    private double _distLimit = GaussKruegerProjection.getLimitDistance();

    /**
     * Constructeur
     */
    public OptionGKProjection()
    {
        // Cr�er les composants (contr�les) :
        initComponents();

        // Autre param�tres de la fen�tre principale :
        setSize(400, 300);
    }

    /**
     * Method initComponents : cr�ation des contr�les de la fen�tre principale
     * 
     * @return boolean tjs 'true'
     */
    private void initComponents()
    {
        // D'abord initialiser le Layout :
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints;

        // Texte :
        _ctrlLabelDistLimit.setText("<html><body>" //$NON-NLS-1$
                + Messages.getString(Messages.MESSAGES, "label_dist_limit") //$NON-NLS-1$
                + "</body></html>"); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        add(_ctrlLabelDistLimit, gridBagConstraints);

        // Edit :
        _ctrlDistLimit.setValue(new Double(_distLimit));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        add(_ctrlDistLimit, gridBagConstraints);

        // Texte :
        _ctrlLabelDistCut.setText("<html><body>" //$NON-NLS-1$
                + Messages.getString(Messages.MESSAGES, "label_dist_cut") //$NON-NLS-1$
                + "</body></html>"); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        add(_ctrlLabelDistCut, gridBagConstraints);

        // Edit :
        _ctrlDistCut.setValue(new Double(_distCut));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        add(_ctrlDistCut, gridBagConstraints);

        // Aide :
        _ctrlLabelHelp.setText("<html><body>" //$NON-NLS-1$
                + Messages.getString(Messages.MESSAGES, "label_help_optgkproj") //$NON-NLS-1$
                + "</body></html>"); //$NON-NLS-1$
        _ctrlLabelHelp.setMinimumSize(new Dimension(400, 100));
        _ctrlLabelHelp.setPreferredSize(new Dimension(400, 100));
        _ctrlLabelHelp.setMaximumSize(new Dimension(400, 100));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        _ctrlLabelHelp.setFont(_ctrlLabelHelp.getFont().deriveFont(0));
        add(_ctrlLabelHelp, gridBagConstraints);
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.ui.OptionPage#updateData(boolean)
     */
    @Override
    public boolean updateData(boolean bSaveAndValidate)
    {
        Number value = (Number) _ctrlDistLimit.getValue();
        if (value != null)
            _distLimit = value.doubleValue();
        value = (Number) _ctrlDistCut.getValue();
        if (value != null)
            _distCut = value.doubleValue();
        return true;
    }

    /**
     * Returns the distCut.
     * 
     * @return double
     */
    public double getDistCut()
    {
        return _distCut;
    }

    /**
     * Returns the distLimit.
     * 
     * @return double
     */
    public double getDistLimit()
    {
        return _distLimit;
    }

}
