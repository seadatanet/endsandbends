package fr.ifremer.sismer.endsandbends.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author ATLANTIDE - ABT Classe servant � g�rer un fichier de propri�t�s par
 *         d�faut
 */
public class DefaultsHandler
{
    // Donn�es globales :
    private static Properties _defaults = null;
    private static String __defaultsName = "sismer.endsandbends.userDefaults";

    /**
     * Method getUserDefaultsPath, r�cup�re le chemin d'acc�s au fichier des
     * pr�f�rences.
     * 
     * @return String chemin d'acc�s au fichier
     */
    protected static String getUserDefaultsPath()
    {
        String pathUser = getUserRoot();
        String filepath = pathUser + getFileSeparator() + __defaultsName;
        return filepath;
    }

    /**
     * Method loadDefaults, chargement des pr�f�rences
     */
    public static void loadDefaults()
    {
        if (_defaults == null)
            _defaults = new Properties();
        else
            _defaults.clear();

        FileInputStream fileIn = null;

        try
        {
            fileIn = new FileInputStream(getUserDefaultsPath());
            _defaults.load(fileIn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != fileIn)
            {
                try
                {
                    fileIn.close();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method saveDefaults, sauvegarde des pr�f�rences locales
     */
    public static void saveDefaults()
    {
        if (_defaults != null)
        {
            FileOutputStream fileOut = null;

            try
            {
                fileOut = new FileOutputStream(getUserDefaultsPath());
                _defaults.store(fileOut,
                    "Valeurs par d\u00E9faut pour Ends&Bends v 1.0");
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.err
                    .println("Erreur dans la sauvegarde des pr\u00E9f\u00E9rences !");
            }
            finally
            {
                if (null != fileOut)
                {
                    try
                    {
                        fileOut.close();
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Method getUserRoot, r�cup�re le chemin d'acc�s sp�cifique � l'utilisateur
     * du syst�me (sans aucun rapport avec le contexte utilisateur)
     * 
     * @return String, chemin d'acc�s
     */
    public static String getUserRoot()
    {
        return System.getProperty("user.home");
    }

    public static String getFileSeparator()
    {
        return System.getProperty("file.separator");
    }

    public static String getPathSeparator()
    {
        return System.getProperty("path.separator");
    }

    public static String getLineSeparator()
    {
        return System.getProperty("line.separator");
    }

    /**
     * Method getDefaultPath, r�cup�re le path par d�faut
     * 
     * @return String
     */
    public static String getDefaultPath(DefaultFolder folder)
    {
        String pathToReturn = null;

        if (null != _defaults)
        {
            pathToReturn = _defaults.getProperty(folder.toString());
        }

        if (null == pathToReturn)
        {
            pathToReturn = getUserRoot();
        }

        return pathToReturn;
    }

    /**
     * Method setDefaultPath.
     * 
     * @param path
     */
    public static void setDefaultPath(DefaultFolder folder, String filepath)
    {
        setDefault(folder.toString(), filepath);
    }

    /**
     * Method getDefault, renvoyer une propri�t�
     * 
     * @param name
     *            nom de la propri�t�
     * @param defValue
     *            valeur par d�faut
     * @return String, valeur de la propri�t� par d�faut
     */
    public static String getDefault(String name, String defValue)
    {
        String retValue = defValue;
        if (_defaults != null)
            retValue = _defaults.getProperty(name);
        if (retValue == null)
            retValue = defValue;
        return retValue;
    }

    /**
     * Method setDefault, modifie une pr�f�rence
     * 
     * @param name
     * @param value
     * @return String
     */
    public static void setDefault(String name, String value)
    {
        if (_defaults != null)
            _defaults.setProperty(name, value);
    }
}
