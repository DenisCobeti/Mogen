package model.map;

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
    public String API = "http://lz4.overpass-api.de/api/map?bbox=";
    public String APIX_FORMAT = "http://overpass-api.de/api/interpreter?data=way(%s,%s,%s,%s);(._;>;);\n" +
                                "out body;";
    private static final String API_FORMAT = "%s%s,%s,%s,%s";
    private static final String APIXX_FORMAT = "http://overpass-api.de/api/interpreter?data=node%2850%2E746%2C7%2E154%2C50%2E748%2C7%2E157%29%3B%28%2E%5F%3B%3E%3B%29%3Bout%3B%0A";
    
    private final HttpURLConnection connection;
    
    public OsmAPI(double minLon, double minLat, double maxLat, double maxLon) 
                  throws MalformedURLException, ProtocolException, IOException{
        
        //this.API = Config.API;
        String petition = String.format(API_FORMAT, API, minLon, minLat, maxLon, maxLat );
        //String petition = String.format(API_FORMAT, API, minLon, minLat, maxLon, maxLat );
        System.out.println(petition);
        URL url = new URL(petition);
        
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
    }
    
    
    @Override
    public InputStream getMap() throws IOException{
        try{
            connection.getInputStream();
        } catch(IOException e){
            System.out.println(e);
        }
        return connection.getInputStream();
    }
    
    @Override
    public void closeConnection() throws IOException{
        if (connection != null){
            connection.getInputStream().close();
            connection.disconnect();
        }
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
