package model.constants;

/**
 * Enum that contains all the constants related to the Netconvert tool.
 * This tool is used to convert an OSM Map to the format used by SUMO
 * @author Denis C
 */
public enum Netconvert {
    
    PROGRAM ("netconvert"), //name of the conversion programm
    
    OSM_MAP ("--osm"), //OSM map to convert
    OUTPUT ("-o"), //File to output
    
    REMOVE_GEOMETRY ("--geometry.remove"), //Simplyfies the irrelevant geometry
    ADD_ROUNDABOUTS("--roundabouts.guess"), //Decides direction of roundabouts
    JOIN_JUNCTIONS ("--junctions.join"), // Joins duplicated junctions, may fail
    LEFTHANDED ("--lefthand");
    
    private String command;

    private Netconvert(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
