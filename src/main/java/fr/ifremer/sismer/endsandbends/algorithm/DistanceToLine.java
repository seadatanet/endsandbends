package fr.ifremer.sismer.endsandbends.algorithm;

/**
 * @author ATLANTIDE - ABT Classe abstraite qui d�fini les op�rations de calcul
 *         de distance entre un point et une droite
 */
public abstract class DistanceToLine
{
    public abstract void defineLine(double x1, double y1, double x2, double y2);

    public abstract double getDistance(double x, double y);

    public abstract double getSquareDistance(double x, double y);

    // Donn�es internes :
    protected String _name;
    protected String _description;

    /**
     * Returns the name.
     * 
     * @return String
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Returns the description.
     * 
     * @return String
     */
    public String getDescription()
    {
        return _description;
    }

    /**
     * Methode statique permettant de r�cup�rer une projection par d�faut
     * 
     * @return DistanceToLine
     */
    public static DistanceToLine getDefault()
    {
        return new VerticalDistanceToLine();
    }
}
