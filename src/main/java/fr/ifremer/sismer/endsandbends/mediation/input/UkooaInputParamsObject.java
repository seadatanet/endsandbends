/* ============================================================================
 * Nom du fichier	: UKOOAInputParamsObject.java
 * Auteur			: Altran
 * Date				: 12 mai 2011
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.mediation.input;

import fr.ifremer.sismer.endsandbends.enums.InputFilesTypes;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.ui.inputData.InputDialog;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class UkooaInputParamsObject extends SuperClassNviUkooaParamsObject
{

    private boolean _isDegreeTheAngularUnit;

    /**
     * Constructeur
     */
    public UkooaInputParamsObject()
    {
        super.setFormat(InputFilesTypes.UKOOA_P1_90);
    }

    /**
     * Constructeur
     * 
     * @param inputObject
     */
    public UkooaInputParamsObject(UkooaInputParamsObject inputObject)
    {
        super(inputObject);
        this._isSplitFileEnabled = inputObject._isSplitFileEnabled;
        this._dateSegmentList = inputObject._dateSegmentList;
        this._isDegreeTheAngularUnit = inputObject._isDegreeTheAngularUnit;
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
            dialog.getUkooaForceSplittingFileInProfiles().isSelected();

        _dateSegmentList = populateSegmentsTable(dialog.getNviSegmentsTable());
        _isDegreeTheAngularUnit = dialog.getjRadioButton_degrees().isSelected();
    }

    /**
     * Getter sur isDegreeTheAngularUnit.
     * 
     * @return the isDegreeTheAngularUnit
     */
    public boolean isDegreeTheAngularUnit()
    {
        return _isDegreeTheAngularUnit;
    }

    /**
     * Setter sur isDegreeTheAngularUnit.
     * 
     * @param isDegreeTheAngularUnit
     *            the isDegreeTheAngularUnit to set
     */
    public void setDegreeTheAngularUnit(boolean isDegreeTheAngularUnit)
    {
        this._isDegreeTheAngularUnit = isDegreeTheAngularUnit;
    }
}
