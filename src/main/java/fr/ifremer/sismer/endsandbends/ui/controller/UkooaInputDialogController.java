/* ============================================================================
 * Nom du fichier	: UKOOAInputDialogController.java
 * Auteur			: Altran
 * Date				: 12 mai 2011
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import fr.ifremer.sismer.endsandbends.mediation.input.InputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NviUKOOAJTable;
import fr.ifremer.sismer.endsandbends.mediation.input.UkooaInputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class UkooaInputDialogController extends InputDialogController
{

    /**
     * @param dialog
     */
    public UkooaInputDialogController(InputDialog dialog,
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
        UkooaInputParamsObject ukooaInputParamsObject =
            (UkooaInputParamsObject) inputObject;

        dialog.setCurrentInputObject(new UkooaInputParamsObject(
            ukooaInputParamsObject));

        dialog.getjRadioButton_degrees().setSelected(
            ukooaInputParamsObject.isDegreeTheAngularUnit());
        dialog.getjRadioButton_grades().setSelected(
            !ukooaInputParamsObject.isDegreeTheAngularUnit());

        dialog.getUkooaForceSplittingFileInProfiles().setSelected(
            ukooaInputParamsObject.isSplitFileEnabled());
        dialog.getUkooaSegmentsTable().setEnabled(
            ukooaInputParamsObject.isSplitFileEnabled());
        populateSegmentsTable(ukooaInputParamsObject);

    }

    private void populateSegmentsTable(UkooaInputParamsObject ukooaInputObject)
    {
        NviUKOOAJTable uklooaTable =
            (NviUKOOAJTable) dialog.getUkooaSegmentsTable();
        for (int i = 0; i < ukooaInputObject.getDateSegmentList().size(); i++)
        {
            uklooaTable.addRow();
            uklooaTable.editCell(i, 0, ukooaInputObject.getDateSegmentList()
                .get(i).getStartDate());
            uklooaTable.editCell(i, 1, ukooaInputObject.getDateSegmentList()
                .get(i).getEndDate());
        }
    }
}