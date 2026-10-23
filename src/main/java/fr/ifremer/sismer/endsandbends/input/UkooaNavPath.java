/* ============================================================================
 * Nom du fichier	: UkooaNavPath.java
 * Auteur			: Altran
 * Date				: 13 mai 2011
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import java.util.List;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.mediation.input.DateSegment;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class UkooaNavPath extends ANavPath implements INavPath
{
    private boolean _isDegreeTheAngularUnit;

    /**
     * Constructeur
     */
    public UkooaNavPath()
    {
        super(null);
    }

    /**
     * Constructeur
     */
    public UkooaNavPath(boolean isDegreeAngularUnit)
    {
        super(null);
        _isDegreeTheAngularUnit = isDegreeAngularUnit;
    }

    /**
     * @param dateSegmentList
     */
    public UkooaNavPath(List<DateSegment> dateSegmentList)
    {
        super(dateSegmentList);
    }

    /**
     * @param dateSegmentList
     */
    public UkooaNavPath(List<DateSegment> dateSegmentList,
            boolean isDegreeAngularUnit)
    {
        super(dateSegmentList);
        _isDegreeTheAngularUnit = isDegreeAngularUnit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loadNavFile(String filePath) throws EABException
    {

        UkooaFileReader fileReader = new UkooaFileReader(filePath);
        fileReader.openFileAndInitNavPath(this, _isDegreeTheAngularUnit);
        return true;
    }
}
