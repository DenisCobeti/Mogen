/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.C4RControl;
import view.C4RView;

/**
 *
 * @author Denis C
 */
public class C4RModel {
    
    private C4RControl control;
    private C4RView view;

    public C4RModel(C4RControl control, C4RView view) {
        this.control = control;
        this.view = view;
    }
    
    
}
