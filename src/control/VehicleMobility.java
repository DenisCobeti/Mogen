package control;

import java.util.LinkedList;
import java.util.List;

import model.routes.Flow;
import model.routes.Route;
import model.routes.VType;
import model.routes.Vehicle;

/**
 *
 * @author Neblis
 */
public class VehicleMobility {
    public static enum Types {FLOW, VEHICLE, ROUTE, VTYPE};
    
    private List<VType> vTypes;
    private List<Vehicle> vehicles;
    private List<Route> routes;
    private List<Flow> flows;

    public VehicleMobility(List<VType> vTypes, List<Vehicle> vehicles, 
            List<Route> routes, List<Flow> flows) {
        this.vTypes = vTypes;
        this.vehicles = vehicles;
        this.routes = routes;
        this.flows = flows;
    }

    public VehicleMobility() {
        this.vTypes = new LinkedList<>();
        this.vehicles = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.flows = new LinkedList<>();
    }
    
    public boolean addElement(Types type, Object element){
        switch(type){
            case FLOW:
                return flows.add((Flow)element);
            case VEHICLE:
                return vehicles.add((Vehicle)element);
            case ROUTE:
                return routes.add((Route)element);
            case VTYPE:
                return vTypes.add((VType)element);
            default:
                return false;
        }
    }
    
    public void save(){
        
    }
    
    public void load(){
        
    }
    
    public String toFile(){
        return null;
    }

    public List<VType> getvTypes() {
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
