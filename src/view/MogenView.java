package view;

import control.ViewListener;
import control.ViewListener.Event;
import control.ViewListener.TableTypes;
import java.awt.CardLayout;
import java.awt.Cursor;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.input.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.xml.stream.XMLStreamException;

import model.map.MapSelection;
import model.Tuple;
import model.constants.FilesExtension;
import model.constants.Netconvert;
import model.constants.RoadTypes;
import model.routes.VType;
import model.mobility.MobilityModel;
import model.mobility.RandomModel;
import model.routes.Flow;
import model.routes.ODElement;
import model.routes.TAZ;
import model.Config;
import model.MogenModel.Mobility;
import model.Progress;
import model.warnings.Warning;

import view.jxmapviewer2.MapSelect;
import view.mapelements.DialogAddType;
import view.mapelements.VTypeIconSelectFrame;
import view.mapsimulation.FlowFrame;
import view.mapsimulation.TAZFrame;
import view.mapelements.VehicleTypePanel;
import view.mapsimulation.MapPanel;
import view.mapsimulation.ODAddFrame;

/**
 *
 * @author Neblis
 */

public class MogenView extends javax.swing.JFrame  implements ActionListener, Observer{
    
    private final MapOptions options;
    private MapPanel mapVisual;
    private ProgressFrame progressFrame;
    
    public final static Font FONT = new Font("Century Gothic", Font.PLAIN, 12);
    public final static Font SMALL_FONT = FONT.deriveFont(10,0);
    
    private final static DefaultComboBoxModel BOX_MODEL = 
                            new DefaultComboBoxModel<>(Mobility.values());
    
    private static final String MENU_ITEM_EXIT = "Exit";
    private static final String MENU_ITEM_NEW_MAP = "New map";
    private static final String MENU_ITEM_OPEN_MAP = "Open map";
    private static final String MENU_ITEM_EXPORT = "Export simulation";
    private static final String MENU_ITEM_SETTINGS = "Settings";
    
    private static final String VEHICLE_TYPES = "Vehicle types";
    private static final String RSU = "RSU";
    private static final String DOWNTOWNS = "Downtowns";
    
    private static final String PROGRAM = "Mogen";
    private static final String MAP_ERROR = "Map has to be imported first";
    private static final String NO_MAP = "No map currently selected";
    private static final String NO_SELECTION_TABLE = "No rows selected";
    private static final String EDIT_ELEMENT = "Only one element must be selected";
    private static final String EXPORT = "Export";
    
    private static final String CONTEXTUAL_MENU_DELETE = "Delete";
    private static final String CONTEXTUAL_MENU_EDIT = "Edit";
    
    private static FlowFrame flowFrame;
    
    
    private static final String INFO_SELECTION = "Latitute: %.2f , %.2f ; "
                                                + "Longitude: %.2f , %.2f";
    
    //private final JLabel addVTypeButton;
    private boolean avalibleMap = false;
    private String currentMap;
    //private final JLabel addDowntown;
    //private final JLabel addRSU;
    
    private final static String TIME = "Time(s): ";
    private final static String RANDOM_TIME_DFLT = "60";
    private final static String RANDOM_REPETITION = "Number of vehicles: ";
    private final static String RANDOM_REPETITION_DFLT = "3";
    private final static String RANDOM_FILES = "Files: ";
    private final static String RANDOM_FILES_DFLT = "1";
    private final static String OD_TIME_DFLT = "3600";
    
    private final static String FLOW_ADD = "Add vehicle flow";
    
    private final static String OD_TAZ_ADD = "Add district";
    private final static String OD_OPT_ADD = "Add";
    private final static String OD_TAZ = "Districts";
    private final static String OD_IMPORT = "Import";
    private final static String OD = "Origin Destination matrix";
    
    private final static String ADD_ICON_IMG = "resources/button/add.png";
    private final static String SEARCH_ICON_IMG = "resources/button/search.png";
    private final static String MAP_ICON_IMG = "resources/button/map.png";
    private final static String SETTINGS_ICON_IMG = "resources/button/settings.png";
    private final static String EXPORT_ICON_IMG = "resources/button/export.png";
    private final static String EDIT_ICON_IMG = "resources/button/editFollowingModel.png";
    
    private static final String ICON_LOCATION_16 = "resources/icon/icon16.png";
    private static final String ICON_LOCATION_32 = "resources/icon/icon32.png";
    private static final String ICON_LOCATION_64 = "resources/icon/icon64.png";
    private static final String ICON_LOCATION_128 = "resources/icon/icon128.png";
    
    private static final String MAP_UNAVALIBLE = "No map currently opened";
    private static final String INSUFFICIENT_TAZ = "At least two districts have "
                                            + "to be avalible to add an element";
    
    public ImageIcon ADD_ICON = new ImageIcon(ADD_ICON_IMG);
    protected ImageIcon SEARCH_ICON = new ImageIcon(SEARCH_ICON_IMG);
    private ImageIcon MAP_ICON = new ImageIcon(MAP_ICON_IMG);
    private ImageIcon SETTINGS_ICON = new ImageIcon(SETTINGS_ICON_IMG);
    private ImageIcon EXPORT_ICON = new ImageIcon(EXPORT_ICON_IMG);
    private ImageIcon EDIT_ICON = new ImageIcon(EDIT_ICON_IMG);
    
    private final ViewListener listenerUI;
    
    private List<VehicleTypePanel> vehicleTypes;
    private String mapInfo;
    /**
     * Creates new form NewJFrame
     * @param listenerUI
     */
    
    public MogenView(ViewListener listenerUI) {
        this.listenerUI = listenerUI;
        
        vehicleTypes = new LinkedList();
        
        //TITLE_FONT = new Font("Century Gothic", Font.BOLD, 16);
        
        Locale.setDefault(Locale.Category.FORMAT, new Locale("en", "UK"));
        
        List<Image> icons = new LinkedList();
        icons.add(new ImageIcon(ICON_LOCATION_16).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_32).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_64).getImage());
        icons.add(new ImageIcon(ICON_LOCATION_128).getImage());
        
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException |  
                InstantiationException | ClassNotFoundException ex) {
        }
        
        initComponents();
        
        // Custom cursor appear when the user moves the mouse over all the buttons
        searchMapButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newMapButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        optionsMapButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exportButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addVTypeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        //menuFileExit.addActionListener(this);
        options = new MapOptions(this);
        vehicleTypesPanel.setLayout(new BoxLayout(vehicleTypesPanel, BoxLayout.Y_AXIS));
        
        //Contextual menu to all the tables
        
        JPopupMenu popupMenuTAZ = new JPopupMenu();
        JPopupMenu popupMenuFlow = new JPopupMenu();
        
        JMenuItem deleteTAZ = new JMenuItem(CONTEXTUAL_MENU_DELETE);
        JMenuItem deleteFlow = new JMenuItem(CONTEXTUAL_MENU_DELETE);
        JMenuItem editFlow = new JMenuItem(CONTEXTUAL_MENU_EDIT);
        
        deleteTAZ.addActionListener((ActionEvent e) -> {
            deleteElement(TableTypes.TAZType);
        });
        
        deleteFlow.addActionListener((ActionEvent e) -> {
            deleteElement(TableTypes.FlowType);
        });
        
        editFlow.addActionListener((ActionEvent e) -> {
            editElement(TableTypes.FlowType);
        });
        popupMenuTAZ.add(deleteTAZ);
        popupMenuFlow.add(deleteFlow);
        popupMenuFlow.add(editFlow);
        
        TAZTable.setComponentPopupMenu(popupMenuTAZ);
        flowTable.setComponentPopupMenu(popupMenuFlow);
      
        TableColumnModel tcm = flowTable.getColumnModel();
        tcm.removeColumn(tcm.getColumn(0));
        
        TableColumnModel tcm2 = ODMTable.getColumnModel();
        tcm2.removeColumn(tcm2.getColumn(0));
        
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

        simulationPanel = new javax.swing.JPanel();
        mobilityComboBox = new javax.swing.JComboBox<>();
        mapPanel = new javax.swing.JPanel();
        mapInfoField = new javax.swing.JTextField();
        searchMapButton = new javax.swing.JLabel();
        newMapButton = new javax.swing.JLabel();
        mobilityOptionsPanel = new javax.swing.JPanel();
        randomOptionsPanel = new javax.swing.JPanel();
        randomPanel = new javax.swing.JPanel();
        vehiclesLabel = new javax.swing.JLabel();
        vehiclesField = new javax.swing.JFormattedTextField();
        timeLabel = new javax.swing.JLabel();
        timeField = new javax.swing.JFormattedTextField();
        filesRandomLabel = new javax.swing.JLabel();
        filesRandomField = new javax.swing.JFormattedTextField();
        jSeparator1 = new javax.swing.JSeparator();
        flowOptionsPanel = new javax.swing.JPanel();
        flowScrollPane = new javax.swing.JScrollPane();
        flowTable = new javax.swing.JTable();
        addFlowButton = new javax.swing.JButton();
        filesFlowLabel = new javax.swing.JLabel();
        filesFlowField = new javax.swing.JFormattedTextField();
        ODOptionsPanel = new javax.swing.JPanel();
        ODMScrollPane = new javax.swing.JScrollPane();
        ODMTable = new javax.swing.JTable();
        TAZInfoPanel = new javax.swing.JPanel();
        TAZLabel = new javax.swing.JLabel();
        TAZAddButton = new javax.swing.JButton();
        TAZSeparator = new javax.swing.JSeparator();
        ODInfoPanel = new javax.swing.JPanel();
        ODLabel = new javax.swing.JLabel();
        ODAddButton = new javax.swing.JButton();
        ODiImportButton = new javax.swing.JButton();
        ODSeparator = new javax.swing.JSeparator();
        TAZScrollPane = new javax.swing.JScrollPane();
        TAZTable = new javax.swing.JTable();
        ODTimeLabel = new javax.swing.JLabel();
        ODTimeField = new javax.swing.JFormattedTextField();
        errorLabel = new javax.swing.JLabel();
        optionsMapButton = new javax.swing.JLabel();
        exportButton = new javax.swing.JLabel();
        mainPanelElements = new javax.swing.JPanel();
        panelElements = new javax.swing.JTabbedPane();
        vehicleTypesScroll = new javax.swing.JScrollPane();
        vehicleTypesPanel = new javax.swing.JPanel();
        addVTypeButton = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileNew = new javax.swing.JMenuItem();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuEditExport = new javax.swing.JMenuItem();
        menuEditSettings = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(PROGRAM);

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

        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mapPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mapInfoField, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mapPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(newMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mapInfoField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mobilityOptionsPanel.setBackground(new java.awt.Color(255, 255, 255));
        mobilityOptionsPanel.setLayout(new java.awt.CardLayout());

        randomOptionsPanel.setBackground(new java.awt.Color(255, 255, 255));

        randomPanel.setBackground(new java.awt.Color(255, 255, 255));

        vehiclesLabel.setFont(FONT);
        vehiclesLabel.setText(RANDOM_REPETITION);

        vehiclesField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        vehiclesField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vehiclesField.setText(RANDOM_REPETITION_DFLT);
        vehiclesField.setFont(FONT);

        timeLabel.setFont(FONT);
        timeLabel.setText(TIME);

        timeField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        timeField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        timeField.setText(RANDOM_TIME_DFLT);
        timeField.setFont(FONT);
        timeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeFieldActionPerformed(evt);
            }
        });

        filesRandomLabel.setText(RANDOM_FILES);

        filesRandomField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        filesRandomField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        filesRandomField.setText(RANDOM_FILES_DFLT);
        filesRandomField.setFont(FONT);

        javax.swing.GroupLayout randomPanelLayout = new javax.swing.GroupLayout(randomPanel);
        randomPanel.setLayout(randomPanelLayout);
        randomPanelLayout.setHorizontalGroup(
            randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(filesRandomLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(vehiclesLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(randomPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vehiclesField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(randomPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filesRandomField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        randomPanelLayout.setVerticalGroup(
            randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(vehiclesField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vehiclesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(randomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filesRandomLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(filesRandomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout randomOptionsPanelLayout = new javax.swing.GroupLayout(randomOptionsPanel);
        randomOptionsPanel.setLayout(randomOptionsPanelLayout);
        randomOptionsPanelLayout.setHorizontalGroup(
            randomOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomOptionsPanelLayout.createSequentialGroup()
                .addGroup(randomOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(randomOptionsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(randomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        randomOptionsPanelLayout.setVerticalGroup(
            randomOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randomOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(randomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 334, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                "Id", "Origin", "Destination", "Begin", "End", "Number", "VType"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        filesFlowLabel.setText(RANDOM_FILES);

        filesFlowField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        filesFlowField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        filesFlowField.setText(RANDOM_FILES_DFLT);
        filesFlowField.setFont(FONT);

        javax.swing.GroupLayout flowOptionsPanelLayout = new javax.swing.GroupLayout(flowOptionsPanel);
        flowOptionsPanel.setLayout(flowOptionsPanelLayout);
        flowOptionsPanelLayout.setHorizontalGroup(
            flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(flowOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(flowScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                    .addGroup(flowOptionsPanelLayout.createSequentialGroup()
                        .addComponent(filesFlowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(filesFlowField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addFlowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        flowOptionsPanelLayout.setVerticalGroup(
            flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(flowOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(flowScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addFlowButton)
                    .addGroup(flowOptionsPanelLayout.createSequentialGroup()
                        .addGroup(flowOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(filesFlowLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(filesFlowField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        mobilityOptionsPanel.add(flowOptionsPanel, "flow");

        ODOptionsPanel.setBackground(new java.awt.Color(255, 255, 255));

        ODMTable.setFont(FONT);
        ODMTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "To", "From", "Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ODMScrollPane.setViewportView(ODMTable);

        TAZInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        TAZLabel.setFont(FONT);
        TAZLabel.setText(OD_TAZ);

        TAZAddButton.setFont(FONT);
        TAZAddButton.setText(OD_TAZ_ADD);
        TAZAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TAZAddButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TAZInfoPanelLayout = new javax.swing.GroupLayout(TAZInfoPanel);
        TAZInfoPanel.setLayout(TAZInfoPanelLayout);
        TAZInfoPanelLayout.setHorizontalGroup(
            TAZInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TAZInfoPanelLayout.createSequentialGroup()
                .addComponent(TAZLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                .addComponent(TAZAddButton)
                .addContainerGap())
        );
        TAZInfoPanelLayout.setVerticalGroup(
            TAZInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TAZInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(TAZLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TAZAddButton))
        );

        ODInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        ODLabel.setFont(FONT);
        ODLabel.setText(OD);

        ODAddButton.setFont(FONT);
        ODAddButton.setText(OD_OPT_ADD);
        ODAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ODAddButtonActionPerformed(evt);
            }
        });

        ODiImportButton.setFont(FONT);
        ODiImportButton.setText(OD_IMPORT);
        ODiImportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ODiImportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ODInfoPanelLayout = new javax.swing.GroupLayout(ODInfoPanel);
        ODInfoPanel.setLayout(ODInfoPanelLayout);
        ODInfoPanelLayout.setHorizontalGroup(
            ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ODInfoPanelLayout.createSequentialGroup()
                .addComponent(ODLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(ODAddButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ODiImportButton)
                .addContainerGap())
        );
        ODInfoPanelLayout.setVerticalGroup(
            ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ODInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ODLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ODAddButton)
                .addComponent(ODiImportButton))
        );

        TAZTable.setFont(FONT);
        TAZTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Roads"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TAZTable.getTableHeader().setReorderingAllowed(false);
        TAZScrollPane.setViewportView(TAZTable);

        ODTimeLabel.setFont(FONT);
        ODTimeLabel.setText(TIME);

        ODTimeField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        ODTimeField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ODTimeField.setText(OD_TIME_DFLT);
        ODTimeField.setFont(FONT);

        javax.swing.GroupLayout ODOptionsPanelLayout = new javax.swing.GroupLayout(ODOptionsPanel);
        ODOptionsPanel.setLayout(ODOptionsPanelLayout);
        ODOptionsPanelLayout.setHorizontalGroup(
            ODOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ODOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ODOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ODOptionsPanelLayout.createSequentialGroup()
                        .addGroup(ODOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TAZInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TAZSeparator)
                            .addComponent(ODInfoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ODOptionsPanelLayout.createSequentialGroup()
                        .addComponent(ODMScrollPane)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ODOptionsPanelLayout.createSequentialGroup()
                        .addComponent(TAZScrollPane)
                        .addContainerGap())
                    .addComponent(ODSeparator)
                    .addGroup(ODOptionsPanelLayout.createSequentialGroup()
                        .addComponent(ODTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ODTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        ODOptionsPanelLayout.setVerticalGroup(
            ODOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ODOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TAZInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(TAZSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TAZScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ODInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ODSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ODMScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ODOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ODTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ODTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        mobilityOptionsPanel.add(ODOptionsPanel, "matrix");

        errorLabel.setFont(FONT);
        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText(MAP_UNAVALIBLE);

        optionsMapButton.setIcon(SETTINGS_ICON);
        optionsMapButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                optionsMapButtonMouseClicked(evt);
            }
        });

        exportButton.setIcon(EXPORT_ICON);
        exportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportButtonMouseClicked(evt);
            }
        });

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
                                .addComponent(mobilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(simulationPanelLayout.createSequentialGroup()
                                .addComponent(mapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(optionsMapButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simulationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        simulationPanelLayout.setVerticalGroup(
            simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simulationPanelLayout.createSequentialGroup()
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mapPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(simulationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(optionsMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(13, 13, 13)
                .addGroup(simulationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mobilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(errorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mobilityOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanelElements.setBackground(new java.awt.Color(255, 255, 255));

        panelElements.setFont(FONT);

        vehicleTypesScroll.setBorder(null);
        vehicleTypesScroll.setHorizontalScrollBar(null);
        vehicleTypesScroll.setMaximumSize(new java.awt.Dimension(468, 215));
        vehicleTypesScroll.setPreferredSize(new java.awt.Dimension(220, 215));

        vehicleTypesPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout vehicleTypesPanelLayout = new javax.swing.GroupLayout(vehicleTypesPanel);
        vehicleTypesPanel.setLayout(vehicleTypesPanelLayout);
        vehicleTypesPanelLayout.setHorizontalGroup(
            vehicleTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 881, Short.MAX_VALUE)
        );
        vehicleTypesPanelLayout.setVerticalGroup(
            vehicleTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );

        vehicleTypesScroll.setViewportView(vehicleTypesPanel);

        panelElements.addTab(VEHICLE_TYPES, vehicleTypesScroll);

        addVTypeButton.setIcon(ADD_ICON);
        addVTypeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addVTypeButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mainPanelElementsLayout = new javax.swing.GroupLayout(mainPanelElements);
        mainPanelElements.setLayout(mainPanelElementsLayout);
        mainPanelElementsLayout.setHorizontalGroup(
            mainPanelElementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelElementsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelElementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelElements, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelElementsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addVTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelElementsLayout.setVerticalGroup(
            mainPanelElementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelElementsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelElements)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addVTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        menuEditSettings.setText(MENU_ITEM_SETTINGS);
        menuEditSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditSettingsActionPerformed(evt);
            }
        });
        menuEdit.add(menuEditSettings);

        menuBar.add(menuEdit);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanelElements, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(simulationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanelElements, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(simulationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileExitMousePressed
        listenerUI.producedEvent(ViewListener.Event.EXIT, null);
    }//GEN-LAST:event_menuFileExitMousePressed

    private void menuFileNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewActionPerformed
        
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
                System.out.println("matrix");
                break;
            default:
                System.out.println("ooooooooooooooooooooooooooooooo");
                break;
        }
        
    }//GEN-LAST:event_menuEditExportActionPerformed

    private void menuFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileOpenActionPerformed
       
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
      
        options.setLocationRelativeTo(this);
        
        options.setAlwaysOnTop(true);
        options.setVisible(true);
    }//GEN-LAST:event_optionsMapButtonMouseClicked

    private void newMapButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newMapButtonMouseClicked
       
        MapSelect map = new MapSelect(this);
        map.setVisible(true);
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

    private void addFlowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFlowButtonActionPerformed
       
        if (mapVisual == null){
            try {
                mapVisual = new MapPanel(currentMap);
            } catch (XMLStreamException | IOException ex) {
                error(MAP_ERROR);
                return;
            } catch (NullPointerException ex){
                error(NO_MAP);
                return;
            }
        }
        flowFrame = new FlowFrame(this, mapVisual, vehicleTypes);
        mapVisual.addMouseHandler(false, flowFrame, MouseEvent.MOUSE_CLICKED);
        flowFrame.setVisible(true);
     
    }//GEN-LAST:event_addFlowButtonActionPerformed

    private void addVTypeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addVTypeButtonMouseClicked
        DialogAddType dialog = new DialogAddType(this);
    }//GEN-LAST:event_addVTypeButtonMouseClicked

    private void exportButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportButtonMouseClicked
        JFileChooser chooser = new JFileChooser();

        int returnVal = chooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            export(chooser.getSelectedFile().getAbsolutePath());
        }
        
    }//GEN-LAST:event_exportButtonMouseClicked

    private void menuEditSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditSettingsActionPerformed
        Settings settings = new Settings (this, Config.getSumoLocation(), Config.getPython2());
        settings.setVisible(true);
    }//GEN-LAST:event_menuEditSettingsActionPerformed

    private void TAZAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TAZAddButtonActionPerformed
        
        TAZFrame TazFrame;
        if (mapVisual == null){
            try {
                mapVisual = new MapPanel(currentMap);
            } catch (XMLStreamException | IOException ex) {
                error(MAP_ERROR);
            } catch (NullPointerException ex){
                error(NO_MAP);
            }
        }else{
            TazFrame = new TAZFrame(this, mapVisual);
            mapVisual.addMouseHandler(false, TazFrame, MouseEvent.MOUSE_CLICKED);
            TazFrame.setVisible(true);
        }
     
    }//GEN-LAST:event_TAZAddButtonActionPerformed

    private void ODAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ODAddButtonActionPerformed
    
        int tazNum = TAZTable.getModel().getRowCount();
        
        if(tazNum < 2){
            error(INSUFFICIENT_TAZ);
            return;
        } 
        //We store all taz/districts in one vector
        String [] tazs = new String[tazNum];
        for (int i = 0; i < tazNum; i++){
            tazs[i] = TAZTable.getModel().getValueAt(i, 0).toString();
        }
        
        ODAddFrame addFrame = new ODAddFrame(this, tazs);
        addFrame.setVisible(true);
    }//GEN-LAST:event_ODAddButtonActionPerformed

    private void ODiImportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ODiImportButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            listenerUI.producedEvent(ViewListener.Event.IMPORT_OD, 
                        chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_ODiImportButtonActionPerformed

    private void timeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeFieldActionPerformed
   
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case MENU_ITEM_EXIT:
                listenerUI.producedEvent(ViewListener.Event.EXIT, null);
            case MENU_ITEM_NEW_MAP:
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
            doneLoading();
            avalibleMap();
            
        } else if (arg instanceof Netconvert[]){
            options.addOptions((Netconvert[])arg);
            
        } else if (arg instanceof Progress){
            loadProgress((Progress)arg);
            
        } else if (arg instanceof RoadTypes[]){
            options.addDefaultRoads((RoadTypes[])arg);
            
        } else if (arg instanceof Warning){
            warning(((Warning)arg).getMessage());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ODAddButton;
    private javax.swing.JPanel ODInfoPanel;
    private javax.swing.JLabel ODLabel;
    private javax.swing.JScrollPane ODMScrollPane;
    private javax.swing.JTable ODMTable;
    private javax.swing.JPanel ODOptionsPanel;
    private javax.swing.JSeparator ODSeparator;
    private javax.swing.JFormattedTextField ODTimeField;
    private javax.swing.JLabel ODTimeLabel;
    private javax.swing.JButton ODiImportButton;
    private javax.swing.JButton TAZAddButton;
    private javax.swing.JPanel TAZInfoPanel;
    private javax.swing.JLabel TAZLabel;
    private javax.swing.JScrollPane TAZScrollPane;
    private javax.swing.JSeparator TAZSeparator;
    private javax.swing.JTable TAZTable;
    private javax.swing.JButton addFlowButton;
    private javax.swing.JLabel addVTypeButton;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel exportButton;
    private javax.swing.JFormattedTextField filesFlowField;
    private javax.swing.JLabel filesFlowLabel;
    private javax.swing.JFormattedTextField filesRandomField;
    private javax.swing.JLabel filesRandomLabel;
    private javax.swing.JPanel flowOptionsPanel;
    private javax.swing.JScrollPane flowScrollPane;
    private javax.swing.JTable flowTable;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanelElements;
    private javax.swing.JTextField mapInfoField;
    private javax.swing.JPanel mapPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuEditExport;
    private javax.swing.JMenuItem menuEditSettings;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenuItem menuFileNew;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JComboBox<String> mobilityComboBox;
    private javax.swing.JPanel mobilityOptionsPanel;
    private javax.swing.JLabel newMapButton;
    private javax.swing.JLabel optionsMapButton;
    private javax.swing.JTabbedPane panelElements;
    private javax.swing.JPanel randomOptionsPanel;
    private javax.swing.JPanel randomPanel;
    private javax.swing.JLabel searchMapButton;
    private javax.swing.JPanel simulationPanel;
    private javax.swing.JFormattedTextField timeField;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JPanel vehicleTypesPanel;
    private javax.swing.JScrollPane vehicleTypesScroll;
    private javax.swing.JFormattedTextField vehiclesField;
    private javax.swing.JLabel vehiclesLabel;
    // End of variables declaration//GEN-END:variables
    
    private void export(String location){
        if(avalibleMap){
            switch((Mobility)mobilityComboBox.getSelectedItem()){
                case Random:
                    listenerUI.producedEvent(Event.EXPORT_RANDOM, 
                                    new Tuple<>(new RandomModel(
                                    Integer.parseInt(timeField.getText()), 
                                    Integer.parseInt(filesRandomField.getText()), 
                                    Integer.parseInt(vehiclesField.getText())), 
                                    location));
                    break;
                case Flow:
                    listenerUI.producedEvent(Event.EXPORT_FLOW, 
                                       new Tuple<>(location, 
                                                   Integer.parseInt(
                                                    filesFlowField.getText())));
                    break;
                case ODMatrix:
                    listenerUI.producedEvent(Event.EXPORT_ODMATRIX, 
                                       new Tuple<>(location, 
                                                   Integer.parseInt(
                                                    ODTimeField.getText())));
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
        mapVisual = null;
        mapInfoField.setText(mapInfo);
        avalibleMap = true;
        errorLabel.setText("");
        //mapInfoLabel.setForeground(Color.BLACK);
    }
    public void newSimulation(String name, String[] commands){
        listenerUI.producedEvent(Event.NEW_SIMULATION, 
                                                new Tuple<>(name, commands));
    }
    public void newSimulation(String[] commands){
        listenerUI.producedEvent(Event.NEW_SIMULATION_CMD, commands);
    }
    
    public void updateSimulation(InputStream stream){
        // Donde mostrar los warnings
    }
    public void exportSimulation(MobilityModel model){
        listenerUI.producedEvent(Event.EXPORT_RANDOM, new Tuple<>(model, ""));
    }
    
    public void enableEvents(boolean events){
        this.setEnabled(events);
    }
    
    private void updateLists(Tuple tuple){
        if(tuple.obj2 instanceof VType){
            
            VehicleTypePanel vType = new VehicleTypePanel((String)tuple.obj1, 
                                                    (VType)tuple.obj2, this);
            vehicleTypes.add(vType);
            
            vehicleTypesPanel.add(vType);
            vehicleTypesPanel.updateUI();
            
        } else if (tuple.obj2 instanceof TAZ){
            newTAZ((String)tuple.obj1, (TAZ)tuple.obj2);
         
        } else if (tuple.obj2 instanceof HashMap){
            updateTables(tuple);
        }
    }
    
    public void error(String msg){
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void importMap(JFrame map, MapSelection selection) {
        map.dispose();
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        JFileChooser chooser = new JFileChooser();
        
        int returnVal = chooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            mapInfo = chooser.getSelectedFile().getAbsolutePath();
            listenerUI.producedEvent(Event.NEW_MAP, new Tuple(selection, mapInfo));
        }
    
    }
    
    public void addVehicleType(java.awt.event.MouseEvent evt){
        DialogAddType addDialog = new DialogAddType(this);
    }
    
    public void addVehicleType(String id){
        listenerUI.producedEvent(Event.NEW_VEHICLE_TYPE, id);
    }
    
    public void addFlow(Flow flow){
        listenerUI.producedEvent(Event.NEW_FLOW, flow);
        //listenerUI.producedEvent(ViewListener.Event.NEW_FLOW, flow);
    }
    
    public void addTAZ(String id, TAZ taz){
        listenerUI.producedEvent(Event.NEW_TAZ, new Tuple(id,taz));
        //listenerUI.producedEvent(ViewListener.Event.NEW_FLOW, flow);
    }
    
    public void addODElement(ODElement element){
        listenerUI.producedEvent(Event.NEW_ODELEMENT, element);
        //listenerUI.producedEvent(ViewListener.Event.NEW_FLOW, flow);
    }
    
    public void selectIcon(VehicleTypePanel panel){
        VTypeIconSelectFrame iconSelect = new VTypeIconSelectFrame(this, panel);
    }
    
    public void editVType(String name, VType type){
        listenerUI.producedEvent(Event.EDIT_VTYPE, new Tuple(name, type));
    }
    
    public void filterRoads (HashSet<String> roads){
        listenerUI.producedEvent(Event.FILTER_ROADS, roads);
    }
    
    public void applyOptions (HashSet<String> roads, Netconvert[] options){
        listenerUI.producedEvent(Event.ROADS_OPTIONS, new Tuple(roads, options));
    }
    
    public void deleteVType(VehicleTypePanel type){
        vehicleTypesPanel.remove(type);
        vehicleTypes.remove(type);
        vehicleTypesPanel.updateUI();
        listenerUI.producedEvent(Event.REMOVE_VTYPE, type.getName());
    }
    
    public void doneLoading(){
        setCursor(Cursor.getDefaultCursor());
    }
    
    public void changeSettings(String python, String sumo){
        listenerUI.producedEvent(Event.EDIT_PYTHON, python);
        listenerUI.producedEvent(Event.EDIT_SUMO, sumo);
    }
    
    private void newFlow(String id, Flow flow) {
        DefaultTableModel model = (DefaultTableModel)flowTable.getModel();
        
        model.addRow(new Object[]{
            id, flow.getOrigin(), flow.getDestination(), 
            flow.getBegin(), flow.getEnd(), flow.getNumber(),
            flow.getType()
        });
        if (flowFrame != null) flowFrame.dispose();
    }
    
    private void newTAZ(String id, TAZ taz) {
        DefaultTableModel model = (DefaultTableModel)TAZTable.getModel();
        
        model.addRow(new Object[]{
            id, taz.getEdges()
        });
    }
    
    private void loadProgress(Progress progress) {
        switch (progress){
            case EXPORT:
                if (progress.getCurrent() == 0){
                    progressFrame = new ProgressFrame(this, progress);
                    progressFrame.setVisible(true);
                    progressFrame.setLocationRelativeTo(this);
                }
                progressFrame.progressLoading(progress);
                break;
            case DOWNLOAD_MAP:
                if (progress.getCurrent() == 0){
                    progressFrame = new ProgressFrame(this, progress);
                    progressFrame.setVisible(true);
                    progressFrame.setLocationRelativeTo(this);
                }else{
                    progressFrame.progressLoading(progress);
                }
                
                break;
                
            case OPEN_MAP:
                if (progress.getCurrent() == 0){
                    progressFrame = new ProgressFrame(this, progress);
                    progressFrame.setVisible(true);
                    progressFrame.setLocationRelativeTo(this);
                }else{
                    progressFrame.progressLoading(progress);
                }
                
                break;
        }         
    }

    public void addODElement(String origin, String destination, String vehiclesNum) {
        listenerUI.producedEvent(Event.NEW_ODELEMENT, 
                                new ODElement(origin, destination, vehiclesNum));
    }

    private void newElement(String id, ODElement odElement) {
        DefaultTableModel model = (DefaultTableModel)ODMTable.getModel();
        
        model.addRow(new Object[]{id,
            odElement.getDestination(), odElement.getOrigin(), odElement.getVehiclesNum()
        });
    }

    private void updateTables(Tuple tuple) {
        if (tuple.obj1 instanceof TableTypes){
            TableTypes type = (TableTypes) tuple.obj1;
            
            switch (type){
                case TAZType:
                    if (tuple.obj2 instanceof HashMap){
                        HashMap TAZMap = (HashMap)tuple.obj2;
                        
                        DefaultTableModel model = (DefaultTableModel)TAZTable.getModel();
                        model.setRowCount(0);
                        
                        TAZMap.forEach((id,TAZ) -> {
                            newTAZ((String)id, (TAZ)TAZ);
                        });
                    }
                    break;
                case FlowType:
                    if(tuple.obj2 instanceof HashMap){
                        HashMap FlowMap = (HashMap) tuple.obj2;
                        
                        DefaultTableModel model = (DefaultTableModel)flowTable.getModel();
                        model.setRowCount(0);
                        
                        FlowMap.forEach((id, flow) -> {
                            newFlow((String)id, (Flow)flow);
                        });
                    }
                    break;
                    
                case ODElementType:
                    if(tuple.obj2 instanceof HashMap){
                        HashMap ODMap = (HashMap) tuple.obj2;
                        
                        DefaultTableModel model = (DefaultTableModel)ODMTable.getModel();
                        model.setRowCount(0);
                        
                        ODMap.forEach((id, ODelement) -> {
                            newElement((String)id, (ODElement)ODelement);
                        });
                    }
                    break;
            }
            
        }
    }
    
    private void deleteElement(TableTypes type){
        
        switch (type){
                case TAZType:
                    removeTableElement(type, Event.REMOVE_TAZ, TAZTable, 0);
                    break;
                case FlowType:
                    removeTableElement(type, Event.REMOVE_FLOW, flowTable, 0);
                    break;
        }
        
    }
    
    private void removeTableElement(TableTypes type, Event event, JTable table,
                                                        int idColumn ){
        int[] selectedRows = table.getSelectedRows();
        String[] removedElement = new String[selectedRows.length];
        
        if (selectedRows.length == 0){
            error(NO_SELECTION_TABLE);
        }else{
            for(int row : selectedRows){
                removedElement[row] = table.getValueAt(row, idColumn).toString();
            }
            listenerUI.producedEvent(event, removedElement);

        }
    }
    
    public void editElement(TableTypes type){
        switch (type){
                case FlowType:
                    int selectedRow = flowTable.getSelectedRow();
                    
                    if (mapVisual == null){
                        try {
                            mapVisual = new MapPanel(currentMap);
                        } catch (XMLStreamException | IOException ex) {
                            error(MAP_ERROR);
                            return;
                        } catch (NullPointerException ex){
                            error(NO_MAP);
                            return;
                        }
                    }
                    
                    flowFrame = new FlowFrame(
                        flowTable.getModel().getValueAt(selectedRow, 0).toString(),
                        Integer.valueOf(flowTable.getModel().getValueAt(selectedRow, 3).toString()),
                        Integer.valueOf(flowTable.getModel().getValueAt(selectedRow, 4).toString()),
                        flowTable.getModel().getValueAt(selectedRow, 1).toString(),    
                        flowTable.getModel().getValueAt(selectedRow, 2).toString(),
                        flowTable.getModel().getValueAt(selectedRow, 6).toString(),
                        Integer.valueOf(flowTable.getModel().getValueAt(selectedRow, 5).toString()),
                        this, mapVisual, vehicleTypes);
                    
                    mapVisual.addMouseHandler(false, flowFrame, MouseEvent.MOUSE_CLICKED);
                    flowFrame.setVisible(true);
                    
        }
    }
    public void editFlow(String id, Flow flow) {
        listenerUI.producedEvent(Event.EDIT_FLOW, new Tuple(id, flow));
    }

    private void warning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}
