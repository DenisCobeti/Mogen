package view.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Neblis
 */
public class WizardController implements ActionListener{
    private final Wizard wizard;

    public WizardController(Wizard wizard) {
        
        this.wizard = wizard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(Wizard.NEXT)) {
            //Selects the next panel
            /*Object id = wizard.getWizardModel().getCurrentPanelDescriptor().getNextPanelDescriptor();
            wizard.setCurrentPanel(id);*/
        } else if (e.getActionCommand().equals(Wizard.BACK)) {
            /*Object id = wizard.getWizardModel().getCurrentPanelDescriptor().getBackPanelDescriptor();
            wizard.setCurrentPanel(id);*/
        } else if (e.getActionCommand().equals(Wizard.CANCEL)) {
            //Asks for exit of the wizard
            wizard.exit();
        } else if (e.getActionCommand().equals(Wizard.FINISH)) {
            //Ends the wizard
            /*
            wizard.getApplication().saveProject();
            wizard.getWizardModel().getCurrentPanelDescriptor().getNextPanelDescriptor();
            wizard.close();*/
        }

    }
}
