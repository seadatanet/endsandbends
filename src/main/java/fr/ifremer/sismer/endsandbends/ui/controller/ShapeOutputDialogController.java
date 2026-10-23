/* ============================================================================
 * Nom du fichier	: ShapeOutputDialogController.java
 * Auteur			: Altran
 * Date				: 28 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.ShapeOutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class ShapeOutputDialogController extends OutputDialogController
{

    /**
     * @param dialog
     */
    public ShapeOutputDialogController(ResultsDialog dialog,
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
        ShapeOutputParamsObject shpOutputObject =
            (ShapeOutputParamsObject) outputObject;

        dialog.setCurrentOutputObject(new ShapeOutputParamsObject(
            shpOutputObject));

    }

}
