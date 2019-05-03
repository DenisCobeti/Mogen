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

    public String API = "http://overpass-api.de/api/map?bbox=";
    private HttpURLConnection connection;
    private static final String API_FORMAT = "%s%s,%s,%s,%s";
    public static final String API2 = "https://lz4.overpass-api.de/api/map?bbox=-1.10557,40.33810,-1.10013,40.34183";
    
    public OSMApi(double minLon, double minLat, double maxLat, double maxLon) 
                    throws MalformedURLException, ProtocolException, IOException  {
        this.API = Config.API;
        //String petition = String.format(API_FORMAT, API2, minLon, minLat, maxLon, maxLat );
        
        URL url = new URL(API);
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
    }
    
    
    @Override
    public InputStream getMap() throws IOException{
        
        return connection.getInputStream();
        
    }
    
    private HttpURLConnection creteConnection(String request) 
            throws MalformedURLException, IOException{
        
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        
        return connection;
    }
}
