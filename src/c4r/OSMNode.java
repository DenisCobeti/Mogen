/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c4r;

import java.util.Map;




public class OSMNode {
	
	private String id;
	
	private String lat;
	
	private String lon;
	
	private final Map<String, String> tags;

	private String version;

    public OSMNode(String id, String lat, String lon, String version, Map<String, String> tags) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.version = version;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public String getVersion() {
        return version;
    }
        
      

}


    

