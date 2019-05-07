/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.C4RControl;
import control.ViewListener;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import model.C4RModel;

/**
 *
 * @author Neblis
 */
public class C4RView extends JFrame implements ActionListener, Observer{
    private C4RModel model;
    private C4RControl control;
  
    public final static Font FONT = 
                    new Font("Century Gothic", Font.PLAIN, 14);
    
    private final ViewListener listenerUI;

    public C4RView(ViewListener listenerUI) {
        this.listenerUI = listenerUI;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
