package view;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Neblis
 */
public class LoadingMap extends JPopupMenu {

    private int WIDTH = 1000;
    private int HEIGHT = 1000;
    
    private String LOADING_TEXT = "Loading Map...";
    
    public LoadingMap() {
        super();
        this.setPopupSize(WIDTH, HEIGHT);
        //this.setLayout(new BorderLayout(100, 100));
        
        JLabel loading = new JLabel(LOADING_TEXT);
        loading.setVisible(true);
        this.add(loading);
    }
    
    
}
