package view.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import view.C4RView;

/**
 *
 * @author Neblis
 */
public class DialogAddType extends Dialog{
    private final JTextField id;
    private final JButton acceptButton;
    private final String TEXT_ID = "Name: ";

    private final String ACCEPT = "Accept";
    
    public DialogAddType(C4RView view) {
        this.view = view;
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBackground(Color.WHITE);
        panel.add(newLabel(TEXT_ID), BorderLayout.WEST);
        panel.add(id = newTextField(),BorderLayout.EAST);
    
        acceptButton = new JButton(ACCEPT);
        acceptButton.setActionCommand(ACCEPT);
        acceptButton.setFont(view.getFont());
        acceptButton.setBackground(Color.LIGHT_GRAY);
        acceptButton.setMinimumSize(new Dimension(10,1000));
        acceptButton.addActionListener(view);
        
        acceptButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                try{
                    if(view.addVehicleType(id.getText())) close();
                }catch(NullPointerException ex){
                    
                }
            } 
        });
        add(panel,BorderLayout.NORTH);
        add(acceptButton, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(view);  // centra en ventana principal    
        setVisible(true); 
    }
    
    
}
