/* ============================================================================
 * Nom du fichier	: Operator.java
 * Auteur			: Altran
 * Date				: Jun 14, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.enums;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * Op�rateurs
 * 
 * @author htaniou
 */
public enum Operator
{
    EQUAL("EQUAL"), //$NON-NLS-1$
    DIFFERENT("DIFFERENT"), //$NON-NLS-1$
    SUPERIOR("SUPERIOR"), //$NON-NLS-1$
    INFERIOR("INFERIOR"); //$NON-NLS-1$

    /**
     * Type du fichier en entr�e.
     */
    private final String _operator;

    /**
     * Constructeur.
     * 
     * @param aOperator
     *            Type de fichier.
     */
    private Operator(final String aOperator)
    {
        _operator = aOperator;

    }

    /**
     * Getter sur _operator.
     * 
     * @return the operator
     */
    public String getOperator()
    {
        return _operator;
    }

    /**
     * Convertit le nom du type de fichier en enum�r�.
     */
    public static Operator translate(final String aTypeName)
    {
        if (Messages
            .getString(Messages.MESSAGES, "EQUAL").equalsIgnoreCase(aTypeName)) //$NON-NLS-1$
        {
            return EQUAL;
        }
        else if (Messages.getString(Messages.MESSAGES, "DIFFERENT") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return DIFFERENT;
        }
        else if (Messages.getString(Messages.MESSAGES, "SUPERIOR") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return SUPERIOR;
        }
        else if (Messages.getString(Messages.MESSAGES, "INFERIOR") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return INFERIOR;
        }
        return null;
    }

    public static String[] toArray()
    {
        String[] returnArray = {Messages.getString(Messages.MESSAGES, "EQUAL"), //$NON-NLS-1$
                Messages.getString(Messages.MESSAGES, "DIFFERENT"), //$NON-NLS-1$
                Messages.getString(Messages.MESSAGES, "SUPERIOR"), //$NON-NLS-1$
                Messages.getString(Messages.MESSAGES, "INFERIOR")}; //$NON-NLS-1$
        return returnArray;
    }
}
