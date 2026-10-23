package fr.ifremer.sismer.endsandbends.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Page de propri�t�s pour tous les seuils utilis�s dans
 *         le programme
 */
public class OptionOutput extends OptionPage
{
    // Contr�les :
    private final JLabel _ctrlLabelUserName = new JLabel();
    private final JTextField _ctrlUserName = new JTextField();
    private final JLabel _ctrlLabelUserComment = new JLabel();
    private final JTextField _ctrlUserComment = new JTextField();
    private final JLabel _ctrlLabelCampagnNumber = new JLabel();
    private final JTextField _ctrlCampagnNumber = new JTextField();
    private final JLabel _ctrlLabelCampagnName = new JLabel();
    private final JTextField _ctrlCampagnName = new JTextField();
    private final JLabel _ctrlLabelCampagnBoat = new JLabel();
    private final JTextField _ctrlCampagnBoat = new JTextField();

    // Donn�es internes :
    private String _userName = ""; //$NON-NLS-1$
    private String _userComment = ""; //$NON-NLS-1$
    private String _campagnNumber = ""; //$NON-NLS-1$
    private String _campagnName = ""; //$NON-NLS-1$
    private String _campagnBoat = ""; //$NON-NLS-1$

    /**
     * Constructeur
     */
    public OptionOutput(String userName, String userComment,
            String campagnNumber, String campagnName, String campagnBoat)
    {
        // Stocker les donn�es :
        _userName = userName;
        _userComment = userComment;
        _campagnNumber = campagnNumber;
        _campagnName = campagnName;
        _campagnBoat = campagnBoat;

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

        // Nom de l'utilisateur...
        // Texte :
        _ctrlLabelUserName.setText(Messages.getString(Messages.MESSAGES,
            "label_username")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlLabelUserName, gridBagConstraints);
        // Edit :
        _ctrlUserName.setText(_userName);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlUserName, gridBagConstraints);

        // Commentaire de l'utilisateur...
        // Texte :
        _ctrlLabelUserComment.setText(Messages.getString(Messages.MESSAGES,
            "label_usercomment")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlLabelUserComment, gridBagConstraints);
        // Edit :
        _ctrlUserComment.setText(_userComment);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlUserComment, gridBagConstraints);

        // Num�ro de campagne...
        // Texte :
        _ctrlLabelCampagnNumber.setText(Messages.getString(Messages.MESSAGES,
            "label_campagnnumber")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlLabelCampagnNumber, gridBagConstraints);
        // Edit :
        _ctrlCampagnNumber.setText(_campagnNumber);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlCampagnNumber, gridBagConstraints);

        // Nom de campagne...
        // Texte :
        _ctrlLabelCampagnName.setText(Messages.getString(Messages.MESSAGES,
            "label_campagnname")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlLabelCampagnName, gridBagConstraints);
        // Edit :
        _ctrlCampagnName.setText(_campagnName);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlCampagnName, gridBagConstraints);

        // Bateau de campagne...
        // Texte :
        _ctrlLabelCampagnBoat.setText(Messages.getString(Messages.MESSAGES,
            "label_campagnboat")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlLabelCampagnBoat, gridBagConstraints);
        // Edit :
        _ctrlCampagnBoat.setText(_campagnBoat);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlCampagnBoat, gridBagConstraints);
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.ui.OptionPage#updateData(boolean)
     */
    @Override
    public boolean updateData(boolean bSaveAndValidate)
    {
        _userName = _ctrlUserName.getText();
        _userComment = _ctrlUserComment.getText();
        _campagnNumber = _ctrlCampagnNumber.getText();
        _campagnName = _ctrlCampagnName.getText();
        _campagnBoat = _ctrlCampagnBoat.getText();
        return true;
    }

    /**
     * Returns the campagnBoat.
     * 
     * @return String
     */
    public String getCampagnBoat()
    {
        return _campagnBoat;
    }

    /**
     * Returns the campagnName.
     * 
     * @return String
     */
    public String getCampagnName()
    {
        return _campagnName;
    }

    /**
     * Returns the campagnNumber.
     * 
     * @return String
     */
    public String getCampagnNumber()
    {
        return _campagnNumber;
    }

    /**
     * Returns the userComment.
     * 
     * @return String
     */
    public String getUserComment()
    {
        return _userComment;
    }

    /**
     * Returns the userName.
     * 
     * @return String
     */
    public String getUserName()
    {
        return _userName;
    }

}
