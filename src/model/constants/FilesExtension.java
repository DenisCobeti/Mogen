package model.constants;

/**
 *
 * @author Denis C
 */
public enum FilesExtension {
    
    OSM(".osm"),
    SUMO_CFG(".sumocfg"),
    NETCONVERT(".net.xml"),
    FCD(".fcd"),
    VEHICLES(".add.xml"),
    NS2_MOBILITY(".mob.tcl"),
    NS2_ACTIVITY(".act.tcl"),
    NS2_TRACE(".trace.tcl"),
    NS2_CONFIG(".conf.tcl"),
    TAZ(".taz.xml"),
    ROUTE(".rou.xml");
    
    private final String extension;

    private FilesExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return extension;
    }
    
}
