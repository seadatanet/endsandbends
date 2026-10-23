package fr.ifremer.sismer.endsandbends.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Bo�te de dialogue utilis�e pour le calcul personalis�
 */
public class DialogUserCompute extends JDialog
{
    // Contr�les :
    private final JLabel _ctrlLabelNbPoints = new JLabel();
    private final JFormattedTextField _ctrlNbPoints = new JFormattedTextField();
// private final JLabel _ctrlLabelHelp = new JLabel();
    private final JButton _ctrlBtnOK = new JButton();
    private final JButton _ctrlBtnCancel = new JButton();

    // Donn�es internes :
    private int _pointsLimit = 0;
    private boolean _ok = false; // validit� de la bo�te

    /**
     * Constructor for DialogUserCompute.
     */
    public DialogUserCompute(Frame parent, int points)
    {
        super(parent, Messages.getString(Messages.MESSAGES,
            "title_dlg_usercompute"), true, null); //$NON-NLS-1$
        _pointsLimit = points;
        initComponents();
    }

    /**
     * Cr�ation des contr�les
     */
    private void initComponents()
    {
        // D'abord initialiser le Layout :
        getContentPane().setLayout(new GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;

        // Lib�ll� :
        _ctrlLabelNbPoints.setText(Messages.getString(Messages.MESSAGES,
            "label_nbpoints")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        getContentPane().add(_ctrlLabelNbPoints, gridBagConstraints);

        // Edit du nombre de points :
        _ctrlNbPoints.setValue(Integer.valueOf(_pointsLimit));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.0;
        getContentPane().add(_ctrlNbPoints, gridBagConstraints);

        // Ajouter les boutons :
        _ctrlBtnCancel.setText(Messages.getString(Messages.MESSAGES,
            "btn_cancel")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        _ctrlBtnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });
        getContentPane().add(_ctrlBtnCancel, gridBagConstraints);
        _ctrlBtnOK.setText(Messages.getString(Messages.MESSAGES, "btn_ok")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        _ctrlBtnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });
        getContentPane().add(_ctrlBtnOK, gridBagConstraints);

        // Ajuster la taille :
        pack();
    }

    /**
     * Method OnOK : r�action � un �v�nement sur le bouton OK
     */
    protected void onOK()
    {
        // Fermer la dialogue :
        _ok = true;
        dispose();
    }

    /**
     * Method OnOK : r�action � un �v�nement sur le bouton Annuler
     */
    protected void onCancel()
    {
        _ok = false;
        dispose();
    }

    /**
     * Affichage de la fen�tre :
     */
    public boolean showDialog()
    {
        // Ajuster la taille :
        pack();

        // Centrer la fen�tre :
        Rectangle rect = getOwner().getBounds();
        Dimension dim = getSize();
        setBounds(rect.x + ((rect.width - dim.width) / 2), rect.y
                + ((rect.height - dim.height) / 2), dim.width, dim.height);

        // Montrer la dialogue :
        _ok = false;
        setVisible(true); // appel bloquant parce que la bo�te est modale...
        return _ok;
    }

    /**
     * Returns the pointsLimit.
     * 
     * @return int
     */
    public int getPointsLimit()
    {
        Number num = (Number) _ctrlNbPoints.getValue();
        _pointsLimit = num.intValue();
        return _pointsLimit;
    }

}
