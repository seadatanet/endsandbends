/* ============================================================================
 * Nom du fichier	: NetCdfInputDialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import java.awt.CardLayout;

import fr.ifremer.sismer.endsandbends.mediation.input.InputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NetCdfInputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class NetCdfInputDialogController extends InputDialogController
{

    /**
     * @param dialog
     */
    public NetCdfInputDialogController(InputDialog dialog,
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
        this.initializeDialogDefault();
        NetCdfInputParamsObject netCdfInputObject =
            (NetCdfInputParamsObject) inputObject;

        dialog.setCurrentInputObject(new NetCdfInputParamsObject(
            netCdfInputObject));

        dialog.getJoinDateTime1().setSelected(
            netCdfInputObject.isJoinDateTimeParamsEnabled());

        // Date & Time
        dialog.getDateTimeVariableName().setText(
            netCdfInputObject.getDateTimeVariableName());

        // Date
        dialog.getDateVariableName().setText(
            netCdfInputObject.getDateVariableName());

        // Time
        dialog.getTimeVariableName().setText(
            netCdfInputObject.getTimeVariableName());

        // Latitude
        dialog.getLatitudeVariableName().setText(
            netCdfInputObject.getLatitudeVariableName());

        // Longitude
        dialog.getLongitudeVariableName().setText(
            netCdfInputObject.getLongitudeVariableName());

        handleDateTimeSplit();

    }

    private void handleDateTimeSplit()
    {
        CardLayout cl = (CardLayout) (dialog.getjPanel8().getLayout());
        if (dialog.getJoinDateTime().isSelected())
        {
            cl.show(dialog.getjPanel8(), "dateTimeJoin");
            // Date
            dialog.getDateVariableName().setText(null);
            // Time
            dialog.getTimeVariableName().setText(null);
        }
        else
        {
            cl.show(dialog.getjPanel8(), "dateTimeSplit");
            dialog.getDateTimeVariableName().setText(null);
        }
    }
}
