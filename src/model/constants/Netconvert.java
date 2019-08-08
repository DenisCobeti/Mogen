package model.constants;

/**
 * Enum that contains all the constants related to the Netconvert tool.
 * The options that have a default boolean, are writen with the oposite one.
 * This tool is used to convert an OSM Map to the format used by SUMO.
 * @author Denis C
 */
public enum Netconvert {
    
    PROGRAM ("netconvert"), //name of the conversion programm
    VEHICLES ("vehicles"), //name of the conversion programm
    MAP_FILE("mapNetconvert.net.xml"),
    
    OSM_MAP ("--osm"), //OSM map to convert
    OUTPUT ("-o"), //File to output
    
    REMOVE_GEOMETRY ("--geometry.remove"), //Simplyfies the irrelevant geometry
    GUESS_ROUNDABOUTS("--roundabouts.guess"), //Decides direction of roundabouts
    JOIN_JUNCTIONS ("--junctions.join"), // Joins duplicated junctions, may fail
    FLATTEN("--flatten"),	//Remove all z-data; default: false
    LEFTHANDED ("--lefthand"),
    STREET_NAMES ("--output.street-names"), // Keep street names if avalible
    
    OVERTAKE_LANES("--opposites.guess TRUE"), // Guess the overtake lanes
    OSM_NO_DUPLICATES("--osm.skip-duplicates-check TRUE"), // 
    EXTEND_EDGE("--plain.extend-edge-shape"), // 
    
    KEEP_ROADS("--keep-edges.by-type"), //keep only certain road types
    REMOVE_ROADS("--remove-edges.by-type"), //filter road types
    
    KEEP_TYPE("--keep-edges.by-vclass"),
    REMOVE_TYPE("--remove-edges.by-vclass"),
    
    GUESS_TRAFIC_LIGHTS("--tls.guess"),
    
    TRUE("TRUE"); /* Needed after OSM_NO_DUPLICATES, OVERTAKE_LANES, 
                    STREET_NAMES, FLATTEN, GUESS_TRAFIC_LIGHTS.*/
    private final String command;
    
    private Netconvert(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
    
    @Override
    public String toString(){
        return command;
    }
}
