/* ============================================================================
 * Nom du fichier	: GmlOutputDuialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import fr.ifremer.sismer.endsandbends.mediation.output.GmlOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class GmlOutputDialogController extends OutputDialogController
{
    /**
     * @param dialog
     */
    public GmlOutputDialogController(ResultsDialog dialog,
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
        GmlOutputParamsObject gmlOutputObject =
            (GmlOutputParamsObject) outputObject;

        dialog
            .setCurrentOutputObject(new GmlOutputParamsObject(gmlOutputObject));

        if (gmlOutputObject.getResolution() <= 7)
        {
            dialog.getGmlResolutionValue().setValue(
                gmlOutputObject.getResolution());
        }

        if (gmlOutputObject.getGenerationType().equalsIgnoreCase("gml"))
        {
            dialog.getGenerateGmlFileRadio().setSelected(true);
            dialog.getInsertIntoMikadoFileRadio().setSelected(false);
        }
        else if (gmlOutputObject.getGenerationType().equalsIgnoreCase("mikado"))
        {
            dialog.getInsertIntoMikadoFileRadio().setSelected(true);
            dialog.getGenerateGmlFileRadio().setSelected(false);
        }

    }

}
