package control;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.C4RModel.ElementType;
import model.exceptions.DuplicatedKeyException;

import model.routes.Flow;
import model.routes.Route;
import model.routes.VType;
import model.routes.Vehicle;

/**
 *
 * @author Neblis
 */
public class VehicleManager {
    
    
    private Map<String,VType> vTypes;
    
    private List<Vehicle> vehicles;
    private List<Route> routes;
    private List<Flow> flows;

    public VehicleManager(Map<String,VType> vTypes, List<Vehicle> vehicles, 
            List<Route> routes, List<Flow> flows) {
        this.vTypes = vTypes;
        this.vehicles = vehicles;
        this.routes = routes;
        this.flows = flows;
    }

    public VehicleManager() {
        this.vTypes = new HashMap<>();
        this.vehicles = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.flows = new LinkedList<>();
    }
    
    public boolean addElement(ElementType type, String id, Object element){
        if (element instanceof VType){
            vTypes.put(id, (VType)element);
            return true;
        }
        switch(type){
            case FLOW:
                return flows.add((Flow)element);
            case VEHICLE:
                return vehicles.add((Vehicle)element);
            case ROUTE:
                return routes.add((Route)element);
            case VTYPE:
                vTypes.put(id, (VType)element);
                return true;
            default:
                return false;
        }
    }
    
    public boolean addElement(String id, Object element){
        if (element instanceof VType){
            if  (vTypes.containsKey(id)) return false;
            vTypes.put(id, (VType)element);
            return true;
        }
        return false;
    }
    
    public void save(){
        
    }
    
    public void load(){
        
    }
    
    public String toFile(){
        return null;
    }
    private void defaultVTypes(){
        vTypes.put("Car", new VType());
        vTypes.put("Sport", new VType());
        vTypes.put("Truck", new VType());
    }
    public Map<String, VType> getvTypes() {
        return vTypes;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<Flow> getFlows() {
        return flows;
    }
    
}
