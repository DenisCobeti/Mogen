package model;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import model.constants.Netconvert;

/**
 * Class that obtains the Map XML in Open Street Maps and returns it as a InputStream
 * @author Denis C
 */
public class OsmAPI implements MapAPI{

    // minLon, minLat, maxLon, maxLat
    public static final String API2 = "https://lz4.overpass-api.de/api/map?bbox=-1.10557,40.33810,-1.10013,40.34183";
    public static final String API3 = "https://lz4.overpass-api.de/api/map?bbox=-1.10557,40.33810,-1.10013,40.34183";
    public String API = "https://lz4.overpass-api.de/api/map?bbox=";
    private static final String API_FORMAT = "%s%s,%s,%s,%s";
    private final HttpURLConnection connection;
    
    
    
    public OsmAPI(double minLon, double minLat, double maxLat, double maxLon) 
                  throws MalformedURLException, ProtocolException, IOException{
        
        //this.API = Config.API;
        String petition = String.format(API_FORMAT, API, minLon, minLat, maxLon, maxLat );
        System.out.println(petition);
        
        URL url = new URL(API2);
        
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
    
    
    @Override
    public List<String> netconvertCommand(String osmFile, String netconvertFile){
        List<String> convertCommand = java.util.Arrays.asList(
                                Netconvert.PROGRAM.toString(), 
                                Netconvert.OSM_MAP.toString(), osmFile ,
                                Netconvert.OUTPUT.toString(), netconvertFile);
       
        return convertCommand;
    }
    
    @Override
    public List<String> netconvertCommand(List<Netconvert> options, 
                                        String osmFile, String netconvertFile){
        List<String> convertCommand = netconvertCommand(osmFile, netconvertFile);
        
        options.forEach((option) -> {
            convertCommand.add(option.toString());
        });
        return convertCommand;
    }
}
