/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.warnings;

import java.util.HashSet;

/**
 *
 * @author darkm
 */
public class NoNamedDistrictsWarning implements Warning {
    
    private final HashSet<String> notFoundDistricts;
    private final String MESSAGE = "Warning: The following names of districts could not be found, \n"
                           + "it is recommended to assign them with the same name in the \n "
                           + "districts section: ";

    public NoNamedDistrictsWarning(HashSet<String> notFoundDistricts) {
        this.notFoundDistricts = notFoundDistricts;
    }
    
    @Override
    public String getMessage() {
        return MESSAGE + notFoundDistricts.toString();
    }
    
}
