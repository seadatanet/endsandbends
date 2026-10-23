/* ============================================================================
 * Nom du fichier	: InputFilesTypes.java
 * Auteur		: Altran
 * Date			: 3 juin 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.enums;

import fr.ifremer.sismer.endsandbends.common.Messages;

/**
 * Types pour le fichier d'entr�e.
 * 
 * @author Altran
 */
public enum InputFilesTypes
{
    NETCDF_NVI_FORMAT("NETCDF_NVI_FORMAT"), //$NON-NLS-1$
    GENERAL_NETCDF_FORMAT("GENERAL_NETCDF_FORMAT"), //$NON-NLS-1$
    CSV_FORMAT("CSV_FORMAT"), //$NON-NLS-1$
    NMEA_183_FORMAT("NMEA_183_FORMAT"), //$NON-NLS-1$
    UKOOA_P1_90("UKOOA_P1_90"); //$NON-NLS-1$

    /**
     * Type du fichier en entr�e.
     */
    private final String _inputFileType;

    /**
     * Constructeur.
     * 
     * @param aFileType
     *            Type de fichier.
     */
    private InputFilesTypes(final String aFileType)
    {
        _inputFileType = aFileType;

    }

    /**
     * Getter sur _inputFileType.
     * 
     * @return the inputFileType
     */
    public String getInputFileType()
    {
        return _inputFileType;
    }

    /**
     * Convertit le nom du type de fichier en enum�r�.
     */
    public static InputFilesTypes translate(final String aTypeName)
    {
        if (Messages
            .getString(Messages.MESSAGES, "NETCDF_NVI_FORMAT").equalsIgnoreCase(aTypeName)) //$NON-NLS-1$
        {
            return NETCDF_NVI_FORMAT;
        }
        else if (Messages.getString(Messages.MESSAGES, "GENERAL_NETCDF_FORMAT") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return GENERAL_NETCDF_FORMAT;
        }
        else if (Messages.getString(Messages.MESSAGES, "CSV_FORMAT") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return CSV_FORMAT;
        }
        else if (Messages.getString(Messages.MESSAGES, "NMEA_183_FORMAT") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return NMEA_183_FORMAT;
        }
        else if (Messages.getString(Messages.MESSAGES, "UKOOA_P1_90") //$NON-NLS-1$
            .equalsIgnoreCase(aTypeName))
        {
            return UKOOA_P1_90;
        }
        return null;
    }

    public static String[] toArray()
    {
        String[] returnArray =
            {Messages.getString(Messages.MESSAGES, "NETCDF_NVI_FORMAT"),
                    Messages.getString(Messages.MESSAGES, "UKOOA_P1_90"),//$NON-NLS-1$
// Messages.getString(Messages.MESSAGES,
// "GENERAL_NETCDF_FORMAT"),
                    Messages.getString(Messages.MESSAGES, "CSV_FORMAT") //$NON-NLS-1$
            /*, Messages.getString(Messages.MESSAGES, "NMEA_183_FORMAT")*/};
        return returnArray;
    }
}
