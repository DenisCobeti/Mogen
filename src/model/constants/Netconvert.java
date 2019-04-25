/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.constants;

/**
 *
 * @author Neblis
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
