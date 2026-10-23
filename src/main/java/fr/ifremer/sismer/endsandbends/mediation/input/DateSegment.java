/* ============================================================================
 * Nom du fichier	: DateSegment.java
 * Auteur			: Altran
 * Date				: 30 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.mediation.input;

import java.util.Date;
import java.util.List;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class DateSegment
{

    private Date startDate, endDate;

    public DateSegment()
    {
    }

    public DateSegment(Date startDate, Date endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public static int indexOfSegmentContainingDate(
            List<DateSegment> dateSegmentList, Date date)
    {
        int size = dateSegmentList.size();
        DateSegment dateSegment;

        for (int index = 0; index < size; index++)
        {
            dateSegment = dateSegmentList.get(index);

            if (date.getTime() >= dateSegment.getStartDate().getTime()
                    && date.getTime() <= dateSegment.getEndDate().getTime())
            {
                return index;
            }
        }

        return -1;
    }

    /**
     * @param dateSegmentList
     * @param date
     * @return
     */
    @Deprecated
    public static int indexedBinarySearch(List<DateSegment> dateSegmentList,
            Date date)
    {
        int i = 0;

        int size = dateSegmentList.size();

        DateSegment currentSegment;

        for (int j = size - 1; i <= j;)
        {
            int k = i + j >>> 1;

            currentSegment = dateSegmentList.get(k);

            int l = currentSegment.compareTo(date);

            if (l < 0)
            {
                i = k + 1;
            }
            else if (l > 0)
            {
                j = k - 1;
            }
            else
            {
                return k;
            }
        }

        return -(i + 1);
    }

    /**
     * @param date
     * @return <li>-1 si le segment est avant la date <li>0 s'il contient la
     *         date <li>1 s'il est apr�s la date
     */
    private int compareTo(Date date)
    {
        int integerToReturn;

        if (date.before(getStartDate()))
        {
            integerToReturn = 1;
        }
        else if (date.after(getEndDate()))
        {
            integerToReturn = -1;
        }
        else
        {
            integerToReturn = 0;
        }

        return integerToReturn;
    }

}
