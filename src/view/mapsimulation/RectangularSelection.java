/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mapsimulation;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import javax.swing.SwingUtilities;

/**
 *
 * @author darkm
 */
public class RectangularSelection extends MouseAdapter {

    private final MapPanel panel;
    
    public RectangularSelection(MapPanel panel) {
        super();
        
        this.panel = panel;
    }
    
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (e.getButton() == java.awt.event.MouseEvent.BUTTON1){

            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            SwingUtilities.convertPointFromScreen(b, panel);

            panel.mouseStartX = e.getXOnScreen();
            panel.mouseStartY = e.getYOnScreen();
            panel.selectionRectangle.setX(panel.mouseStartX);
            panel.selectionRectangle.setY(panel.mouseStartY);
            panel.selectionRectangle.setWidth(0);
            panel.selectionRectangle.setHeight(0);
            System.out.println("dale");
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        if (e.getButton() == java.awt.event.MouseEvent.BUTTON1){
            /*
            mouseStartX = e.getX();
            mouseStartY = e.getY();
            selectionRectangle.setX(mouseStartX);
            selectionRectangle.setY(mouseStartY);
            selectionRectangle.setWidth(0);
            selectionRectangle.setHeight(0);*/
            System.out.println("daliendo");
        }
    }
    
}
