package view.jxmapviewer2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;
import model.Config;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import view.C4RView;

/**
 * 
 */
public class MapViewer extends JFrame{
   
    private final static String USE_INSTRUCTIONS = "Use left mouse button to pan, "
                             + "mouse wheel to zoom and right mouse to select";
    private final static String CACHE_FILE = "cache.jxmapviewer2";
    private final static String EXPORT_TEXT = "Export selection";

    private final C4RView view;
    
    public MapViewer(C4RView view) {
        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        JLabel label = new JLabel(EXPORT_TEXT);
        File cacheDir = new File(CACHE_FILE);
        JXMapViewer mapViewer = new JXMapViewer();
        this.view = view;
        
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));
        mapViewer.setTileFactory(tileFactory);
        // Set the focus
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(Config.DEFAULT_POSITION);

         // Add interactions
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        
        
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));

        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        // Add a selection painter
        SelectionAdapter sa = new SelectionAdapter(mapViewer);
        SelectionPainter sp = new SelectionPainter(sa);
        mapViewer.addMouseListener(sa);
        mapViewer.addMouseMotionListener(sa);
        mapViewer.setOverlayPainter(sp);

        this.setLayout(new BorderLayout());
        this.add(new JLabel(USE_INSTRUCTIONS), BorderLayout.NORTH);
        this.add(mapViewer, BorderLayout.CENTER);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(view);
        this.setVisible(true);
        
        
        mapViewer.addPropertyChangeListener("zoom", (PropertyChangeEvent evt) -> {
            updateWindowTitle(this, mapViewer);
        });

        mapViewer.addPropertyChangeListener("center", (PropertyChangeEvent evt) -> {
            updateWindowTitle(this, mapViewer);
        });
        mapViewer.getCenterPosition();
        label.setMinimumSize(new Dimension(400, 400));
        label.setVisible(true);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                label.setBackground(Color.BLACK);
                label.setForeground(Color.WHITE);
            }
            @Override
            public void mouseClicked(MouseEvent evt){
                System.out.println(sa.getGeoCoordinates(mapViewer).toString());
            }
        });
        label.addMouseListener(sa);
        mapViewer.add(label);
        
        //mapViewer.getTileFactory().pixelToGeo(pd, 0);
        updateWindowTitle(this, mapViewer);
    }
    

    private static void updateWindowTitle(JFrame frame, JXMapViewer mapViewer){
        
        double lat = mapViewer.getCenterPosition().getLatitude();
        double lon = mapViewer.getCenterPosition().getLongitude();
        int zoom = mapViewer.getZoom();
        
        frame.setTitle(String.format("JXMapviewer2 (%.2f / %.2f) - Zoom: %d", lat, lon, zoom));
    }

}
