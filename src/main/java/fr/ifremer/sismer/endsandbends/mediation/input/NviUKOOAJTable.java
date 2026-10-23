/* ============================================================================
 * Nom du fichier	: NviUKOOAJTable.java
 * Auteur			: Altran
 * Date				: 1 oct. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.mediation.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.ui.controller.NviInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.controller.UkooaInputDialogController;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class NviUKOOAJTable extends JTable
{

    private Vector rows = null;
    private Vector columns = null;
    private final InputDialog inputDialog;
    private DefaultTableModel tabModel = null;
    private final JButton cmdAdd, cmdDelete, cmdEdit, cmdInsert;

    private static final String SIMPLEDATEFORMATMASK = Messages.getString(
        Messages.MESSAGES, "SIMPLEDATEFORMATMASK");
    private static final String SIMPLETIMEFORMATMASK = Messages.getString(
        Messages.MESSAGES, "SIMPLETIMEFORMATMASK");
    private static final String SIMPLEDATE_TIMEFORMATMASK =
        SIMPLEDATEFORMATMASK + " " + SIMPLETIMEFORMATMASK;

    private static final long serialVersionUID = -7962156499410110909L;

    public NviUKOOAJTable(JButton cmdAdd, JButton cmdDelete, JButton cmdEdit,
            JButton cmdInsert, InputDialog inputDialog)
    {
        super();
        this.cmdAdd = cmdAdd;
        this.cmdDelete = cmdDelete;
        this.cmdEdit = cmdEdit;
        this.cmdInsert = cmdInsert;
        this.inputDialog = inputDialog;
    }

    public void initNviUKOOAJTable()
    {
        rows = new Vector<String>();
        columns = new Vector<String>();
        String[] columnNames = {"Segment start date", "Segment end date"};
        addColumns(columnNames);

        tabModel = new DefaultTableModel();
        tabModel.setDataVector(rows, columns);
        cmdAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent source)
            {
                if (isEnabled())
                {
                    addRow();
                }
            }
        });
        cmdDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent source)
            {
                if (isEnabled())
                {
                    deleteRow(getSelectedRows());

                }
            }
        });
        cmdEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent source)
            {
                if (isEnabled() && getSelectedRows().length == 1
                        && getSelectedColumns().length == 1)
                {
                    editCell(getSelectedRow(), getSelectedColumn());
                    try
                    {
                        getInputDialog().getInputController()
                            .validateDateInput(getSelectedRow(),
                                getSelectedColumn());
                    }
                    catch (ParseException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        cmdInsert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent source)
            {
                if (isEnabled())
                {
                    insertRow(getSelectedRow());
                }
            }
        });
        this.setModel(tabModel);
    }

    private void addColumns(String[] colName)// Table Columns
    {
        for (int i = 0; i < colName.length; i++)
        {
            columns.addElement(colName[i]);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public void editCell(int selectedRow, int selectedColumn)
    {
        DateSegmentInputDialog dialog =
            new DateSegmentInputDialog((JFrame) getInputDialog().getOwner(),
                true);
        String cellValue =
            (String) this.getValueAt(selectedRow, selectedColumn);
        if (cellValue != null && !cellValue.isEmpty())
        {
            dialog.initDialog(cellValue);
        }
        Date date = dialog.showDialog();
        if (date != null)
        {
            try
            {
                String strDate = dateFormatter(date, SIMPLEDATE_TIMEFORMATMASK);
                JTable table = null;
                if (getInputDialog().getCurrentInputObject() instanceof NviInputParamsObject)
                    table = getInputDialog().getNviSegmentsTable();
                else if (getInputDialog().getCurrentInputObject() instanceof UkooaInputParamsObject)
                    table = getInputDialog().getUkooaSegmentsTable();
                table.setValueAt(strDate, selectedRow, selectedColumn);
            }
            catch (ParseException ex)
            {
                Logger.getLogger(NviUKOOAJTable.class.getName()).log(
                    Level.SEVERE, null, ex);
            }
        }
    }

    public void editCell(int selectedRow, int selectedColumn, Date date)// Table
    // Columns
    {
        String strDate;
        try
        {
            strDate = dateFormatter(date, SIMPLEDATE_TIMEFORMATMASK);
            JTable table = null;
            if (getInputDialog().getInputController() instanceof NviInputDialogController)
                table = getInputDialog().getNviSegmentsTable();
            else if (getInputDialog().getInputController() instanceof UkooaInputDialogController)
                table = getInputDialog().getUkooaSegmentsTable();
            table.setValueAt(strDate, selectedRow, selectedColumn);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String dateFormatter(Date formattedDate, String format)
            throws ParseException
    {
        UTCDateFormat isoSDF = new UTCDateFormat(format);
        return isoSDF.format(formattedDate);
    }

    public void addRow() // Add Row
    {
        Vector<String> r = new Vector<String>();
        r = createBlankElement();
        rows.addElement(r);
        addNotify();

    }

    public void insertRow(int selectedRow) // Add Row
    {
        Vector<String> r = new Vector<String>();
        r = createBlankElement();
        if (selectedRow < 0)
        {
            rows.addElement(r);
        }
        else
        {
            rows.insertElementAt(r, selectedRow);
        }
        addNotify();
    }

    public Vector<String> createBlankElement()
    {
        Vector<String> t = new Vector<String>();
        t.addElement("");
        t.addElement("");
        return t;
    }

    void deleteRow(int[] selectedRows)
    {
        for (int i = selectedRows.length - 1; i >= 0; i--)// At least one Row in
        {
            try
            {
                rows.removeElementAt(selectedRows[i]);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
            }
        }
        selectRowAfterDeletion(selectedRows);
        addNotify();
    }// Delete Row

    void selectRowAfterDeletion(int[] selectedRows)
    {
        getSelectionModel().clearSelection();
        int min = selectedRows[0];
        for (int i = selectedRows.length - 1; i >= 0; i--)// At least one Row in
        {
            if (min > selectedRows[i])
            {
                min = selectedRows[i];
            }
        }
        if (min > 0)
        {
            setColumnSelectionAllowed(true);
            setRowSelectionAllowed(true);
            changeSelection(min - 1, getSelectedColumn(), true, false);
        }
        else if (min == 0 && getRowCount() != 0)
        {
            setColumnSelectionAllowed(true);
            setRowSelectionAllowed(true);
            changeSelection(0, getSelectedColumn(), true, false);
        }
    }// Delete Row

    /**
     * Getter sur inputDialog.
     * 
     * @return the inputDialog
     */
    public InputDialog getInputDialog()
    {
        return inputDialog;
    }
}
