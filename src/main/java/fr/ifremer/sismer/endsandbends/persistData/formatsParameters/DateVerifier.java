package fr.ifremer.sismer.endsandbends.persistData.formatsParameters;

import java.awt.Color;
import java.util.Date;

import javax.swing.InputVerifier;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;

/**
 * @author Mark Pendergast Copyright Mark Pendergast
 */
public class DateVerifier extends InputVerifier
{

    private static final Color INVALID_COLOR = Color.red;
    private static final Color VALID_COLOR = Color.black;
    private int DATE = 0;
    private int MONTH = 0;
    private int YEAR = 0;
    UTCDateFormat sdf = null; // formatted used to check date formats

    /**
     * Default constructor
     */
    public DateVerifier()
    {
        sdf = new UTCDateFormat();
    }

    /**
     * Constructor that accepts a mask
     * 
     * @param mask
     *            SimpleDateFormat mask
     */
    public DateVerifier(String mask)
    {
        sdf = new UTCDateFormat(mask);
    }

    /**
     * Check the contents to see if its a valid date
     * 
     * @param jc
     *            JComponent (the date field)
     * @return true if valid date, false if not
     */
    @Override
    public boolean verify(javax.swing.JComponent jc)
    {
        JFormattedTextField fdf = (JFormattedTextField) jc;
        try
        {
            if (fdf.getText() != null && !fdf.getText().isEmpty())
            {
                Date d = sdf.parse(fdf.getText());
                dateBeforeFormatting(fdf.getText());
                if (!isDateCorrect(sdf))
                {
                    throw new Exception();
                }
                jc.setForeground(VALID_COLOR);
            }

            return true;
        }
        catch (Exception e)
        {
            jc.setForeground(INVALID_COLOR);
            JOptionPane.showMessageDialog(fdf.getTopLevelAncestor(), Messages
                .getString(Messages.MESSAGES, "VERIF_DATE"), "Input Error", //$NON-NLS-1$
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    private void dateBeforeFormatting(String unformattedDate)
    {
        String[] subStrings = unformattedDate.split("/");
        if (Messages.getString(Messages.MESSAGES, "SIMPLEDATEFORMATMASK")
            .equalsIgnoreCase("dd/MM/yyyy"))
        {
            DATE = Integer.valueOf(subStrings[0]);
            MONTH = Integer.valueOf(subStrings[1]);
            YEAR = Integer.valueOf(subStrings[2]);
        }
        else
        {
            MONTH = Integer.valueOf(subStrings[0]);
            DATE = Integer.valueOf(subStrings[1]);
            YEAR = Integer.valueOf(subStrings[2]);
        }
    }

    private boolean isDateCorrect(UTCDateFormat sdf)
    {
        if (DATE > 31 || MONTH > 12)
        {
            return false;
        }
        else if (DATE == 31
                && (MONTH == 4 || MONTH == 6 || MONTH == 9 || MONTH == 11 || MONTH == 2))
        {
            return false;
        }
        else if (DATE == 30
                && (MONTH == 1 || MONTH == 3 || MONTH == 5 || MONTH == 7
                        || MONTH == 8 || MONTH == 10 || MONTH == 12 || MONTH == 2))
        {
            return false;
        }
        else if (DATE == 29 && MONTH == 2
                && (YEAR % 4 != 0 || (YEAR % 400 != 0 && YEAR % 100 == 0)))
        {
            return false;
        }
        else if (DATE == 28 && MONTH == 2
                && (YEAR % 400 == 0 || (YEAR % 4 == 0 && YEAR % 100 != 0)))
        {
            return false;
        }
        else if (MONTH == 0 || DATE == 0)
        {
            return false;
        }
        return true;
    }
}
