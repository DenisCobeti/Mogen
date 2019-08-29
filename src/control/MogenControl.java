package control;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import model.map.OsmAPI;
import java.net.ProtocolException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.MogenModel;
import model.Config;
import model.Progress;
import model.Tuple;
import model.map.MapAPI;
import model.map.MapAPI.APIS;
import model.map.MapSelection;
import model.constants.FilesExtension;
import model.mobility.FlowModel;
import model.mobility.MobilityModel;
import model.routes.VType;
import view.MogenView;

/**
 *
 * @author Denis C
 */
public class MogenControl {
    public static final String DEFAULT_MAP_NAME = "mapNetconvert";
    public static final String DEFAULT_VTYPE_LOCATION = "vehicles";
    
    private final MogenModel model;
    private final MogenView view;
    
    private MapConverter converter;
    private HashSet<String> roads;
    //private HashMap<String, Simulation> simulations;
    private boolean hasMap = false;
    private String map;
    
    private Progress progress;
    private final static int MAP_DOWNLOAD_STEPS = 3;

    public MogenControl(MogenModel model, MogenView view) {
        this.model = model;
        this.view = view;
        
        roads = new HashSet();
        //obtainMap(0,0,0,0);
        Config.load();
        view.update(model, MapConverter.DEFAULT_OPTIONS);
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
    
    
    
    public void saveMap(MapSelection selection, String location) throws ProtocolException, 
                                                            IOException,
                                                            InterruptedException{
        progress = Progress.MAP;
        progress.initialize(MAP_DOWNLOAD_STEPS);
        view.update(model, progress);
        
        String map = location + FilesExtension.OSM;
        MapAPI api = new OsmAPI(selection.minLon, selection.minLat, 
                            selection.maxLat, selection.maxLon);
        //lento
        progress.progress();
        view.update(model, progress);
        InputStream in = api.getMap();
        
        File osmFile = new File(map);
        osmFile.createNewFile();
        //lento
        progress.progress();
        view.update(model, progress);
        inputStreamToFile(in, osmFile);
        // Modify second parameter to change the imported map type
        progress.progress();
        view.update(model, progress);
        openMap(map);
    }
    
    public void openMap(String location) throws ProtocolException, 
                                                            IOException,
                                                            InterruptedException{
        // Modify second parameter to change the imported map type
        converter = new MapConverter(location, APIS.OSM);
        String stream = converter.executeConvert(DEFAULT_MAP_NAME, roads);
        converter.pruneNodes(DEFAULT_MAP_NAME);
        model.setMap(DEFAULT_MAP_NAME);
        model.getFlows().clear();
        
        map = location;
        hasMap = true;
    }
    
    
    public void exportSimulation(MobilityModel mobilityModel, String location) throws IOException, InterruptedException{
        File vehicles = new File(DEFAULT_VTYPE_LOCATION + 
                                        FilesExtension.VEHICLES.getExtension());
        vehicles.createNewFile();
        
        PrintWriter writer = new PrintWriter(vehicles.getAbsoluteFile(), "UTF-8");
        writer.println("<additional>");
        writer.println("<vTypeDistribution id=\"" + VType.DISTRIBUTION + "\">");
        for (Map.Entry<String, VType> entry : model.getvTypes().entrySet()) {
            //writer.println(entry.getValue().toFile(entry.getKey()));
            if(entry.getValue().isEnabled()){
                writer.println(entry.getValue().toFile(entry.getKey()));
            }
        }
        writer.println("</vTypeDistribution>");
        writer.println("</additional>");
        writer.close();
        
        mobilityModel.export(location, model.getMap(), vehicles.getAbsolutePath(), this);
    }
    
    public static void inputStreamToFile(InputStream inputStream, File file) 
		throws IOException {
        
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

    public void exportFlows(String location, int files) throws IOException, InterruptedException {
        File vehicles = new File(DEFAULT_VTYPE_LOCATION + 
                                        FilesExtension.VEHICLES.getExtension());
        vehicles.createNewFile();
        
        PrintWriter writer = new PrintWriter(vehicles.getAbsoluteFile(), "UTF-8");
        writer.println("<additional>");
        writer.println("<vTypeDistribution id=\"" + VType.DISTRIBUTION + "\">");
        for (Map.Entry<String, VType> entry : model.getvTypes().entrySet()) {
            //writer.println(entry.getValue().toFile(entry.getKey()));
            if(entry.getValue().isEnabled()){
                writer.println(entry.getValue().toFile(entry.getKey()));
            }
        }
        writer.println("</vTypeDistribution>");
        writer.println("</additional>");
        writer.close();
        
        FlowModel flowModel = new FlowModel(files, model.getFlows());
        flowModel.export(location, model.getMap(), vehicles.getAbsolutePath(), this);
    }
    
    public void setRoadsFiltered(HashSet<String> roads) throws IOException, 
                                        ProtocolException, InterruptedException{
        this.roads = roads;
        if (hasMap) openMap(map);
        
    }
    
    public void startExport(int num){
        progress = Progress.EXPORT;
        progress.initialize(num);
        view.update(model, progress);
    }
    
    public void progressExport(int num){
        if (progress != null){
            progress.progress();
            view.update(model, progress);
        }
    }
    
    public void endExport(int num){
        
    }
}
