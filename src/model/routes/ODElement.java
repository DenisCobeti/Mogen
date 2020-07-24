/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.routes;

import java.util.Objects;

/**
 *
 * @author darkm
 */
public class ODElement {
    private final String origin;
    private final String destination;
    private int vehiclesNum;

    private static final String FILE_FORMAT = "%s   %s  %s";
    
    public ODElement(String origin, String destination, int vehiclesNum) {
        this.origin = origin;
        this.destination = destination;
        this.vehiclesNum = vehiclesNum;
    }

    public ODElement(String origin, String destination, String vehiclesNum) {
        this.origin = origin;
        this.destination = destination;
        this.vehiclesNum = Integer.valueOf(vehiclesNum);
    }

    public void setVehiclesNum(int vehiclesNum) {
        this.vehiclesNum = vehiclesNum;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.origin);
        hash = 11 * hash + Objects.hashCode(this.destination);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ODElement other = (ODElement) obj;
        if (!Objects.equals(this.origin, other.origin)) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        return true;
    }
    
    public String toFile(){
        return String.format(FILE_FORMAT, origin, destination, vehiclesNum + ".00");
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getVehiclesNum() {
        return vehiclesNum;
    }
    
}
