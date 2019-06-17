package model.exceptions;

import model.constants.Errors;

/**
 *
 * @author Neblis
 */
public class DuplicatedSimulationExcepion extends Exception{
    /**
     * Creates a new instance of Exception without error message.
     */
    public DuplicatedSimulationExcepion() {
        super();
    }


    /**
     * Constructs an instance of Exception with error message.
     * @param msg the error message.
     */
    public DuplicatedSimulationExcepion(String msg) {
        super(msg);
    }
    public DuplicatedSimulationExcepion(Errors error) {
        super(error.toString());
    }
}
