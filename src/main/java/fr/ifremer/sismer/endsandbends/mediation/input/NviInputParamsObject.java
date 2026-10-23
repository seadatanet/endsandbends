/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.ifremer.sismer.endsandbends.mediation.input;

import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * @author mbettich
 */
public class NviInputParamsObject extends SuperClassNviUkooaParamsObject
{

    /**
     * Constructeur
     */
    public NviInputParamsObject()
    {
        super.setFormat(InputFilesTypes.UKOOA_P1_90);
    }

    /**
     * Constructeur
     * 
     * @param inputObject
     */
    public NviInputParamsObject(NviInputParamsObject inputObject)
    {
        super(inputObject);
        this._isSplitFileEnabled = inputObject._isSplitFileEnabled;
        this._dateSegmentList = inputObject._dateSegmentList;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EABException
     */
    @Override
    public void updateFromInputDialog(InputDialog dialog) throws EABException
    {
        super.updateFromInputDialog(dialog);

        _isSplitFileEnabled =
            dialog.getNviForceSplittingFileInProfiles().isSelected();
        _dateSegmentList = populateSegmentsTable(dialog.getNviSegmentsTable());
    }
}
