package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import view.C4RView;

/**
 *
 * @author Neblis
 */
public abstract class Dialog extends JDialog {
    
    protected C4RView view;
    
    private final int WIDTH = 120;
    private final int HEIGHT = 25;
    
    protected JTextField newTextField(){
        JTextField field = new JTextField();
        
        field.setFont(view.getFont());
        field.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        field.setMaximumSize(new Dimension(WIDTH,HEIGHT));
        field.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        field.setBorder(BorderFactory.createMatteBorder
                                                (0, 0, 1, 0, Color.BLACK));
        
        return field;
    }
    
    protected JLabel newLabel(String text){
        JLabel label = new JLabel(" " + text);
        label.setFont(view.getFont());
        
        return label;
    }
    
    protected void close() {
        view.enableEvents();
        dispose();    
    }
}
