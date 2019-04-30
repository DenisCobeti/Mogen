/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;

/**
 *
 * @author Denis C
 */
public interface MapAPI {
    
    public InputStream getMap(double minLon, double minLat, double maxLon, double maxLat) throws ProtocolException, IOException, MalformedURLException;
    
}
