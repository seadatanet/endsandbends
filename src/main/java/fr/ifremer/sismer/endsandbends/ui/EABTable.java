package fr.ifremer.sismer.endsandbends.ui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints;

/**
 * @author ATLANTIDE - ABT Surcharge de la table...
 */
public class EABTable extends JTable
{
    /**
     * 
     */
    private static final long serialVersionUID = -4187283176120597688L;
    // Donn�es :
    NavPathPoints[] _paths = null;
    int _modelGlobal = 0;
    int _totalOriginPoints = 0;
    int _totalOptimizedPoints = 0;
    double _totalError = 0;
    boolean _bComputed = false;

    /**
     * Method resetGlobalModel.
     */
    public void resetGlobalModel(boolean computed)
    {
        _bComputed = computed;
        _modelGlobal = 0;
    }

    /**
     * Method getPath.
     * 
     * @param row
     * @return NavPath
     */
    private NavPathPoints getPath(int row)
    {
        if ((row < 0) || (row >= _paths.length))
            return null;
        return _paths[row];
    }

    /**
     * Method getModelsCount.
     * 
     * @param row
     * @return int
     */
    private int getModelsCount(int row)
    {
        NavPathPoints path = getPath(row);
        if (path == null)
            return 0;
        int count = 0;
        for (int model = 1; model < NavPathPoints.get_nbModels(); model++)
        {
            if (path.getModelCause(model) != null)
                count++;
            else
                break;
        }
        return count;
    }

    /**
     * @author ATLANTIDE - ABT Stockage d'une entr�e des combo-boxes, afin de
     *         retrouver sans probl�me de codage du mod�le.
     */
    private static class Atom
    {
        public int model;
        public String cause;

        public Atom(int amodel, String acause)
        {
            model = amodel;
            cause = acause;
        }

        @Override
        public String toString()
        {
            return cause;
        }
    }

    /**
     * @author ATLANTIDE - ABT Mod�le de remplissage pour la table, directement
     *         branch� sur les donn�es.
     */
    private class Model extends AbstractTableModel
    {
        /**
         * 
         */
        private static final long serialVersionUID = 2565937462298648430L;

        protected Model()
        {
        }

        private final String[] _columnNames =
            {"N\u00B0", "Nb points", "Mod\u00E8le", "Nb Pts", "Erreur"};

        public int getColumnCount()
        {
            return _columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex)
        {
            if ((columnIndex >= 0) && (columnIndex < _columnNames.length))
            {
                return _columnNames[columnIndex];
            }

            return "";
        }

        public int getRowCount()
        {
            if (_paths == null)
            {
                return 0;
            }

            return _paths.length + 1;
        }

        public Object getValueAt(int rowIndex, int columnIndex)
        {
            if (rowIndex < _paths.length)
            {
                NavPathPoints path = getPath(rowIndex);
                if (path == null)
                    return null;
                int model = path.getSelectedModel();
                String cause = path.getModelCause(model);
                switch (columnIndex)
                {
                    case 0:
                        return Integer.valueOf(rowIndex + 1);
                    case 1:
                        return Integer.valueOf(path.getPointsCount());
                    case 2:
                    {
                        if (cause == null)
                        {
                            return "aucun";
                        }

                        return cause;
                    }
                    case 3:
                    {
                        if (model > 0)
                        {
                            return Integer.valueOf(path.getModelCount(model));
                        }

                        return Integer.valueOf(path.getPointsCount());
                    }
                    case 4:
                    {
                        if (model > 0)
                        {
                            return Double.valueOf(path.getModelError(model));
                        }

                        return 0.0;
                    }
                    default:
                        return null;
                }
            }
            else if (rowIndex == _paths.length)
            {
                switch (columnIndex)
                {
                    case 0:
                        return "total";
                    case 1:
                        return Integer.valueOf(_totalOriginPoints);
                    case 2:
                    {
                        if (_modelGlobal == 0)
                        {
                            return ""; //$NON-NLS-1$
                        }

                        return Globals.MODEL_CAUSE_ERROR
                            .format(new String[] {String.valueOf(_modelGlobal)});
                    }
                    case 3:
                        return Integer.valueOf(_totalOptimizedPoints);
                    case 4:
                        return Double.valueOf(_totalError);
                    default:
                        return null;
                }
            }
            else
                return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            if (rowIndex < _paths.length)
            {
                if (columnIndex == 2)
                {
                    return (getModelsCount(rowIndex) > 1);
                }

                return false;
            }
            else if (rowIndex == _paths.length)
            {
                if ((columnIndex == 2) && _bComputed)
                {
                    return true;
                }
                return false;
            }
            else
                return false;
        }

        @Override
        public void setValueAt(Object value, int row, int column)
        {
            if (column == 2)
            {
                if (row < _paths.length)
                {
                    NavPathPoints path = getPath(row);
                    if (path != null)
                    {
                        Atom atom = (Atom) value;
                        path.selectModel(atom.model);
                        _modelGlobal = 0;
                        updateUI(_paths);
                    }
                }
                else if ((row == _paths.length) && _bComputed)
                {
                    if (null != value)
                    {
                        Atom atom = (Atom) value;
                        _modelGlobal = atom.model;
                        for (int index = 0; index < _paths.length; index++)
                            _paths[index].selectErrorModel(_modelGlobal);
                        updateUI(_paths);
                    }
                }
            }
        }

    }

    /**
     * Constructor for EABTable.
     */
    public EABTable()
    {
        super();
        setModel(new Model());
// TableColumn modelColumn = getColumnModel().getColumn(2);
// JComboBox comboBox = new JComboBox();
// comboBox.setFont(comboBox.getFont().deriveFont(0));
// modelColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    /**
     * Method updateUI.
     * 
     * @param paths
     */
    public void updateUI(NavPathPoints[] paths)
    {
        // Sauvegarde de la s�lection
        int[] rowIndexes = getSelectedRows();

        // Plus de s�lection ni d'�dition :
// clearSelection();
        removeEditor();

        // Stocker les segments :
        _paths = paths;

        // Parcourir les segments pour remettre � jour les compteurs
        // globaux :
        _totalOriginPoints = 0;
        _totalOptimizedPoints = 0;
        _totalError = 0;
        if (_paths != null)
        {
            NavPathPoints path;
            int model = 0;
            for (int index = 0; index < _paths.length; index++)
            {
                path = _paths[index];
                model = path.getSelectedModel();
                _totalOriginPoints += path.getPointsCount();
                if (model > 0)
                {
                    _totalOptimizedPoints += path.getModelCount(model);
                    _totalError += path.getModelError(model);
                }
                else
                    _totalOptimizedPoints += path.getPointsCount();
            }
            _totalError /= _paths.length;
        }

        // Signaler un changement de donn�es :
        setAutoCreateColumnsFromModel(false);

        setModel(new Model());
        ((Model) getModel()).fireTableDataChanged();

        if (null != _paths)
        {
            // Restauration de la s�lection
            for (int index : rowIndexes)
            {
                getSelectionModel().addSelectionInterval(index, index);
            }
        }
    }

    /**
     * @see javax.swing.JTable#prepareEditor(TableCellEditor, int, int)
     */
    @Override
    public Component prepareEditor(TableCellEditor editor, int row, int column)
    {
        if (column != 2)
        {
            return null;
        }

        Component editComponent = super.prepareEditor(editor, row, 2);
        try
        {
            JComboBox combo = (JComboBox) editComponent;
            combo.removeAllItems();
            if (row < _paths.length)
            {
                NavPathPoints path = _paths[row];
                for (int index = 1; index < NavPathPoints.get_nbModels(); index++)
                {
                    String cause = path.getModelCause(index);
                    if (cause == null)
                        break;
                    combo.addItem(new Atom(index, cause));
                }
                if (path.getSelectedModel() > 0)
                    combo.setSelectedIndex(path.getSelectedModel() - 1);
                else
                    combo.setSelectedIndex(-1);
            }
            else if ((row == _paths.length) && _bComputed)
            {
                combo.addItem(new Atom(3, "err < 10-3"));
                combo.addItem(new Atom(4, "err < 10-4"));
                combo.addItem(new Atom(5, "err < 10-5"));
                if (_modelGlobal > 0)
                    combo.setSelectedIndex(_modelGlobal - 3);
                else
                    combo.setSelectedIndex(-1);
            }
        }
        catch (ClassCastException cce)
        {
            cce.printStackTrace();
        }
        return editComponent;
    }

}
