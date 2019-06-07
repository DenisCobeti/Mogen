package control;


import model.sumo.Simulation;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import model.map.OsmAPI;
import model.constants.Errors;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import model.C4RModel;
import model.Config;
import model.map.MapAPI;
import model.map.MapAPI.APIS;
import model.map.MapSelection;
import model.constants.FilesExtension;
import view.C4RView;

/**
 *
 * @author Denis C
 */
public class C4RControl {
    private C4RModel model;
    private C4RView view;
    
    private MapConverter converter;
    //private HashMap<String, Simulation> simulations;
    private List<Simulation> simulations;

    public C4RControl(C4RModel model, C4RView view) {
        this.model = model;
        this.view = view;
        simulations = new LinkedList<>();
        //obtainMap(0,0,0,0);
        Config.load();
        System.out.println(Config.osmMap +" " +Config.sumoMap);
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
        converter = new MapConverter(map, APIS.OSM);
    }
    public void openMap(String location) throws ProtocolException, 
                                                            IOException{
        // Modify second parameter to change the imported map type
        converter = new MapConverter(location, APIS.OSM);
        
    }
    public Simulation createSimulation(String name, String[] commands) 
                                    throws IOException{
        Simulation sim = new Simulation(name);
        converter.addOptions(commands);
        converter.executeConvert(name);
        simulations.add(sim);
        return sim;
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
    
}
