/* ============================================================================
 * Nom du fichier	: CsvInputDialogController.java
 * Auteur			: Altran
 * Date				: 10 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import java.awt.CardLayout;

import fr.ifremer.sismer.endsandbends.mediation.input.CsvInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.InputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class CsvInputDialogController extends InputDialogController
{

    /**
     * @param dialog
     */
    public CsvInputDialogController(InputDialog dialog,
            InputParamsObject inputObject)
    {
        super(dialog, inputObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeDialog()
    {
        initializeDialogDefault();
        CsvInputParamsObject csvInputObject =
            (CsvInputParamsObject) inputObject;

        dialog.setCurrentInputObject(new CsvInputParamsObject(csvInputObject));

        // First Line
        if (String.valueOf(csvInputObject.getFirstLine()) != null)
        {
            dialog.getFirstLine().setText(
                String.valueOf(csvInputObject.getFirstLine()));
        }

        // Delimiter Used
        if (csvInputObject.getDelimiterUsed() != null)
        {
            dialog.getDelimiterUsed().setSelectedItem(
                csvInputObject.getDelimiterUsed());
        }

        // Last Line
        if (String.valueOf(csvInputObject.getLastLine()) != null)
        {
            dialog.getLastLine().setText(
                (String.valueOf(csvInputObject.getLastLine())));
        }

        // Check Validity Value
        dialog.getIsCheckValidityEnabled().setSelected(
            csvInputObject.isCheckValidityEnabled());
        dialog.getCheckValidityColumnNumber().setEnabled(
            csvInputObject.isCheckValidityEnabled());
        if (String.valueOf(csvInputObject.getCheckValidityColumnNumber()) != null)
        {
            dialog.getCheckValidityColumnNumber().setText(
                String.valueOf(csvInputObject.getCheckValidityColumnNumber()));
        }
        dialog.getCheckValidityoperatorCombo().setEnabled(
            csvInputObject.isCheckValidityEnabled());
        dialog.getCheckValidityoperatorCombo().setSelectedItem(
            csvInputObject.getCheckValidityOperator());
        dialog.getValidityValue().setEnabled(
            csvInputObject.isCheckValidityEnabled());
        if (String.valueOf(csvInputObject.getValidityValue()) != null)
        {
            dialog.getValidityValue().setText(
                String.valueOf(csvInputObject.getValidityValue()));
        }

        // Date Time: Enabled & Joined
        dialog.getIsDateTimeEnabled().setSelected(
            !csvInputObject.isDateTimeEnabled());

        if (!csvInputObject.isDateTimeEnabled())
        {
            dialog.getIsTimeFilteredEnabled().setSelected(false);
            updateDateTimeFieldsDependingOnTimeFilterSelection();
        }

        dialog.getJoinDateTime().setEnabled(csvInputObject.isDateTimeEnabled());
        dialog.getJoinDateTime().setSelected(
            csvInputObject.isJoinDateTimeParamsEnabled());

        // Date
        if (String.valueOf(csvInputObject.getDateFormatColumnNumber()) != null)
        {
            dialog.getDateFormatColumnNumber().setText(
                String.valueOf(csvInputObject.getDateFormatColumnNumber()));
        }
        dialog.getDateFormatCombo().setSelectedItem(
            csvInputObject.getDateFormat());

        // Date & Time
        if (String.valueOf(csvInputObject.getDateTimeColumnNumber()) != null)
        {
            dialog.getDateTimeColumnNumber().setText(
                String.valueOf(csvInputObject.getDateTimeColumnNumber()));
        }
        dialog.getDateTimeFormat().setSelectedItem(
            csvInputObject.getDateTimeFormat());

        // Time
        if (String.valueOf(csvInputObject.getTimeFormatColumnNumber()) != null)
        {
            dialog.getTimeFormatColumnNumber().setText(
                String.valueOf(csvInputObject.getTimeFormatColumnNumber()));
        }
        dialog.getTimeFormatCombo().setSelectedItem(
            csvInputObject.getTimeFormat());

        dialog.getDateLabel().setEnabled(csvInputObject.isDateTimeEnabled());
        dialog.getTimeLabel().setEnabled(csvInputObject.isDateTimeEnabled());
        dialog.getTimeFormatColumnNumber().setEnabled(
            csvInputObject.isDateTimeEnabled());
        dialog.getTimeFormatCombo().setEnabled(
            csvInputObject.isDateTimeEnabled());
        dialog.getDateFormatColumnNumber().setEnabled(
            csvInputObject.isDateTimeEnabled());
        dialog.getDateFormatCombo().setEnabled(
            csvInputObject.isDateTimeEnabled());

        // Latitude
        if (String.valueOf(csvInputObject.getLatitudeFormatColumnNumber()) != null)
        {
            dialog.getLatitudeFormatColumnNumber().setText(
                String.valueOf(csvInputObject.getLatitudeFormatColumnNumber()));
        }
        dialog.getLatitudeFormat().setSelectedItem(
            csvInputObject.getLatitudeFormat());

        // Longitude
        if (String.valueOf(csvInputObject.getLongitudeFormatColumnNumber()) != null)
        {
            dialog.getLongitudeFormatColumnNumber()
                .setText(
                    String.valueOf(csvInputObject
                        .getLongitudeFormatColumnNumber()));
        }
        dialog.getLongitudeFormat().setSelectedItem(
            csvInputObject.getLongitudeFormat());
        handleDateTimeSplit();
    }

    private void handleDateTimeSplit()
    {
        CardLayout cl = (CardLayout) (dialog.getjPanel3().getLayout());
        if (dialog.getJoinDateTime().isSelected())
        {
            cl.show(dialog.getjPanel3(), "dateTimeJoin");
            // Date
            dialog.getDateFormatColumnNumber().setText(null);
            dialog.getDateFormatCombo().setSelectedItem(null);

            // Time
            dialog.getTimeFormatColumnNumber().setText(null);
            dialog.getTimeFormatCombo().setSelectedItem(null);
        }
        else
        {
            cl.show(dialog.getjPanel3(), "dateTimeSplit");
            dialog.getDateTimeColumnNumber().setText(null);
            dialog.getDateTimeFormat().setSelectedItem(null);
        }
    }
}