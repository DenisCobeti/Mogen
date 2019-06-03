package view;

import view.mapelements.VehicleType;
import control.ViewListener;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.C4RModel.ElementType;
import model.map.MapSelection;
import model.Tuple;
import model.routes.VType;
import view.mapelements.DialogAddType;
import view.jxmapviewer2.MapViewer;

/**
 *
 * @author Neblis
 */

public class C4RView extends javax.swing.JFrame  implements ActionListener, Observer{
    private LoadingMap loading;
    
    public final static Font FONT = new java.awt.Font("Century Gothic", Font.PLAIN, 12);
    public final static Font SMALL_FONT = FONT.deriveFont(10,0);
    
    private static MapViewer map;
    private static int tab;
    
    private static final String MENU_ITEM_EXIT = "Exit";
    private static final String MENU_ITEM_EDIT = "Edit";
    private static final String MENU_ITEM_FILE = "Exit program";
    private static final String MENU_ITEM_NEW_MAP = "New map";
    private static final String MENU_ITEM_OPEN_MAP = "Open map";
    
    private static final String VEHICLE_TYPES = "Vehicle types";
    private static final String RSU = "RSU";
    private static final String DOWNTOWNS = "Downtowns";
    
    private static final String ADD_SIMULATION = " +  ";
    private static final String WELCOME = "Welcome!";
    
    private static final String PROGRAM = "C4R";
    
    private final JLabel addVTypeButton;
    //private final JLabel addDowntown;
    //private final JLabel addRSU;
    
    private final static String ADD_ICON_IMG = "resources/button/add.png";
    private ImageIcon ADD_ICON = new ImageIcon(ADD_ICON_IMG);
    
    private final ViewListener listenerUI;
    private int numSimulations;
    private boolean avalibleMap;
    
    private List<VehicleType> VehicleTypes;
    /**
     * Creates new form NewJFrame
     */
    
    public C4RView(ViewListener listenerUI) {
        this.listenerUI = listenerUI;
        Locale.setDefault(Locale.Category.FORMAT,new Locale("en", "UK"));
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException |  
                InstantiationException | ClassNotFoundException ex) {}
        
        initComponents();
        
        JPanel panel = new JPanel();
        panelMaps.addTab(ADD_SIMULATION, panel);
        panelMaps.addChangeListener(new ChangeListener(){
            
            public void stateChanged(ChangeEvent evt){

                    JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();
                    if(tabbedPane.getSelectedIndex() == tabbedPane.indexOfTab
                        (ADD_SIMULATION)) addSimulation();

                }
            });
        //menuFileExit.addActionListener(this);
        tab = 2;
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
        numSimulations = 0; //////////////////////////
        avalibleMap = false;
        
        this.setTitle(PROGRAM);
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

        panelFile = new javax.swing.JPanel();
        panelMaps = new javax.swing.JTabbedPane();
        addTab = new javax.swing.JPanel();
        panelOptions = new javax.swing.JTabbedPane();
        vehicleTypesScroll = new javax.swing.JScrollPane();
        vehicleTypesPanel = new javax.swing.JPanel();
        RSUScroll = new javax.swing.JScrollPane();
        downtownScroll = new javax.swing.JScrollPane();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFileNew = new javax.swing.JMenuItem();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelFile.setBackground(new java.awt.Color(255, 255, 255));
        panelFile.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout panelFileLayout = new javax.swing.GroupLayout(panelFile);
        panelFile.setLayout(panelFileLayout);
        panelFileLayout.setHorizontalGroup(
            panelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );
        panelFileLayout.setVerticalGroup(
            panelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        addTab.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout addTabLayout = new javax.swing.GroupLayout(addTab);
        addTab.setLayout(addTabLayout);
        addTabLayout.setHorizontalGroup(
            addTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 948, Short.MAX_VALUE)
        );
        addTabLayout.setVerticalGroup(
            addTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        panelMaps.addTab(WELCOME, addTab);

        vehicleTypesScroll.setBorder(null);
        vehicleTypesScroll.setHorizontalScrollBar(null);
        vehicleTypesScroll.setMaximumSize(new java.awt.Dimension(468, 215));
        vehicleTypesScroll.setPreferredSize(new java.awt.Dimension(220, 215));

        vehicleTypesPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout vehicleTypesPanelLayout = new javax.swing.GroupLayout(vehicleTypesPanel);
        vehicleTypesPanel.setLayout(vehicleTypesPanelLayout);
        vehicleTypesPanelLayout.setHorizontalGroup(
            vehicleTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );
        vehicleTypesPanelLayout.setVerticalGroup(
            vehicleTypesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );

        vehicleTypesScroll.setViewportView(vehicleTypesPanel);

        panelOptions.addTab(VEHICLE_TYPES, vehicleTypesScroll);
        panelOptions.addTab(RSU, RSUScroll);
        panelOptions.addTab(DOWNTOWNS, downtownScroll);

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
        menuFileOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuFileOpenMouseClicked(evt);
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
        menuBar.add(menuEdit);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMaps)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelOptions)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOptions))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMaps)
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

    private void menuFileOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileOpenMouseClicked
        
    }//GEN-LAST:event_menuFileOpenMouseClicked
   
    
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
            
        } else if (arg instanceof VType){
            updateTypes((VType) arg);
            
        } else if (arg instanceof Exception){
            error(((Exception) arg).getMessage());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane RSUScroll;
    private javax.swing.JPanel addTab;
    private javax.swing.JScrollPane downtownScroll;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenuItem menuFileNew;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JPanel panelFile;
    private javax.swing.JTabbedPane panelMaps;
    private javax.swing.JTabbedPane panelOptions;
    private javax.swing.JPanel vehicleTypesPanel;
    private javax.swing.JScrollPane vehicleTypesScroll;
    // End of variables declaration//GEN-END:variables

    private void addSimulation(){
        System.out.println("boom");
        panelMaps.addTab("New Tab",new JPanel());
        panelMaps.setSelectedIndex(tab);
        tab++;
    }
    
    public void enableEvents(){
        
    }
    private void updateLists(Tuple tuple){
        if(tuple.obj2 instanceof VType){
            VehicleType vType = new VehicleType((String)tuple.obj1, (VType)tuple.obj2, this);
            vehicleTypesPanel.add(vType);
            vehicleTypesPanel.updateUI();
        }
    }
    private void updateLists(ElementType type, List list){
        switch(type){
            case VTYPE:
                /*VehicleType v1 = new VehicleType("boom", this);
                vehicleTypesPanel.add(v1);*/
        }
    }
    
    private void updateTypes(VType type){
        
    }
    
    public void newProject(NewProject selection) {
        selection.dispose();
    }
    
    public void error(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
    public void importMap(JFrame map, MapSelection selection) {
        map.dispose();
        loading = new LoadingMap();
        this.add(loading);
        loading.setEnabled(true);
        loading.show(this, 100, 100);
        loading.setVisible(true);
        listenerUI.producedEvent(ViewListener.Event.NEW_MAP, selection);
        avalibleMap = true;
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
    
    public void deleteVType(VehicleType type){
        vehicleTypesPanel.remove(type);
        vehicleTypesPanel.updateUI();
    }
    
    void selectMapArea(NewProject selection) {
        selection.dispose();
        map = new MapViewer(this);
    }
    public void doneLoading(){
        loading.setVisible(false);
        loading = null;
    }
    
    
}
