package view.simulation;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import view.C4RView;

/**
 *
 * @author Neblis
 */
public class AddSimulationListener implements ChangeListener {

    private final C4RView view;
    public AddSimulationListener(C4RView view) {
        this.view = view;
    }
    
    @Override
    public void stateChanged(ChangeEvent evt) {
        JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();
                    if(tabbedPane.getSelectedIndex() == tabbedPane.indexOfTab
                        (C4RView.ADD_SIMULATION)) view.addSimulation();
    }
    
}
