package model;

import control.C4RControl;
import control.Simulation;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import model.routes.VType;
import view.C4RView;

/**
 *
 * @author Denis C
 */
public class C4RModel extends Observable{
    
    public static enum ElementType {FLOW, VEHICLE, ROUTE, VTYPE};
    
    private C4RControl control;
    private C4RView view;
    
    private Map<String,VType> vTypes;
    private Map<String,Simulation> simulations;
    
    
    public C4RModel(C4RControl control, C4RView view) {
        this.control = control;
        this.view = view;
        
        this.vTypes = new HashMap<>();
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
        }
        return false;
    }
    
    private void defaultVTypes(){
        vTypes.put("Car", new VType());
        vTypes.put("Sport", new VType());
        vTypes.put("Truck", new VType());
    }
    public Map<String, VType> getvTypes() {
        return vTypes;
    }
    
}
