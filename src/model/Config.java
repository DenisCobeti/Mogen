package model;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.jxmapviewer.viewer.GeoPosition;

/**
 * Class that will read the config.ini file. If these variables 
 * arent found, it uses the default values.
 * @author Denis C
 */
public class Config {
    public static  final String API = "https://lz4.overpass-api.de/api/map?bbox=-1.10557,40.33810,-1.10013,40.34183";
    private static final String CONFIG_FILE = "config.properties";
    private static final String VEHICLE_FILE = "vehicles.json";
    public static String OSM_API = "http://overpass-api.de/api/map?bbox=";
    private static Properties properties;
    
    public static String osmMap = "osmMapFile";
    public static String sumoMap = "netconvertMapFile";
    
    public static final String OSM_MAP_DEFAULT = "./maps/OSMap";
    public static final String SUMO_MAP_DEFFAULT = "./maps/SUMOMap";
    public static final String PYTHON_DEFFAULT = "python2";
    
    public static final String SUMO_PROGRAM = "\\bin\\sumo";
    public static final String OD2TRIPS_PROGRAM = "\\bin\\od2trips";
    public static Path SUMO_EXE;
    
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit()
                                                     .getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit()
                                                     .getScreenSize().height;
    
    public static final GeoPosition DEFAULT_POSITION = new GeoPosition(50.11, 8.68);
    public static String sumoLocation = "lib\\Sumo";
    
    public static String python2 = "python2";
    
    public static void load()  {
        properties = new Properties();
        
        try {
            FileInputStream in = new FileInputStream(CONFIG_FILE);
            properties.load(in);
            
            osmMap = (String)properties.get(osmMap);
            sumoMap = (String)properties.get(sumoMap);
            
            python2 = PYTHON_DEFFAULT;
        }catch (IOException ex) {
        //If the config file isnt found, it uses the default values
            osmMap = OSM_MAP_DEFAULT;
            sumoMap = SUMO_MAP_DEFFAULT;
            
            python2 = PYTHON_DEFFAULT;
        }
        SUMO_EXE = Paths.get(sumoLocation, SUMO_PROGRAM);
    }

    public static String getSumoLocation() {
        return sumoLocation;
    }

    public static String getPython2() {
        return python2;
    }

    public static void setSumoLocation(String sumoLocation) {
        Config.sumoLocation = sumoLocation;
        SUMO_EXE = Paths.get(sumoLocation, SUMO_PROGRAM);
    }

    public static void setPython2(String python2) {
        Config.python2 = python2;
    }
    
}
