package fr.ifremer.sismer.endsandbends.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine;
import fr.ifremer.sismer.endsandbends.algorithm.OrthoDistanceToLine;
import fr.ifremer.sismer.endsandbends.algorithm.VerticalDistanceToLine;
import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Page de propri�t�s pour modifier la projection
 */
public class OptionDistance extends OptionPage
{
    // Contr�les :
    private final JLabel _ctrlDistanceLabel = new JLabel();
    private final JList _ctrlListDistances = new JList();
    private final JLabel _ctrlDistanceProjectionDescription = new JLabel();

    // Evenements :
    private final ListSelectionListener _listenerSelList =
        new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e)
            {
                updateIHM();
            }
        };

    // Donn�es internes :
    private DistanceToLine _distSelected = null;

    /**
     * Constructeur
     */
    public OptionDistance(DistanceToLine distance)
    {
        // Stocker l'action :
        _distSelected = distance;

        // Cr�er les composants (contr�les) :
        initComponents();
        updateContents();

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
        _ctrlDistanceLabel.setText(Messages.getString(Messages.MESSAGES,
            "label_distancelist")); //$NON-NLS-1$
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0;
        add(_ctrlDistanceLabel, gridBagConstraints);

        // Liste :
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(_ctrlListDistances);
        scroll.setMinimumSize(new Dimension(300, 100));
        scroll.setPreferredSize(new Dimension(500, 150));
        add(scroll, gridBagConstraints);
        _ctrlListDistances.addListSelectionListener(_listenerSelList);
        _ctrlListDistances.setFont(_ctrlListDistances.getFont().deriveFont(0));

        // D�tail de la s�lection :
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlDistanceProjectionDescription, gridBagConstraints);
        _ctrlDistanceProjectionDescription.setBorder(BorderFactory
            .createLoweredBevelBorder());
        _ctrlDistanceProjectionDescription.setFont(_ctrlDistanceProjectionDescription.getFont()
            .deriveFont(0));
    }

    /**
     * @author ATLANTIDE - ABT Utilitaire por stocker la liste des projections
     */
    private static class DistanceListProxy
    {
        private final Class<?> _class;
        private final String _name;
        private final String _description;

        public DistanceListProxy(Class<?> classDistance, String name,
                String description)
        {
            _class = classDistance;
            _name = name;
            _description = description;
        }

        @Override
        public String toString()
        {
            return _name;
        }

        public DistanceToLine getDistance()
        {
            DistanceToLine dist = null;
            try
            {
                dist = (DistanceToLine) _class.newInstance();
            }
            catch (InstantiationException ie)
            {
                ie.printStackTrace();
                dist = null;
            }
            catch (IllegalAccessException iae)
            {
                iae.printStackTrace();
                dist = null;
            }
            if (dist == null)
                dist = DistanceToLine.getDefault();
            return dist;
        }

        public String getDescription()
        {
            return _description;
        }

        public String getName()
        {
            return _name;
        }
    }

    /**
     * Mise � jour des contenus : liste
     */
    public void updateContents()
    {
        // Cr�er un vecteur contenant les projections :
        DistanceListProxy[] distanceList = new DistanceListProxy[2];
        distanceList[0] =
            new DistanceListProxy(OrthoDistanceToLine.class, Messages
                .getString(Messages.MESSAGES, "dist_ortho_name"), Messages
                .getString(Messages.MESSAGES, "dist_ortho_desc"));
        distanceList[1] =
            new DistanceListProxy(VerticalDistanceToLine.class, Messages
                .getString(Messages.MESSAGES, "dist_vert_name"), Messages
                .getString(Messages.MESSAGES, "dist_vert_desc"));
        _ctrlListDistances.setListData(distanceList);

        // Faire la s�lection :
        int selIndex = -1;
        if (_distSelected != null)
        {
            for (int index = 0; index < distanceList.length; index++)
            {
                if (distanceList[index]._class.isInstance(_distSelected))
                {
                    selIndex = index;
                    break;
                }
            }
            _ctrlListDistances.setSelectedIndex(selIndex);
        }

        // Mise � jour de l'�tat :
        updateIHM();
    }

    /**
     * Mise � jour par rapport � la s�lection dans la liste...
     */
    public void updateIHM()
    {
        DistanceListProxy dlp =
            (DistanceListProxy) _ctrlListDistances.getSelectedValue();
        if (dlp != null)
        {
            _distSelected = dlp.getDistance();
            _ctrlDistanceProjectionDescription.setText("<html><body><p>"
                    + dlp.getDescription() + "</p></body></html>");
        }
        else
        {
            _distSelected = null;
            _ctrlDistanceProjectionDescription.setText("");
        }
    }

    /**
     * Returns the projSelected.
     * 
     * @return GeoProjection
     */
    public DistanceToLine getDistance()
    {
        return _distSelected;
    }

    /**
     * @see fr.ifremer.sismer.endsandbends.ui.OptionPage#updateData(boolean)
     */
    @Override
    public boolean updateData(boolean bSaveAndValidate)
    {
        if (bSaveAndValidate && (_distSelected == null))
            return false;
        return true;
    }

}
