package view;

import java.awt.Font;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;
import model.constants.Netconvert;
import model.constants.RoadTypes;

/**
 *
 * @author Neblis
 */
public class MapOptions extends javax.swing.JFrame {

    /**
     * Creates new form MapOptions
     */
    public static Font FONT;
    private final Font  TITLE_FONT;
    
    private static final String  TITLE = "Map options";
    
    private final static String LEFTHANDED = "Lefthanded";
    private final static String REMOVE_GEOMETRY = "Remove geometry";
    private final static String ROUNDABOUTS = "Guess roundabouts";
    private final static String STREET_NAMES = "Street names";
    private final static String OVERTAKE_LANES = "Overtake lanes";
    private final static String JOIN_JUNCTIONS = "Overtake lanes";
    private static final String ACCEPT = "Accept";
    
    private final static String VEHICLE_FILTERS = "Vehicle filters";
    private final static String ROAD_FILTERS = "Road filters";
    private final static String OPTIONS = "Options";
    
    private final HashSet<Netconvert> options;
    private final HashSet<String> roads;
    private final HashSet<String> filteredRoads;
    
    private final MogenView view;
    
    public MapOptions(MogenView view) {
        this.view = view;
        
        options = new HashSet<>();
        filteredRoads = new HashSet<>();
        roads = new HashSet<>();
        
        FONT = view.FONT;
        TITLE_FONT = new Font("Century Gothic", Font.BOLD, 16);
        
        initComponents();
        
        for (RoadTypes road : RoadTypes.values()){
            ((DefaultTableModel)roadsTable.getModel()).addRow(new String[] { 
                road.toString()
            });
            roads.add(road.getName());
        }
        
        roadsTable.setAutoCreateRowSorter(true);
        filteredRoadsTable.setAutoCreateRowSorter(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        tabPanel = new javax.swing.JTabbedPane();
        optionsTab = new javax.swing.JPanel();
        togglePanel = new javax.swing.JPanel();
        lefthandedLabel = new javax.swing.JLabel();
        geometryLabel = new javax.swing.JLabel();
        roundaboutsLabel = new javax.swing.JLabel();
        streetNamesLabel = new javax.swing.JLabel();
        overtakeLanesLabel = new javax.swing.JLabel();
        lefthandedBox = new javax.swing.JCheckBox();
        geometryBox = new javax.swing.JCheckBox();
        roundaboutBox = new javax.swing.JCheckBox();
        streetNamesBox = new javax.swing.JCheckBox();
        overtakeBox = new javax.swing.JCheckBox();
        junctionsLabel = new javax.swing.JLabel();
        junctionsBox = new javax.swing.JCheckBox();
        mapInfoLabel = new javax.swing.JLabel();
        roadFilterTab = new javax.swing.JPanel();
        roadsPane = new javax.swing.JScrollPane();
        roadsTable = new javax.swing.JTable();
        filterButton = new javax.swing.JButton();
        unfilterButton = new javax.swing.JButton();
        filteredRoadsPane = new javax.swing.JScrollPane();
        filteredRoadsTable = new javax.swing.JTable();
        road2FilterType = new javax.swing.JPanel();
        acceptLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();

        setTitle(TITLE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        tabPanel.setBackground(new java.awt.Color(255, 255, 255));
        tabPanel.setFont(FONT);

        optionsTab.setBackground(new java.awt.Color(255, 255, 255));

        togglePanel.setBackground(new java.awt.Color(255, 255, 255));

        lefthandedLabel.setFont(FONT);
        lefthandedLabel.setText(LEFTHANDED);

        geometryLabel.setFont(FONT);
        geometryLabel.setText(REMOVE_GEOMETRY);

        roundaboutsLabel.setFont(FONT);
        roundaboutsLabel.setText(ROUNDABOUTS);

        streetNamesLabel.setFont(FONT);
        streetNamesLabel.setText(STREET_NAMES);

        overtakeLanesLabel.setFont(FONT);
        overtakeLanesLabel.setText(OVERTAKE_LANES);

        lefthandedBox.setBackground(new java.awt.Color(255, 255, 255));

        geometryBox.setBackground(new java.awt.Color(255, 255, 255));

        roundaboutBox.setBackground(new java.awt.Color(255, 255, 255));

        streetNamesBox.setBackground(new java.awt.Color(255, 255, 255));

        overtakeBox.setBackground(new java.awt.Color(255, 255, 255));

        junctionsLabel.setFont(FONT);
        junctionsLabel.setText(JOIN_JUNCTIONS);

        junctionsBox.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout togglePanelLayout = new javax.swing.GroupLayout(togglePanel);
        togglePanel.setLayout(togglePanelLayout);
        togglePanelLayout.setHorizontalGroup(
            togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(togglePanelLayout.createSequentialGroup()
                .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lefthandedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(geometryLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundaboutsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(streetNamesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(overtakeLanesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(togglePanelLayout.createSequentialGroup()
                        .addComponent(lefthandedBox)
                        .addGap(45, 45, 45)
                        .addComponent(junctionsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(junctionsBox)
                        .addGap(57, 57, 57))
                    .addGroup(togglePanelLayout.createSequentialGroup()
                        .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(geometryBox)
                            .addComponent(roundaboutBox)
                            .addComponent(streetNamesBox)
                            .addComponent(overtakeBox))
                        .addGap(63, 63, 63))))
        );
        togglePanelLayout.setVerticalGroup(
            togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(togglePanelLayout.createSequentialGroup()
                .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(togglePanelLayout.createSequentialGroup()
                        .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(togglePanelLayout.createSequentialGroup()
                                .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(geometryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(junctionsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(togglePanelLayout.createSequentialGroup()
                                            .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(lefthandedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lefthandedBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(junctionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(geometryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(roundaboutsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(roundaboutBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(streetNamesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(streetNamesBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(overtakeLanesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(overtakeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 139, Short.MAX_VALUE))
        );

        mapInfoLabel.setFont(FONT);
        mapInfoLabel.setText("jLabel1");

        javax.swing.GroupLayout optionsTabLayout = new javax.swing.GroupLayout(optionsTab);
        optionsTab.setLayout(optionsTabLayout);
        optionsTabLayout.setHorizontalGroup(
            optionsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mapInfoLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                    .addComponent(togglePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        optionsTabLayout.setVerticalGroup(
            optionsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(togglePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(mapInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPanel.addTab(OPTIONS, optionsTab);

        roadFilterTab.setBackground(new java.awt.Color(255, 255, 255));

        roadsPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        roadsTable.setFont(FONT);
        roadsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Road type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        roadsTable.getTableHeader().setReorderingAllowed(false);
        roadsPane.setViewportView(roadsTable);

        filterButton.setText(">>");
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });

        unfilterButton.setText("<<");
        unfilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unfilterButtonActionPerformed(evt);
            }
        });

        filteredRoadsPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        filteredRoadsTable.setFont(FONT);
        filteredRoadsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Filtered roads"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        filteredRoadsTable.getTableHeader().setReorderingAllowed(false);
        filteredRoadsPane.setViewportView(filteredRoadsTable);

        javax.swing.GroupLayout roadFilterTabLayout = new javax.swing.GroupLayout(roadFilterTab);
        roadFilterTab.setLayout(roadFilterTabLayout);
        roadFilterTabLayout.setHorizontalGroup(
            roadFilterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roadFilterTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roadsPane, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roadFilterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unfilterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filteredRoadsPane, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
        );
        roadFilterTabLayout.setVerticalGroup(
            roadFilterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roadFilterTabLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(filterButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unfilterButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(roadFilterTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roadFilterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filteredRoadsPane)
                    .addComponent(roadsPane))
                .addContainerGap())
        );

        tabPanel.addTab(ROAD_FILTERS, roadFilterTab);

        road2FilterType.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout road2FilterTypeLayout = new javax.swing.GroupLayout(road2FilterType);
        road2FilterType.setLayout(road2FilterTypeLayout);
        road2FilterTypeLayout.setHorizontalGroup(
            road2FilterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
        );
        road2FilterTypeLayout.setVerticalGroup(
            road2FilterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 456, Short.MAX_VALUE)
        );

        tabPanel.addTab(ROAD_FILTERS, road2FilterType);

        acceptLabel.setBackground(new java.awt.Color(255, 255, 255));
        acceptLabel.setFont(FONT);
        acceptLabel.setText(ACCEPT);
        acceptLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acceptLabelMouseClicked(evt);
            }
        });

        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(new java.awt.Color(204, 0, 102));
        titleLabel.setText(TITLE);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabPanel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(acceptLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(acceptLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acceptLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acceptLabelMouseClicked
       
        String[] commands = new String[options.size()];
        commands = options.toArray(commands);
        view.newSimulation(commands);
        view.enableEvents(true);
        this.setVisible(false);
    }//GEN-LAST:event_acceptLabelMouseClicked

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterButtonActionPerformed
        // TODO add your handling code here:
        int row;
        while(!roadsTable.getSelectionModel().isSelectionEmpty()){
            row = roadsTable.getSelectedRow();
            String road = (String)roadsTable.getValueAt(row, 0);
            
            filteredRoads.add(road);
            roads.remove(road);
            
            ((DefaultTableModel)filteredRoadsTable.getModel()).addRow(new String[] { 
                road
            });
            ((DefaultTableModel)roadsTable.getModel()).removeRow(row);
        }
        roadsTable.clearSelection();
    }//GEN-LAST:event_filterButtonActionPerformed

    private void unfilterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unfilterButtonActionPerformed
        // TODO add your handling code here:
        for (int i : filteredRoadsTable.getSelectedRows()){
            String road = (String)filteredRoadsTable.getValueAt(i, 0);
            
            filteredRoads.remove(road);
            roads.add(road);
            ((DefaultTableModel)roadsTable.getModel()).addRow(new String[] { 
                road
            });
            
        }
        for (int i : filteredRoadsTable.getSelectedRows()){
            ((DefaultTableModel)filteredRoadsTable.getModel()).removeRow(filteredRoadsTable.getSelectedRow());
        }
        filteredRoadsTable.clearSelection();
    }//GEN-LAST:event_unfilterButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acceptLabel;
    private javax.swing.JButton filterButton;
    private javax.swing.JScrollPane filteredRoadsPane;
    private javax.swing.JTable filteredRoadsTable;
    private javax.swing.JCheckBox geometryBox;
    private javax.swing.JLabel geometryLabel;
    private javax.swing.JCheckBox junctionsBox;
    private javax.swing.JLabel junctionsLabel;
    private javax.swing.JCheckBox lefthandedBox;
    private javax.swing.JLabel lefthandedLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel mapInfoLabel;
    private javax.swing.JPanel optionsTab;
    private javax.swing.JCheckBox overtakeBox;
    private javax.swing.JLabel overtakeLanesLabel;
    private javax.swing.JPanel road2FilterType;
    private javax.swing.JPanel roadFilterTab;
    private javax.swing.JScrollPane roadsPane;
    private javax.swing.JTable roadsTable;
    private javax.swing.JCheckBox roundaboutBox;
    private javax.swing.JLabel roundaboutsLabel;
    private javax.swing.JCheckBox streetNamesBox;
    private javax.swing.JLabel streetNamesLabel;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel togglePanel;
    private javax.swing.JButton unfilterButton;
    // End of variables declaration//GEN-END:variables
    
    public void addOptions(HashSet<Netconvert> options){
        options.forEach((option) -> {
            checkBox(true, option);
        });
        this.options.addAll(options);
    }
    
    public void addOptions(Netconvert... options){
        for (Netconvert option : options) checkBox(true, option);
        this.options.addAll(Arrays.asList(options));
    }
    
    private void checkBox(boolean select, Netconvert option){
        switch(option){
            case GUESS_ROUNDABOUTS:
                roundaboutBox.setSelected(select);
                break;
                
            case REMOVE_GEOMETRY:
                geometryBox.setSelected(select);
                break;
                
            case LEFTHANDED:
                lefthandedBox.setSelected(select);
                break;
                
            case OVERTAKE_LANES:
                overtakeBox.setSelected(select);
                break;
                
            case JOIN_JUNCTIONS:
                junctionsBox.setSelected(select);
                break;
        }
    }
}

