/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Class that obtains the Map XML in Open Street Maps
 * @author Denis C
 */
public class OSMApi implements MapAPI{

    public static String API2 = "http://overpass-api.de/api/map?bbox=";
    public static String API = "https://lz4.overpass-api.de/api/map?bbox=2.0823,41.3454,2.222,41.4445";
    
    public OSMApi() {}
    
    
    @Override
    public InputStream getMap(double minLon, double minLat, double maxLat, double maxLon) 
                throws ProtocolException, IOException, MalformedURLException{
        
        String petition = String.format( "%s%s,%s,%s,%s", API2, minLon, minLat, maxLat, maxLon );
        System.out.println(petition);
        HttpURLConnection connection = creteConnection(API);
        connection.setRequestMethod("GET");
        
        
        return connection.getInputStream();
        
    }
    
    private HttpURLConnection creteConnection(String request) 
            throws MalformedURLException, IOException{
        
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        
        return connection;
    }
}
