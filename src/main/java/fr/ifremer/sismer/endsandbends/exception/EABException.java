/* ============================================================================
 * Nom du fichier	: HistoricUploadException.java
 * Auteur			: Altran
 * Date				: Apr 13, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.exception;

import java.awt.Component;

import javax.swing.JOptionPane;

import fr.ifremer.sismer.endsandbends.common.Config;
import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.StringTools;

/**
 * Erreur survenue au niveau de l'application.<br>
 * Se r�f�rer au code pour le d�tail de l'erreur.
 * 
 * @author Altran
 */
public class EABException extends Exception
{
    /**
     * Serialisation.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Message de base de l'exception.
     */
    public static final String GENERIC_EXCEPTION_MESSAGE =
        Messages.getString(Messages.MESSAGES, "GENERIC_EXCEPTION_MESSAGE"); //$NON-NLS-1$

    /**
     * Code pr�cisant le type d'erreur.
     */
    private final ExceptionCode _code;

    /**
     * Getter sur _code.
     * 
     * @return the code
     */
    public ExceptionCode getCode()
    {
        return _code;
    }

    /**
     * Constructeur.
     * 
     * @param code
     *            code de l'exception permettant d'avoir plus de d�tails
     */
    public EABException(final ExceptionCode code)
    {
        super(generateExceptionMsg(code, null, "")); //$NON-NLS-1$
        this._code = code;
    }

    /**
     * Constructeur.
     * 
     * @param code
     *            code de l'exception permettant d'avoir plus de d�tails
     * @param cause
     *            cause de l'exception
     */
    public EABException(final ExceptionCode code, final Throwable cause)
    {
        super(generateExceptionMsg(code, cause, "")); //$NON-NLS-1$
        this._code = code;
    }

    /**
     * Constructeur.
     * 
     * @param code
     *            code de l'exception permettant d'avoir plus de d�tails
     * @param messages
     *            Message(s) de l'erreur
     */
    public EABException(final ExceptionCode code, final String... messages)
    {
        super(generateExceptionMsg(code, null, messages));
        this._code = code;
    }

    /**
     * Constructeur.
     * 
     * @param code
     *            code de l'exception permettant d'avoir plus de d�tails
     * @param message
     *            Message de l'erreur
     */
    public EABException(final ExceptionCode code, final String message)
    {
        super(generateExceptionMsg(code, null, message));
        this._code = code;
    }

    /**
     * Constructeur.
     * 
     * @param code
     *            code de l'exception permettant d'avoir plus de d�tails
     * @param cause
     *            cause de l'exception
     * @param messages
     *            Message(s) de l'erreur
     */
    public EABException(final ExceptionCode code, final Throwable cause,
            final String... messages)
    {
        super(generateExceptionMsg(code, cause, messages), cause);
        this._code = code;
    }

    /**
     * Constructeur.
     * 
     * @param code
     *            code de l'exception permettant d'avoir plus de d�tails
     * @param cause
     *            cause de l'exception
     * @param message
     *            Message de l'erreur
     */
    public EABException(final ExceptionCode code, final Throwable cause,
            final String message)
    {
        super(generateExceptionMsg(code, cause, message), cause);
        this._code = code;
    }

    /**
     * @param code
     * @param cause
     * @param messages
     * @return
     */
    private static String generateExceptionMsg(final ExceptionCode code,
            final Throwable cause, final String... messages)
    {
        StringBuilder concatMsg = new StringBuilder();

        // Prise en compte des messages optionnels.
        if (null != messages)
        {
            for (String st : messages)
            {
                concatMsg.append(st).append(Config.CR);
            }
        }
        
        concatMsg.append(code.toString());

        if (null != cause)
        {
            concatMsg.append(Config.CR).append(cause.getMessage());
        }

        return concatMsg.toString();
    }

    /**
     * @param code
     * @param cause
     * @param message
     * @return
     */
    private static String generateExceptionMsg(final ExceptionCode code,
            final Throwable cause, final String message)
    {
        StringBuilder concatMsg = new StringBuilder();

        // Prise en compte du messages optionnel.
        if (null != message)
        {
            concatMsg.append(message);
        }

        concatMsg.append(Config.CR);
        concatMsg.append(code.toString());

        if (null != cause)
        {
            concatMsg.append(" : "); //$NON-NLS-1$
            concatMsg.append(cause.getMessage());
        }

        return concatMsg.toString();
    }

    /**
     * @param component
     */
    public void displayFatalError(Component component)
    {
        this.printStackTrace();

        StringBuilder message = new StringBuilder(Globals.UNCAUGHT_EXCEPTION);

        message.append(StringTools.getStackTrace(this));

        JOptionPane.showMessageDialog(component, message.toString(), Messages
            .getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
            JOptionPane.ERROR_MESSAGE);
    }

    public void displayFatalError()
    {
        this.displayFatalError((Component) null);
    }

    /**
     * Affichage d'une exception dans une fen�tre libre
     * 
     * @param e
     * @param component
     */
    public static void displayFatalError(Throwable e, Component component)
    {
        e.printStackTrace();

        StringBuilder message = new StringBuilder(Globals.UNCAUGHT_EXCEPTION);

        message.append(StringTools.getStackTrace(e));

        JOptionPane.showMessageDialog(component, message.toString(), Messages
            .getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
            JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Affichage d'une exception dans une fen�tre libre
     * 
     * @param e
     */
    public static void displayFatalError(Throwable e)
    {
        displayFatalError(e, null);
    }

}
