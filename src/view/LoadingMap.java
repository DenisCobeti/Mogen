package view;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

/**
 *
 * @author Neblis
 */
public class LoadingMap extends JWindow{

    private int WIDTH = 100;
    private int HEIGHT = 100;
    
    private String LOADING_TEXT = "Loading Map...";
    
    public LoadingMap() {
        super();
        this.setSize(WIDTH, HEIGHT);
        this.setAlwaysOnTop(true);
        //this.setLayout(new BorderLayout(100, 100));
        JLabel loading = new JLabel(LOADING_TEXT);
        this.add(loading, BorderLayout.CENTER);
    }
    
    
}
