/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.ifremer.sismer.endsandbends.ui;

import fr.ifremer.sismer.endsandbends.algorithm.DistanceToLine;
import fr.ifremer.sismer.endsandbends.algorithm.GeoProjection;
import fr.ifremer.sismer.endsandbends.input.NavPathPoints;

/**
 * @author htaniou
 */
public class NavigationData
{
    // Donn�es :
    public NavPathPoints[] _paths = null;
    public GeoProjection _projection = GeoProjection.getDefault(false);
    public GeoProjection _globalProjection = GeoProjection.getDefault(true);
    public DistanceToLine _distance = DistanceToLine.getDefault();
    public long _timeStarted = 0;
    public long _timeEnded = 0;
    public double _minLongitude = 0.0;
    public double _maxLongitude = 0.0;
    public double _minLatitude = 0.0;
    public double _maxLatitude = 0.0;
    public boolean _isDateIgnored = false;
}
