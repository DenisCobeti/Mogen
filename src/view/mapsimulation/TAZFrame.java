package view.mapsimulation;

import java.util.HashSet;
import java.util.LinkedList;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import model.routes.TAZ;
import model.topology.Lane;
import view.MogenView;

/**
 *
 * @author darkm
 */
public class TAZFrame extends javax.swing.JFrame implements MapMouseEvent{

    private final static String TITLE = "Add a traffic assignment zone";
    private final static String INFO = "Assign roads to the ids on the OD Matrix file";
    
    private final static String ID = "ID";
    private final static String EDGES = "Roads";
    private final static String ADD = "Add";
    
    private final static String EMPTY_EDGES_ERROR = "No roads selected for TAZ";
    
    private final MogenView view;
    private final LinkedList<Lane> lanes;
    /**
     * Creates new form TAZFrame
     * @param view
     * @param map
     */
    public TAZFrame(MogenView view, MapPanel map) {
        this.view = view;
        this.lanes = new LinkedList();
        
        initComponents();
        
        this.setLocationRelativeTo(view);
        mapView.add(map);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionsPanel = new javax.swing.JPanel();
        idLabel = new javax.swing.JLabel();
        idField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        idLabel1 = new javax.swing.JLabel();
        infoTextArea = new javax.swing.JTextArea();
        infoTextArea1 = new javax.swing.JTextArea();
        addButton = new javax.swing.JButton();
        mapView = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(TITLE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        optionsPanel.setBackground(new java.awt.Color(255, 255, 255));

        idLabel.setFont(view.getFont());
        idLabel.setText(ID);

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(idLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idField)
                .addContainerGap())
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(idLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        idLabel1.setFont(view.getFont());
        idLabel1.setText(EDGES);

        infoTextArea.setColumns(20);
        infoTextArea.setFont(view.getFont());
        infoTextArea.setRows(5);
        infoTextArea.setText(INFO);
        infoTextArea.setAlignmentX(1.0F);
        infoTextArea.setAlignmentY(1.0F);
        infoTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(idLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(infoTextArea))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(idLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        infoTextArea1.setColumns(20);
        infoTextArea1.setFont(view.getFont());
        infoTextArea1.setRows(5);
        infoTextArea1.setText(INFO);
        infoTextArea1.setAlignmentX(1.0F);
        infoTextArea1.setAlignmentY(1.0F);
        infoTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        addButton.setFont(view.getFont());
        addButton.setText(ADD);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        mapView.setBackground(new java.awt.Color(255, 255, 255));
        mapView.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mapView, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(optionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(infoTextArea1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                    .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mapView, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infoTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addButton)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        if (lanes.isEmpty()){
            view.error(EMPTY_EDGES_ERROR);
        }else{
            view.addTAZ(idField.getText(), new TAZ(lanes));
            unselectLanes();
            this.dispose();
        }

    }//GEN-LAST:event_addButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        unselectLanes();
    }//GEN-LAST:event_formWindowClosing
    
    @Override
    public void unselectLanes(){
        lanes.forEach((edge)-> 
                    edge.getPolyline().setStroke(Paint.valueOf
                    (UNSELECTED_LANE_COLOR)));
    }
    /*
    public void updateSelection(double maxLon, double minLon, double maxLat, double minLat){
        double height = distance(maxLat,maxLon, minLat,maxLon);
        double width = distance(maxLat,maxLon, maxLat,minLon);
        
        selectionArea = height * width;
        areaLabel.setText(String.format(AREA_TEXT, currentArea));
    }*/
    
    @Override
    public void addFunctionToLanes(Lane lane,  MouseEvent e) {
        MouseButton button = e.getButton();
        if (button == MouseButton.PRIMARY){
            if(this.lanes.contains(lane)){
                lane.getPolyline().setStroke(Paint.valueOf(UNSELECTED_LANE_COLOR));
                lanes.remove(lane);
            }else{
                lane.getPolyline().setStroke(Paint.valueOf(SELECTED_LANE_COLOR));
                lanes.add(lane);
            }
        } 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel idLabel1;
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JTextArea infoTextArea1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel mapView;
    private javax.swing.JPanel optionsPanel;
    // End of variables declaration//GEN-END:variables

}
