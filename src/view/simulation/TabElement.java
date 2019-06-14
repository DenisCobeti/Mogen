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
    private static final String NEW_SIMULATION = "new ";
    
    C4RView view;
    
    JLabel titleLabel;
    JLabel closeButton;
    
    private final int tab;

    public TabElement(C4RView view, int tab, String name, JPanel simulation) {
        
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        if (name.isEmpty() || name == null){ 
            titleLabel = new JLabel(NEW_SIMULATION + tab);
        }else{
            this.setName(name);
            titleLabel = new JLabel(name);
        }
        closeButton = new JLabel(CLOSE_SIMULATION);
        closeButton.setToolTipText(CLOSE_SIMULATION_TOOLTIP);
        closeButton.setForeground(Color.red);
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                closeSimulation(simulation);
            }
        });
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        add(titleLabel);
        add(closeButton);
        setOpaque(false);
        
        this.tab = tab;
        this.view = view;
    }
    
    private void closeSimulation(JPanel simulation){
        view.closeSimulation(simulation);
    }
}
