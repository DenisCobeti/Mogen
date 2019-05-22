package control;

import java.util.List;
import model.constants.Sumo;

/**
 *
 * @author Neblis
 */
public class SumoSimulation {
    private List<String> command;
    
    
    private String setDefaultValues(){
        
        return Sumo.PROGRAMM.toString(); 
    }
    
    private void executeSimulation(){
        ProcessBuilder netconvert = new ProcessBuilder(command);
    }
}
