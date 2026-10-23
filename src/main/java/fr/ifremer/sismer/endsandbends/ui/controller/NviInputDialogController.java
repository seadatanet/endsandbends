/* ============================================================================
 * Nom du fichier	: NviInputDialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import fr.ifremer.sismer.endsandbends.mediation.input.InputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NviInputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NviUKOOAJTable;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class NviInputDialogController extends InputDialogController
{

    /**
     * @param dialog
     */
    public NviInputDialogController(InputDialog dialog,
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
        NviInputParamsObject nviInputObject =
            (NviInputParamsObject) inputObject;

        dialog.setCurrentInputObject(new NviInputParamsObject(nviInputObject));

        dialog.getNviForceSplittingFileInProfiles().setSelected(
            nviInputObject.isSplitFileEnabled());
        dialog.getNviSegmentsTable().setEnabled(
            nviInputObject.isSplitFileEnabled());
        populateSegmentsTable(nviInputObject);

    }

    private void populateSegmentsTable(NviInputParamsObject nviInputObject)
    {
        NviUKOOAJTable nviTable = (NviUKOOAJTable) dialog.getNviSegmentsTable();
        for (int i = 0; i < nviInputObject.getDateSegmentList().size(); i++)
        {
            nviTable.addRow();
            nviTable.editCell(i, 0, nviInputObject.getDateSegmentList().get(i)
                .getStartDate());
            nviTable.editCell(i, 1, nviInputObject.getDateSegmentList().get(i)
                .getEndDate());
        }
    }
}
