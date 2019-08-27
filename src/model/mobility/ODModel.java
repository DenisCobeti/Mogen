package model.mobility;

import control.MogenControl;
import java.io.IOException;
import java.util.HashMap;
import model.routes.TAZ;
import view.MogenView;

/**
 *
 * @author darkm
 */
public class ODModel extends MobilityModel {

    private final static String HEADER = "<tazs>";
    private final static String FOOTER = "</tazs>";
    private final static String FILE_LOCATION = "models/flow/";
    
    private final static String TAZ_FILE = "tazs.taz.xml";
    
    private HashMap<String, TAZ> tazs;

    public ODModel() {
        tazs = new HashMap();
    }

    public void setTazs(HashMap<String, TAZ> tazs) {
        this.tazs = tazs;
    }
    
    public boolean addTazs(String id, TAZ taz){
        if (tazs.containsKey(id)) return false;
        tazs.put(id, taz);
        return true;
    }
    
    @Override
    public void export(String location, String sim, String vTypes, MogenControl control) throws IOException, InterruptedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
