package model;

import control.MogenControl;
import control.Simulation;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.scene.shape.Polyline;
import model.routes.Flow;
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
    
    private Map<String,VType> vTypes;
    private Map<String,Simulation> simulations;
    private Map<String,Flow> flows;
    
    private String map;
    
    public MogenModel(MogenControl control, MogenView view) {
        this.control = control;
        this.view = view;
        
        this.vTypes = new HashMap<>();
        this.flows = new HashMap<>();
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
    
    public void addFlow(Flow flow){
        flows.put(String.valueOf(flows.size()), flow);
    }
    
    public void removeSim(String id){
        simulations.remove(id);
    }
    
    public void removeVType(String id){
        vTypes.remove(id);
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

    public void setMap(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }
    
    
}
