package fr.ifremer.sismer.endsandbends.common;

import java.util.Date;

/**
 * @author ATLANTIDE - ABT Utilitaires pour la conversion de dates...
 */
public class Time
{

    // Dur�e d'une minute en codage epoch :
    private static long _oneMinut = 60 * 1000; // une minute en millisecondes...
    // Dur�e d'une heure en codage epoch :
    private static long _oneHour = 60 * _oneMinut; // une heure en
    // millisecondes...
    // Dur�e d'une journ�e en codage epoch :
    private static long _oneDay = 24 * _oneHour; // un jour en millisecondes...

    /**
     * Conversion d'une date julienne (pas exactement, le 0 est situ� au
     * 01/01/1970), en codage epoch (nbre de millisecondes �coul�es depuis le
     * 01/01/1970.
     * 
     * @param date
     *            date julienne, sauf que 0 est pour le 1/1/1970
     * @param time
     *            heure en millisecondes...
     * @return long, codage epoch pour la date et l'heure
     */
    public static long julianToEpoch(long date, long time)
    {
        return date * _oneDay + time;
    }

    /**
     * Conversion d'une dur�e, exprim�e en heure et minute dans le codage epoch,
     * c'est � dire en millisecondes depuis le 01/01/1970.
     * 
     * @param hour
     *            nombre d'heures
     * @param minuts
     *            nombre de minutes
     * @return long, dur�e en milliseconde.
     */
    public static long timeToEpoch(long hour, long minuts)
    {
        return hour * _oneHour + minuts * _oneMinut;
    }

    /**
     * Conversion d'une date epoch vers un comptage de minutes
     * 
     * @param epoch
     * @return long
     */
    public static long epochToMinuts(long epoch)
    {
        // [TNU] Pas besoin d'arrondir, la division de 2 entiers renvoie un
        // entier
// return StrictMath.round(epoch / _oneMinut);
        return epoch / _oneMinut;
        // [/TNU]
    }

    public static Date epochToDate(long epoch)
    {
        return new Date(epoch);
    }

}
