/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.exceptions;

/**
 *
 * @author darkm
 */
public class NoRouteConnectionException extends Exception {
    
    public NoRouteConnectionException() {
        super();
    }

    public NoRouteConnectionException(String message) {
        super(message);
    }
    
}
