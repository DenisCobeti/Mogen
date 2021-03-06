package view.mapelements;

import view.Dialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import view.MogenView;

/**
 *
 * @author Neblis
 */
public class DialogAddType extends Dialog{
    private final JTextField id;
    private final JButton acceptButton;
    private final String TEXT_ID = "Name: ";

    private final String ACCEPT = "Accept";
    
    public DialogAddType(MogenView view) {
        this.view = view;
        this.setIconImage(view.getIconImage());
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
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
                view.addVehicleType(id.getText());
                dispose();
            } 
        });
        add(panel, BorderLayout.NORTH);
        add(acceptButton, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(view);  // centra en ventana principal    
        setVisible(true); 
    }
    
    
}
