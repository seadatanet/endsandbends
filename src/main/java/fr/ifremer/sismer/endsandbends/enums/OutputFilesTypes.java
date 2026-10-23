/* ============================================================================
 * Nom du fichier	: OutputFilesTypes.java
 * Auteur		: Altran
 * Date			: 3 juin 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.enums;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * Types pour le fichier de sortie.
 * 
 * @author Altran
 */
public enum OutputFilesTypes
{
    ASCII_IFREMER_FORMAT("ASCII_IFREMER_FORMAT"), //$NON-NLS-1$
    COLUMNS_ASCII_FORMAT("COLUMNS_ASCII_FORMAT"), //$NON-NLS-1$
    WKT_FORMAT("WKT_FORMAT"), //$NON-NLS-1$
    GML_FORMAT("GML_FORMAT"), //$NON-NLS-1$
    SHAPE_FORMAT("SHAPE_FORMAT"); //$NON-NLS-1$

    /**
     * Type du fichier en sortie.
     */
    private final String _outputFileType;

    /**
     * Constructeur.
     * 
     * @param aFileType
     *            Type de fichier.
     */
    private OutputFilesTypes(final String aFileType)
    {
        _outputFileType = aFileType;

    }

    /**
     * Getter sur _outputFileType.
     * 
     * @return the outputFileType
     */
    public String getOutputFileType()
    {
        return _outputFileType;
    }

    /**
     * Convertit le nom du type de fichier en enum�r�.
     */
    public static OutputFilesTypes translate(final String aTypeName)
    {
        if (Messages
            .getString(Messages.MESSAGES, "IFREMER_ASCII_FORMAT").equalsIgnoreCase(aTypeName)) //$NON-NLS-1$
        {
            return ASCII_IFREMER_FORMAT;
        }
        else if (Messages.getString(Messages.MESSAGES, "COLUMNS_ASCII_FORMAT") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return COLUMNS_ASCII_FORMAT;
        }
        else if (Messages.getString(Messages.MESSAGES, "WKT_FORMAT") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return WKT_FORMAT;
        }
        else if (Messages.getString(Messages.MESSAGES, "GML_FORMAT") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return GML_FORMAT;
        }
        else if (Messages.getString(Messages.MESSAGES, "SHAPE_FORMAT") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return SHAPE_FORMAT;
        }
        return null;
    }

    public static String[] toArray()
    {
        String[] returnArray =
            {
                    Messages.getString(Messages.MESSAGES,
                        "IFREMER_ASCII_FORMAT"), //$NON-NLS-1$
                    Messages.getString(Messages.MESSAGES,
                        "COLUMNS_ASCII_FORMAT"), //$NON-NLS-1$
                    Messages.getString(Messages.MESSAGES, "WKT_FORMAT"), //$NON-NLS-1$
                    Messages.getString(Messages.MESSAGES, "GML_FORMAT"), //$NON-NLS-1$
                    Messages.getString(Messages.MESSAGES, "SHAPE_FORMAT")}; //$NON-NLS-1$
        return returnArray;
    }
}
