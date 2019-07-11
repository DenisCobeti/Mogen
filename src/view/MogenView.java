package view;

import control.ViewListener;
import java.awt.Color;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UnsupportedLookAndFeelException;
import model.MogenModel.Mobility;

import model.map.MapSelection;
import model.Tuple;
import model.routes.VType;
import model.constants.Errors;
import model.mobility.MobilityModel;

import view.export.MobilityModelFrame;
import view.mapelements.DialogAddType;
import view.jxmapviewer2.MapViewer;
import view.simulation.TabElement;
import view.mapelements.VehicleTypePanel;

/**
 *
 * @author Neblis
 */

public class MogenView extends javax.swing.JFrame  implements ActionListener, Observer{
    private LoadingMap loading;
    
    public final static Font FONT = new Font("Century Gothic", Font.PLAIN, 12);
    public final static Font SMALL_FONT = FONT.deriveFont(10,0);
    
    private static MapViewer map;
    private final static DefaultComboBoxModel BOX_MODEL = 
                            new DefaultComboBoxModel<>(Mobility.values());
    private static int tab;
    
    private static final String MENU_ITEM_EXIT = "Exit";
    private static final String MENU_ITEM_EDIT = "Edit";
    private static final String MENU_ITEM_FILE = "Exit program";
    private static final String MENU_ITEM_NEW_MAP = "New map";
    private static final String MENU_ITEM_OPEN_MAP = "Open map";
    private static final String MENU_ITEM_EXPORT = "Export simulation";
    
    private static final String VEHICLE_TYPES = "Vehicle types";
    private static final String RSU = "RSU";
    private static final String DOWNTOWNS = "Downtowns";
    private static final String ADD_SIMULATION_TOOLTIP = "New simulation";
    
    public static final String ADD_SIMULATION = " +  ";
    private static final String WELCOME = "Welcome!";
    private static final String  TITLE = "New simulation";
    
    private static final String PROGRAM = "Mogen";
    private static final String DEFAULT_SIM_NAME = "New ";
    private static final String MAP_ERROR = "Map has to be imported first";
    private final Font  TITLE_FONT;
    
    private final static String VEHICLE_FILTERS = "Vehicle filters";
    private final static String ROAD_FILTERS = "Road filters";
    
    private final static String OPTIONS = "Options";
    
    ////////////////////
    private final static String NAME = "Name";
    private final static String LEFTHANDED = "Lefthanded";
    private final static String REMOVE_GEOMETRY = "Remove geometry";
    private final static String ROUNDABOUTS = "Guess roundabouts";
    private final static String STREET_NAMES = "Street names";
    private final static String OVERTAKE_LANES = "Overtake lanes";
    private static final String ACCEPT = "Accept";
    
    private final JLabel addVTypeButton;
    ImageIcon EDIT_ICON = new ImageIcon(EDIT_ICON_IMG);
    private final static String EDIT_ICON_IMG = "resources/button/editFollowingModel.png";
    //private final JLabel addDowntown;
    //private final JLabel addRSU;
    
    private final static String ADD_ICON_IMG = "resources/button/add.png";
    private final static String SEARCH_ICON_IMG = "resources/button/search.png";
    private static final String ICON_LOCATION = "resources/icon/icon.png";
    private static final String ICON_LOCATION_16 = "resources/icon/icon16.png";
    private static final String ICON_LOCATION_32 = "resources/icon/icon32.png";
    private static final String ICON_LOCATION_64 = "resources/icon/icon64.png";
    private static final String ICON_LOCATION_128 = "resources/icon/icon128.png";
    
    private static final String MAP_UNAVALIBLE = "No map currently opened. File "
                                                    + "-> Open map/New map";
    private static final String MAP_AVALIBLE = "Current map opened: ";
    
    private static final int DEFAULT_NUM_TABS = 1;
    private ImageIcon ADD_ICON = new ImageIcon(ADD_ICON_IMG);
    private ImageIcon SEARCH_ICON = new ImageIcon(SEARCH_ICON_IMG);
    
    private final ViewListener listenerUI;
    private boolean avalibleMap;
    
    private List<VehicleTypePanel> VehicleTypes;
    private HashSet<String> command;
    
    private String name;
    private String mapInfo;
    /**
     * Creates new form NewJFrame
     * @param listenerUI
     */
    
    public MogenView(ViewListener listenerUI) {
        this.listenerUI = listenerUI;
        VehicleTypes = new LinkedList();
        TITLE_FONT = new Font("Century Gothic", Font.BOLD, 16);
        
        Locale.setDefault(Locale.Category.FORMAT,new Locale("en", "UK"));
        
        List<Image> icons = new LinkedList();
        icons.add(new ImageIcon(ICON_LOCATION_16).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_32).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_64).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_128).getImage());
        
        command = new HashSet<>();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException |  
                InstantiationException | ClassNotFoundException ex) {}
        
        initComponents();
        
        //menuFileExit.addActionListener(this);
        tab = DEFAULT_NUM_TABS;
        panelMaps.setFont(FONT);
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
        avalibleMap = false;
        mapInfoLabel.setText(MAP_UNAVALIBLE);
        mapInfoLabel.setForeground(Color.red);
        
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

        panelMaps = new javax.swing.JTabbedPane();
        addTab = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        tabPanel = new javax.swing.JTabbedPane();
        optionsTab = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
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
        panelOptions = new javax.swing.JTabbedPane();
        vehicleTypesScroll = new javax.swing.JScrollPane();
        vehicleTypesPanel = new javax.swing.JPanel();
        RSUScroll = new javax.swing.JScrollPane();
        downtownScroll = new javax.swing.JScrollPane();
        simulationPanel = new javax.swing.JPanel();
        mobilityComboBox = new javax.swing.JComboBox<>();
        mapPanel = new javax.swing.JPanel();
        mapInfoField = new javax.swing.JTextField();
        optionsMapButton = new javax.swing.JLabel();
        searchMapButton = new javax.swing.JLabel();
        newMapButton = new javax.swing.JLabel();
        mapOptionsPanel = new javax.swing.JPanel();
        randomOptionsPanel = new javax.swing.JPanel();
        flowOptionsPanel = new javax.swing.JPanel();
        matrixOptionsPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileNew = new javax.swing.JMenuItem();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuEditExport = new javax.swing.JMenuItem();

        addTab.setBackground(new java.awt.Color(255, 255, 255));

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

        nameLabel.setFont(FONT);
        nameLabel.setText(NAME);

        nameField.setText(name);

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
                        .addGap(18, 18, 18)
                        .addComponent(lefthandedLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))
                    .addComponent(geometryBox)
                    .addComponent(roundaboutBox)
                    .addComponent(streetNamesBox)
                    .addComponent(overtakeBox))
                .addGap(90, 90, 90))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mapInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(togglePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, optionsTabLayout.createSequentialGroup()
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        optionsTabLayout.setVerticalGroup(
            optionsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(optionsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(togglePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mapInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );

        tabPanel.addTab(OPTIONS, optionsTab);

        vehicleFilterTab.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout vehicleFilterTabLayout = new javax.swing.GroupLayout(vehicleFilterTab);
        vehicleFilterTab.setLayout(vehicleFilterTabLayout);
        vehicleFilterTabLayout.setHorizontalGroup(
            vehicleFilterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
        );
        vehicleFilterTabLayout.setVerticalGroup(
            vehicleFilterTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 594, Short.MAX_VALUE)
        );

        tabPanel.addTab(VEHICLE_FILTERS, vehicleFilterTab);

        roadFilterType.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout roadFilterTypeLayout = new javax.swing.GroupLayout(roadFilterType);
        roadFilterType.setLayout(roadFilterTypeLayout);
        roadFilterTypeLayout.setHorizontalGroup(
            roadFilterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
        );
        roadFilterTypeLayout.setVerticalGroup(
            roadFilterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 594, Short.MAX_VALUE)
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

        javax.swing.GroupLayout addTabLayout = new javax.swing.GroupLayout(addTab);
        addTab.setLayout(addTabLayout);
        addTabLayout.setHorizontalGroup(
            addTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        addTabLayout.setVerticalGroup(
            addTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelMaps.addTab(WELCOME, addTab);

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
        mobilityComboBox.setEnabled(false);
        mobilityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilityComboBoxActionPerformed(evt);
            }
        });

        mapPanel.setBackground(new java.awt.Color(255, 255, 255));

        mapInfoField.setEditable(false);
        mapInfoField.setFont(FONT);
        mapInfoField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        optionsMapButton.setIcon(EDIT_ICON);
        optionsMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                optionsMapButtonMouseClicked(evt);
            }
        });

        searchMapButton.setIcon(SEARCH_ICON);
        searchMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchMapButtonMouseClicked(evt);
            }
        });

        newMapButton.setIcon(EDIT_ICON);
        newMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newMapButtonMouseClicked(evt);
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
                .addComponent(searchMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optionsMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mapPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(mapInfoField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(optionsMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        mapOptionsPanel.setBackground(new java.awt.Color(255, 255, 255));
        mapOptionsPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout randomOptionsPanelLayout = new javax.swing.GroupLayout(randomOptionsPanel);
        randomOptionsPanel.setLayout(randomOptionsPanelLayout);
        randomOptionsPanelLayout.setHorizontalGroup(
            randomOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        randomOptionsPanelLayout.setVerticalGroup(
            randomOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        mapOptionsPanel.add(randomOptionsPanel, "card2");

        javax.swing.GroupLayout flowOptionsPanelLayout = new javax.swing.GroupLayout(flowOptionsPanel);
        flowOptionsPanel.setLayout(flowOptionsPanelLayout);
        flowOptionsPanelLayout.setHorizontalGroup(
            flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        flowOptionsPanelLayout.setVerticalGroup(
            flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        mapOptionsPanel.add(flowOptionsPanel, "card3");

        javax.swing.GroupLayout matrixOptionsPanelLayout = new javax.swing.GroupLayout(matrixOptionsPanel);
        matrixOptionsPanel.setLayout(matrixOptionsPanelLayout);
        matrixOptionsPanelLayout.setHorizontalGroup(
            matrixOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        matrixOptionsPanelLayout.setVerticalGroup(
            matrixOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        mapOptionsPanel.add(matrixOptionsPanel, "card4");

        jButton1.setText("jButton1");

        errorLabel.setText("jLabel1");

        javax.swing.GroupLayout simulationPanelLayout = new javax.swing.GroupLayout(simulationPanel);
        simulationPanel.setLayout(simulationPanelLayout);
        simulationPanelLayout.setHorizontalGroup(
            simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simulationPanelLayout.createSequentialGroup()
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(simulationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mapOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(simulationPanelLayout.createSequentialGroup()
                                .addComponent(mobilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simulationPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1))))
                    .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        simulationPanelLayout.setVerticalGroup(
            simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simulationPanelLayout.createSequentialGroup()
                .addComponent(mapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mobilityComboBox)
                    .addComponent(errorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mapOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
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
                .addComponent(panelOptions, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
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
        map = new MapViewer(this);
    }//GEN-LAST:event_menuFileNewActionPerformed

    private void menuEditExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditExportActionPerformed

        if(panelMaps.getTabCount() < 2){
            JOptionPane.showMessageDialog(this,Errors.FIRST_SIM);
            return;
        }
        if(panelMaps.getSelectedIndex() < 1) {
            JOptionPane.showMessageDialog(this,Errors.NO_SELECTED_SIM);
            return;
        }
        System.out.println(((TabElement)panelMaps.getTabComponentAt
                                    (panelMaps.getSelectedIndex())).getName());
        JFrame export = new MobilityModelFrame(this, ((TabElement)panelMaps
                .getTabComponentAt(panelMaps.getSelectedIndex())).getName(),
                VehicleTypes);
        export.setVisible(true);
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

    private void acceptLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acceptLabelMouseClicked
        if (avalibleMap){
            name = nameField.getText();
            String[] commands = new String[command.size()];
            commands = command.toArray(commands);
            newSimulation(name, commands);
        }else{
            error(MAP_ERROR);
        }
    }//GEN-LAST:event_acceptLabelMouseClicked

    private void optionsMapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_optionsMapButtonMouseClicked
        // TODO add your handling code here:
        MapOptions options = new MapOptions(this);
        options.setLocationRelativeTo(this);
        
        options.setAlwaysOnTop(true);
        options.setVisible(true);
    }//GEN-LAST:event_optionsMapButtonMouseClicked

    private void newMapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newMapButtonMouseClicked
        // TODO add your handling code here:
        map = new MapViewer(this);
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
    }//GEN-LAST:event_mobilityComboBoxActionPerformed
   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case MENU_ITEM_EXIT:
                listenerUI.producedEvent(ViewListener.Event.SALIR, null);
            case MENU_ITEM_NEW_MAP:
                map = new MapViewer(this);
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
            
        } else if (arg instanceof String){
            updateSimulation((String)arg);
            
        } else if (arg instanceof Boolean){
            //mapInfoLabel.setText(MAP_AVALIBLE + mapInfo);
            mapInfoField.setText(mapInfo);
            mobilityComboBox.setEnabled(true);
            //mapInfoLabel.setForeground(Color.BLACK);
            avalibleMap = true;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane RSUScroll;
    private javax.swing.JLabel acceptLabel;
    private javax.swing.JPanel addTab;
    private javax.swing.JScrollPane downtownScroll;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JPanel flowOptionsPanel;
    private javax.swing.JCheckBox geometryBox;
    private javax.swing.JLabel geometryLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox lefthandedBox;
    private javax.swing.JLabel lefthandedLabel;
    private javax.swing.JLabel lefthandedLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField mapInfoField;
    private javax.swing.JLabel mapInfoLabel;
    private javax.swing.JPanel mapOptionsPanel;
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
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel newMapButton;
    private javax.swing.JLabel optionsMapButton;
    private javax.swing.JPanel optionsTab;
    private javax.swing.JCheckBox overtakeBox;
    private javax.swing.JLabel overtakeLanesLabel;
    private javax.swing.JTabbedPane panelMaps;
    private javax.swing.JTabbedPane panelOptions;
    private javax.swing.JPanel randomOptionsPanel;
    private javax.swing.JPanel roadFilterType;
    private javax.swing.JCheckBox roundaboutBox;
    private javax.swing.JLabel roundaboutsLabel;
    private javax.swing.JLabel searchMapButton;
    private javax.swing.JPanel simulationPanel;
    private javax.swing.JCheckBox streetNamesBox;
    private javax.swing.JLabel streetNamesLabel;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JPanel togglePanel;
    private javax.swing.JPanel vehicleFilterTab;
    private javax.swing.JPanel vehicleTypesPanel;
    private javax.swing.JScrollPane vehicleTypesScroll;
    // End of variables declaration//GEN-END:variables
    /*
    public void addSimulationDialog(){
        JFrame simulation = new AddSimulation(this, DEFAULT_SIM_NAME + tab);
        simulation.setVisible(true);
    }*/
    
    public void newSimulation(String name, String[] commands){
        listenerUI.producedEvent(ViewListener.Event.NEW_SIMULATION, 
                                                new Tuple<>(name, commands));
    }
    
    public void updateSimulation(String name){
        JPanel panel = new JPanel();
        JPanel tabTitle = new TabElement(this, tab, name, panel);
        
        panelMaps.add(panel);
        panelMaps.setTabComponentAt(tab, tabTitle);
        panelMaps.setSelectedIndex(tab);
        tab++;
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
            
            JPanel panel = new JPanel();
            JPanel tabTitle = new TabElement(this, tab, (String)tuple.obj1, panel);
            JScrollPane textScroll = new JScrollPane();
            JTextArea text = new JTextArea();
            
            text.setFont(FONT);
            //text.setPreferredSize(new Dimension(648, 705));
            BufferedReader in = new BufferedReader  
                    (new InputStreamReader((InputStream)tuple.obj2));  
            
            panelMaps.add(panel);
            panelMaps.setTabComponentAt(tab, tabTitle);
            panelMaps.setComponentAt(tab, textScroll);
            panelMaps.setSelectedIndex(tab);
            tab++;
            
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
    
    public void noAvalibleMap(){
        avalibleMap = false;
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

    public void closeSimulation(JPanel simulation) {
        panelMaps.remove(simulation);
        tab--;
    }

    
    
}
