/* ============================================================================
 * Nom du fichier	: UkooaFileReader.java
 * Auteur			: Altran
 * Date				: 13 mai 2011
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.exception.ExceptionCode;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class UkooaFileReader
{
    private final File ukooaFile;
    private final List<UkooaNavpoint> ukooaPoints;
//    private static final String H0100 = "H0100";//$NON-NLS-1$
//    private static final String H0102 = "H0102";//$NON-NLS-1$
//    private static final String H0103 = "H0103";//$NON-NLS-1$
//    private static final String H0104 = "H0104";//$NON-NLS-1$
//    private static final String H0200 = "H0200";//$NON-NLS-1$
//    private static final String H0201 = "H0201";//$NON-NLS-1$
//    private static final String H0202 = "H0202";//$NON-NLS-1$
//    private static final String H0203 = "H0203";//$NON-NLS-1$
//    private static final String H0300 = "H0300";//$NON-NLS-1$
//    private static final String H0400 = "H0400";//$NON-NLS-1$
//    private static final String H0500 = "H0500";//$NON-NLS-1$
//    private static final String H0600 = "H0600";//$NON-NLS-1$
//    private static final String H0700 = "H0700";//$NON-NLS-1$
//    private static final String H0800 = "H0800";//$NON-NLS-1$
//    private static final String H0900 = "H0900";//$NON-NLS-1$
//    private static final String H1000 = "H1000";//$NON-NLS-1$
//    private static final String H1400 = "H1400";//$NON-NLS-1$
//    private static final String H1401 = "H1401";//$NON-NLS-1$
//    private static final String H1500 = "H1500";//$NON-NLS-1$
//    private static final String H1501 = "H1501";//$NON-NLS-1$
//    private static final String H1600 = "H1600";//$NON-NLS-1$
//    private static final String H1700 = "H1700";//$NON-NLS-1$
//    private static final String H1800 = "H1800";//$NON-NLS-1$
//    private static final String H1900 = "H1900";//$NON-NLS-1$
//    private static final String H2000 = "H2000";//$NON-NLS-1$
//    private static final String H2001 = "H2001";//$NON-NLS-1$
//    private static final String H2100 = "H2100";//$NON-NLS-1$
//    private static final String H2200 = "H2200";//$NON-NLS-1$
//    private static final String H2301 = "H2301";//$NON-NLS-1$
//    private static final String H2302 = "H2302";//$NON-NLS-1$
//    private static final String H2600 = "H2600";//$NON-NLS-1$
//    private static final String H8000 = "H8000";//$NON-NLS-1$
//    private static final String H8001 = "H8001";//$NON-NLS-1$

    private static final String H2002 = "H2002";//$NON-NLS-1$
    public static int COL_DEBUT_LATITUDE = 25;
    public static int COL_FIN_LATITUDE = 34;
    public static int COL_DEBUT_LONGITUDE = 35;
    public static int COL_FIN_LONGITUDE = 45;
    public static int COL_DEBUT_SIGNE_LATITUDE = 34;
    public static int COL_FIN_SIGNE_LATITUDE = 35;
    public static int COL_DEBUT_SIGNE_LONGITUDE = 45;
    public static int COL_FIN_SIGNE_LONGITUDE = 46;

    // relatif a l'interpretation des heures des enregistrements
// public static int JGREG = 15 + 31 * (10 + 12 * 1582);
// public static int COL_DEBUT_JOUR_JULIEN = 70;
// public static int COL_FIN_JOUR_JULIEN = 73;
// public static int COL_DEBUT_HEURES = 73;
// public static int COL_FIN_HEURES = 75;
// public static int COL_DEBUT_MINUTES = 75;
// public static int COL_FIN_MINUTES = 77;
// public static int COL_DEBUT_SECONDES = 77;
// public static int COL_FIN_SECONDES = 79;

// public static double HALFSECOND = 0.5;
// private int gregorianYear;

    public UkooaFileReader(String filePath)
    {
        ukooaFile = new File(filePath);
        ukooaPoints = new ArrayList<UkooaNavpoint>();
    }

    public int testFileConformity() throws EABException
    {
        Scanner scanner;
        int isAngularUnitDegree = 0;
        try
        {
            scanner = new Scanner(ukooaFile);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (line.startsWith(H2002))
                {
                    String data = line.substring(6);
                    if (data.contains(Messages.getString(Messages.MESSAGES,
                        "ANGULAR_UNITS"))) //$NON-NLS-1$
                    {
                        if (data.trim().split(
                            Messages.getString(Messages.MESSAGES,
                                "ANGULAR_UNITS"))[1].trim() //$NON-NLS-1$
                            .startsWith(
                                Messages.getString(Messages.MESSAGES,
                                    "DEGREE_CODE"))) //$NON-NLS-1$
                            isAngularUnitDegree = 1;
                        else if (data.trim().split(
                            Messages.getString(Messages.MESSAGES,
                                "ANGULAR_UNITS"))[1].trim() //$NON-NLS-1$
                            .startsWith(
                                Messages.getString(Messages.MESSAGES,
                                    "GRADE_CODE"))) //$NON-NLS-1$
                            isAngularUnitDegree = -1;
                    }
                }
                else if (line.startsWith(Messages.getString(Messages.MESSAGES,
                    "S_CHARACTER")) //$NON-NLS-2$
                        || line.startsWith(Messages.getString(
                            Messages.MESSAGES, "T_CHARACTER")) //$NON-NLS-1$
                        || line.startsWith(Messages.getString(
                            Messages.MESSAGES, "A_CHARACTER")) //$NON-NLS-2$
                        || line.startsWith(Messages.getString(
                            Messages.MESSAGES, "G_CHARACTER")) //$NON-NLS-1$ 
                        || line.startsWith(Messages.getString(
                            Messages.MESSAGES, "Q_CHARACTER")) //$NON-NLS-2$
                        || line.startsWith(Messages.getString(
                            Messages.MESSAGES, "Z_CHARACTER")) //$NON-NLS-1$
                        || line.startsWith(Messages.getString(
                            Messages.MESSAGES, "V_CHARACTER")) //$NON-NLS-1$
                        || line.startsWith(Messages.getString(
                            Messages.MESSAGES, "E_CHARACTER")) //$NON-NLS-1$
                        || line.startsWith(Messages.getString(
                            Messages.MESSAGES, "C_CHARACTER"))) //$NON-NLS-1$
                {
                    break;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            throw new EABException(ExceptionCode.UNKOWN_ERROR, e.getCause());
        }
        return isAngularUnitDegree;
    }

    public void openFileAndInitNavPath(UkooaNavPath navPath,
            boolean isDegreeTheAngularUnit) throws EABException
    {

        Scanner scanner;
        try
        {
            scanner = new Scanner(ukooaFile);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (!processLineToInitObject(line, isDegreeTheAngularUnit))
                    break;
            }
            processUkooaNavPoints(navPath);
        }
        catch (FileNotFoundException e)
        {
            throw new EABException(ExceptionCode.UNKOWN_ERROR, e.getCause());
        }
    }

    /**
     * Tri les points selon temps latitude puis longitude fin d'extraire les
     * valeur min et max
     * 
     * @param navPath
     */
    private void processUkooaNavPoints(UkooaNavPath navPath)
    {
        // TODO voir s le tri sr grosses liste aissue de gros fichier p�nalise
        // les perfs. Dans ce cas r�cup�rer le smin max � la lecture du fichier
        List<UkooaNavpoint> ukooaNavpointsCopy = new ArrayList<UkooaNavpoint>();
        ukooaNavpointsCopy.addAll(ukooaPoints);
// Collections.sort(ukooaPoints, new Comparator<UkooaNavpoint>() {
//
// @Override
// public int compare(UkooaNavpoint o1, UkooaNavpoint o2)
// {
// if (o1.time > o2.time)
// return 1;
// else if (o2.time > o1.time)
// return -1;
// return 0;
// }
// });
// navPath._timeStarted = ukooaPoints.get(0).time;
// navPath._timeEnded = ukooaPoints.get(ukooaPoints.size() - 1).time;
        Collections.sort(ukooaPoints, new Comparator<UkooaNavpoint>() {

            @Override
            public int compare(UkooaNavpoint o1, UkooaNavpoint o2)
            {
                if (o1.latitude > o2.latitude)
                    return 1;
                else if (o2.latitude > o1.latitude)
                    return -1;
                return 0;
            }
        });
        navPath._minLatitude = ukooaPoints.get(0).latitude;
        navPath._maxLatitude = ukooaPoints.get(ukooaPoints.size() - 1).latitude;
        Collections.sort(ukooaPoints, new Comparator<UkooaNavpoint>() {

            @Override
            public int compare(UkooaNavpoint o1, UkooaNavpoint o2)
            {
                if (o1.longitude > o2.longitude)
                    return 1;
                else if (o2.longitude > o1.longitude)
                    return -1;
                return 0;
            }
        });
        navPath._minLongitude = ukooaPoints.get(0).longitude;
        navPath._maxLongitude =
            ukooaPoints.get(ukooaPoints.size() - 1).longitude;

        navPath._navPathPoints.setNbPoints(ukooaPoints.size());
// if (gregorianYear != 0)
// navPath.initializeArrays(true);
// else
        navPath.initializeArrays(false);

        ListIterator<UkooaNavpoint> iterator =
            ukooaNavpointsCopy.listIterator();
        while (iterator.hasNext())
        {
            int index = iterator.nextIndex();
            UkooaNavpoint navPoint = iterator.next();
            navPath._navPathPoints.getLatitudes()[index] = navPoint.latitude;
            navPath._navPathPoints.getLongitudes()[index] = navPoint.longitude;
            if (navPath._navPathPoints.getDates() != null)
                navPath._navPathPoints.getDates()[index] = navPoint.time;
        }
    }

    private boolean processLineToInitObject(String line,
            boolean isDegreeTheAngularUnit)
    {
// if (line.startsWith(H0200))
// {
// String data = line.substring(6);
//            String content = ""; //$NON-NLS-1$
// // TODO specifier ce qu'on supporte comme chaine de caracteres
//            if (data.contains(Messages.getString(Messages.MESSAGES,"DATE_OF_SURVEY"))) //$NON-NLS-1$
// {
// if
// (data.trim().split(Messages.getString(Messages.MESSAGES,"DATE_OF_SURVEY")).length
// > 0)
// {
// content =
// data.trim().split(Messages.getString(Messages.MESSAGES,"DATE_OF_SURVEY"))[1].trim();
// }
// }
//            else if (data.contains(Messages.getString(Messages.MESSAGES,"SURVEY_DATE"))) //$NON-NLS-1$
// {
// if
// (data.trim().split(Messages.getString(Messages.MESSAGES,"SURVEY_DATE")).length
// > 0)
// {
// content =
// data.trim().split(Messages.getString(Messages.MESSAGES,"SURVEY_DATE"))[1].trim();
// }
// }
//            if (!content.equals("")) //$NON-NLS-1$
// {
//                String REGEX1 = \\d{4}; //$NON-NLS-1$
// // String REGEX2 = "\\d{1,2}/\\d{1,2}/\\d{2}";
// Pattern p = Pattern.compile(REGEX1);
// for (int i = 0; i + 4 <= content.length(); i++)
// {
// String subStringYear = content.substring(i, i + 4);
// Matcher m = p.matcher(subStringYear);
// if ((m.matches() == true))
// {
// gregorianYear = Integer.parseInt(subStringYear);
// break;
// }
// }
// }
//
// }
        if (line.startsWith(Messages
            .getString(Messages.MESSAGES, "S_CHARACTER")) //$NON-NLS-2$
                || line.startsWith(Messages.getString(Messages.MESSAGES,
                    "T_CHARACTER")) //$NON-NLS-1$
                || line.startsWith(Messages.getString(Messages.MESSAGES,
                    "A_CHARACTER")) //$NON-NLS-2$
                || line.startsWith(Messages.getString(Messages.MESSAGES,
                    "G_CHARACTER")) //$NON-NLS-1$ 
                || line.startsWith(Messages.getString(Messages.MESSAGES,
                    "Q_CHARACTER")) //$NON-NLS-2$
                || line.startsWith(Messages.getString(Messages.MESSAGES,
                    "Z_CHARACTER")) //$NON-NLS-1$
                || line.startsWith(Messages.getString(Messages.MESSAGES,
                    "V_CHARACTER")) //$NON-NLS-1$
                || line.startsWith(Messages.getString(Messages.MESSAGES,
                    "E_CHARACTER")) //$NON-NLS-1$
                || line.startsWith(Messages.getString(Messages.MESSAGES,
                    "C_CHARACTER"))) //$NON-NLS-1$
        {
            double latitude =
                extractLatitudeFromUkooaRecord(
                    line.substring(COL_DEBUT_LATITUDE, COL_FIN_LATITUDE),
                    isDegreeTheAngularUnit);
            double longitude =
                extractLongitudeFromUkooaRecord(
                    line.substring(COL_DEBUT_LONGITUDE, COL_FIN_LONGITUDE),
                    isDegreeTheAngularUnit);

            if (line.substring(COL_DEBUT_SIGNE_LONGITUDE,
                COL_FIN_SIGNE_LONGITUDE).equals(
                Messages.getString(Messages.MESSAGES, "W_CHARACTER"))) //$NON-NLS-1$
                longitude = -longitude;
            if (line
                .substring(COL_DEBUT_SIGNE_LATITUDE, COL_FIN_SIGNE_LATITUDE)
                .equals(Messages.getString(Messages.MESSAGES, "S_CHARACTER"))) //$NON-NLS-1$
                latitude = -latitude;
            // julian day
            long time = 0;
// if (gregorianYear != 0)
// {
// // TODO commenter les �tapes de calculs
// // TODO externaliser les num de colonnes ou cr�er des cstes bien
// // comment�es
// int[] surveyDate = {gregorianYear, 1, 1};
// double julianDate = toJulian(surveyDate);
// int day =
// Integer.parseInt(line.substring(COL_DEBUT_JOUR_JULIEN,
// COL_FIN_JOUR_JULIEN).trim());
// int[] recordDate = fromJulian(julianDate + day - 1);
// int hours =
// Integer.parseInt(line.substring(COL_DEBUT_HEURES,
// COL_FIN_HEURES).trim());
// int minutes =
// Integer.parseInt(line.substring(COL_DEBUT_MINUTES,
// COL_FIN_MINUTES).trim());
// int seconds =
// Integer.parseInt(line.substring(COL_DEBUT_SECONDES,
// COL_FIN_SECONDES).trim());
// GregorianCalendar c =
// new GregorianCalendar(recordDate[0], recordDate[1],
// recordDate[2], hours, minutes, seconds);
// time = c.getTime().getTime();
// }
            ukooaPoints.add(new UkooaNavpoint(longitude, latitude, time));
        }
        else if (line.startsWith(Messages.getString(Messages.MESSAGES,
            "END_OF_FILE_INDICATOR"))) //$NON-NLS-1$
        {
            return false;
        }
        return true;
    }

    private double extractLongitudeFromUkooaRecord(String record,
            boolean isDegreeTheAngularUnit)
    {
        DecimalFormat twoDecimalForm =
            new DecimalFormat(Messages.getString(Messages.MESSAGES,
                "2_DDECIMAL_NUMBER_FORMAT")); //$NON-NLS-1$
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(Messages.getString(Messages.MESSAGES,
            "DECIMAL_SEPARATOR").charAt(0));
        dfs.setGroupingSeparator('\'');
        twoDecimalForm.setDecimalFormatSymbols(dfs);
        if (isDegreeTheAngularUnit)
        {
            // longitude degree sur 3 digits
            double degree = Double.parseDouble(record.substring(0, 3).trim());
            double minutes = Double.parseDouble(record.substring(3, 5).trim());
            double secondes =
                Double.parseDouble(record.substring(5, 10).trim());
          
            return Double.valueOf(twoDecimalForm.format(degree + minutes / 60
                    + secondes / 3600));
// return degree + minutes / 60 + secondes / 3600;
        }
        else
            return Double.valueOf(twoDecimalForm.format(Double
                .parseDouble(record) * 180 / 200));
// return Double.parseDouble(record) / 0.9;
    }

    private double extractLatitudeFromUkooaRecord(String record,
            boolean isDegreeTheAngularUnit)
    {
        DecimalFormat twoDecimalForm =
            new DecimalFormat(Messages.getString(Messages.MESSAGES,
                "2_DDECIMAL_NUMBER_FORMAT")); //$NON-NLS-1$
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(Messages.getString(Messages.MESSAGES,
            "DECIMAL_SEPARATOR").charAt(0));
        dfs.setGroupingSeparator('\'');
        twoDecimalForm.setDecimalFormatSymbols(dfs);

        if (isDegreeTheAngularUnit)
        {
            double degree = Double.parseDouble(record.substring(0, 2).trim());
            double minutes = Double.parseDouble(record.substring(2, 4).trim());
            double secondes = Double.parseDouble(record.substring(4, 9).trim());
            String val =
                twoDecimalForm.format(degree + minutes / 60 + secondes / 3600);
            return Double.valueOf(val);
// return degree + minutes / 60 + secondes / 3600;
        }
        else
            return Double.valueOf(twoDecimalForm.format(Double
                .parseDouble(record) * 180 / 200));
// return Double.parseDouble(record) / 0.9;
    }

    private class UkooaNavpoint
    {

        private final double longitude;
        private final double latitude;
        private final long time;

        /**
         * 
         */
        public UkooaNavpoint(double longitude, double latitude, long time)
        {
            this.longitude = longitude;
            this.latitude = latitude;
            this.time = time;
        }
    }

// public double toJulian(int[] ymd)
// {
// int year = ymd[0];
// int month = ymd[1];
// int day = ymd[2];
// int julianYear = year;
// if (year < 0)
// julianYear++;
// int julianMonth = month;
// if (month > 2)
// {
// julianMonth++;
// }
// else
// {
// julianYear--;
// julianMonth += 13;
// }
//
// double julian =
// (java.lang.Math.floor(365.25 * julianYear)
// + java.lang.Math.floor(30.6001 * julianMonth) + day + 1720995.0);
// if (day + 31 * (month + 12 * year) >= JGREG)
// {
// // change over to Gregorian calendar
// int ja = (int) (0.01 * julianYear);
// julian += 2 - ja + (0.25 * ja);
// }
// return java.lang.Math.floor(julian);
// }
//
// /**
// * Converts a Julian day to a calendar date ref : Numerical Recipes in C,
// * 2nd ed., Cambridge University Press 1992
// */
// public int[] fromJulian(double injulian)
// {
// int jalpha, ja, jb, jc, jd, je, year, month, day;
// double julian = injulian + HALFSECOND / 86400.0;
// ja = (int) julian;
// if (ja >= JGREG)
// {
// jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
// ja = ja + 1 + jalpha - jalpha / 4;
// }
//
// jb = ja + 1524;
// jc = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
// jd = 365 * jc + jc / 4;
// je = (int) ((jb - jd) / 30.6001);
// day = jb - jd - (int) (30.6001 * je);
// month = je - 1;
// if (month > 12)
// month = month - 12;
// year = jc - 4715;
// if (month > 2)
// year--;
// if (year <= 0)
// year--;
//
// return new int[] {year, month, day};
// }
}
