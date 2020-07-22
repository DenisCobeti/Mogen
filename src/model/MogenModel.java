package model;

import control.MogenControl;
import control.Simulation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import model.routes.Flow;
import model.routes.ODElement;
import model.routes.TAZ;
import model.routes.VType;
import view.MogenView;

/**
 *
 * @author Denis C
 */
public class MogenModel extends Observable{
    
    public static enum ElementType {FLOW, VEHICLE, ROUTE, VTYPE};
    public static enum Mobility {Random, Flow, ODMatrix};
    
    private final MogenControl control;
    private final MogenView view;
    
    private Map<String, VType> vTypes;
    private Map<String, Simulation> simulations;
    private HashMap<String, Flow> flows;
    private HashMap <String, TAZ> tazs;
    private ArrayList<ODElement> elements;
    
    
    private String map;
    
    public MogenModel(MogenControl control, MogenView view) {
        this.control = control;
        this.view = view;
        
        this.vTypes = new HashMap<>();
        this.flows = new HashMap<>();
        this.tazs =  new HashMap<>();
        this.elements = new ArrayList<>();
        
        defaultVTypes();
        
        this.simulations = new HashMap<>();
    }
    
    public boolean addElement(String id, Object element){
        if (element instanceof VType){
            if  (vTypes.containsKey(id)) return false;
            vTypes.put(id, (VType)element);
            return true;
            
        } else if (element instanceof Simulation){
            if  (simulations.containsKey(id)) return false;
            simulations.put(id, (Simulation)element);
            return true;
            
        } else if (element instanceof Flow){
            if  (flows.containsKey(id)) return false;
            flows.put(id, (Flow)element);
            return true;
        }
        return false;
    }
    
    public HashMap addFlow(Flow flow){
        int id = flows.size();
        flows.put(String.valueOf(id), flow);
        
        return flows;
    }
    
    public HashMap addFlow(String id, Flow flow){
        flows.put(id, flow);
        
        return flows;
    }
    
    public HashMap<String, TAZ> addTAZ(String id, TAZ taz) {
        tazs.put(id, taz);
        return tazs;
    }
    
    public Tuple addODElement(ODElement element) {
        elements.add(element);
        
        return new Tuple<>(null, element);
    }
    
    public HashMap<String, Flow> getFlows() {
        return flows;
    }
    
    public void removeSim(String id){
        simulations.remove(id);
    }
    
    public void removeVType(String id){
        vTypes.remove(id);
    }
    
    public void removeTAZ(String id){
        tazs.remove(id);
    }
    
    private void defaultVTypes(){
        vTypes.put("Car", new VType());
        
        VType sport = new VType();
        sport.setMaxSpeed(80);
        vTypes.put("Sport", sport);
        
        VType truck = new VType();
        truck.setMaxSpeed(40);
        truck.setAccel(1.4);
        truck.setLength(3);
        vTypes.put("Truck", truck);
    }
    
    public Map<String, VType> getvTypes() {
        return vTypes;
    }

    public HashMap<String, TAZ> getTazs() {
        return tazs;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }
    
    
}
