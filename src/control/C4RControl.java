/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.C4RModel;
import view.C4RView;

/**
 *
 * @author Neblis
 */
public class C4RControl {
    private C4RModel model;
    private C4RView view;

    public C4RControl(C4RModel model, C4RView view) {
        this.model = model;
        this.view = view;
    }
  
}
