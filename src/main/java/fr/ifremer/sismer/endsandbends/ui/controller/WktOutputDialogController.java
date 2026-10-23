/* ============================================================================
 * Nom du fichier	: WktOutputDialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.WktOutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class WktOutputDialogController extends OutputDialogController
{

    /**
     * @param dialog
     */
    public WktOutputDialogController(ResultsDialog dialog,
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
        WktOutputParamsObject wktOutputObject =
            (WktOutputParamsObject) outputObject;

        dialog
            .setCurrentOutputObject(new WktOutputParamsObject(wktOutputObject));

        if (wktOutputObject.getResolution() <= 7)
        {
            dialog.getWktResolutionValue().setValue(
                wktOutputObject.getResolution());
        }

    }
}
