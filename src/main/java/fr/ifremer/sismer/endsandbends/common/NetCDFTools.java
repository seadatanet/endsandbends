/* ============================================================================
 * Nom du fichier	: NetCDFTools.java
 * Auteur			: Altran
 * Date				: 19 oct. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public abstract class NetCDFTools
{
    /**
     * R�cup�ration d'une variable non nulle dans le fichier NetCDF � partir de
     * son nom
     * 
     * @param variableName
     * @return
     * @throws EABException
     */
    public static Variable getVariable(NetcdfFile file, String variableName)
            throws EABException
    {
        Variable variableToReturn = file.findVariable(variableName);

        if (null == variableToReturn)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR,
                Globals.NETCDF_GEN_VARIABLE_NOT_FOUND
                    .format(new String[] {variableName}));
        }

        return variableToReturn;
    }
}
