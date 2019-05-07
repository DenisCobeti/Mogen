package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import model.constants.Netconvert;

/**
 * Class that will read the config.ini file for values. If these variables 
 * arent found, it uses the default values.
 * @author Denis C
 */
public class Config {
    public static  final String API = "https://lz4.overpass-api.de/api/map?bbox=-1.10557,40.33810,-1.10013,40.34183";
    
    public static String OSM_API = "http://overpass-api.de/api/map?bbox=";
    
    public static final String OSM_MAP = "./maps/OSMap";
    public static final String SUMO_MAP = "./maps/SUMOMap";
    
    private static final String DEFAULT_OSM_MAP = "./maps/OSMap";
    private static final String DEFAULT_SUMO_MAP = "./maps/SUMOMap";
    
    public static List<String> netconvertOptions = new LinkedList<>(
                    Arrays.asList(Netconvert.PROGRAM.getCommand(),
                        Netconvert.OUTPUT.getCommand() + Config.OSM_MAP,
                        Netconvert.REMOVE_GEOMETRY.getCommand(),
                        Netconvert.GUESS_ROUNDABOUTS.getCommand())
    );
    
}
