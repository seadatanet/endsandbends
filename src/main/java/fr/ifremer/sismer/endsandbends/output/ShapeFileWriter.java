/* ============================================================================
 * Nom du fichier	: ShapeFileWriter.java
 * Auteur			: Altran
 * Date				: 23 sept. 2010
 * ============================================================================
 */
package fr.ifremer.sismer.endsandbends.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.Hints;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;

import fr.ifremer.sismer.endsandbends.exception.EABException;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints;
import fr.ifremer.sismer.endsandbends.mediation.output.OutputParamsObject;
import fr.ifremer.sismer.endsandbends.ui.NavigationData;

/**
 * Description: TODO
 * 
 * @author mbettich
 */
public class ShapeFileWriter extends AEndsAndBendsWriter implements
        IEndsAndBendsWriter
{

    /**
     * 
     */
    private static final String XML = "XML";
    /**
     * 
     */
    private static final String UTF_8 = "UTF-8";
    /**
     * Param�tres � utiliser lors de l'ex�cution de l'algorithme.
     */
    OutputParamsObject _shapeParams = null;

    /**
     * @param main
     * @param data
     */
    protected ShapeFileWriter(JFrame main, NavigationData data)
    {
        super(main, data);
    }

    /**
     * Constructeur.
     * 
     * @param file
     * @throws FileNotFoundException
     */
    public ShapeFileWriter(JFrame main, NavigationData data,
            OutputParamsObject shapeParams)
    {
        super(main, data);
        _shapeParams = shapeParams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFile(NavPathPoints path, int optimPoints)
            throws EABException
    {
        convertShapeToDOM(path);
    }

    /**
     * @param path
     * @return
     */
    private void convertShapeToDOM(NavPathPoints path)
    {
        /*
         * We use the DataUtilities class to create a FeatureType that will describe the data in our
         * shapefile.
         * 
         * See also the createFeatureType method below for another, more flexible approach.
         */
        SimpleFeatureType TYPE;
        try
        {
            TYPE =
                DataUtilities.createType("Location",
                    "location:Point:srid=4326," + "name:String");

            /*
             * We create a FeatureCollection into which we will put each Feature created from a record
             * in the input csv data file
             */
            SimpleFeatureCollection collection =
                FeatureCollections.newCollection();

            /*
             * GeometryFactory will be used to create the geometry attribute of each feature (a Point
             * object for the location)
             */
            GeometryFactory geometryFactory =
                JTSFactoryFinder.getGeometryFactory(new Hints());

            SimpleFeatureBuilder featureBuilder =
                new SimpleFeatureBuilder(TYPE);

            int modelp;
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

                    if (null != path.getPoint(index))
                    {
                        coordinateList.add(new Coordinate(path
                            .getLongitude(index), path.getLatitude(index)));
                    }
                }

                lineStringList.add(geometryFactory
                    .createLineString(coordinateList
                        .toArray(new Coordinate[coordinateList.size()])));
            }
            MultiLineString multiCurve =
                geometryFactory.createMultiLineString(lineStringList
                    .toArray(new LineString[lineStringList.size()]));

            SimpleFeatureTypeBuilder typeBuilder =
                new SimpleFeatureTypeBuilder();
            typeBuilder.setName("Geometry");
            typeBuilder.add("geometry", MultiLineString.class);
            typeBuilder.add("name", String.class);
            SimpleFeatureType type = typeBuilder.buildFeatureType();

            featureBuilder = new SimpleFeatureBuilder(type);
            featureBuilder.add(multiCurve);
            featureBuilder.add("multiCurve");
            collection.add(featureBuilder.buildFeature(null));
            /*
             * Get an output file name and create the new shapefile
             */
            File newFile =
                new File(_shapeParams.getSelectedFile().getAbsolutePath());

            ShapefileDataStoreFactory dataStoreFactory =
                new ShapefileDataStoreFactory();

            Map<String, Serializable> params =
                new HashMap<String, Serializable>();
            try
            {
                params.put("url", newFile.toURI().toURL());
            }
            catch (MalformedURLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            params.put("create spatial index", Boolean.TRUE);

            ShapefileDataStore newDataStore;
            try
            {
                newDataStore =
                    (ShapefileDataStore) dataStoreFactory
                        .createNewDataStore(params);
                newDataStore.createSchema(TYPE);
                newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);
                /*
                 * Write the features to the shapefile
                 */
                Transaction transaction = new DefaultTransaction("create");

                String typeName = newDataStore.getTypeNames()[0];
                SimpleFeatureSource featureSource =
                    newDataStore.getFeatureSource(typeName);
                if (featureSource instanceof SimpleFeatureStore)
                {
                    SimpleFeatureStore featureStore =
                        (SimpleFeatureStore) featureSource;

                    featureStore.setTransaction(transaction);
                    try
                    {
                        featureStore.addFeatures(collection);
                        transaction.commit();

                    }
                    catch (Exception problem)
                    {
                        problem.printStackTrace();
                        transaction.rollback();

                    }
                    finally
                    {
                        transaction.close();
                    }
                }
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        catch (SchemaException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    /**
     * Here is how you can use a SimpleFeatureType builder to create the schema
     * for your shapefile dynamically.
     * <p>
     * This method is an improvement on the code used in the main method above
     * (where we used DataUtilities.createFeatureType) because we can set a
     * Coordinate Reference System for the FeatureType and a a maximum field
     * length for the 'name' field dddd
     */
    private static SimpleFeatureType createFeatureType()
    {

        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference
        // system

        // add attributes in order
        builder.add("Location", Point.class);
        builder.length(15).add("Name", String.class); // <- 15 chars width for
        // name field

        // build the type
        final SimpleFeatureType LOCATION = builder.buildFeatureType();

        return LOCATION;
    }

}
