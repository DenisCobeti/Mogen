package model.constants;

/**
 *
 * @author Denis C
 */
public enum FilesExtension {
    
    OSM(".osm"),
    SUMO_CFG(".sumocfg"),
    NETCONVERT(".net.xml"),
    ROUTE(".rou.xml");
    
    private final String extension;

    private FilesExtension(String extension) {
        this.extension = extension;
    }

    public String getCommand() {
        return extension;
    }
}
