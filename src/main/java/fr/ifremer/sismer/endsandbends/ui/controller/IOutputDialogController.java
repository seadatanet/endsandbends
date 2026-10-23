/* ============================================================================
 * Nom du fichier	: IOutputDialogController.java
 * Auteur			: Altran
 * Date				: 17 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.ui.controller;

import java.util.List;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;

/**
 * Interface repr�sentant un controlleur pour la fen�tre d'output
 * 
 * @author mbettich
 */
public interface IOutputDialogController
{
    /**
     * Initialize la fen�tre d'input si on charge un mod�le
     */
    public void initializeDialog();

    /**
     * Getter sur l'objet courant stockant les entr�es utilisateurs dans la
     * fen�tre d'output
     * 
     * @return the outputObject
     */
    public OutputParamsObject getOutputObject();

    /**
     * Setter sur l'objet courant stockant les entr�es utilisateurs dans la
     * fen�tre d'output
     * 
     * @param outputObject
     *            the outputObject to set
     */
    public void setOutputObject(OutputParamsObject outputObject);

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

    public void saveOutputFile() throws EABException;

}
