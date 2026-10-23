/* ============================================================================
 * Nom du fichier	: ReferenceValue.java
 * Auteur			: Altran
 * Date				: 23 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.model;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public class ReferenceValue
{
    private final String _model;

    private final String _pointNumber;

    private final String _error;

    /**
     * Constructeur
     * 
     * @param pointNumber
     * @param error
     */
    public ReferenceValue(String model, String pointNumber, String error)
    {
        _model = model;
        _pointNumber = pointNumber;
        _error = error;
    }

    /**
     * Getter sur _pointNumber.
     * 
     * @return the pointNumber
     */
    public String getPointNumber()
    {
        return _pointNumber;
    }

    /**
     * Getter sur _error.
     * 
     * @return the error
     */
    public String getError()
    {
        return _error;
    }

    /**
     * Getter sur _model.
     * 
     * @return the model
     */
    public String getModel()
    {
        return _model;
    }
}
