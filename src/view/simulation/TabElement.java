package view.simulation;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.C4RView;

/**
 *
 * @author Neblis
 */
public class TabElement extends JPanel{
    
    private static final String CLOSE_SIMULATION_TOOLTIP = "Close selected simulation";
    private static final String CLOSE_SIMULATION = "   X ";
    
    C4RView view;
    
    JLabel titleLabel;
    JLabel closeButton;
    
    private final int tab;

    public TabElement(C4RView view, int tab, String name) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleLabel = new JLabel("new");
        closeButton = new JLabel(CLOSE_SIMULATION);
        closeButton.setToolTipText(CLOSE_SIMULATION_TOOLTIP);
        closeButton.setForeground(Color.red);
        
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        add(titleLabel);
        add(closeButton);
        setOpaque(false);
        
        this.tab = tab;
        this.view = view;
    }
    
    
    
}
