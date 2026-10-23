/* ============================================================================
 * Nom du fichier	: InputDialogController.java
 * Auteur			: Altran
 * Date				: 10 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import fr.ifremer.sismer.endsandbends.common.DefaultFolder;
import fr.ifremer.sismer.endsandbends.common.DefaultsHandler;
import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;
import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.mediation.input.CsvInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.IInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.InputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NviInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.UkooaInputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.MainForm;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class InputDialogController implements IInputDialogController
{
    InputDialog dialog;
    InputParamsObject inputObject;

    private static final String SIMPLEDATEFORMATMASK = Messages.getString(
        Messages.MESSAGES, "SIMPLEDATEFORMATMASK");
    private static final String SIMPLETIMEFORMATMASK = Messages.getString(
        Messages.MESSAGES, "SIMPLETIMEFORMATMASK");
    private static final String SIMPLEDATE_TIMEFORMATMASK =
        SIMPLEDATEFORMATMASK + " " + SIMPLETIMEFORMATMASK;

    /**
     * Liste des erreurs sur le contenu des champs.
     */
    private final List<String> _errorsMessages = new ArrayList<String>();

    /**
     * Getter sur inputObject.
     * 
     * @return the inputObject
     */
    public InputParamsObject getInputObject()
    {
        return inputObject;
    }

    /**
     * Setter sur inputObject.
     * 
     * @param inputObject
     *            the inputObject to set
     */
    public void setInputObject(InputParamsObject inputObject)
    {
        this.inputObject = inputObject;
    }

    public InputDialogController(InputDialog dialog,
            InputParamsObject inputObject)
    {
        this.dialog = dialog;
        this.inputObject = inputObject;
        this.dialog.setInputController(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeDialog()
    {
        initializeDialogDefault();
    }

    /**
     * {@inheritDoc}
     */
    public void initializeDialogDefault()
    {
        dialog.getFileTypeValue().setSelectedItem(
            Messages.getString(Messages.MESSAGES, inputObject.getFormat()
                .getInputFileType()));
        dialog.getFileTypeValue().setEnabled(false);
        dialog.getIsTimeFilteredEnabled().setSelected(
            inputObject.isTimeFilterEnabled());

        updateDateTimeFieldsDependingOnTimeFilterSelection();

        if (inputObject.getStartDate() != null)
        {
            dialog.getStartDate_date().setText(
                dateFormatter(inputObject.getStartDate()));
            dialog.getStartDate_time().setText(
                timeFormatter(inputObject.getStartDate()));
        }
        if (inputObject.getEndDate() != null)
        {
            dialog.getEndDate_date().setText(
                dateFormatter(inputObject.getEndDate()));
            dialog.getEndDate_time().setText(
                timeFormatter(inputObject.getEndDate()));
        }
    }

    public void updateDateTimeFieldsDependingOnTimeFilterSelection()
    {
        boolean isTimeFilteredSelected =
            dialog.getIsTimeFilteredEnabled().isSelected();
        dialog.getStartDate_flabel().setEnabled(isTimeFilteredSelected);
        dialog.getStartDate_label().setEnabled(isTimeFilteredSelected);
        dialog.getStartDate_ftlabel().setEnabled(isTimeFilteredSelected);
        dialog.getEndDate_flabel().setEnabled(isTimeFilteredSelected);
        dialog.getEndDate_label().setEnabled(isTimeFilteredSelected);
        dialog.getEndDate_ftlabel().setEnabled(isTimeFilteredSelected);
        dialog.getEndDate_ftlabel().setEnabled(isTimeFilteredSelected);
        dialog.getEndDate_date().setEnabled(isTimeFilteredSelected);
        dialog.getStartDate_date().setEnabled(isTimeFilteredSelected);
        dialog.getStartDate_time().setEnabled(isTimeFilteredSelected);
        dialog.getEndDate_time().setEnabled(isTimeFilteredSelected);
    }

    private String dateFormatter(Date unformattedDate)
    {
        String formattedDate = null;
        UTCDateFormat dateSDF =
            new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_DATE);

        formattedDate = dateSDF.format(unformattedDate);
        return formattedDate;
    }

    private Date dateFormatter(String unformattedDate, String formattedTime)
            throws ParseException
    {
        Date formattedDate = null;
        UTCDateFormat dateSDF =
            new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_DATE);
        Date tempFormattedDate = dateSDF.parse(unformattedDate);
        UTCDateFormat dateISOSDF =
            new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_DATEISO);
        String finalDateStr = dateISOSDF.format(tempFormattedDate);
        UTCDateFormat isoSDF = new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_ISO);
        formattedDate = isoSDF.parse(finalDateStr + "T" + formattedTime + ".0");
        return formattedDate;
    }

    private Date dateFormatter1(String formattedDate, String format)
            throws ParseException
    {
        UTCDateFormat isoSDF = new UTCDateFormat(format);
        return isoSDF.parse(formattedDate);
    }

    private String timeFormatter(Date unformattedTime)
    {
        String formattedTime = null;
        UTCDateFormat timeSDF =
            new UTCDateFormat(MainForm.SIMPLEDATEFORMAT_TIME);

        formattedTime = timeSDF.format(unformattedTime);
        return formattedTime;
    }

    /**
     * Effectue la validation des donn�es du formulaire d'entr�e.
     */
    @Override
    public boolean validateForm()
    {
        clearErrorsMessages();
        // R�sultat de la validation
        boolean result = true;
        IInputParamsObject currentInputObject = dialog.getCurrentInputObject();
        String inputFilePath = dialog.getInputFilePath().getText();

        boolean isTimeFilterEnabled =
            dialog.getIsTimeFilteredEnabled().isSelected();

        // Gestion du type de fichier
        currentInputObject.setFormat(InputFilesTypes.translate((String) dialog
            .getFileTypeValue().getSelectedItem()));

        // L'emplacement du fichier de g�n�ration est partiellement g�r� par la
        // m�thode chooseInputFile(). On v�rifie ici que l'utilisateur ne l'a
        // pas supprim� ensuite.

        // Il faut �galement v�rifier la saisie du path direct
        if (null == inputFilePath || inputFilePath.isEmpty())
        {
            addRequiredFieldMissingError("input file"); //$NON-NLS-1$
            result = false;
        }
        else
        {
            if (!new File(inputFilePath).exists())
            {
                addErrorMessage(Globals.WRONG_FILEPATH);
                result = false;
            }
        }

        if (isTimeFilterEnabled)
        {
            boolean isStartDateOK = true;
            boolean isEndDateOK = true;
            if (dialog.getStartDate_date().getText() == null
                    || dialog.getStartDate_date().getText().isEmpty())
            {
                addRequiredFieldMissingError("date in Start Date"); //$NON-NLS-1$
                result = false;
                isStartDateOK = false;
            }
            if (dialog.getStartDate_time().getText() == null
                    || dialog.getStartDate_time().getText().isEmpty())
            {
                addRequiredFieldMissingError("time in Start Date"); //$NON-NLS-1$
                isStartDateOK = false;
                result = false;
            }
            if (dialog.getEndDate_date().getText() == null
                    || dialog.getEndDate_date().getText().isEmpty())
            {
                addRequiredFieldMissingError("date in End Date"); //$NON-NLS-1$
                isEndDateOK = true;
                result = false;
            }
            if (dialog.getEndDate_time().getText() == null
                    || dialog.getEndDate_time().getText().isEmpty())
            {
                addRequiredFieldMissingError("time in End Date"); //$NON-NLS-1$
                isEndDateOK = true;
                result = false;
            }
            if (isEndDateOK && isStartDateOK)
            {
                try
                {
                    Date startDate =
                        dateFormatter(dialog.getStartDate_date().getText(),
                            dialog.getStartDate_time().getText());
                    Date endDate =
                        dateFormatter(dialog.getEndDate_date().getText(),
                            dialog.getEndDate_time().getText());
                    if (startDate.compareTo(endDate) > 0)
                    {
                        addDateMisOrderError("End date", "start date");
                        result = false;
                    }
                }
                catch (ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        // Emplacement des donn�es � utiliser lorsque le format est csv
        if (InputFilesTypes.CSV_FORMAT.equals(currentInputObject.getFormat()))
        {
            boolean isCheckValidityEnabled =
                dialog.getIsCheckValidityEnabled().isSelected();
            boolean isDateTimeIgnored =
                dialog.getIsDateTimeEnabled().isSelected();
            boolean isJoinDateTime = dialog.getJoinDateTime().isSelected();

            String firstLine = dialog.getFirstLine().getText();
            String lastLine = dialog.getLastLine().getText();

            if (firstLine == null || firstLine.isEmpty())
            {
                addRequiredFieldMissingError("first line"); //$NON-NLS-1$
                result = false;
            }

            if (lastLine == null || lastLine.isEmpty())
            {
                // La derni�re ligne de donn�es est une information optionnelle.
                ((CsvInputParamsObject) currentInputObject)
                    .setLastLineDefined(false);
            }
            else
            {
                // La derni�re ligne de donn�es est une information optionnelle.
                ((CsvInputParamsObject) currentInputObject)
                    .setLastLineDefined(true);
                if (firstLine != null
                        && !firstLine.isEmpty()
                        && Integer.valueOf(firstLine) > Integer
                            .valueOf(lastLine))
                {
                    addIntMisOrderError("Last line", "first line");
                    result = false;
                }
            }
            if (isCheckValidityEnabled)
            {
                if (dialog.getValidityValue().getText() == null
                        || dialog.getValidityValue().getText().isEmpty())
                {
                    addRequiredFieldMissingError("validity value"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getCheckValidityColumnNumber().getText() == null
                        || dialog.getCheckValidityColumnNumber().getText()
                            .isEmpty()
                        || Integer.valueOf(dialog
                            .getCheckValidityColumnNumber().getText()) <= 0)
                {
                    addRequiredFieldMissingError("validity column number"); //$NON-NLS-1$
                    result = false;
                }
            }
            if (!isDateTimeIgnored && isJoinDateTime)
            {
                if (dialog.getDateTimeColumnNumber().getText() == null
                        || ""
                            .equals(dialog.getDateTimeColumnNumber().getText())
                        || Integer.valueOf(dialog.getDateTimeColumnNumber()
                            .getText()) <= 0)
                {
                    addRequiredFieldMissingError("date & time column number"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getDateTimeFormat().getSelectedItem() == null)
                {
                    addRequiredFieldMissingError("date & time format"); //$NON-NLS-1$
                    result = false;
                }
            }
            if (!isDateTimeIgnored && !isJoinDateTime)
            {
                if (dialog.getDateFormatColumnNumber().getText() == null
                        || dialog.getDateFormatColumnNumber().getText()
                            .isEmpty()
                        || Integer.valueOf(dialog.getDateFormatColumnNumber()
                            .getText()) <= 0)
                {
                    addRequiredFieldMissingError("date column number"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getDateFormatCombo().getSelectedItem() == null)
                {
                    addRequiredFieldMissingError("date format"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getTimeFormatColumnNumber().getText() == null
                        || dialog.getTimeFormatColumnNumber().getText()
                            .isEmpty()
                        || Integer.valueOf(dialog.getTimeFormatColumnNumber()
                            .getText()) <= 0)
                {
                    addRequiredFieldMissingError("time column number"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getTimeFormatCombo().getSelectedItem() == null)
                {
                    addRequiredFieldMissingError("time format"); //$NON-NLS-1$
                    result = false;
                }
            }

            if (dialog.getLatitudeFormatColumnNumber().getText() == null
                    || dialog.getLatitudeFormatColumnNumber().getText()
                        .isEmpty()
                    || Integer.valueOf(dialog.getLatitudeFormatColumnNumber()
                        .getText()) <= 0)
            {
                addRequiredFieldMissingError("latitude column number"); //$NON-NLS-1$
                result = false;
            }
            if (dialog.getLatitudeFormat().getSelectedItem() == null
                    || ((String) dialog.getLatitudeFormat().getSelectedItem())
                        .isEmpty())
            {
                addRequiredFieldMissingError("latitude format"); //$NON-NLS-1$
                result = false;
            }
            if (dialog.getLongitudeFormatColumnNumber().getText() == null
                    || dialog.getLongitudeFormatColumnNumber().getText()
                        .isEmpty()
                    || Integer.valueOf(dialog.getLongitudeFormatColumnNumber()
                        .getText()) <= 0)
            {
                addRequiredFieldMissingError("longitude column number"); //$NON-NLS-1$
                result = false;
            }
            if (dialog.getLongitudeFormat().getSelectedItem() == null
                    || ((String) dialog.getLongitudeFormat().getSelectedItem())
                        .isEmpty())
            {
                addRequiredFieldMissingError("longitude format"); //$NON-NLS-1$
                result = false;
            }
        }
        // Emplacement des donn�es � utiliser lorsque le format est csv
        if (InputFilesTypes.GENERAL_NETCDF_FORMAT.equals(currentInputObject
            .getFormat()))
        {
            boolean isJoinDateTime = dialog.getJoinDateTime1().isSelected();
            if (isJoinDateTime)
            {
                if (dialog.getDateTimeVariableName().getText() == null
                        || ""
                            .equals(dialog.getDateTimeVariableName().getText()))
                {
                    addRequiredFieldMissingError("date & time column name"); //$NON-NLS-1$
                    result = false;
                }
            }
            if (!isJoinDateTime)
            {
                if (dialog.getDateVariableName().getText() == null
                        || dialog.getDateVariableName().getText().isEmpty())
                {
                    addRequiredFieldMissingError("date column name"); //$NON-NLS-1$
                    result = false;
                }
                if (dialog.getTimeVariableName().getText() == null
                        || dialog.getTimeVariableName().getText().isEmpty())
                {
                    addRequiredFieldMissingError("time column name"); //$NON-NLS-1$
                    result = false;
                }
            }
            if (dialog.getLatitudeFormatColumnName().getText() == null
                    || dialog.getLatitudeFormatColumnName().getText().isEmpty())
            {
                addRequiredFieldMissingError("latitude column name"); //$NON-NLS-1$
                result = false;
            }
            if (dialog.getLongitudeFormatColumnName().getText() == null
                    || dialog.getLongitudeFormatColumnName().getText()
                        .isEmpty())
            {
                addRequiredFieldMissingError("longitude column name"); //$NON-NLS-1$
                result = false;
            }
        }
        // Emplacement des donn�es � utiliser lorsque le format est nmea
        if (InputFilesTypes.NMEA_183_FORMAT.equals(currentInputObject
            .getFormat()))
        {
            if (dialog.getLossOfAccuracyField().getText() == null
                    || dialog.getLossOfAccuracyField().getText().isEmpty())
            {
                addRequiredFieldMissingError("loss of accuracy"); //$NON-NLS-1$
                result = false;
            }
        }
        // Emplacement des donn�es � utiliser lorsque le format est nvi
        if (InputFilesTypes.NETCDF_NVI_FORMAT.equals(currentInputObject
            .getFormat()))
        {
            if (dialog.getNviForceSplittingFileInProfiles().isSelected())
            {
                if (dialog.getNviSegmentsTable().getRowCount() == 0)
                {
                    addRequiredFieldMissingError("profiles"); //$NON-NLS-1$
                    result = false;
                }
            }
        }
        // Emplacement des donn�es � utiliser lorsque le format est ukooa
        if (InputFilesTypes.UKOOA_P1_90.equals(currentInputObject.getFormat()))
        {
            if (dialog.getUkooaForceSplittingFileInProfiles().isSelected())
            {
                if (dialog.getUkooaSegmentsTable().getRowCount() == 0)
                {
                    addRequiredFieldMissingError("profiles"); //$NON-NLS-1$
                    result = false;
                }
            }
        }
        if (!result)
        {
            showErrorDialog();
        }
        return result;
    }

    public void showErrorDialog()
    {
        List<String> errors = getErrorsMessages();
        StringBuilder concatErrors = new StringBuilder();
        for (String anError : errors)
        {
            concatErrors.append(anError).append("\n"); //$NON-NLS-1$ // NOI18N
        }
        clearErrorsMessages();
        JOptionPane.showMessageDialog(dialog, concatErrors.toString());
    }

    public void saveInputFile() throws EABException
    {
        int n =
            JOptionPane.showConfirmDialog(dialog,
                Globals.SAVE_PARAMETER_FILE_QUESTION,
                Globals.SAVE_PARAMETER_FILE_TITLE, JOptionPane.YES_NO_OPTION);

        if (JOptionPane.OK_OPTION == n)
        {// INFO CLT Interface de demande de renseignements sur le
         // fichier

            // Create a file chooser
            final JFileChooser fc = new JFileChooser();

            fc.setCurrentDirectory(new File(DefaultsHandler
                .getDefaultPath(DefaultFolder.INPUT_PARAMETER_FOLDER)));

            // In response to a button click:
            int returnVal = fc.showOpenDialog(dialog);

            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File chosenFile = fc.getSelectedFile();

                DefaultsHandler.setDefaultPath(
                    DefaultFolder.INPUT_PARAMETER_FOLDER,
                    chosenFile.getParent());

                try
                {
                    dialog.getCurrentInputObject().convertToXML(chosenFile);
                }
                catch (TransformerConfigurationException e)
                {
                    JOptionPane
                        .showMessageDialog(
                            dialog.getParent(),
                            Messages.getString(Messages.MESSAGES,
                                "TRANSFORMER_CONFIGURATION_EXCEPTION"), "Transformer creation Error", //$NON-NLS-1$
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (ParserConfigurationException e)
                {
                    JOptionPane.showMessageDialog(dialog.getParent(), Messages
                        .getString(Messages.MESSAGES,
                            "PARSER_CONFIGURATION_EXCEPTION"), "Parsing Error", //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
                }
                catch (TransformerException e)
                {
                    JOptionPane
                        .showMessageDialog(
                            dialog.getParent(),
                            Messages.getString(Messages.MESSAGES,
                                "TRANSFORMER_EXCEPTION"), "Dom to stream conversion Error", //$NON-NLS-1$
                            JOptionPane.ERROR_MESSAGE);
                }
                catch (IOException e)
                {
                    JOptionPane.showMessageDialog(dialog.getParent(),
                        Messages.getString(Messages.MESSAGES,
                            "IO_EXCEPTION_WRITE"), "File write Error", //$NON-NLS-1$
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    public void validateDateInput(int selectedRow, int selectedColumn)
            throws ParseException
    {
        // R�sultat de la validation
        boolean result = true;
        Date currentDate, beforeDate, afterDate;
        JTable table = null;
        if (this.dialog.getCurrentInputObject() instanceof NviInputParamsObject)
            table = dialog.getNviSegmentsTable();
        else if (this.dialog.getCurrentInputObject() instanceof UkooaInputParamsObject)
            table = dialog.getUkooaSegmentsTable();
        if (((String) table.getValueAt(selectedRow, selectedColumn)) != null
                && !((String) table.getValueAt(selectedRow, selectedColumn))
                    .isEmpty())
        {
            currentDate =
                dateFormatter1(
                    (String) table.getValueAt(selectedRow, selectedColumn),
                    SIMPLEDATE_TIMEFORMATMASK);
            if (selectedColumn == 0)
            {
                if (selectedRow > 0
                        && table.getValueAt(selectedRow - 1, 1) != null
                        && !((String) table.getValueAt(selectedRow - 1, 1))
                            .isEmpty())
                {
                    beforeDate =
                        dateFormatter1(
                            (String) table.getValueAt(selectedRow - 1, 1),
                            SIMPLEDATE_TIMEFORMATMASK);
                    if (beforeDate.compareTo(currentDate) > 0)
                    {
                        addDateMisOrderError("Start date", "end date");
                        result = false;
// changeTextColor(result, new int[] {selectedRow - 1,
// selectedRow}, new int[] {1, 0});

                    }
                }
                if (table.getValueAt(selectedRow, 1) != null
                        && !((String) table.getValueAt(selectedRow, 1))
                            .isEmpty())
                {
                    afterDate =
                        dateFormatter1(
                            (String) table.getValueAt(selectedRow, 1),
                            SIMPLEDATE_TIMEFORMATMASK);
                    if (currentDate.compareTo(afterDate) > 0)
                    {
                        addDateMisOrderError("End date", "start date");
                        result = false;
// changeTextColor(result,
// new int[] {selectedRow, selectedRow}, new int[] {0, 1});
                    }
                }
            }
            else if (selectedColumn == 1)
            {
                if (table.getValueAt(selectedRow, 0) != null
                        && !((String) table.getValueAt(selectedRow, 0))
                            .isEmpty())
                {
                    beforeDate =
                        dateFormatter1(
                            (String) table.getValueAt(selectedRow, 0),
                            SIMPLEDATE_TIMEFORMATMASK);
                    if (beforeDate.compareTo(currentDate) > 0)
                    {
                        addDateMisOrderError("End date", "start date");
                        result = false;
// changeTextColor(result,
// new int[] {selectedRow, selectedRow}, new int[] {0, 1});
                    }
                }
                if (selectedRow + 1 < table.getRowCount()
                        && table.getValueAt(selectedRow + 1, 0) != null
                        && !((String) table.getValueAt(selectedRow + 1, 0))
                            .isEmpty())
                {
                    afterDate =
                        dateFormatter1(
                            (String) table.getValueAt(selectedRow + 1, 0),
                            SIMPLEDATE_TIMEFORMATMASK);
                    if (currentDate.compareTo(afterDate) > 0)
                    {
                        addDateMisOrderError("Start date", "end date");
                        result = false;
// changeTextColor(result, new int[] {selectedRow + 1,
// selectedRow}, new int[] {0, 1});
                    }
                }
            }
            if (!result)
            {
                showErrorDialog();
            }
        }
// else if (result && selectedRow > 0)
// {
// changeTextColor(result, new int[] {selectedRow - 1, selectedRow,
// selectedRow, selectedRow + 1}, new int[] {1, 0, 1, 0});
// }
// else if (result && selectedRow == 0)
// {
// changeTextColor(result, new int[] {selectedRow, selectedRow,
// selectedRow + 1}, new int[] {0, 1, 0});
// }
    }

    /**
     * @param result
     * @param selectedRow
     * @param selectedColumn
     */
    private void changeTextColor(boolean result, int[] selectedRows,
            int[] selectedColumns)
    {
        for (int i = 0; i < selectedRows.length; i++)
        {
            JTable table = null;
            if (this instanceof NviInputDialogController)
                table = dialog.getNviSegmentsTable();
            else if (this instanceof UkooaInputDialogController)
                table = dialog.getUkooaSegmentsTable();
            DefaultTableCellRenderer cellRenderer =
                ((DefaultTableCellRenderer) table.getCellRenderer(
                    selectedRows[i], selectedColumns[i]));
            Component c =
                cellRenderer.getTableCellRendererComponent(table,
                    table.getValueAt(selectedRows[i], selectedColumns[i]),
                    false, false, selectedRows[i], selectedColumns[i]);
            if (!result)
            {
                c.setForeground(Color.RED);
            }

        }
    }

    /**
     * Clear errorsMessages.
     */
    public void clearErrorsMessages()
    {
        _errorsMessages.clear();
    }

    /**
     * Getter sur _errorsMessages.
     * 
     * @return the errorsMessages
     */
    public List<String> getErrorsMessages()
    {
        return _errorsMessages;
    }

    /**
     * Add an errorMessage.
     */
    public void addErrorMessage(final String msg)
    {
        _errorsMessages.add(msg);
    }

    /**
     * Add an errorMessage for a missing mandatory field.
     */
    public void addRequiredFieldMissingError(final String aField)
    {
        addErrorMessage(Messages.getString(Messages.MESSAGES,
            "mandatory.field.missing", new String[] {aField}));
    }

    /**
     * Add an errorMessage
     */
    public void addDateMisOrderError(final String aField1, final String aField2)
    {
        addErrorMessage(Messages.getString(Messages.MESSAGES,
            "mandatory.field.date.misorder", new String[] {aField1, aField2}));
    }

    /**
     * Add an errorMessage for
     */
    public void addIntMisOrderError(final String aField1, final String aField2)
    {
        addErrorMessage(Messages.getString(Messages.MESSAGES,
            "mandatory.field.line.misorder", new String[] {aField1, aField2}));
    }

    /**
     * Remove an errorMessage.
     */
    public void removeErrorMessage(final String msg)
    {
        _errorsMessages.remove(msg);
    }

}
