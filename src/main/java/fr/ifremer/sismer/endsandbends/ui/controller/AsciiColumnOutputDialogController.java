/* ============================================================================
 * Nom du fichier	: AsciiColumnOutputDialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import java.awt.CardLayout;

import fr.ifremer.sismer.endsandbends.mediation.output.AsciiColumnOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class AsciiColumnOutputDialogController extends OutputDialogController
{
    /**
     * @param dialog
     */
    public AsciiColumnOutputDialogController(ResultsDialog dialog,
            OutputParamsObject outputObject)
    {
        super(dialog, outputObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeDialog()
    {
        this.initializeDialogDefault();
        AsciiColumnOutputParamsObject asciiColmunOutputObject =
            (AsciiColumnOutputParamsObject) outputObject;

        dialog.setCurrentOutputObject(new AsciiColumnOutputParamsObject(
            asciiColmunOutputObject));

        // Time correction
        if (asciiColmunOutputObject.getTimeCorrection() <= 24)
        {
            dialog.getTimeToApply().setValue(
                asciiColmunOutputObject.getTimeCorrection());
        }

        dialog.getColumnsAsciiGroupSeparateDateTimeFormatsCheckBox()
            .setSelected(asciiColmunOutputObject.isJoinDateTimeParamsEnabled());

        // Date
        if (String.valueOf(asciiColmunOutputObject.getDateFormatColumnNumber()) != null)
        {
            dialog.getColumnsAsciiDateColumnNumber().setText(
                String.valueOf(asciiColmunOutputObject
                    .getDateFormatColumnNumber()));
        }
        dialog.getColumnsAsciiDateFormats().setSelectedItem(
            asciiColmunOutputObject.getDateFormat());

        // Date & Time
        if (String.valueOf(asciiColmunOutputObject
            .getDateTimeFormatColumnNumber()) != null)
        {
            dialog.getColumnsAsciiDatetimeColumnNumber().setText(
                String.valueOf(asciiColmunOutputObject
                    .getDateTimeFormatColumnNumber()));
        }
        dialog.getColumnsAsciiDatetimeFormats().setSelectedItem(
            asciiColmunOutputObject.getDateTimeFormat());

        // Time
        if (String.valueOf(asciiColmunOutputObject.getTimeFormatColumnNumber()) != null)
        {
            dialog.getColumnsAsciiTimeColumnNumber().setText(
                String.valueOf(asciiColmunOutputObject
                    .getTimeFormatColumnNumber()));
        }
        dialog.getColumnsAsciiTimeFormats().setSelectedItem(
            asciiColmunOutputObject.getTimeFormat());

        // Latitude
        if (String.valueOf(asciiColmunOutputObject
            .getLatitudeFormatColumnNumber()) != null)
        {
            dialog.getColumnsAsciiLatitudeColumnNumber().setText(
                String.valueOf(asciiColmunOutputObject
                    .getLatitudeFormatColumnNumber()));
        }
        dialog.getColumnsAsciiLatitudeFormat().setSelectedItem(
            asciiColmunOutputObject.getLatitudeFormat());

        // Longitude
        if (String.valueOf(asciiColmunOutputObject
            .getLongitudeFormatColumnNumber()) != null)
        {
            dialog.getColumnsAsciiLongitudeColumnNumber().setText(
                String.valueOf(asciiColmunOutputObject
                    .getLongitudeFormatColumnNumber()));
        }
        dialog.getColumnsAsciiLongitudeFormat().setSelectedItem(
            asciiColmunOutputObject.getLongitudeFormat());

        // Start End Indicator
        if (String.valueOf(asciiColmunOutputObject
            .getStartEndIndicatorColumnNumber()) != null)
        {
            dialog.getStartEndIndicatorColumnNumber().setText(
                String.valueOf(asciiColmunOutputObject
                    .getStartEndIndicatorColumnNumber()));
        }

        // Tokenizer
        dialog.getColumnsAsciiTokenizer().setText(
            asciiColmunOutputObject.getTokenizer());

        // Start Indicator
        dialog.getStartIndicatorField().setText(
            asciiColmunOutputObject.getStartIndicator());
        // End Indicator
        dialog.getEndIndicatorField().setText(
            asciiColmunOutputObject.getEndIndicator());

        handleDateTimeSplit();

    }

    private void handleDateTimeSplit()
    {
        CardLayout cl = (CardLayout) (dialog.getjPanel3().getLayout());
        if (dialog.getColumnsAsciiGroupSeparateDateTimeFormatsCheckBox()
            .isSelected())
        {
            cl.show(dialog.getjPanel3(), "dateTimeJoin");
            // Date
            dialog.getColumnsAsciiDateColumnNumber().setText(null);
            dialog.getColumnsAsciiDateFormats().setSelectedItem(null);

            // Time
            dialog.getColumnsAsciiTimeColumnNumber().setText(null);
            dialog.getColumnsAsciiTimeFormats().setSelectedItem(null);
        }
        else
        {
            cl.show(dialog.getjPanel3(), "dateTimeSplit");
            dialog.getColumnsAsciiDatetimeColumnNumber().setText(null);
            dialog.getColumnsAsciiDatetimeFormats().setSelectedItem(null);
        }
    }
}
