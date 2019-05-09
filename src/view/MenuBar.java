package view;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Neblis
 */
public class MenuBar extends JMenuBar {

    public MenuBar() {
        super();
        initUI();
    }
    
    
    
    private void initUI() {
        createMenuBar();
    }
    
    private void createMenuBar(){
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem eMenuItem = new JMenuItem("Exit");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((event) -> System.exit(0));
        
        fileMenu.add(eMenuItem);
        this.add(fileMenu);
    }
}
