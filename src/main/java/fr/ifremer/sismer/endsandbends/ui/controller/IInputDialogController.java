/* ============================================================================
 * Nom du fichier	: IInputDialogController.java
 * Auteur			: Altran
 * Date				: 10 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import java.text.ParseException;
import java.util.List;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.mediation.input.InputParamsObject;

/**
 * Interface repr�sentant un controlleur pour la fen�tre d'input
 * 
 * @author mbettich
 */
public interface IInputDialogController
{
    /**
     * Initialize la fen�tre d'input si on charge un mod�le
     */
    public void initializeDialog();

    /**
     * Getter sur l'objet courant stockant les entr�es utilisateurs dans la
     * fen�tre d'input
     * 
     * @return the inputObject
     */
    public InputParamsObject getInputObject();

    /**
     * Setter sur l'objet courant stockant les entr�es utilisateurs dans la
     * fen�tre d'input
     * 
     * @param inputObject
     *            the inputObject to set
     */
    public void setInputObject(InputParamsObject inputObject);

    public boolean validateForm();

    public void clearErrorsMessages();

    /**
     * Getter sur _errorsMessages.
     * 
     * @return the errorsMessages
     */
    public List<String> getErrorsMessages();

    /**
     * Add an errorMessage.
     */
    public void addErrorMessage(final String msg);

    /**
     * Add an errorMessage for a missing mandatory field.
     */
    public void addRequiredFieldMissingError(final String aField);

    /**
     * Remove an errorMessage.
     */
    public void removeErrorMessage(final String msg);

    public void saveInputFile() throws EABException;

    public void validateDateInput(int selectedRow, int selectedColumn)
            throws ParseException;

}
