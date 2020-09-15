package model;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jxmapviewer.viewer.GeoPosition;

/**
 * Class that will read the config.ini file. If these variables 
 * arent found, it uses the default values.
 * @author Denis C
 */
public class Config {
    public static  final String API = "https://lz4.overpass-api.de/api/map?bbox=-1.10557,40.33810,-1.10013,40.34183";
    private static final String CONFIG_FILE = "config.properties";
    public static String OSM_API = "http://overpass-api.de/api/map?bbox=";
    private static Properties properties;
    
    private static final String OSM_MAP_PROPERTY = "osmMapFile";
    private static final String SUMO_MAP_PROPERTY = "netconvertMapFile";
    private static final String PYTHON_PROPERTY = "python";
    private static final String SUMO_PROPERTY = "sumo";
    
    public static String osmMap;
    public static String sumoMap;
    public static String python;
    public static String sumo;
    
    public static final String OSM_MAP_DEFAULT = "./maps/OSMap";
    public static final String SUMO_MAP_DEFAULT = "./maps/SUMOMap";
    public static final String PYTHON_DEFAULT = "python2";
    public static final String SUMO_DEFAULT = "lib/Sumo";
    
    public static final String SUMO_PROGRAM = "/bin/sumo";
    public static final String OD2TRIPS_PROGRAM = "/bin/od2trips";
    public static Path SUMO_EXE;
    
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit()
                                                     .getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit()
                                                     .getScreenSize().height;
    
    public static final GeoPosition DEFAULT_POSITION = new GeoPosition(50.11, 8.68);
    
    
    public static void load()  {
        properties = new Properties();
        
        try {
            FileInputStream in = new FileInputStream(CONFIG_FILE);
            properties.load(in);
            
            osmMap = (String)properties.get(OSM_MAP_PROPERTY);
            sumoMap = (String)properties.get(SUMO_MAP_PROPERTY);
            python = (String)properties.get(PYTHON_PROPERTY);
            sumo = (String)properties.get(SUMO_PROPERTY);
            
            if(osmMap == null) osmMap = OSM_MAP_DEFAULT;
            if(sumoMap == null) sumoMap = SUMO_MAP_DEFAULT;
            if(python == null) python = PYTHON_DEFAULT;
            if(sumo == null) sumo = SUMO_DEFAULT;
            
        }catch (IOException ex) {
            //If the config file isnt found, it uses the default values
            osmMap = OSM_MAP_DEFAULT;
            sumoMap = SUMO_MAP_DEFAULT;
            python = PYTHON_DEFAULT;
            sumo = SUMO_DEFAULT;
        }
        SUMO_EXE = Paths.get(sumo, SUMO_PROGRAM);
    }
    
    public static void save(){
        
        properties.setProperty(OSM_MAP_PROPERTY, osmMap);
        properties.setProperty(SUMO_MAP_PROPERTY, sumoMap);
        properties.setProperty(PYTHON_PROPERTY, python);
        properties.setProperty(SUMO_PROPERTY, sumo);
        
        File configFile = new File(CONFIG_FILE);
        
        try {
            if (!configFile.exists()) configFile.createNewFile();
            FileOutputStream out = new FileOutputStream(CONFIG_FILE);
            
            properties.store(out, null);
            
        } catch (IOException ex) { /*unreachable*/ }
    }

    public static String getSumoLocation() {
        return sumo;
    }

    public static String getPython() {
        return python;
    }

    private static void checkProperties(){
        
    }
    
    public static void setSumoLocation(String sumoLocation) {
        Config.sumo = sumoLocation;
        SUMO_EXE = Paths.get(sumoLocation, SUMO_PROGRAM);
    }

    public static void setPython(String python) {
        Config.python = python;
    }
    
}
