package view;

import java.awt.Font;
import java.util.HashSet;

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
    
    private static final String  TITLE = "New simulation";
    
    private final static String NAME = "Name";
    private final static String LEFTHANDED = "Lefthanded";
    private final static String REMOVE_GEOMETRY = "Remove geometry";
    private final static String ROUNDABOUTS = "Guess roundabouts";
    private final static String STREET_NAMES = "Street names";
    private final static String OVERTAKE_LANES = "Overtake lanes";
    private static final String ACCEPT = "Accept";
    
    private final static String VEHICLE_FILTERS = "Vehicle filters";
    private final static String ROAD_FILTERS = "Road filters";
    private final static String OPTIONS = "Options";
    
    private HashSet<String> options;
    private MogenView view;
    
    public MapOptions(MogenView view) {
        this.view = view;
        options = new HashSet<>();
        FONT = view.FONT;
        TITLE_FONT = new Font("Century Gothic", Font.BOLD, 16);
        
        initComponents();
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
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
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
        lefthandedLabel1 = new javax.swing.JLabel();
        mapInfoLabel = new javax.swing.JLabel();
        vehicleFilterTab = new javax.swing.JPanel();
        roadFilterType = new javax.swing.JPanel();
        acceptLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(new java.awt.Color(204, 0, 102));
        titleLabel.setText(TITLE);

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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

        lefthandedLabel1.setFont(FONT);
        lefthandedLabel1.setText(LEFTHANDED);

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
                        .addComponent(lefthandedLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                    .addComponent(geometryBox)
                    .addComponent(roundaboutBox)
                    .addComponent(streetNamesBox)
                    .addComponent(overtakeBox))
                .addGap(63, 63, 63))
        );
        togglePanelLayout.setVerticalGroup(
            togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(togglePanelLayout.createSequentialGroup()
                .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(togglePanelLayout.createSequentialGroup()
                        .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(togglePanelLayout.createSequentialGroup()
                                .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(togglePanelLayout.createSequentialGroup()
                                        .addGroup(togglePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lefthandedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lefthandedBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lefthandedLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(geometryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(geometryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(mapInfoLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
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

        vehicleFilterTab.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout vehicleFilterTabLayout = new javax.swing.GroupLayout(vehicleFilterTab);
        vehicleFilterTab.setLayout(vehicleFilterTabLayout);
        vehicleFilterTabLayout.setHorizontalGroup(
            vehicleFilterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );
        vehicleFilterTabLayout.setVerticalGroup(
            vehicleFilterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );

        tabPanel.addTab(VEHICLE_FILTERS, vehicleFilterTab);

        roadFilterType.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout roadFilterTypeLayout = new javax.swing.GroupLayout(roadFilterType);
        roadFilterType.setLayout(roadFilterTypeLayout);
        roadFilterTypeLayout.setHorizontalGroup(
            roadFilterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );
        roadFilterTypeLayout.setVerticalGroup(
            roadFilterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );

        tabPanel.addTab(ROAD_FILTERS, roadFilterType);

        acceptLabel.setBackground(new java.awt.Color(255, 255, 255));
        acceptLabel.setFont(FONT);
        acceptLabel.setText(ACCEPT);
        acceptLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acceptLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabPanel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(titlePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(acceptLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPanel)
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
        this.dispose();
    }//GEN-LAST:event_acceptLabelMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acceptLabel;
    private javax.swing.JCheckBox geometryBox;
    private javax.swing.JLabel geometryLabel;
    private javax.swing.JCheckBox lefthandedBox;
    private javax.swing.JLabel lefthandedLabel;
    private javax.swing.JLabel lefthandedLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel mapInfoLabel;
    private javax.swing.JPanel optionsTab;
    private javax.swing.JCheckBox overtakeBox;
    private javax.swing.JLabel overtakeLanesLabel;
    private javax.swing.JPanel roadFilterType;
    private javax.swing.JCheckBox roundaboutBox;
    private javax.swing.JLabel roundaboutsLabel;
    private javax.swing.JCheckBox streetNamesBox;
    private javax.swing.JLabel streetNamesLabel;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JPanel togglePanel;
    private javax.swing.JPanel vehicleFilterTab;
    // End of variables declaration//GEN-END:variables
}
