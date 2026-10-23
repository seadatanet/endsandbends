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
import fr.ifremer.sismer.endsandbends.algorithm.GaussKruegerProjection;
import fr.ifremer.sismer.endsandbends.algorithm.GeoProjection;
import fr.ifremer.sismer.endsandbends.algorithm.OrthoDistanceToLine;
import fr.ifremer.sismer.endsandbends.algorithm.VerticalDistanceToLine;
import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * @author ATLANTIDE - ABT Page de propri�t�s pour modifier la projection
 */
public class OptionProjection extends OptionPage
{
    // Contr�les :
    // Pour les projections
    private final JLabel _ctrlLabel = new JLabel();
    private final JList _ctrlListProjections = new JList();
    private final JLabel _ctrlProjectionDescription = new JLabel();

    // Pour les distances
    private final JLabel _ctrlDistanceLabel = new JLabel();
    private final JList _ctrlListDistances = new JList();
    private final JLabel _ctrlDistanceProjectionDescription = new JLabel();

    // Evenements :
    private final ListSelectionListener _listenerProjectionSelList =
        new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e)
            {
                updateProjectionSelection();
            }
        };
    private final ListSelectionListener _listenerDistanceSelList =
        new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e)
            {
                updateDistanceSelection();
            }
        };

    // Donn�es internes :
    private GeoProjection _projSelected = null;

    private DistanceToLine _distSelected = null;

    /**
     * Constructeur
     */
    public OptionProjection(final GeoProjection projection,
            final DistanceToLine distance)
    {
        // Stocker l'action :
        _projSelected = projection;
        _distSelected = distance;

        // Cr�er les composants (contr�les) :
        initComponents();
        updateContents();

        // Autre param�tres de la fen�tre principale :
        setSize(400, 600);
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

        // Partie projections
        // Texte :
        _ctrlLabel.setText(Messages.getString(Messages.MESSAGES,
            "label_projectionlist")); //$NON-NLS-1$
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0;
        add(_ctrlLabel, gridBagConstraints);

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
        scroll.setViewportView(_ctrlListProjections);
        scroll.setMinimumSize(new Dimension(300, 100));
        scroll.setPreferredSize(new Dimension(500, 150));
        add(scroll, gridBagConstraints);
        _ctrlListProjections
            .addListSelectionListener(_listenerProjectionSelList);
        _ctrlListProjections.setFont(_ctrlListProjections.getFont().deriveFont(
            0));

        // D�tail de la s�lection :
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlProjectionDescription, gridBagConstraints);
        _ctrlProjectionDescription.setBorder(BorderFactory
            .createLoweredBevelBorder());
        _ctrlProjectionDescription.setFont(_ctrlProjectionDescription.getFont()
            .deriveFont(0));

        // Partie distances
        // Texte :
        _ctrlDistanceLabel.setText(Messages.getString(Messages.MESSAGES,
            "label_distancelist")); //$NON-NLS-1$
        GridBagConstraints distanceGridBagConstraints =
            new GridBagConstraints();
        distanceGridBagConstraints.gridx = 0;
        distanceGridBagConstraints.gridy = 3;
        distanceGridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        distanceGridBagConstraints.anchor = GridBagConstraints.WEST;
        distanceGridBagConstraints.weightx = 1.0;
        distanceGridBagConstraints.weighty = 0;
        add(_ctrlDistanceLabel, distanceGridBagConstraints);

        // Liste :
        distanceGridBagConstraints = new GridBagConstraints();
        distanceGridBagConstraints.gridx = 0;
        distanceGridBagConstraints.gridy = 4;
        distanceGridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        distanceGridBagConstraints.anchor = GridBagConstraints.CENTER;
        distanceGridBagConstraints.weightx = 1.0;
        distanceGridBagConstraints.weighty = 1.0;
        distanceGridBagConstraints.fill = GridBagConstraints.BOTH;
        JScrollPane distanceScroll = new JScrollPane();
        distanceScroll.setViewportView(_ctrlListDistances);
        distanceScroll.setMinimumSize(new Dimension(300, 100));
        distanceScroll.setPreferredSize(new Dimension(500, 150));
        add(distanceScroll, distanceGridBagConstraints);
        _ctrlListDistances.addListSelectionListener(_listenerDistanceSelList);
        _ctrlListDistances.setFont(_ctrlListDistances.getFont().deriveFont(0));

        // D�tail de la s�lection :
        distanceGridBagConstraints = new GridBagConstraints();
        distanceGridBagConstraints.gridx = 0;
        distanceGridBagConstraints.gridy = 5;
        distanceGridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        distanceGridBagConstraints.anchor = GridBagConstraints.WEST;
        distanceGridBagConstraints.weightx = 1.0;
        distanceGridBagConstraints.weighty = 0;
        distanceGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(_ctrlDistanceProjectionDescription, distanceGridBagConstraints);
        _ctrlDistanceProjectionDescription.setBorder(BorderFactory
            .createLoweredBevelBorder());
        _ctrlDistanceProjectionDescription
            .setFont(_ctrlDistanceProjectionDescription.getFont().deriveFont(0));
    }

    /**
     * @author ATLANTIDE - ABT Utilitaire por stocker la liste des projections
     */
    private static class ProjectionListProxy
    {
        private final Class _classProjection;
        private final String _name;
        private final String _description;

        public ProjectionListProxy(Class classProjection, String name,
                String description)
        {
            _classProjection = classProjection;
            _name = name;
            _description = description;
        }

        @Override
        public String toString()
        {
            return _name;
        }

        public GeoProjection getProjection()
        {
            GeoProjection proj = null;
            try
            {
                proj = (GeoProjection) _classProjection.newInstance();
            }
            catch (InstantiationException ie)
            {
                ie.printStackTrace();
                proj = null;
            }
            catch (IllegalAccessException iae)
            {
                iae.printStackTrace();
                proj = null;
            }
            if (proj == null)
                proj = GeoProjection.getDefault(false);
            return proj;
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
        ProjectionListProxy[] list = new ProjectionListProxy[1];
// list[0] = new ProjectionListProxy(NullProjection.class,
        // StringTable.get("proj_null_name"),
        // StringTable.get("proj_null_desc"));
        list[0] =
            new ProjectionListProxy(GaussKruegerProjection.class,
                Messages.getString(Messages.MESSAGES, "proj_gk_name"),
                Messages.getString(Messages.MESSAGES, "proj_gk_desc"));
        _ctrlListProjections.setListData(list);

        // Cr�er un vecteur contenant les projections :
        DistanceListProxy[] distanceList = new DistanceListProxy[2];
        distanceList[0] =
            new DistanceListProxy(OrthoDistanceToLine.class,
                Messages.getString(Messages.MESSAGES, "dist_ortho_name"),
                Messages.getString(Messages.MESSAGES, "dist_ortho_desc"));
        distanceList[1] =
            new DistanceListProxy(VerticalDistanceToLine.class,
                Messages.getString(Messages.MESSAGES, "dist_vert_name"),
                Messages.getString(Messages.MESSAGES, "dist_vert_desc"));
        _ctrlListDistances.setListData(distanceList);

        // Faire la s�lection pour les projections :
        int selIndex = -1;
        if (_projSelected != null)
        {
            for (int index = 0; index < list.length; index++)
            {
                if (list[index]._classProjection.isInstance(_projSelected))
                {
                    selIndex = index;
                    break;
                }
            }
            _ctrlListProjections.setSelectedIndex(selIndex);
        }

        // Faire la s�lection pour les distances :
        int selIdx = -1;
        if (_distSelected != null)
        {
            for (int index = 0; index < distanceList.length; index++)
            {
                if (distanceList[index]._class.isInstance(_distSelected))
                {
                    selIdx = index;
                    break;
                }
            }
            _ctrlListDistances.setSelectedIndex(selIdx);
        }

        // Mise � jour de l'�tat :
        updateIHM();
    }

    /**
     * Mise � jour par rapport � la s�lection dans la liste...
     */
    public void updateIHM()
    {
        ProjectionListProxy plp =
            (ProjectionListProxy) _ctrlListProjections.getSelectedValue();
        if (plp != null)
        {
            _projSelected = plp.getProjection();
            _ctrlProjectionDescription.setText("<html><body><p>"
                    + plp.getDescription() + "</p></body></html>");
        }
        else
        {
            _projSelected = null;
            _ctrlProjectionDescription.setText("");
        }

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
     * Mise � jour par rapport � la s�lection dans la liste projections...
     */
    public void updateProjectionSelection()
    {
        ProjectionListProxy plp =
            (ProjectionListProxy) _ctrlListProjections.getSelectedValue();
        if (plp != null)
        {
            _projSelected = plp.getProjection();
            _ctrlProjectionDescription.setText("<html><body><p>"
                    + plp.getDescription() + "</p></body></html>");
        }
        else
        {
            _projSelected = null;
            _ctrlProjectionDescription.setText("");
        }
    }

    /**
     * Mise � jour par rapport � la s�lection dans la liste distances...
     */
    public void updateDistanceSelection()
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
    public GeoProjection getProjection()
    {
        return _projSelected;
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
        if (bSaveAndValidate && (_projSelected == null))
            return false;

        if (bSaveAndValidate && (_distSelected == null))
            return false;

        return true;
    }

}
