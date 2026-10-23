/* ============================================================================
 * Nom du fichier	: NmeaInputDialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import fr.ifremer.sismer.endsandbends.mediation.input.InputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.input.NmeaInputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class NmeaInputDialogController extends InputDialogController
{

    /**
     * @param dialog
     */
    public NmeaInputDialogController(InputDialog dialog,
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
        NmeaInputParamsObject nmeaInputObject =
            (NmeaInputParamsObject) inputObject;

        dialog
            .setCurrentInputObject(new NmeaInputParamsObject(nmeaInputObject));

        // Loss Of Accuracy
        if (String.valueOf(nmeaInputObject.getLossOfAccuracy()) != null)
        {
            dialog.getLossOfAccuracyField().setText(
                String.valueOf(nmeaInputObject.getLossOfAccuracy()));
        }
    }
}
