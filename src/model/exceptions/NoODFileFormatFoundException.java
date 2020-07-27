/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.exceptions;

import model.constants.Errors;

/**
 *
 * @author darkm
 */
public class NoODFileFormatFoundException extends Exception{
    /**
     * Creates a new instance of Exception without error message.
     */
    public NoODFileFormatFoundException() {
        super();
    }


    /**
     * Constructs an instance of Exception with error message.
     * @param msg the error message.
     */
    public NoODFileFormatFoundException(String msg) {
        super(msg);
    }
    public NoODFileFormatFoundException(Errors error) {
        super(error.toString());
    }
}
