package view;

import control.ViewListener;
import java.awt.CardLayout;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.stream.XMLStreamException;
import model.MogenModel.Mobility;

import model.map.MapSelection;
import model.Tuple;
import model.constants.FilesExtension;
import model.routes.VType;
import model.mobility.MobilityModel;

import view.mapelements.DialogAddType;
import view.jxmapviewer2.MapViewer;
import view.mapsimulation.FlowFrame;
import view.mapelements.VehicleTypePanel;

/**
 *
 * @author Neblis
 */

public class MogenView extends javax.swing.JFrame  implements ActionListener, Observer{
    
    private LoadingMap loading;
    
    public final static Font FONT = new Font("Century Gothic", Font.PLAIN, 12);
    public final static Font SMALL_FONT = FONT.deriveFont(10,0);
    
    private final static DefaultComboBoxModel BOX_MODEL = 
                            new DefaultComboBoxModel<>(Mobility.values());
    
    private static final String MENU_ITEM_EXIT = "Exit";
    private static final String MENU_ITEM_FILE = "Exit program";
    private static final String MENU_ITEM_NEW_MAP = "New map";
    private static final String MENU_ITEM_OPEN_MAP = "Open map";
    private static final String MENU_ITEM_EXPORT = "Export simulation";
    
    private static final String VEHICLE_TYPES = "Vehicle types";
    private static final String RSU = "RSU";
    private static final String DOWNTOWNS = "Downtowns";
    
    
    private static final String PROGRAM = "Mogen";
    private static final String MAP_ERROR = "Map has to be imported first";
    private static final String EXPORT = "Export";
    
    private final JLabel addVTypeButton;
    private boolean avalibleMap = false;
    private String currentMap;
    //private final JLabel addDowntown;
    //private final JLabel addRSU;
    
    private final static String RANDOM_TIME = "Time: ";
    private final static String RANDOM_TIME_DFLT = "60";
    private final static String RANDOM_REPETITION = "Repetition rate: ";
    private final static String RANDOM_REPETITION_DFLT = "3";
    
    private final static String FLOW_ADD = "Add vehicle flow";
    
    private final static String ADD_ICON_IMG = "resources/button/add.png";
    private final static String SEARCH_ICON_IMG = "resources/button/search.png";
    private final static String MAP_ICON_IMG = "resources/button/map.png";
    private final static String SETTINGS_ICON_IMG = "resources/button/settings.png";
    private final static String EDIT_ICON_IMG = "resources/button/editFollowingModel.png";
    private static final String ICON_LOCATION_16 = "resources/icon/icon16.png";
    private static final String ICON_LOCATION_32 = "resources/icon/icon32.png";
    private static final String ICON_LOCATION_64 = "resources/icon/icon64.png";
    private static final String ICON_LOCATION_128 = "resources/icon/icon128.png";
    
    private static final String MAP_UNAVALIBLE = "No map currently opened";
    
    private ImageIcon ADD_ICON = new ImageIcon(ADD_ICON_IMG);
    private ImageIcon SEARCH_ICON = new ImageIcon(SEARCH_ICON_IMG);
    private ImageIcon MAP_ICON = new ImageIcon(MAP_ICON_IMG);
    private ImageIcon SETTINGS_ICON = new ImageIcon(SETTINGS_ICON_IMG);
    private ImageIcon EDIT_ICON = new ImageIcon(EDIT_ICON_IMG);
    
    private final ViewListener listenerUI;
    
    private List<VehicleTypePanel> VehicleTypes;
    private HashSet<String> options;
    
    private String mapInfo;
    /**
     * Creates new form NewJFrame
     * @param listenerUI
     */
    
    public MogenView(ViewListener listenerUI) {
        this.listenerUI = listenerUI;
        VehicleTypes = new LinkedList();
        //TITLE_FONT = new Font("Century Gothic", Font.BOLD, 16);
        
        Locale.setDefault(Locale.Category.FORMAT,new Locale("en", "UK"));
        
        List<Image> icons = new LinkedList();
        icons.add(new ImageIcon(ICON_LOCATION_16).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_32).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_64).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_128).getImage());
        
        options = new HashSet<>();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException |  
                InstantiationException | ClassNotFoundException ex) {}
        
        initComponents();
        
        //menuFileExit.addActionListener(this);
        panelOptions.setFont(FONT);
        
        vehicleTypesPanel.setLayout(new BoxLayout(vehicleTypesPanel, BoxLayout.Y_AXIS));
        
        addVTypeButton = new JLabel();
        addVTypeButton.setIcon(ADD_ICON);
        addVTypeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addVehicleType(evt);
            }
        });
        vehicleTypesPanel.add(addVTypeButton);
        this.setTitle(PROGRAM);
        this.setIconImages(icons);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelOptions = new javax.swing.JTabbedPane();
        vehicleTypesScroll = new javax.swing.JScrollPane();
        vehicleTypesPanel = new javax.swing.JPanel();
        RSUScroll = new javax.swing.JScrollPane();
        downtownScroll = new javax.swing.JScrollPane();
        simulationPanel = new javax.swing.JPanel();
        mobilityComboBox = new javax.swing.JComboBox<>();
        mapPanel = new javax.swing.JPanel();
        mapInfoField = new javax.swing.JTextField();
        searchMapButton = new javax.swing.JLabel();
        newMapButton = new javax.swing.JLabel();
        optionsMapButton = new javax.swing.JLabel();
        mobilityOptionsPanel = new javax.swing.JPanel();
        randomOptionsPanel = new javax.swing.JPanel();
        randomPanel = new javax.swing.JPanel();
        repetitionLabel = new javax.swing.JLabel();
        repetitionField = new javax.swing.JFormattedTextField();
        timeLabel = new javax.swing.JLabel();
        timeField = new javax.swing.JFormattedTextField();
        flowOptionsPanel = new javax.swing.JPanel();
        flowScrollPane = new javax.swing.JScrollPane();
        flowTable = new javax.swing.JTable();
        addFlowButton = new javax.swing.JButton();
        matrixOptionsPanel = new javax.swing.JPanel();
        exportButton = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileNew = new javax.swing.JMenuItem();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuEditExport = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        vehicleTypesScroll.setBorder(null);
        vehicleTypesScroll.setHorizontalScrollBar(null);
        vehicleTypesScroll.setMaximumSize(new java.awt.Dimension(468, 215));
        vehicleTypesScroll.setPreferredSize(new java.awt.Dimension(220, 215));

        vehicleTypesPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout vehicleTypesPanelLayout = new javax.swing.GroupLayout(vehicleTypesPanel);
        vehicleTypesPanel.setLayout(vehicleTypesPanelLayout);
        vehicleTypesPanelLayout.setHorizontalGroup(
            vehicleTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 821, Short.MAX_VALUE)
        );
        vehicleTypesPanelLayout.setVerticalGroup(
            vehicleTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );

        vehicleTypesScroll.setViewportView(vehicleTypesPanel);

        panelOptions.addTab(VEHICLE_TYPES, vehicleTypesScroll);
        panelOptions.addTab(RSU, RSUScroll);
        panelOptions.addTab(DOWNTOWNS, downtownScroll);

        simulationPanel.setBackground(new java.awt.Color(255, 255, 255));

        mobilityComboBox.setFont(FONT);
        mobilityComboBox.setModel(BOX_MODEL);
        mobilityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilityComboBoxActionPerformed(evt);
            }
        });

        mapPanel.setBackground(new java.awt.Color(255, 255, 255));

        mapInfoField.setEditable(false);
        mapInfoField.setFont(FONT);
        mapInfoField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        searchMapButton.setIcon(SEARCH_ICON);
        searchMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchMapButtonMouseClicked(evt);
            }
        });

        newMapButton.setIcon(MAP_ICON);
        newMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newMapButtonMouseClicked(evt);
            }
        });

        optionsMapButton.setIcon(SETTINGS_ICON);
        optionsMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                optionsMapButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mapPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mapInfoField, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(newMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optionsMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mapPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(newMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(optionsMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mapInfoField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mobilityOptionsPanel.setBackground(new java.awt.Color(255, 255, 255));
        mobilityOptionsPanel.setLayout(new java.awt.CardLayout());

        randomOptionsPanel.setBackground(new java.awt.Color(255, 255, 255));

        randomPanel.setBackground(new java.awt.Color(255, 255, 255));

        repetitionLabel.setFont(FONT);
        repetitionLabel.setText(RANDOM_REPETITION);

        repetitionField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        repetitionField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        repetitionField.setText(RANDOM_REPETITION_DFLT);
        repetitionField.setFont(FONT);

        timeLabel.setFont(FONT);
        timeLabel.setText(RANDOM_TIME);

        timeField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        timeField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        timeField.setText(RANDOM_TIME_DFLT);
        timeField.setFont(FONT);

        javax.swing.GroupLayout randomPanelLayout = new javax.swing.GroupLayout(randomPanel);
        randomPanel.setLayout(randomPanelLayout);
        randomPanelLayout.setHorizontalGroup(
            randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(repetitionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(randomPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(repetitionField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(randomPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        randomPanelLayout.setVerticalGroup(
            randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(repetitionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(repetitionField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout randomOptionsPanelLayout = new javax.swing.GroupLayout(randomOptionsPanel);
        randomOptionsPanel.setLayout(randomOptionsPanelLayout);
        randomOptionsPanelLayout.setHorizontalGroup(
            randomOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(randomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        randomOptionsPanelLayout.setVerticalGroup(
            randomOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(randomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(297, Short.MAX_VALUE))
        );

        mobilityOptionsPanel.add(randomOptionsPanel, "random");

        flowOptionsPanel.setBackground(new java.awt.Color(255, 255, 255));

        flowScrollPane.setBorder(null);
        flowScrollPane.setPreferredSize(new java.awt.Dimension(200, 200));

        flowTable.setFont(FONT);
        flowTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Origin", "Destination", "Begin", "End", "Number", "VType"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        flowScrollPane.setViewportView(flowTable);

        addFlowButton.setFont(FONT);
        addFlowButton.setText(FLOW_ADD);
        addFlowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFlowButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout flowOptionsPanelLayout = new javax.swing.GroupLayout(flowOptionsPanel);
        flowOptionsPanel.setLayout(flowOptionsPanelLayout);
        flowOptionsPanelLayout.setHorizontalGroup(
            flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(flowOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(flowScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addGroup(flowOptionsPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addFlowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        flowOptionsPanelLayout.setVerticalGroup(
            flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(flowOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(flowScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addFlowButton)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        mobilityOptionsPanel.add(flowOptionsPanel, "flow");

        javax.swing.GroupLayout matrixOptionsPanelLayout = new javax.swing.GroupLayout(matrixOptionsPanel);
        matrixOptionsPanel.setLayout(matrixOptionsPanelLayout);
        matrixOptionsPanelLayout.setHorizontalGroup(
            matrixOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );
        matrixOptionsPanelLayout.setVerticalGroup(
            matrixOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );

        mobilityOptionsPanel.add(matrixOptionsPanel, "matrix");

        exportButton.setFont(FONT);
        exportButton.setText(EXPORT);
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        errorLabel.setFont(FONT);
        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText(MAP_UNAVALIBLE);

        javax.swing.GroupLayout simulationPanelLayout = new javax.swing.GroupLayout(simulationPanel);
        simulationPanel.setLayout(simulationPanelLayout);
        simulationPanelLayout.setHorizontalGroup(
            simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simulationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mobilityOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(simulationPanelLayout.createSequentialGroup()
                        .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(simulationPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(mobilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simulationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exportButton)
                .addContainerGap())
        );
        simulationPanelLayout.setVerticalGroup(
            simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simulationPanelLayout.createSequentialGroup()
                .addComponent(mapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mobilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(errorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mobilityOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportButton)
                .addContainerGap())
        );

        menuBar.setFont(FONT);

        menuFile.setText("File");
        menuFile.setFont(FONT);

        menuFileNew.setText(MENU_ITEM_NEW_MAP);
        menuFileNew.setFont(FONT);
        menuFileNew.setActionCommand(MENU_ITEM_NEW_MAP);
        menuFileNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileNewActionPerformed(evt);
            }
        });
        menuFile.add(menuFileNew);

        menuFileOpen.setFont(FONT);
        menuFileOpen.setText(MENU_ITEM_OPEN_MAP);
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileOpenActionPerformed(evt);
            }
        });
        menuFile.add(menuFileOpen);

        menuFileExit.setFont(FONT);
        menuFileExit.setText(MENU_ITEM_EXIT);
        menuFileExit.setToolTipText("");
        menuFileExit.setActionCommand(MENU_ITEM_EXIT);
        menuFileExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuFileExitMousePressed(evt);
            }
        });
        menuFile.add(menuFileExit);

        menuBar.add(menuFile);

        menuEdit.setText("Edit");
        menuEdit.setFont(FONT);

        menuEditExport.setText(MENU_ITEM_EXPORT);
        menuEditExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditExportActionPerformed(evt);
            }
        });
        menuEdit.add(menuEditExport);

        menuBar.add(menuEdit);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(simulationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(simulationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOptions))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileExitMousePressed
        listenerUI.producedEvent(ViewListener.Event.SALIR, null);
    }//GEN-LAST:event_menuFileExitMousePressed

    private void menuFileNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewActionPerformed
        MapViewer map = new MapViewer(this);
    }//GEN-LAST:event_menuFileNewActionPerformed

    private void menuEditExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditExportActionPerformed
        switch((Mobility)mobilityComboBox.getSelectedItem()){
            case Random:
                System.out.println("random");
                break;
            case Flow:
                System.out.println("flow");
                break;
            case ODMatrix:
                System.out.println("atrix");
                break;
            default:
                System.out.println("ooooooooooooooooooooooooooooooo");
                break;
        }
        
    }//GEN-LAST:event_menuEditExportActionPerformed

    private void menuFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileOpenActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter
                                      (null, "osm");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            mapInfo = chooser.getSelectedFile().getAbsolutePath();
            listenerUI.producedEvent(ViewListener.Event.OPEN_MAP, 
                        chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_menuFileOpenActionPerformed

    private void optionsMapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_optionsMapButtonMouseClicked
        // TODO add your handling code here:
        MapOptions options = new MapOptions(this);
        options.setLocationRelativeTo(this);
        
        options.setAlwaysOnTop(true);
        options.setVisible(true);
    }//GEN-LAST:event_optionsMapButtonMouseClicked

    private void newMapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newMapButtonMouseClicked
        // TODO add your handling code here:
        MapViewer map = new MapViewer(this);
    }//GEN-LAST:event_newMapButtonMouseClicked

    private void searchMapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMapButtonMouseClicked

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter
                                      (null, "osm");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            mapInfo = chooser.getSelectedFile().getAbsolutePath();
            listenerUI.producedEvent(ViewListener.Event.OPEN_MAP, 
                        chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_searchMapButtonMouseClicked

    private void mobilityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilityComboBoxActionPerformed
        // TODO add your handling code here:
        CardLayout layout = (CardLayout)mobilityOptionsPanel.getLayout();
        
        switch((Mobility)mobilityComboBox.getSelectedItem()){
            case Random:
                layout.show(mobilityOptionsPanel, "random");
                break;
            case Flow:
                layout.show(mobilityOptionsPanel, "flow");
                break;
            case ODMatrix:
                layout.show(mobilityOptionsPanel, "matrix");
                break;
            default:
                layout.show(mobilityOptionsPanel, "random");
                break;
        }
    }//GEN-LAST:event_mobilityComboBoxActionPerformed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        // TODO add your handling code here:
        export();
    }//GEN-LAST:event_exportButtonActionPerformed

    private void addFlowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFlowButtonActionPerformed
        // TODO add your handling code here:
        FlowFrame flowFrame;
        try {
            flowFrame = new FlowFrame(this, currentMap);
            flowFrame.setVisible(true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MogenView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(MogenView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_addFlowButtonActionPerformed
   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case MENU_ITEM_EXIT:
                listenerUI.producedEvent(ViewListener.Event.SALIR, null);
            case MENU_ITEM_NEW_MAP:
                MapViewer map = new MapViewer(this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == null){
            doneLoading();
            
        } else if (arg instanceof Tuple){
            updateLists((Tuple)arg);
            
        } else if (arg instanceof Exception){
            error(((Exception) arg).getMessage());
            
        } else if (arg instanceof InputStream){
            updateSimulation((InputStream)arg);
            
        } else if (arg instanceof String){
            currentMap = (String)arg + FilesExtension.NETCONVERT;
            avalibleMap();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane RSUScroll;
    private javax.swing.JButton addFlowButton;
    private javax.swing.JScrollPane downtownScroll;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton exportButton;
    private javax.swing.JPanel flowOptionsPanel;
    private javax.swing.JScrollPane flowScrollPane;
    private javax.swing.JTable flowTable;
    private javax.swing.JTextField mapInfoField;
    private javax.swing.JPanel mapPanel;
    private javax.swing.JPanel matrixOptionsPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuEditExport;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenuItem menuFileNew;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JComboBox<String> mobilityComboBox;
    private javax.swing.JPanel mobilityOptionsPanel;
    private javax.swing.JLabel newMapButton;
    private javax.swing.JLabel optionsMapButton;
    private javax.swing.JTabbedPane panelOptions;
    private javax.swing.JPanel randomOptionsPanel;
    private javax.swing.JPanel randomPanel;
    private javax.swing.JFormattedTextField repetitionField;
    private javax.swing.JLabel repetitionLabel;
    private javax.swing.JLabel searchMapButton;
    private javax.swing.JPanel simulationPanel;
    private javax.swing.JFormattedTextField timeField;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JPanel vehicleTypesPanel;
    private javax.swing.JScrollPane vehicleTypesScroll;
    // End of variables declaration//GEN-END:variables
    /*
    public void addSimulationDialog(){
        JFrame simulation = new AddSimulation(this, DEFAULT_SIM_NAME + tab);
        simulation.setVisible(true);
    }*/
    private void export(){
        if(avalibleMap){
            switch((Mobility)mobilityComboBox.getSelectedItem()){
                case Random:
                    System.out.println("random");
                    break;
                case Flow:
                    System.out.println("flow");
                    break;
                case ODMatrix:
                    System.out.println("atrix");
                    break;
                default:
                    System.out.println("ooooooooooooooooooooooooooooooo");
                    break;
            }
        } else{
            error(MAP_UNAVALIBLE);
        }
    }
    private void avalibleMap(){
        //mapInfoLabel.setText(MAP_AVALIBLE + mapInfo);
        mapInfoField.setText(mapInfo);
        avalibleMap = true;
        errorLabel.setText("");
        //mapInfoLabel.setForeground(Color.BLACK);
    }
    public void newSimulation(String name, String[] commands){
        listenerUI.producedEvent(ViewListener.Event.NEW_SIMULATION, 
                                                new Tuple<>(name, commands));
    }
    public void newSimulation(String[] commands){
        listenerUI.producedEvent(ViewListener.Event.NEW_SIMULATION_CMD, commands);
    }
    
    public void updateSimulation(InputStream stream){
        // Donde mostrar los warnings
    }
    public void exportSimulation(MobilityModel model, String name){
        Tuple tuple = new Tuple(model, name);
        listenerUI.producedEvent(ViewListener.Event.EXPORT, tuple);
    }
    
    public void enableEvents(boolean events){
        this.setEnabled(events);
    }
    
    private void updateLists(Tuple tuple){
        if(tuple.obj2 instanceof VType){
            
            VehicleTypePanel vType = new VehicleTypePanel((String)tuple.obj1, 
                                                    (VType)tuple.obj2, this);
            VehicleTypes.add(vType);
            
            vehicleTypesPanel.add(vType);
            vehicleTypesPanel.updateUI();
            
        } else if (tuple.obj2 instanceof InputStream){
            
            JScrollPane textScroll = new JScrollPane();
            JTextArea text = new JTextArea();
            
            text.setFont(FONT);
            //text.setPreferredSize(new Dimension(648, 705));
            BufferedReader in = new BufferedReader  
                    (new InputStreamReader((InputStream)tuple.obj2));  
            
            
            String line;
            try {
                while ((line = in.readLine()) != null) {
                   System.out.println(line);
                    // You should set JtextArea
                    text.append(line + "\n");
                }
                in.close();
            } catch (IOException e) { // exception thrown
                System.err.println("Command failed!");
            }
            textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            textScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            textScroll.setBorder(null);
            textScroll.setViewportView(text);
        }
    }
    static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
    
    public void error(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
    public void importMap(JFrame map, MapSelection selection) {
        map.dispose();
        
        loading = new LoadingMap();
        loading.setEnabled(true);
        loading.setLocationRelativeTo(this);
        loading.setVisible(true);
        
        mapInfo = selection.maxLat + ", " + selection.minLat + "; " 
                + selection.maxLon + ", " + selection.minLon;
        listenerUI.producedEvent(ViewListener.Event.NEW_MAP, selection);
    }
    
    public void addVehicleType(java.awt.event.MouseEvent evt){
        DialogAddType addDialog = new DialogAddType(this);
    }
    
    public void addVehicleType(String id){
        listenerUI.producedEvent(ViewListener.Event.NEW_VEHICLE_TYPE, id);
    }
    
    public void selectIcon(){
        System.out.println("boom");
    }
    
    public void editVType(String name, VType type){
        listenerUI.producedEvent(ViewListener.Event.EDIT_VTYPE, new Tuple(name, type));
    }
    
    public void deleteVType(VehicleTypePanel type){
        vehicleTypesPanel.remove(type);
        VehicleTypes.remove(type);
        vehicleTypesPanel.updateUI();
    }
    
    public void doneLoading(){
        loading.setVisible(false);
        loading = null;
    }
 
}
