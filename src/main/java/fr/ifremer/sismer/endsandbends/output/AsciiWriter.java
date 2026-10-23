package fr.ifremer.sismer.endsandbends.output;

import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import fr.ifremer.sismer.endsandbends.common.AppException;
import fr.ifremer.sismer.endsandbends.common.Config;
import fr.ifremer.sismer.endsandbends.common.Messages;
import fr.ifremer.sismer.endsandbends.common.UTCCalendar;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints;
import fr.ifremer.sismer.endsandbends.mediation.output.AsciiOutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.NavigationData;

/**
 * @author ATLANTIDE - ABT Classe utilitaire pour formater des fichiers texte...
 */
public class AsciiWriter extends AEndsAndBendsWriter
{
    // // M�thodes helpers sp�cifiques aux fichiers texte:

    private final Calendar _calendar = new UTCCalendar();

    private final boolean _withSeconds;

    /**
     * Param�tres � utiliser lors de l'ex�cution de l'algorithme.
     */
    private AsciiOutputParamsObject _asciiIfremerParams = null;

    /**
     * @param asciiIfremerParams
     * @param file
     * @throws FileNotFoundException
     */
    public AsciiWriter(JFrame main, NavigationData data,
            final AsciiOutputParamsObject asciiIfremerParams)
    {
        super(main, data);
        _asciiIfremerParams = asciiIfremerParams;

        _withSeconds = asciiIfremerParams.isWithSeconds();
    }

    // // M�thodes directes pour �crire dans le fichier...

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFile(NavPathPoints path, int optimPoints)
    {
        // R�cup�rer le fichier :
        File file =
            new File(_asciiIfremerParams.getSelectedFile().getAbsolutePath());

        if (null != file)
        {
            try
            {
                _printWriter = new PrintWriter(new FileOutputStream(file));
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

// // Cr�er un writer sur ce fichier :
// AsciiWriter writer = null;
// try
// {
// writer = new AsciiWriter(_mainFrame);
// }
// catch (Exception fnfe)
// {
// fnfe.printStackTrace();
// writer = null;
// }
// if (writer == null)
// {
// JOptionPane.showMessageDialog(_mainFrame, StringTable
// .get("error_writefile"), StringTable.get("error_title"),
// JOptionPane.ERROR_MESSAGE);
// return;
// }

            // Calculer la progression :
            int nProgressRange = optimPoints;
            int nProgressPosition = 1;

            // Initialiser la progression :
            ProgressMonitor progress =
                new ProgressMonitor(_mainFrame, Messages.getString(
                    Messages.MESSAGES, "prog_save"), "", 0, nProgressRange);
            Cursor oldone = _mainFrame.getCursor();
            _mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            _mainFrame.setEnabled(false);

            // Protection pour sortir correctement dans tous les cas
            // d'erreur...
            try
            {
                // Cas Campagne "C"
                if (_asciiIfremerParams.getType().equalsIgnoreCase("survey"))
                {
                    // Premi�re ligne, chapitre:0, sous-chapitre:1
                    _printWriter.print("01        "); // 01 + espace(03-05) +
                    // espace(06-10)
                    _printWriter.print("M"); // code de mise � jour
                    _printWriter.print("C"); // code de mise � jour
                    printDate(new Date()); // date de cr�ation du
                    // fichier
                    _printWriter.print("Ends&Bends10"); // version
                    _printWriter.print("CAMPAGNE1 "); // format de r�sum�,
                    // constante
                    printString(20, _asciiIfremerParams.getOperator()); // nom
                    // de
                    // l'op�rateur
                    printString(20, _asciiIfremerParams.getComments()); // commentaire
                    _printWriter.println();

                    // Deuxi�me ligne, chapitre:1, sous-chapitre:1
                    _printWriter.print("11        "); // 11 + espace(03-05) +
                    // espace(06-10)
                    printString(8, _asciiIfremerParams.getCampaignId()); // num�ro
                    // de
                    // campagne
                    printString(25, _asciiIfremerParams.getCampaignTitle()); // nom
                    // de
                    // campagne
                    printString(25, _asciiIfremerParams.getBoatName()); // nom
                    // du
                    // navire
                    if (!_data._isDateIgnored)
                    {
                        printDate(new Date(_data._timeStarted));
                        printDate(new Date(_data._timeEnded));
                    }
                    _printWriter.println();
                }
                // Cas Fichier "F"
                else
                {
                    // Premi�re ligne, chapitre:0, sous-chapitre:1
                    _printWriter.print("01        "); // 01 + espace(03-05) +
                    // espace(06-10)
                    _printWriter.print("M"); // code de mise � jour
                    _printWriter.print("F"); // code de mise � jour
                    printDate(new Date()); // date de cr�ation du
                    // fichier
                    _printWriter.print("Ends&Bends10"); // version
                    _printWriter.print("DONNEES2  "); // format de r�sum�,
                    // constante
                    printString(20, _asciiIfremerParams.getOperator()); // nom
                    // de
                    // l'op�rateur
                    printString(20, _asciiIfremerParams.getComments()); // commentaire
                    _printWriter.println();

                    // Deuxi�me ligne, chapitre:1, sous-chapitre:1
                    _printWriter.print("11        "); // 11 + espace(03-05) +
                    // espace(06-10)
                    printString(8, _asciiIfremerParams.getCampaignId()); // identifiant
                    // de
                    // campagne
                    _printWriter.print("NAVI11");
                    _printWriter.print("DSNAVIXX04");
                    _printWriter.print("2.5");
                    _printWriter.print(" ");
                    _printWriter.print("000000");
                    // on compl�te avec des blancs pour avoir 80 chars sur la
                    // ligne (37 blancs)
                    _printWriter.print(padChar(37, ' '));

                    _printWriter.println();
                }

                // Troisi�me ligne, chapitre:1, sous-chapitre:3
                _printWriter.print("13        "); // 13 + espace(03-05) +
                // espace(06-10)
                printPosition(2, 2, 0, _data._maxLatitude, AsciiWriter.CEIL); // nord
                // =
                // max
                // latitude
                printPosition(2, 2, 0, _data._minLatitude, AsciiWriter.FLOOR); // sud
                // =
                // min
                // latitude
                // Pour la longitude, il faut g�rer le cas o� le segment est
                // invers�, � cause des arrondis...
                if (_data._minLongitude <= _data._maxLongitude)
                {
                    printPosition(3, 2, 0, _data._minLongitude,
                        AsciiWriter.FLOOR); // ouest = min longitude
                    printPosition(3, 2, 0, _data._maxLongitude,
                        AsciiWriter.CEIL); // est = max longitude
                }
                else
                {
                    printPosition(3, 2, 0, _data._minLongitude,
                        AsciiWriter.FLOOR); // ouest = min longitude
                    printPosition(3, 2, 0, _data._maxLongitude,
                        AsciiWriter.CEIL); // est = max longitude
                }
                _printWriter.println();

                // Traitement d'un profil :
// NavPathPoints path;
                int modelp;
                int number = 0;
                int model;

                List<String> dataLines = new ArrayList<String>();
                boolean replaceLastRecord;
                Set<String> writtenDates = new HashSet<String>();
                Boolean alreadyIncremented;

                String strNumber, strDate, strLatitude, strLongitude, strCut;

                for (int i = 0; i < _data._paths.length; i++)
                {
                    // R�cup�rer le parcours :
                    path = _data._paths[i];
                    model = path.getSelectedModel();

                    // Parcours des points :
                    int count = path.getPointsCount();
                    for (int index = 0; index < count; index++)
                    {
                        replaceLastRecord = false;
                        // Sauter s'il n'est pas dans le mod�le :
                        modelp = path.getModel(index);
                        if ((model != 0) && ((modelp == 0) || (modelp > model)))
                        {
                            continue;
                        }

                        if ((_data._isDateIgnored)
                                || (path.getDate(index) == null))
                        {
                            strDate = ""; //$NON-NLS-1$
                        }
                        else
                        {
                            strDate = getDateAsString(path.getDate(index));

                            if (!_withSeconds)
                            {
                                alreadyIncremented =
                                    writtenDates.contains(strDate);

                                if (alreadyIncremented)
                                {
                                    _calendar.setTime(path.getDate(index));

                                    _calendar.add(Calendar.MINUTE, 1);

                                    strDate =
                                        getDateAsString(_calendar.getTime());

                                    alreadyIncremented =
                                        writtenDates.contains(strDate);

                                    if (alreadyIncremented)
                                    {
                                        replaceLastRecord = true;
                                    }
                                    else
                                    {
                                        writtenDates.add(strDate);
                                    }
                                }
                                else
                                {
                                    writtenDates.add(strDate);
                                }

                            }
                        }

                        if (!replaceLastRecord)
                        {
                            number++;
                        }

                        strNumber = formatInteger(3, ' ', number);

                        strLatitude =
                            getPositionString(2, 2, 2, path.getLatitude(index),
                                AEndsAndBendsWriter.ROUND);

                        strLongitude =
                            getPositionString(3, 2, 2,
                                path.getLongitude(index),
                                AEndsAndBendsWriter.ROUND);

                        // log
                        logSegment(index, count, path);

                        if ((index == 0) && path.needStartIndicator())
                        {
                            strCut = formatString(10, "D");
                        }
                        else if ((index == count - 1)
                                && path.needEndIndicator())
                        {
                            strCut = formatString(10, "F");
                        }
                        else
                        {
                            strCut = formatString(10, "");
                        }

                        String line =
                            Config.P50_LINE_FORMAT.format(new String[] {
                                    strNumber, strDate, strLatitude,
                                    strLongitude, strCut});

                        if (replaceLastRecord)
                        {
                            if (dataLines.isEmpty())
                            {
                                // la liste est vide, on ajoute une ligne
                                dataLines.add(line);
                            }
                            else
                            {
                                // on �crase la derni�re ligne
                                dataLines.set(dataLines.size() - 1, line);
                            }
                        }
                        else
                        {
                            dataLines.add(line);
                        }

                        // Progression :
                        progress.setProgress(nProgressPosition++);
                        if (progress.isCanceled())
                        {
                            throw new AppException(Messages.getString(
                                Messages.MESSAGES, "error_canceled"));
                        }
                    }
                }

                for (String line : dataLines)
                {
                    _printWriter.println(line);
                }

            }
            catch (AppException ue)
            {
                JOptionPane.showMessageDialog(_mainFrame, ue.getMessage(),
                    Messages.getString(Messages.MESSAGES, "error_title"), //$NON-NLS-1$
                    JOptionPane.ERROR_MESSAGE);
            }
            finally
            {
                // Fin de traitement :
                progress.close();
                _mainFrame.setCursor(oldone);
                _mainFrame.setEnabled(true);

                // Fermer le writer :
                _printWriter.close();
            }
        }
    }

    private String getPositionString(int degChars, int minChars, int fracChars,
            double lat, int round)
    {
        StringBuilder buffer = new StringBuilder();

        if (lat < 0)
        {
            buffer.append('-');

            lat = Math.abs(lat);
            if (round == CEIL)
            {
                round = FLOOR;
            }
            else if (round == FLOOR)
            {
                round = CEIL;
            }
        }
        else
        {
            buffer.append('+');
        }

        int deg = (int) lat;
        double fmin = (lat - deg) * 60;
        int min = 0;
        if (fracChars > 0)
        {
            long mult = 1;

            for (int i = 0; i < fracChars; i++)
            {
                mult *= 10;
            }

            if (round == CEIL)
            {
                fmin = Math.ceil(fmin * mult);
            }
            else if (round == ROUND)
            {
                fmin = Math.round(fmin * mult);
            }
            else if (round == FLOOR)
            {
                fmin = Math.floor(fmin * mult);
            }

            min = (int) (fmin / mult);

            int frac = (int) (fmin - min * mult);

            if (min >= 60)
            {
                min -= 60;
                deg += 1;
                if (deg >= 360)
                {
                    deg -= 360;
                }
            }

            buffer.append(formatInteger(degChars, '0', deg));
            buffer.append(formatInteger(minChars, '0', min));
            buffer.append('.');
            buffer.append(formatInteger(fracChars, '0', frac));
        }
        else
        {
            if (round == CEIL)
            {
                min = (int) Math.ceil(fmin);
            }
            else if (round == ROUND)
            {
                min = (int) Math.round(fmin);
            }
            else if (round == FLOOR)
            {
                min = (int) Math.floor(fmin);
            }

            if (min >= 60)
            {
                min -= 60;
                deg += 1;
                if (deg >= 360)
                {
                    deg -= 360;
                }
            }
            buffer.append(formatInteger(degChars, '0', deg));
            buffer.append(formatInteger(minChars, '0', min));
        }

        return buffer.toString();
    }

    private String padChar(int number, char charToPad)
    {
        StringBuilder builder = new StringBuilder(number);

        for (int i = 0; i < number; i++)
        {
            builder.append(charToPad);
        }

        return builder.toString();
    }

    private String getDateAsString(Date date)
    {
        _calendar.setTime(date);

        StringBuilder buffer =
            new StringBuilder(formatInteger(2, '0',
                _calendar.get(Calendar.YEAR)));

        buffer.append(formatInteger(2, '0', _calendar.get(Calendar.MONTH) + 1)); // index�
        // � 0 !
        buffer.append(formatInteger(2, '0',
            _calendar.get(Calendar.DAY_OF_MONTH)));
        buffer
            .append(formatInteger(2, '0', _calendar.get(Calendar.HOUR_OF_DAY))); // HOUR
        // c'est pour une heure sur 12...
        buffer.append(formatInteger(2, '0', _calendar.get(Calendar.MINUTE)));

        if (_withSeconds)
        {
            buffer
                .append(formatInteger(2, '0', _calendar.get(Calendar.SECOND)));
        }

        return buffer.toString();
    }

    protected void printDate(Date date)
    {
        _calendar.setTime(date);

        // Format JJMMAA
        StringBuilder buffer =
            new StringBuilder(formatInteger(2, '0',
                _calendar.get(Calendar.DAY_OF_MONTH)));

        buffer.append(formatInteger(2, '0', _calendar.get(Calendar.MONTH) + 1)); // index�
        // � 0 !
        buffer.append(formatInteger(2, '0', _calendar.get(Calendar.YEAR)));

        _printWriter.print(buffer.toString());
    }
}
