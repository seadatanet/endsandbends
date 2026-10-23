/* ============================================================================
 * Nom du fichier	: DefaultProperty.java
 * Auteur			: Altran
 * Date				: 29 oct. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public enum DefaultFolder
{
    INPUT_FOLDER,
    OUTPUT_FOLDER,
    INPUT_PARAMETER_FOLDER,
    OUTPUT_PARAMETER_FOLDER;

    @Override
    public String toString()
    {
        return this.name();
    };
}
