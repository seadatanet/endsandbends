/* ============================================================================
 * Nom du fichier	: StringTools.java
 * Auteur			: Altran
 * Date				: Jun 15, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.common;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Description: TODO
 * 
 * @author htaniou
 */
public abstract class StringTools
{
    /**
     * return a String format of the stackTrace
     * 
     * @param e
     * @return stackTrace as String
     */
    public static String getStackTrace(Throwable e)
    {
        StringWriter traceWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(traceWriter, false);
        e.printStackTrace(printWriter);
        printWriter.close();
        return traceWriter.getBuffer().toString();
    }

    public static String getGuiPresentation(String... names)
    {
        StringBuilder builder = new StringBuilder("\""); //$NON-NLS-1$

        int length = names.length;
        int lengthMinusOne = length - 1;

        for (int i = 0; i < length; i++)
        {
            builder.append(names[i]);

            if (i < lengthMinusOne)
            {
                builder.append(">"); //$NON-NLS-1$
            }
        }

        return builder.append("\"").toString(); //$NON-NLS-1$
    }

}
