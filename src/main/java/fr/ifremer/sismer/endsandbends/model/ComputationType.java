/* ============================================================================
 * Nom du fichier	: ComputationType.java
 * Auteur			: Altran
 * Date				: 23 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.model;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public enum ComputationType
{
    DEFAULT(Messages.getString(Messages.MESSAGES, "COMPUTATION_TYPE_DEFAULT")), //$NON-NLS-1$
    FARTHEST(Messages.getString(Messages.MESSAGES, "COMPUTATION_TYPE_FARTHEST")), //$NON-NLS-1$
    ERROR(Messages.getString(Messages.MESSAGES, "COMPUTATION_TYPE_ERROR")); //$NON-NLS-1$

    /**
     * Libell� du type
     */
    private final String _label;

    /**
     * Constructeur
     * 
     * @param label
     */
    private ComputationType(String label)
    {
        _label = label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return _label;
    }
}
