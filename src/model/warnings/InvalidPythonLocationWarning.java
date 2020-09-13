/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.warnings;

/**
 *
 * @author darkm
 */
public class InvalidPythonLocationWarning implements Warning {
    
    private final String MESSAGE = "Warning: Current Location for the python executable: \n"
            + "%s is invalid. \n\n"
            + "To change this go to Edit > Settings > Python";
    private final String location;

    public InvalidPythonLocationWarning(String location) {
        this.location = location;
    }
    
    @Override
    public String getMessage() {
        return String.format(MESSAGE, location);
    }
    
}
