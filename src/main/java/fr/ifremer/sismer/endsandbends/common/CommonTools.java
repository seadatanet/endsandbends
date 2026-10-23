/* ============================================================================
 * Nom du fichier	: CommonTools.java
 * Auteur			: Altran
 * Date				: 21 oct. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public abstract class CommonTools
{
    public static boolean equals(Object object1, Object object2)
    {
        if ((null == object1) != (null == object2))
        {
            return false;
        }

        if (!(null == object1) && !object1.equals(object2))
        {
            return false;
        }

        return true;
    }
}
