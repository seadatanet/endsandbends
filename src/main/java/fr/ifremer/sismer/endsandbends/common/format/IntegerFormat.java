package fr.ifremer.sismer.endsandbends.common.format;

import java.io.OutputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;

import org.xml.sax.Attributes;

import com.ago.xml.SAXConsumer;
import com.ago.xml.XMLOutputWriter;

/**
 * Class IntegerFormat
 * 
 * @author Gerard BUNEL
 */
public class IntegerFormat extends ExternalizableFormat implements Cloneable
{

    private static final long serialVersionUID = 8178150076649342176L;
    protected String _formatString;

    public IntegerFormat()
    {
        super();
        _formatString = ""; //$NON-NLS-1$
    }

    /**
     * @param a_Format
     */
    public IntegerFormat(String a_Format)
    {
        this();
        _formatString = a_Format;
    }

    /**
     * A new XML Element as been found. Parse it.
     * 
     * @param a_URI
     *            XML Namespace (SAX 2)
     * @param a_LocalName
     *            Tag name
     * @param a_QName
     *            Qualified tag name
     * @param a_Attributes
     *            XML Element attributes
     * @return SAXConsumer
     */
    public SAXConsumer consumeElement(String a_URI, String a_LocalName,
            String a_QName, Attributes a_Attributes)
    {
        return null;
    }

    /**
     * The XML Element is a CDATA filled element
     * 
     * @param a_Characters
     *            The CDATA content
     */
    public void consumeCharacters(String a_Characters)
    {
    }

    /**
     * Generate XML defintion on a Stream
     * 
     * @param a_Stream
     *            the OutptStream to which to write the XML Definition
     */
    public void writeXMLOn(OutputStream a_Stream)
    {
        XMLOutputWriter writer = new XMLOutputWriter(a_Stream);
        writeXMLOn(writer);
    }

    /**
     * Read accessor (getter) for attribute _formatString
     * 
     * @return String
     * @see IntegerFormat#_formatString
     */
    public String getFormatString()
    {
        return _formatString;
    }

    /**
     * Write accessor (setter) for attribute _formatString
     * 
     * @param x
     *            new value for _formatString
     * @see IntegerFormat#_formatString
     */
    public void setFormatString(String x)
    {
        _formatString = x;
    }

    /**
     * @return Object
     */
    @Override
    public Object clone()
    {
        return new IntegerFormat(new String(_formatString));
    }

    /**
     * @param obj
     * @param toAppendTo
     * @param pos
     * @return StringBuffer
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos)
    {
        toAppendTo.append(String
            .format(java.util.Locale.US, _formatString, obj));
        return toAppendTo;
    }

    /**
     * @param source
     * @param pos
     * @return Integer
     */
    @Override
    public Integer parseObject(String source, ParsePosition pos)
    {
        pos.setIndex(source.length());
        return Integer.valueOf(source);
    }

    /**
     * @return String
     */
    @Override
    public String toString()
    {
        return _formatString;
    }

}