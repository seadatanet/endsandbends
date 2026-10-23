/* ============================================================================
 * Nom du fichier	: CsvEntry.java
 * Auteur			: Altran
 * Date				: Jun 9, 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input.csv;

import java.text.ParseException;
import java.util.Date;

import fr.ifremer.sismer.endsandbends.common.Globals;
import fr.ifremer.sismer.endsandbends.enums.Operator;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;
import fr.ifremer.sismer.endsandbends.input.IFormatters;
import fr.ifremer.sismer.endsandbends.mediation.input.CsvInputParamsObject;

/**
 * Ligne d'un fichier CSV
 * 
 * @author htaniou
 */
public class CsvEntry
{
    /**
     * Date
     */
    private Date _date;

    /**
     * Latitude
     */
    private double _latitude;

    /**
     * Longitude
     */
    private double _longitude;

    /**
     * Validit�
     */
    private boolean _isValid;

    /**
     * Initialisation des attributs � partir de la ligne lue et des param�tres
     * 
     * @param line
     * @param parameters
     * @param formatters
     * @throws EABException
     */
    public void initialize(String line, CsvInputParamsObject parameters,
            IFormatters formatters, String separator) throws EABException
    {
        String[] elements = line.split(separator);
// String[] elements = line.split(Globals.SEMICOLON);

        _date = parseDate(elements, parameters, formatters);

        _latitude = parseLatitude(elements, parameters, formatters);

        _longitude = parseLongitude(elements, parameters, formatters);

        _isValid = checkValidity(elements, parameters);
    }

    /**
     * Validit� de la ligne
     * 
     * @param elements
     * @param parameters
     * @return
     * @throws EABException
     */
    private boolean checkValidity(String[] elements,
            CsvInputParamsObject parameters) throws EABException
    {
        boolean isValid = true;

        if (parameters.isCheckValidityEnabled())
        {
            String value =
                getElement(elements, parameters.getCheckValidityColumnNumber());

            String validityValue =
                String.valueOf(parameters.getValidityValue());

            isValid =
                compareValues(value, validityValue,
                    Operator.translate(parameters.getCheckValidityOperator()));
        }

        return isValid;
    }

    /**
     * Comparaison de 2 valeurs
     * 
     * @param value
     * @param validityValue
     * @param inferior
     * @return
     * @throws EABException
     */
    private boolean compareValues(String valueString,
            String validityValueString, Operator operator) throws EABException
    {
        boolean booleanToReturn = false;

        double value, validityValue;

        try
        {
            value = Double.parseDouble(valueString);
            validityValue = Double.parseDouble(validityValueString);
        }
        catch (NumberFormatException e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e);
        }

        switch (operator)
        {
            case EQUAL:
                booleanToReturn = (value == validityValue);
            break;

            case DIFFERENT:
                booleanToReturn = (value != validityValue);
            break;

            case INFERIOR:
                booleanToReturn = (value < validityValue);
            break;

            case SUPERIOR:
                booleanToReturn = (value > validityValue);
            break;

            default:
                throw new EABException(ExceptionCode.UNKOWN_ERROR,
                    new UnsupportedOperationException());
        }

        return booleanToReturn;
    }

    /**
     * R�cup�ration de la date
     * 
     * @param elements
     * @param parameters
     * @param formatters
     * @return
     * @throws EABException
     */
    private Date parseDate(String[] elements, CsvInputParamsObject parameters,
            IFormatters formatters) throws EABException
    {
        Date dateToReturn = null;

        if (!parameters.isDateTimeEnabled())
        {
            String date;

            if (parameters.isJoinDateTimeParamsEnabled())
            {
                date =
                    getElement(elements, parameters.getDateTimeColumnNumber());
            }
            else
            {
                date =
                    new StringBuilder(getElement(elements,
                        parameters.getDateFormatColumnNumber())).append(
                        getElement(elements,
                            parameters.getTimeFormatColumnNumber())).toString();
            }

            try
            {
                dateToReturn = formatters.getDateTimeFormat().parse(date);
            }
            catch (ParseException e)
            {
                throw new EABException(ExceptionCode.PARSING_ERROR, e);
            }
        }

        return dateToReturn;
    }

    /**
     * R�cup�ration de la latitude
     * 
     * @param elements
     * @param parameters
     * @param formatters
     * @return
     * @throws EABException
     */
    private double parseLatitude(String[] elements,
            CsvInputParamsObject parameters, IFormatters formatters)
            throws EABException
    {
        String latitudeStringValue =
            getElement(elements, parameters.getLatitudeFormatColumnNumber());

        double latitudeDoubleValue;

        try
        {
            latitudeDoubleValue =
                ((Number) formatters.getLatitudeFormat().parseObject(
                    latitudeStringValue)).doubleValue();
        }
        catch (ParseException e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e);
        }

        return latitudeDoubleValue;
    }

    /**
     * R�cup�ration de la longitude
     * 
     * @param elements
     * @param parameters
     * @param formatters
     * @return
     * @throws EABException
     */
    private double parseLongitude(String[] elements,
            CsvInputParamsObject parameters, IFormatters formatters)
            throws EABException
    {
        String longitudeStringValue =
            getElement(elements, parameters.getLongitudeFormatColumnNumber());

        double longitudeDoubleValue;

        try
        {
            longitudeDoubleValue =
                ((Number) formatters.getLongitudeFormat().parseObject(
                    longitudeStringValue)).doubleValue();
        }
        catch (ParseException e)
        {
            throw new EABException(ExceptionCode.PARSING_ERROR, e);
        }

        return longitudeDoubleValue;
    }

    /**
     * R�cup�ration d'un �l�ment de la ligne � partir des param�tres
     * 
     * @param elements
     * @param columnNumber
     * @return
     * @throws EABException
     */
    private String getElement(String[] elements, int columnNumber)
            throws EABException
    {
        int javaColumnNumber = columnNumber - 1;

        String stringToReturn;

        if (0 <= javaColumnNumber && javaColumnNumber < elements.length)
        {
            stringToReturn = elements[javaColumnNumber];
        }
        else
        {
            throw new EABException(ExceptionCode.WRONG_NUMBER_OF_ELEMENTS,
                Globals.INVALID_COLUMN_NUMBER.format(new String[] {String
                    .valueOf(columnNumber)}));
        }

        return stringToReturn;
    }

// public static void main(String[] args) throws ParseException, EABException
// {
// String line =
// "386003811;+0000;860922;121700.0;+10.51403;-08.038659;3;9999999;99999;59;9;999999999999999999999999;+00000;999999999999999999999999999999";
//
// CsvEntry entry = new CsvEntry();
//
// CsvInputParamsObject parameters = new CsvInputParamsObject();
// parameters.setCsvIsDateTimeConcatenated(false);
// parameters.setCsvDateColumnNumber(3);
// parameters.setCsvTimeColumnNumber(4);
// parameters.setCsvLatitudeColumnNumber(5);
// parameters.setCsvLongitudeColumnNumber(6);
//
// parameters.setCsvIsValidityColumnActivated(true);
// parameters.setCsvValidityColumnNumber(9);
// parameters.setCsvValidityColumnOperator(Operator.INFERIOR);
// parameters.setCsvValidityColumnValue("99999");
//
// IFormatters formatters =
// new Formatters("yyMMddHHmmss.S", "+DD.ddddd", "+DDD.ddddd");
// entry.initialize(line, parameters, formatters);
//
// System.out.println(entry.getDate());
// System.out.println(entry.getLatitude());
// System.out.println(entry.getLongitude());
// System.out.println(entry.isValid() ? "valid" : "invalid");
// }

    /*********************
     * GETTERS & SETTERS *
     *********************/

    /**
     * Getter sur _date.
     * 
     * @return the date
     */
    public Date getDate()
    {
        return _date;
    }

    /**
     * Getter sur _latitude.
     * 
     * @return the latitude
     */
    public double getLatitude()
    {
        return _latitude;
    }

    /**
     * Getter sur _longitude.
     * 
     * @return the longitude
     */
    public double getLongitude()
    {
        return _longitude;
    }

    /**
     * Getter sur _isValid.
     * 
     * @return the isValid
     */
    public boolean isValid()
    {
        return _isValid;
    }
}
