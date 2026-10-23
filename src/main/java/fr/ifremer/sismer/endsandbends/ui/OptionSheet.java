package fr.ifremer.sismer.endsandbends.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Fen�tre de propri�t�s
 */
public class OptionSheet extends JDialog
{

    // Donn�es membres :
    protected String _strName; // titre de la bo�te de propri�t�s
    protected Vector _arNames; // titre de chaque onglet
    protected Vector _arPanes; // panneaux
    protected boolean _ok; // validit� de la bo�te

    // Contr�les :
    protected JTabbedPane _ctrlTabbedPane;
    protected JButton _ctrlBtnOK;
    protected JButton _ctrlBtnCancel;

    /**
     * Constructeur
     * 
     * @param owner
     * @throws HeadlessException
     */
    public OptionSheet(Frame owner, String name)
    {
        // Classe de base (passer le owner) :
        super(owner, name, true);

        // Initialisation des donn�es membres :
        _strName = name;
        _arNames = null;
        _arPanes = null;
        _ok = false;

        // Cr�ation des contr�les :
        initComponents();

        // Centrer la fen�tre :
        Rectangle rect = owner.getBounds();
        Dimension dim = new Dimension(600, 300);
        setBounds(rect.x + ((rect.width - dim.width) / 2), rect.y
                + ((rect.height - dim.height) / 2), dim.width, dim.height);
    }

    /**
     * Method initComponents : cr�ation des contr�les de la fen�tre principale
     */
    protected void initComponents()
    {
        // D'abord initialiser le Layout :
        getContentPane().setLayout(new GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints;

        // Cr�er le panneau des onglets :
        _ctrlTabbedPane = new JTabbedPane();

        // Ajouter le pane dans la fen�tre :
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 0.8;
        getContentPane().add(_ctrlTabbedPane, gridBagConstraints);
        _ctrlTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e)
            {
                OptionPage page =
                    (OptionPage) _ctrlTabbedPane.getSelectedComponent();
                page.onActivated();
            }
        });

        // Ajouter les boutons :
        _ctrlBtnCancel = new JButton();
        _ctrlBtnCancel.setText(Messages.getString(Messages.MESSAGES,
            "btn_cancel")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
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

        _ctrlBtnOK = new JButton();
        _ctrlBtnOK.setText(Messages.getString(Messages.MESSAGES, "btn_ok")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
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
        // Parcourir les pages pour les valider :
        JPanel panel;
        OptionPage page;
        for (int i = 0; i < _arPanes.size(); i++)
        {
            panel = (JPanel) _arPanes.elementAt(i);
            page = (OptionPage) panel;
            if (page != null)
            {
                if (!page.updateData(true))
                {
                    _ctrlTabbedPane.setSelectedIndex(i);
                    return;
                }
            }
        }

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
     * Method AddPane : ajout d'un onglet Cette m�thode DOIT �tre appel�e avant
     * show()...
     * 
     * @param name
     *            Nom de l'onglet, celui qui appara�t
     * @param pane
     *            Fen�tre contenue dans l'onglet
     * @return boolean tjs 'true'
     */
    public boolean addPane(String name, JPanel pane)
    {
        if ((_arNames == null) || (_arPanes == null))
        {
            _arNames = new Vector(1);
            _arPanes = new Vector(1);
        }
        _arNames.add(name);
        _arPanes.add(pane);
        return true;
    }

    /**
     * Affichage de la fen�tre et de ses onglets :
     */
    public boolean showOptions()
    {
        // Ajouter les pages dans l'onglet :
        if ((_arPanes == null) || (_arPanes.size() == 0))
            return false;
        String name;
        for (int i = 0; i < _arPanes.size(); i++)
        {
            name = (String) _arNames.elementAt(i);
            _ctrlTabbedPane.addTab(name, (JPanel) _arPanes.elementAt(i));
        }

        // Utiliser la cha�ne de titre :
        setTitle(_strName);

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

}
