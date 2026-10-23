/* ============================================================================
 * Nom du fichier	: ExceptionCode.java
 * Auteur			: Altran
 * Date				: 9 juin 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.exception;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * Codes d'erreur pouvant �tre embarqu�s dans une HistoricUploadException.
 * 
 * @author Altran
 */
@SuppressWarnings("nls")
public enum ExceptionCode
{
    ARGUMENT_NULL_OR_EMPTY(Messages.getString(Messages.MESSAGES,
        "ARGUMENT_NULL_OR_EMPTY")),
    CHECKSUM_EXCEPTION(Messages.getString(Messages.MESSAGES,
        "CHECKSUM_EXCEPTION")),
    WRONG_NUMBER_OF_ELEMENTS(Messages.getString(Messages.MESSAGES,
        "WRONG_NUMBER_OF_ELEMENTS")),
    PARSING_ERROR(Messages.getString(Messages.MESSAGES, "PARSING_ERROR")),
    IO_ERROR(Messages.getString(Messages.MESSAGES, "IO_ERROR")),
    UNKOWN_ERROR(Messages.getString(Messages.MESSAGES, "UNKOWN_ERROR")),
    CHRONOLOGICAL_ERROR(Messages.getString(Messages.MESSAGES,
        "CHRONOLOGICAL_ERROR")),
    PROPERTIES_CONTENT_ERROR(Messages.getString(Messages.MESSAGES,
        "PROPERTIES_CONTENT_ERROR")),

    CDIVERSION_ERROR(Messages.getString(Messages.MESSAGES,
            "CDIVERSION_ERROR"));

    /**
     * Nom de l'exception.
     */
    private final String _name;

    /**
     * Constructeur.
     * 
     * @param name
     */
    private ExceptionCode(String name)
    {
        _name = name;
    }

    /**
     * Getter sur _name.
     * 
     * @return the name
     */
    public String getName()
    {
        return _name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return _name;
    }
}
