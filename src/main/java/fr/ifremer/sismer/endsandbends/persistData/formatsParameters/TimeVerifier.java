/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.ifremer.sismer.endsandbends.persistData.formatsParameters;

import java.awt.Color;
import java.util.Date;

import javax.swing.InputVerifier;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.UTCDateFormat;

/**
 * @author mbettich
 */
public class TimeVerifier extends InputVerifier
{

    private static final Color INVALID_COLOR = Color.red;
    private static final Color VALID_COLOR = Color.black;
    private int HOURS = 0;
    private int MINUTES = 0;
    private int SECONDS = 0;
    UTCDateFormat sdf = null; // formatted used to check date formats

    /**
     * Default constructor
     */
    public TimeVerifier()
    {
        sdf = new UTCDateFormat();
    }

    /**
     * Constructor that accepts a mask
     * 
     * @param mask
     *            UTCDateFormat mask
     */
    public TimeVerifier(String mask)
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
                timeBeforeFormatting(fdf.getText());
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
                .getString(Messages.MESSAGES, "VERIF_TIME"), "Input Error", //$NON-NLS-1$
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    private void timeBeforeFormatting(String unformattedDate)
    {
        String[] subStrings = unformattedDate.split(":");
        HOURS = Integer.valueOf(subStrings[0]);
        MINUTES = Integer.valueOf(subStrings[1]);
        SECONDS = Integer.valueOf(subStrings[2]);
    }

    private boolean isDateCorrect(UTCDateFormat sdf)
    {
        if (HOURS > 23 || MINUTES > 59 || SECONDS > 59)
        {
            return false;
        }
        return true;
    }
}
