package control;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import model.map.OsmAPI;
import java.net.ProtocolException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MogenModel;
import model.Config;
import model.Tuple;
import model.map.MapAPI;
import model.map.MapAPI.APIS;
import model.map.MapSelection;
import model.constants.FilesExtension;
import model.mobility.MobilityModel;
import view.MogenView;

/**
 *
 * @author Denis C
 */
public class MogenControl {
    public static final String DEFAULT_MAP_NAME = "mapNetconvert";
    
    private MogenModel model;
    private MogenView view;
    
    private MapConverter converter;
    private final VehicleManager vehicleManager;
    //private HashMap<String, Simulation> simulations;
    private boolean hasMap = false;

    public MogenControl(MogenModel model, MogenView view) {
        this.model = model;
        this.view = view;
        
        vehicleManager = new VehicleManager();
        //obtainMap(0,0,0,0);
        Config.load();
        System.out.println(Config.osmMap +" " +Config.sumoMap);
        model.getvTypes().forEach((k, v) -> view.update(model, new Tuple(k, v)));
    }
    /*
    public void saveMap(MapSelection selection){
        
        MapAPI api = null;
        String map = Config.osmMap + FilesExtension.OSM;
        
        try{
            api = new OsmAPI(selection.minLon, selection.minLat, 
                                selection.maxLat, selection.maxLon);
        }catch(ProtocolException | MalformedURLException ex){
            C4R.handleError(ex, Errors.OSM_DOWNLOAD);
            
        } catch (IOException ex) {
            C4R.handleError(ex, Errors.FILE_WRITE);
            
        }finally{
            try{ 
                InputStream in = api.getMap();
                File osmFile = new File(map);
                
                osmFile.createNewFile();
                copyInputStreamToFile(in, osmFile);
                // Modify second parameter to change the imported map type
                converter = new MapConverter(map, APIS.OSM);
                
            } catch (IOException ex) {
                C4R.handleError(ex, Errors.FILE_WRITE);
            }
        }
    }*/
    
    
    
    public void saveMap(MapSelection selection) throws ProtocolException, 
                                                            IOException{
        
        MapAPI api = null;
        String map = Config.osmMap + FilesExtension.OSM;
        
        api = new OsmAPI(selection.minLon, selection.minLat, 
                            selection.maxLat, selection.maxLon);

        InputStream in = api.getMap();
        File osmFile = new File(map);

        osmFile.createNewFile();
        copyInputStreamToFile(in, osmFile);
        // Modify second parameter to change the imported map type
        openMap(map);
    }
    
    public void openMap(String location) throws ProtocolException, 
                                                            IOException{
        // Modify second parameter to change the imported map type
        converter = new MapConverter(location, APIS.OSM);
        converter.executeConvert(DEFAULT_MAP_NAME);
        model.setMap(DEFAULT_MAP_NAME);
        hasMap = true;
    }
    
    public InputStream createSimulation(String name, String[] commands) 
                                    throws IOException {
        Simulation sim = new Simulation(commands);
        converter.addOptions(cleanOptions(commands));
        InputStream output = converter.executeConvert(name);
        model.addElement(name, sim);
        return output;
    }
    
    public InputStream createSimulation(String[] commands) throws IOException {
        Simulation sim = new Simulation(commands);
        converter.addOptions(cleanOptions(commands));
        InputStream output = converter.executeConvert(DEFAULT_MAP_NAME);
        model.addElement(DEFAULT_MAP_NAME, sim);
        return output;
    }
    
    public void exportSimulation(MobilityModel mobilityModel, String sim) 
                                                            throws IOException{
        mobilityModel.export("pruebasses", sim, model.getvTypes());
    }
    //esto esta copiado, habra que cambiarlo
    public static void copyInputStreamToFile(InputStream inputStream, File file) 
		throws IOException {
        file.createNewFile();
        
        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }

    private List cleanOptions(String[] commands) {
        LinkedList<String> newCommands = new LinkedList<>();
        
        for (String command: commands){
            if (command.contains(" ")){
                newCommands.addAll(Arrays.asList(command.split(" ")));
            }else{
                newCommands.add(command);
            }
        }
        
        return newCommands;
    }

    public boolean hasMap() {
        return hasMap;
    }
}
