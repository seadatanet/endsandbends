/* ============================================================================
 * Nom du fichier	: AAsciiWriter.java
 * Auteur			: Altran
 * Date				: Jun 2, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.output;

import java.io.PrintWriter;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import fr.ifremer.sismer.endsandbends.input.NavPathPoints;
import fr.ifremer.sismer.endsandbends.ui.NavigationData;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public abstract class AEndsAndBendsWriter implements IEndsAndBendsWriter
{
    /**
     * logger
     */
    protected static final Logger myLogger = Logger
        .getLogger(AEndsAndBendsWriter.class);

    protected PrintWriter _printWriter;

    protected final JFrame _mainFrame;

    protected final NavigationData _data;

    /**
     * @param out
     */
    protected AEndsAndBendsWriter(JFrame main, NavigationData data)
    {
        _mainFrame = main;
        _data = data;
    }

    /**
     * Method formatString.
     * 
     * @param nWidth
     * @param value
     * @return String
     */
    protected String formatString(int nWidth, String value)
    {
        char[] chars = new char[nWidth];
        for (int pos = 0; pos < nWidth; pos++)
        {
            if (pos < value.length())
                chars[pos] = value.charAt(pos);
            else
                chars[pos] = ' ';
        }
        return new String(chars);
    }

    protected String formatStringLeft(int nWidth, String value)
    {
        char[] chars = new char[nWidth];
        int off = nWidth - value.length();
        for (int pos = nWidth - 1; pos >= 0; pos--)
        {
            if ((pos - off < value.length()) && (pos - off >= 0))
                chars[pos] = value.charAt(pos - off);
            else
                chars[pos] = ' ';
        }
        return new String(chars);
    }

    /**
     * Method formatInteger, formatage d'un entier
     * 
     * @param nWidth
     * @param leftPad
     * @param value
     * @return String
     */
    protected String formatInteger(int nWidth, char leftPad, int value)
    {
        char[] chars = new char[nWidth];
        boolean minus = false;
        if (value < 0)
        {
            value = -value;
            minus = true;
        }
        for (int pos = nWidth - 1; pos >= 0; pos--)
        {
            if ((value != 0) || (pos == nWidth - 1))
                chars[pos] = Character.forDigit(value % 10, 10);
            else if ((value == 0) && minus)
            {
                chars[pos] = '-';
                minus = false;
            }
            else
                chars[pos] = leftPad;
            value = value / 10;
        }
        return new String(chars);
    }

    /**
     * Method formatReal, formatage d'un r�el
     * 
     * @param nWidth
     * @param nDecimal
     * @param leftPad
     * @param value
     * @return String
     */
    protected String formatReal(int nWidth, int nDecimal, char leftPad,
            double value)
    {
        // Partie d�cimale :
        double decmul = 1.0;
        for (int i = 0; i < nDecimal; i++)
            decmul *= 10.0;
        int nDecValue = (int) StrictMath.rint(value * decmul);
        boolean minus = false;
        if (nDecValue < 0)
        {
            nDecValue = -nDecValue;
            minus = true;
        }
        String strDecValue = formatInteger(nDecimal, '0', nDecValue);
        strDecValue = strDecValue.substring(strDecValue.length() - nDecimal);
        strDecValue = "." + strDecValue; //$NON-NLS-1$

        // Partie enti�re :
        if (nWidth < nDecimal + 1)
            nWidth = nDecimal + 2;
        String strMainValue;
        int intValue = (int) (StrictMath.rint(value * decmul) / decmul);
        if ((intValue == 0) && minus)
            strMainValue = formatStringLeft(nWidth - nDecimal - 1, "-0"); //$NON-NLS-1$
        else
            strMainValue =
                formatInteger(nWidth - nDecimal - 1, leftPad, intValue);

        // Renvoyer les deux :
        return strMainValue + strDecValue;
    }

    public static int CEIL = 0;
    public static int ROUND = 1;
    public static int FLOOR = 2;

    public void printPosition(int degChars, int minChars, int fracChars,
            double lat, int round)
    {
        if (lat < 0)
        {
            _printWriter.print('-');
            lat = Math.abs(lat);
            if (round == CEIL)
                round = FLOOR;
            else if (round == FLOOR)
                round = CEIL;
        }
        else
            _printWriter.print('+');
        int deg = (int) lat;
        double fmin = (lat - deg) * 60;
        int min = 0;
        if (fracChars > 0)
        {
            long mult = 1;
            for (int i = 0; i < fracChars; i++)
                mult *= 10;
            if (round == CEIL)
                fmin = Math.ceil(fmin * mult);
            else if (round == ROUND)
                fmin = Math.round(fmin * mult);
            else if (round == FLOOR)
                fmin = Math.floor(fmin * mult);
            min = (int) (fmin / mult);
            int frac = (int) (fmin - min * mult);
            if (min >= 60)
            {
                min -= 60;
                deg += 1;
                if (deg >= 360)
                    deg -= 360;
            }
            printInteger(degChars, '0', deg);
            printInteger(minChars, '0', min);
            _printWriter.print('.');
            printInteger(fracChars, '0', frac);
        }
        else
        {
            if (round == CEIL)
                min = (int) Math.ceil(fmin);
            else if (round == ROUND)
                min = (int) Math.round(fmin);
            else if (round == FLOOR)
                min = (int) Math.floor(fmin);
            if (min >= 60)
            {
                min -= 60;
                deg += 1;
                if (deg >= 360)
                    deg -= 360;
            }
            printInteger(degChars, '0', deg);
            printInteger(minChars, '0', min);
        }
    }

    public void printString(int nWidth, String value)
    {
        _printWriter.print(formatString(nWidth, value));
    }

    public void printStringLeft(int nWidth, String value)
    {
        _printWriter.print(formatStringLeft(nWidth, value));
    }

    public void printInteger(int nWidth, char leftPad, int value)
    {
        _printWriter.print(formatInteger(nWidth, leftPad, value));
    }

    public void printReal(int nWidth, int nDecimal, char leftPad, double value)
    {
        _printWriter.print(formatReal(nWidth, nDecimal, leftPad, value));
    }

    protected void logSegment(int index, int count, NavPathPoints path)
    {
        if (index == 0)
        {
            myLogger.debug("start segment index = " + index + " start type: "
                    + path.getStartType().toString());
        }
        if (index == count - 1)
        {
            myLogger.debug("end segment index = " + index + " end type: "
                    + path.getEndType().toString());
        }
    }
}
