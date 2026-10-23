/* ============================================================================
 * Nom du fichier	: ReferenceTable.java
 * Auteur			: Altran
 * Date				: 23 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JTable;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public class ReferenceTable
{
    private static final int SEGMENT_NUMBER_COLUMN = 0;
    private static final int SEGMENT_MODEL_COLUMN = 2;
    private static final int SEGMENT_POINT_NUMBER_COLUMN = 3;
    private static final int SEGMENT_ERROR_COLUMN = 4;

    private static final int REFERENCE_TYPE_COLUMN = 0;
    private static final int REFERENCE_MODEL_COLUMN = 1;
    private static final int REFERENCE_POINT_NUMBER = 2;
    private static final int REFERENCE_ERROR_COLUMN = 3;

    /**
     * Singleton
     */
    private static ReferenceTable _instance;

    /**
     * Map des valeurs de r�f�rence par type d'optimisation et par segment
     */
    private final Map<Integer, Map<ComputationType, ReferenceValue>> _table;

    /**
     * Constructeur
     */
    private ReferenceTable()
    {
        _table = new HashMap<Integer, Map<ComputationType, ReferenceValue>>();
    }

    /**
     * R�cup�ration du singleton
     * 
     * @return
     */
    public static final ReferenceTable getInstance()
    {
        if (null == _instance)
        {
            _instance = new ReferenceTable();
        }

        return _instance;
    }

    public void resetReferences(JTable table)
    {
        _instance = new ReferenceTable();

        _instance.clearTable(table);
    }

    public void updateReferenceTable(JTable referenceTable,
            JTable segmentTable, ComputationType type)
    {
        int rowCount = segmentTable.getRowCount() - 1;

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
        {
            Integer segmentNumber =
                (Integer) segmentTable.getValueAt(rowIndex,
                    SEGMENT_NUMBER_COLUMN);

            ReferenceValue value =
                new ReferenceValue(getValueFromTable(segmentTable, rowIndex,
                    SEGMENT_MODEL_COLUMN), getValueFromTable(segmentTable,
                    rowIndex, SEGMENT_POINT_NUMBER_COLUMN), getValueFromTable(
                    segmentTable, rowIndex, SEGMENT_ERROR_COLUMN));

            Map<ComputationType, ReferenceValue> valuesByType =
                _table.get(segmentNumber);

            if (null == valuesByType)
            {
                valuesByType = new TreeMap<ComputationType, ReferenceValue>();
            }

            valuesByType.put(type, value);

            _table.put(segmentNumber, valuesByType);
        }

        updateUI(referenceTable, segmentTable);
    }

    /**
     * @param referenceTable
     */
    public void updateUI(JTable referenceTable, JTable segmentTable)
    {
        clearTable(referenceTable);

        int[] indexes = segmentTable.getSelectedRows();

        // on v�rifie qu'il y a une seule ligne s�lectionn�e, et qu'il ne s'agit
        // pas de la derni�re (ligne de total)
        if (1 == indexes.length && indexes[0] != -1
                && indexes[0] < (segmentTable.getRowCount() - 1))
        {
            int index = indexes[0];

            Integer segmentNumber =
                (Integer) segmentTable.getValueAt(index, SEGMENT_NUMBER_COLUMN);

            Map<ComputationType, ReferenceValue> valuesByType =
                _table.get(segmentNumber);

            if (null != valuesByType)
            {
                int row = 0;

                for (Entry<ComputationType, ReferenceValue> entry : valuesByType
                    .entrySet())
                {
                    referenceTable.setValueAt(entry.getKey().toString(), row,
                        REFERENCE_TYPE_COLUMN);

                    ReferenceValue value = entry.getValue();

                    referenceTable.setValueAt(value.getModel(), row,
                        REFERENCE_MODEL_COLUMN);
                    referenceTable.setValueAt(value.getPointNumber(), row,
                        REFERENCE_POINT_NUMBER);
                    referenceTable.setValueAt(value.getError(), row,
                        REFERENCE_ERROR_COLUMN);

                    row++;
                }
            }
        }
    }

    /**
     * @param referenceTable
     */
    private void clearTable(JTable table)
    {
        for (int i = 0; i < table.getRowCount(); i++)
        {
            for (int j = 0; j < table.getColumnCount(); j++)
            {
                table.setValueAt("", i, j); //$NON-NLS-1$
            }
        }
    }

    private final String getValueFromTable(JTable table, int row, int column)
    {
        return String.valueOf(table.getValueAt(row, column));
    }
}
