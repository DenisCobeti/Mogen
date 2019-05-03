package model.constants;

/**
 *
 * @author Denis C
 */
public enum Errors {
    
    OSM_DOWNLOAD("Error: couldn´t download OSM map"),
    FILE_WRITE("Error: "),
    NETCONVERT(".net.xml"),
    ROUTE(".rou.xml");
    
    private final String msg;

    private Errors(String extension) {
        this.msg = extension;
    }

    public String getErrorMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
