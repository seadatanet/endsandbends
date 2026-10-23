package fr.ifremer.sismer.endsandbends.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTWriter;

import fr.ifremer.sismer.endsandbends.input.NavPathPoints;
import fr.ifremer.sismer.endsandbends.mediation.output.WktOutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.NavigationData;

/**
 * @author ATLANTIDE - ABT Classe utilitaire pour formater des fichiers texte...
 */
public class WKTFileWriter extends AEndsAndBendsWriter
{
    // // M�thodes helpers sp�cifiques aux fichiers texte:
    WktOutputParamsObject _wktParameters;

    /**
     * @param file
     * @throws FileNotFoundException
     */
    public WKTFileWriter(JFrame main, NavigationData data,
            WktOutputParamsObject params)
    {
        super(main, data);

        _wktParameters = params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFile(NavPathPoints path, int optimPoints)
    {
        // R�cup�rer le fichier :
        File file =
            new File(_wktParameters.getSelectedFile().getAbsolutePath());

        try
        {
            _printWriter = new PrintWriter(new FileOutputStream(file));
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Cr�ation du writer sp�cifique
        WKTWriter wktWriter = new WKTWriter(2);

        double precision;

        if (_wktParameters.getResolution() > 0)
        {
            precision = Math.pow(10.0, _wktParameters.getResolution() - 1);
        }
        else
        {
            precision = 0;
        }

        // Cr�ation du GeometryFactory
        GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(precision));

        // Traitement d'un profil :
        int modelp;
        int number = 0;
        int model;

        List<LineString> lineStringList = new ArrayList<LineString>();

        for (int i = 0; i < _data._paths.length; i++)
        {
            // R�cup�rer le parcours :
            path = _data._paths[i];
            model = path.getSelectedModel();

            // Parcours des points :
            int count = path.getPointsCount();

            List<Coordinate> coordinateList = new ArrayList<Coordinate>();

            for (int index = 0; index < count; index++)
            {
                // Sauter s'il n'est pas dans le mod�le :
                modelp = path.getModel(index);
                if ((model != 0) && ((modelp == 0) || (modelp > model)))
                    continue;
                number++;

                if (null != path.getPoint(index))
                {
                    // Nouvelle entr�e dans le tableau de coordonn�es
                    coordinateList.add(new Coordinate(path.getLongitude(index),
                        path.getLatitude(index)));
                }
            }

            // Tableau de Coordonn�es pour la lineString en cours
            Coordinate[] coordinates =
                coordinateList.toArray(new Coordinate[coordinateList.size()]);

            // Nouvelle entr�e dans le tableau lineStrings
            lineStringList.add(geometryFactory.createLineString(coordinates));
        }

        // Ecriture dans le fichier de sortie � l'aide du writer WKT
        try
        {
            wktWriter.write(geometryFactory
                .createMultiLineString(lineStringList
                    .toArray(new LineString[lineStringList.size()])),
                _printWriter);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        _mainFrame.setEnabled(true);

        // Fermer le writer :
        _printWriter.close();
    }
}
