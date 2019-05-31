package model.exceptions;

import model.constants.Errors;

/**
 *
 * @author Neblis
 */
public class DuplicatedKeyException extends Exception{
    /**
     * Creates a new instance of Exception without error message.
     */
    public DuplicatedKeyException() {
        super();
    }


    /**
     * Constructs an instance of Exception with error message.
     * @param msg the error message.
     */
    public DuplicatedKeyException(String msg) {
        super(msg);
    }
    public DuplicatedKeyException(Errors error) {
        super(error.toString());
    }
}
