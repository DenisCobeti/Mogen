package view.mapelements;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import model.routes.VType;
import view.C4RView;
import view.export.MobilityModelFrame;

/**
 *
 * @author Neblis
 */
public class VehicleTypePanel extends javax.swing.JPanel {

    /**
     * Creates new form vehicleTypeElement
     */
    private static final String MAX_SPEED = "Max speed";
    private static final String ACCEL = "Accel";
    private static final String DECEL = "Decel";
    private static final String TAU = "Tau";
    private static final String LENGHT = "Length";
    
    private final static Font NAME_FONT = new Font("Century Gothic", Font.BOLD, 14);
    
    private final static String DELETE_ICON_IMG = "resources/button/delete.png";
    private final static String EDIT_ICON_IMG = "resources/button/editFollowingModel.png";
    
    private final static int SPEED_SPORT = 50;
    private final static double ACCEL_SPORT = 3;
    private final static double LENGTH_TRUCK = 3;

    ImageIcon DELETE_ICON = new ImageIcon(DELETE_ICON_IMG);
    ImageIcon EDIT_ICON = new ImageIcon(EDIT_ICON_IMG);
    
    private C4RView view;
    private String name;
    private VType type;
    
    private enum Icon {
        CAR("resources/icon/car.png"), 
        SPORT("resources/icon/sport.png"), 
        TRUCK("resources/icon/truck.png"), 
        HEAVY("resources/icon/heavy.png");
        
        private final String location; 
        
        private Icon(String location){
            this.location = location;
        }
        
        private String getLocation(){
            return location;
        }
    }
    
    public VehicleTypePanel(String name, VType type, C4RView view) {
        this.name = name;
        this.view = view;
        this.type = type;
        
        ImageIcon imageIcon;
                
        if (type.getAccel() > ACCEL_SPORT || type.getMaxSpeed() > SPEED_SPORT){
            imageIcon = new ImageIcon(Icon.SPORT.getLocation());
        }else if (type.getLength() >= LENGTH_TRUCK){
            imageIcon = new ImageIcon(Icon.TRUCK.getLocation());
        }else{
            imageIcon = new ImageIcon(Icon.CAR.getLocation());
        }
        
        initComponents();
        update();
        
        icon.setIcon(imageIcon);
        setFont(view.getFont());
    }
    
    public void update(){
        if(type == null) return;
        accelField.setText(Double.toString(type.getAccel()));
        decelField.setText(Double.toString(type.getDecel()));
        lengthField.setText(Integer.toString(type.getLength()));
        sigmaField.setText(Double.toString(type.getTau()));
        speedField.setText(Integer.toString(type.getMaxSpeed()));
        this.updateUI();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        icon = new javax.swing.JLabel();
        optionsPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        speedLabel = new javax.swing.JLabel();
        accelLabel = new javax.swing.JLabel();
        decelLabel = new javax.swing.JLabel();
        sigmaLabel = new javax.swing.JLabel();
        sigmaField = new javax.swing.JFormattedTextField();
        accelField = new javax.swing.JFormattedTextField();
        decelField = new javax.swing.JFormattedTextField();
        speedField = new javax.swing.JFormattedTextField();
        lengthLabel = new javax.swing.JLabel();
        lengthField = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        editLabel = new javax.swing.JLabel();
        removeLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        setMaximumSize(new java.awt.Dimension(600, 67));

        icon.setPreferredSize(new java.awt.Dimension(50, 50));
        icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconMouseClicked(evt);
            }
        });

        optionsPanel.setBackground(new java.awt.Color(255, 255, 255));

        nameLabel.setFont(NAME_FONT);
        nameLabel.setForeground(new java.awt.Color(204, 0, 102));
        nameLabel.setText(name
        );

        speedLabel.setFont(view.getFont());
        speedLabel.setText(MAX_SPEED);

        accelLabel.setFont(view.getFont());
        accelLabel.setText(ACCEL);

        decelLabel.setFont(view.getFont());
        decelLabel.setText(DECEL);

        sigmaLabel.setFont(view.getFont());
        sigmaLabel.setText(TAU);

        sigmaField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        sigmaField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        sigmaField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        accelField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        accelField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        accelField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        accelField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accelFieldActionPerformed(evt);
            }
        });

        decelField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        decelField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        decelField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        speedField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        speedField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        speedField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lengthLabel.setFont(view.getFont());
        lengthLabel.setText(LENGHT);

        lengthField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        lengthField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        lengthField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(5, 5));

        editLabel.setIcon(EDIT_ICON);
        editLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editLabelMouseClicked(evt);
            }
        });

        removeLabel.setIcon(DELETE_ICON);
        removeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(removeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(editLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addComponent(speedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(speedField, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(accelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(decelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decelField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accelField, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sigmaLabel)
                    .addComponent(lengthLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sigmaField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lengthField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accelLabel)
                    .addComponent(sigmaLabel)
                    .addComponent(sigmaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(accelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(speedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(speedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(decelLabel)
                    .addComponent(decelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lengthLabel)
                    .addComponent(lengthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void iconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconMouseClicked
        // TODO add your handling code here:
        view.selectIcon();
    }//GEN-LAST:event_iconMouseClicked

    private void accelFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accelFieldActionPerformed
        // TODO add your handling code here:
        //
        //
        //hay que tratar errores
        //
        //
        accelField.setText(accelField.getText().replace(',', '.'));
        type.setAccel(Double.valueOf(accelField.getText()));
        view.editVType(name, type);
    }//GEN-LAST:event_accelFieldActionPerformed

    private void removeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeLabelMouseClicked
        // TODO add your handling code here:
        view.deleteVType(this);
    }//GEN-LAST:event_removeLabelMouseClicked

    private void editLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editLabelMouseClicked
        // TODO add your handling code here:
        JFrame edit = new FollowingModelFrame(view, name, type);
        edit.setVisible(true);
    }//GEN-LAST:event_editLabelMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField accelField;
    private javax.swing.JLabel accelLabel;
    private javax.swing.JFormattedTextField decelField;
    private javax.swing.JLabel decelLabel;
    private javax.swing.JLabel editLabel;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFormattedTextField lengthField;
    private javax.swing.JLabel lengthLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JLabel removeLabel;
    private javax.swing.JFormattedTextField sigmaField;
    private javax.swing.JLabel sigmaLabel;
    private javax.swing.JFormattedTextField speedField;
    private javax.swing.JLabel speedLabel;
    // End of variables declaration//GEN-END:variables
}
