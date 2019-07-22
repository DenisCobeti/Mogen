package view.mapsimulation;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javax.swing.GroupLayout;
import javax.swing.SwingUtilities;
import javax.xml.stream.XMLStreamException;
import model.mobility.FlowModel;
import model.routes.Flow;
import model.sumo.Lane;
import view.MogenView;

/**
 *
 * @author Neblis
 */
public class FlowFrame extends javax.swing.JFrame implements MapMouseEvent {

    /**
     * Creates new form FlowFrame
     */
    private final static String TITLE = "Add new vehicle Flow";
    private final static String ADD = "Add";
    
    private final static String DESTINATION = "Destination";
    private final static String ORIGIN = "Origin";
    private final static String BEGIN = "Begin (time)";
    private final static String END = "End (time)";
    private final static String TYPE = "Vehicle type";
    private final static String NUMBER = "Number";
    
    private final static String SELECTED_LANE_COLOR = "RED";
    private final static String SELECTED_LANE_COLOR2 = "BLUE";
    
    private final static String UNSELECTED_LANE_COLOR = "BLACK";
    
    private final static String INFO = "Left click: select origin lane \n"
            + "Right click: select destination lane";
    
    private Lane selectedLaneOrigin, selectedLaneDestination;
    private final MapPanel map;
    private final MogenView view;
    
    public FlowFrame(MogenView view, String map) throws FileNotFoundException, 
                                     XMLStreamException {
        this.view = view;
        this.map = new MapPanel(map, this);
        
        initComponents();
        this.setLocationRelativeTo(view);
        mapPanel.add(this.map);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        separator = new javax.swing.JSeparator();
        ODInfoPanel = new javax.swing.JPanel();
        originLabel = new javax.swing.JLabel();
        originInfoLabel = new javax.swing.JLabel();
        destinationLabel = new javax.swing.JLabel();
        destinationInfoLabel = new javax.swing.JLabel();
        optionsPanel = new javax.swing.JPanel();
        adddButton = new javax.swing.JButton();
        beginLabel = new javax.swing.JLabel();
        endLabel = new javax.swing.JLabel();
        numberLabel = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        infoTextArea = new javax.swing.JTextArea();
        mapScrollPane = new javax.swing.JScrollPane();
        mapPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(TITLE);
        setBackground(new java.awt.Color(255, 255, 255));

        ODInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        originLabel.setFont(view.getFont());
        originLabel.setText(ORIGIN);

        originInfoLabel.setFont(view.getFont());

        destinationLabel.setFont(view.getFont());
        destinationLabel.setText(DESTINATION);

        destinationInfoLabel.setFont(view.getFont());

        javax.swing.GroupLayout ODInfoPanelLayout = new javax.swing.GroupLayout(ODInfoPanel);
        ODInfoPanel.setLayout(ODInfoPanelLayout);
        ODInfoPanelLayout.setHorizontalGroup(
            ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ODInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(destinationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(originLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(originInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(destinationInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        ODInfoPanelLayout.setVerticalGroup(
            ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ODInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(originLabel)
                    .addComponent(originInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(destinationLabel)
                    .addComponent(destinationInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        optionsPanel.setBackground(new java.awt.Color(255, 255, 255));

        adddButton.setFont(view.getFont());
        adddButton.setText(ADD);
        adddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adddButtonActionPerformed(evt);
            }
        });

        beginLabel.setFont(view.getFont());
        beginLabel.setText(BEGIN);

        endLabel.setFont(view.getFont());
        endLabel.setText(END);

        numberLabel.setFont(view.getFont());
        numberLabel.setText(NUMBER);

        typeLabel.setFont(view.getFont());
        typeLabel.setText(TYPE);

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField1.setFont(view.getFont());

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField2.setFont(view.getFont());

        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField3.setFont(view.getFont());

        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField4.setFont(view.getFont());

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(adddButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(endLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(beginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFormattedTextField1)
                    .addComponent(beginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextField2)
                    .addComponent(endLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextField3)
                    .addComponent(numberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField4))
                .addGap(126, 126, 126)
                .addComponent(adddButton)
                .addContainerGap())
        );

        infoTextArea.setColumns(20);
        infoTextArea.setFont(view.getFont());
        infoTextArea.setRows(5);
        infoTextArea.setText(INFO);
        infoTextArea.setAlignmentX(1.0F);
        infoTextArea.setAlignmentY(1.0F);
        infoTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        mapPanel.setBackground(new java.awt.Color(255, 255, 255));
        mapPanel.setLayout(new java.awt.BorderLayout());
        mapScrollPane.setViewportView(mapPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mapScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(separator)
                    .addComponent(ODInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoTextArea))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mapScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ODInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(infoTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void adddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adddButtonActionPerformed
        // TODO add your handling code here:
        if (selectedLaneOrigin == null || selectedLaneDestination == null){
            
        }else{
            
        }
            
    }//GEN-LAST:event_adddButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ODInfoPanel;
    private javax.swing.JButton adddButton;
    private javax.swing.JLabel beginLabel;
    private javax.swing.JLabel destinationInfoLabel;
    private javax.swing.JLabel destinationLabel;
    private javax.swing.JLabel endLabel;
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JPanel mapPanel;
    private javax.swing.JScrollPane mapScrollPane;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JLabel originInfoLabel;
    private javax.swing.JLabel originLabel;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addFunctionToLanes(Lane lane, MouseEvent e) {
        MouseButton button = e.getButton();
        if (button == MouseButton.PRIMARY){
            if(this.selectedLaneOrigin != null){
                this.selectedLaneOrigin.getPolyline().setStroke(Paint.valueOf(UNSELECTED_LANE_COLOR));
            }
            
            lane.getPolyline().setStroke(Paint.valueOf(SELECTED_LANE_COLOR));
            this.selectedLaneOrigin = lane;
            originInfoLabel.setText(selectedLaneOrigin.getName());
            
        } else if (button == MouseButton.SECONDARY){
            if(this.selectedLaneDestination != null){
                this.selectedLaneDestination.getPolyline().setStroke(Paint.valueOf(UNSELECTED_LANE_COLOR));
            }
            
            lane.getPolyline().setStroke(Paint.valueOf(SELECTED_LANE_COLOR2));
            this.selectedLaneDestination = lane;
            destinationInfoLabel.setText(selectedLaneDestination.getName());
        }
        System.out.println(lane.toString()); 
    }
}
