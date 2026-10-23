/* ============================================================================
 * Nom du fichier	: AsciiOutputDialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import fr.ifremer.sismer.endsandbends.mediation.output.AsciiOutputParamsObject;
import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.resultsDataDialog.ResultsDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class AsciiOutputDialogController extends OutputDialogController
{
    /**
     * @param dialog
     */
    public AsciiOutputDialogController(ResultsDialog dialog,
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
        AsciiOutputParamsObject asciiOutputObject =
            (AsciiOutputParamsObject) outputObject;

        dialog.setCurrentOutputObject(new AsciiOutputParamsObject(
            asciiOutputObject));

        dialog.getWriteSeconds().setSelected(asciiOutputObject.isWithSeconds());

        if (asciiOutputObject.getBoatName() != null)
            dialog.getIfremerAsciiBoatNameValue().setText(
                asciiOutputObject.getBoatName());
        if (asciiOutputObject.getCampaignId() != null)
            dialog.getIfremerAsciiCampainIdentifierValue().setText(
                asciiOutputObject.getCampaignId());
        if (asciiOutputObject.getCampaignTitle() != null)
            dialog.getIfremerAsciiCampainTitleValue().setText(
                asciiOutputObject.getCampaignTitle());
        if (asciiOutputObject.getOperator() != null)
            dialog.getIfremerAsciiOperatorNameValue().setText(
                asciiOutputObject.getOperator());
        if (asciiOutputObject.getComments() != null)
            dialog.getIfremerAsciiCommentValue().setText(
                asciiOutputObject.getComments());

        if (asciiOutputObject.getType().equalsIgnoreCase("Survey"))
        {
            dialog.getAsciiTypeDataRadioButton().setSelected(false);
            dialog.getAsciiTypeSurveyRadioButton().setSelected(true);
        }
        else if (asciiOutputObject.getType().equalsIgnoreCase("Data"))
        {
            dialog.getAsciiTypeDataRadioButton().setSelected(true);
            dialog.getAsciiTypeSurveyRadioButton().setSelected(false);
        }

    }
}
