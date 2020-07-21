package control;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ProtocolException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;

import model.map.OsmAPI;
import model.MogenModel;
import model.Config;
import model.Progress;
import model.Tuple;
import model.constants.Errors;
import model.map.MapAPI;
import model.map.MapAPI.APIS;
import model.map.MapSelection;
import model.constants.FilesExtension;
import model.exceptions.NoRouteConnectionException;
import model.mobility.FlowModel;
import model.mobility.MobilityModel;
import model.mobility.ODModel;
import model.routes.Flow;
import model.routes.ODElement;
import model.routes.TAZ;
import model.routes.VType;

import view.MogenView;

/**
 *
 * @author Denis C
 */
public class MogenControl {
    public static final String DEFAULT_MAP_NAME = "mapNetconvert";
    public static final String DEFAULT_VTYPE_LOCATION = "vehicles";
    private final static String PYTHON_CHECK = "\\tools\\net\\netcheck.py";
    
    private static final String LOADING_EXPORT = "Exporting file nº";
    private static final String[] LOADING_MAP = {"Connecting to server..." ,
                                                 "Saving file...", 
                                                 "Opening map..."};
    private final MogenModel model;
    private final MogenView view;
    
    private MapConverter converter;
    private HashSet<String> roads;
    //private HashMap<String, Simulation> simulations;
    private boolean hasMap = false;
    private String map;
    
    private Progress progress;

    public MogenControl(MogenModel model, MogenView view) {
        this.model = model;
        this.view = view;
        
        roads = new HashSet();
        //obtainMap(0,0,0,0);
        Config.load();
        view.update(model, MapConverter.DEFAULT_OPTIONS);
        view.update(model, MapConverter.DEFAULT_ROADS);
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
                                                            InterruptedException,
                                                            FileNotFoundException,
                                                            XMLStreamException{
        progress = Progress.DOWNLOAD_MAP;
        progress.initialize(LOADING_MAP.length);
        view.update(model, progress);
        
        String map = location + FilesExtension.OSM;
        MapAPI api = new OsmAPI(selection.minLon, selection.minLat, 
                            selection.maxLat, selection.maxLon);
        //lento
        progress.progress(LOADING_MAP);
        view.update(model, progress);
        InputStream in = api.getMap();
        
        File osmFile = new File(map);
        osmFile.createNewFile();
        //lento
        progress.progress(LOADING_MAP);
        view.update(model, progress);
        inputStreamToFile(in, osmFile);
        
        // Modify second parameter to change the imported map type
        progress.progress(LOADING_MAP);
        view.update(model, progress);
        openMapPruneNodes(map, selection);
        progress.end();
        view.update(model, progress);
    }
    
    public void openMapPruneNodes(String location, MapSelection selection) throws 
                                    ProtocolException, IOException,
                                    InterruptedException,FileNotFoundException,
                                    XMLStreamException{
        converter = new MapConverter(location, APIS.OSM);
        String stream = converter.executeConvert(DEFAULT_MAP_NAME, roads);
        converter.pruneNodes(location, selection);
        model.setMap(DEFAULT_MAP_NAME);
        model.getFlows().clear();
        
        map = location;
        hasMap = true;
    }
    
    public void openMap(String location) throws ProtocolException, 
                                                            IOException,
                                                            InterruptedException,
                                                            FileNotFoundException{
        // Modify second parameter to change the imported map type
        converter = new MapConverter(location, APIS.OSM);
        String stream = converter.executeConvert(DEFAULT_MAP_NAME, roads);
        model.setMap(DEFAULT_MAP_NAME);
        model.getFlows().clear();
        
        map = location;
        hasMap = true;
    }
    public HashMap addFlow(Flow flow) throws NoRouteConnectionException, IOException, 
                                                        InterruptedException{
        // Fist we check if the edges are reachable
        LinkedList <String> command = new LinkedList(Arrays.asList(
                Config.python2,
                Config.sumoLocation + PYTHON_CHECK,
                model.getMap() + FilesExtension.NETCONVERT,
                "--source",
                flow.originEdge()
        ));
        LinkedList <String> reachableEdges = new LinkedList();
        
        System.out.println(command);
        ProcessBuilder checkEdges = new ProcessBuilder(command);
        
        Process process = checkEdges.start();
        
        InputStream stdin = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin);
        BufferedReader br = new BufferedReader(isr);

        String output = "";
        String line = null;
        while ((line = br.readLine()) != null)output += line;

        System.out.println(output);
        output = output.substring(output.indexOf("["));
        
        if (!output.contains( "'"+ flow.destinationEdge() + "'"))
                                throw new NoRouteConnectionException
                                          (Errors.NO_CONNECTION.getErrorMsg());
        System.out.println(output);
        process.waitFor();
        
        return model.addFlow(flow);
    }
    
    public void exportSimulation(MobilityModel mobilityModel, String location) 
                                       throws IOException, InterruptedException{
        /*
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
        writer.close();*/
        String vehiclesPath = exportVehicles(DEFAULT_VTYPE_LOCATION 
                                        + FilesExtension.VEHICLES.getExtension());
        mobilityModel.export(location, model.getMap(), vehiclesPath ,this);
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
        /*
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
        */
        String vehiclesPath = exportVehicles(DEFAULT_VTYPE_LOCATION 
                                        + FilesExtension.VEHICLES.getExtension());
        FlowModel flowModel = new FlowModel(files, model.getFlows());
        
        flowModel.export(location, model.getMap(), vehiclesPath, this);
    }
    
    public void exportODMatrix(String location, int time) throws IOException, InterruptedException {
        
        String vehiclesPath = exportVehicles(DEFAULT_VTYPE_LOCATION 
                                        + FilesExtension.VEHICLES.getExtension());
        ODModel ODmodel = new ODModel(time, model.getTazs());
        
        ODmodel.export(location, model.getMap(), vehiclesPath, this);
    }
    
    public String exportVehicles(String file) throws FileNotFoundException, IOException{
        File vehicles = new File(file);
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
        
        return vehicles.getAbsolutePath();
    }
    
    public void setRoadsFiltered(HashSet<String> roads) throws IOException, 
                                        ProtocolException, InterruptedException{
        this.roads = roads;
        if (hasMap) openMap(map);
        
    }
    
    public Tuple addODElemnt(ODElement element){
        return model.addODElement(element);
    }
    
    public void startExport(int num){
        progress = Progress.EXPORT;
        progress.initialize(num);
        view.update(model, progress);
    }
    
    public void progressExport(int num){
        if (progress != null){
            progress.progress(LOADING_EXPORT + progress.getCurrent());
            view.update(model, progress);
        }
    }

    public HashMap addTAZ(String string, TAZ taz) {
        return model.addTAZ(string, taz);
    }

    public HashMap removeTAZ(String[] tazId) {
        for(String id : tazId){
            model.removeTAZ(id);
        }
        return model.getTazs();
    }
}
