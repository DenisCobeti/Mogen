package control;

import static control.ViewListener.TableTypes;
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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.stream.XMLStreamException;
import model.Config;

import model.MogenModel;
import model.Progress;
import model.Tuple;
import model.map.MapSelection;
import model.constants.Errors;
import model.constants.FilesExtension;
import model.constants.RoadTypes;
import model.exceptions.DownloadMapException;
import model.exceptions.DuplicatedKeyException;
import model.exceptions.NoODFileFormatFoundException;
import model.exceptions.NoRouteConnectionException;
import model.map.MapAPI;
import model.map.OsmAPI;
import model.mobility.FlowModel;
import model.mobility.MobilityModel;
import model.mobility.ODModel;
import model.mobility.ODModel.Format;
import model.routes.Flow;
import model.routes.ODElement;
import model.routes.TAZ;
import model.routes.VType;

import view.MogenView;


/**
 *
 * @author Denis C
 */
public class MogenControl implements ViewListener{
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    
    private final MogenModel model;
    private final MogenView view;
   
    public static final String DEFAULT_MAP_NAME = "mapNetconvert";
    public static final String DEFAULT_VTYPE_LOCATION = "vehicles";
    private final static String PYTHON_CHECK = "\\tools\\net\\netcheck.py";
    
    private static final String LOADING_EXPORT = "Exporting file nº";
    private static final String[] LOADING_MAP = {"Connecting to server..." ,
                                                 "Saving file...", 
                                                 "Opening map..."};
    private static final String[] OPEN_MAP = { "Converting map..."};
    
        
    
    private MapConverter converter;
    private HashSet<String> roads;
    //private HashMap<String, Simulation> simulations;
    private boolean hasMap = false;
    
    private Progress progress;

    public MogenControl(String[] args) {
        
        view = new MogenView(this);
        model = new MogenModel(view);
        roads = new HashSet();
        converter = new MapConverter();
        
        for(RoadTypes road : MapConverter.DEFAULT_ROADS) roads.add(road.toString());
        //obtainMap(0,0,0,0);
        Config.load();
        view.update(model, MapConverter.DEFAULT_OPTIONS);
        view.update(model, MapConverter.DEFAULT_ROADS);
        System.out.println(Config.osmMap +" " +Config.sumoMap);
        model.getvTypes().forEach((k, v) -> view.update(model, new Tuple(k, v)));
        
    }

    @Override
    public void producedEvent(Event event, Object obj) {
        Tuple tuple;
        switch (event) {
            case EXIT:
                salir();
                break;
                
            case NEW_MAP:
                tuple = (Tuple)obj;
                try {
                    saveMap((MapSelection)tuple.obj1, (String)tuple.obj2);
                } catch (IOException | InterruptedException | XMLStreamException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
                }finally{
                    view.update(model, model.getOSMMap());
                }
                break;
                
            case OPEN_MAP:
                try {
                    openMap((String)obj);
                } catch (IOException | InterruptedException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
                } finally {
                    view.update(model, model.getOSMMap());
                }
                break;
                
            case NEW_VEHICLE_TYPE:
                VType type = new VType();
                
                if(model.addElement((String)obj, type)){ 
                    view.update(model, new Tuple<>((String)obj, type));
                    System.out.println(obj.toString());
                }else{
                    view.update(model, new DuplicatedKeyException
                                            (Errors.DUPLICATED_VTYPE));
                }
                break;
                
           case NEW_FLOW:
                Flow flow = (Flow)obj;
                try {
                    
                    view.update(model, new Tuple<>(TableTypes.FlowType, 
                                addFlow(flow)));
                } catch (NoRouteConnectionException ex) {
                    view.update(model, ex);
                } catch (IOException | InterruptedException ex2){
                    
                } 
                break;
           case EDIT_FLOW:
                tuple = (Tuple)obj;
                try {
                    
                    view.update(model, new Tuple<>(TableTypes.FlowType, 
                                editFlow((String)tuple.obj1, (Flow)tuple.obj2)));
                } catch (NoRouteConnectionException ex) {
                    view.update(model, ex);
                } catch (IOException | InterruptedException ex2){
                    
                } 
                break;
           case NEW_TAZ:
                tuple = (Tuple)obj;
                
                view.update(model, new Tuple<>(TableTypes.TAZType, 
                          addTAZ((String)tuple.obj1, (TAZ)tuple.obj2)));
                break;
                
           case EXPORT_RANDOM:
                tuple = (Tuple)obj;
                try{
                    exportSimulation((MobilityModel)tuple.obj1, 
                            (String)tuple.obj2);
                } catch (IOException | InterruptedException ex) {
                    handleError(ex, Errors.ROUTE);
                }
                break;
                
            case EXPORT_FLOW:
                tuple = (Tuple)obj;
                try{
                    exportFlows((String)tuple.obj1, (int)tuple.obj2);
                } catch (IOException | InterruptedException ex) {
                    handleError(ex, Errors.ROUTE);
                }
                break; 
                
            case EXPORT_ODMATRIX:
                tuple = (Tuple)obj;
                try{
                    exportODMatrix((String)tuple.obj1, (int)tuple.obj2);
                } catch (IOException | InterruptedException ex) {
                    handleError(ex, Errors.ROUTE);
                }
                break; 
                
            case NEW_ODELEMENT:
                view.update(model, new Tuple<>(TableTypes.ODElementType, addODElemnt((ODElement)obj)));
                break;
                
            case EDIT_VTYPE:
                tuple = (Tuple)obj;
                model.getvTypes().put((String)tuple.obj1, (VType)tuple.obj2);
                break;
                
            case FILTER_ROADS:
                HashSet roads = (HashSet)obj;
        
                try {
                    setRoadsFiltered(roads);
                    view.update(model, model.getOSMMap());
                } catch (IOException | InterruptedException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
                }
                break;
                
            case REMOVE_VTYPE:
                model.removeVType((String)obj);
                break;
                
            case ROADS_OPTIONS:
                tuple = (Tuple)obj;
                
                HashSet filteredRoads = (HashSet)tuple.obj1;
                HashSet options = (HashSet)tuple.obj2;
                
                try {
                    converter.addOptions(options);
                    setRoadsFiltered(filteredRoads);
                } catch (IOException | InterruptedException ex) {
                    view.update(model, new DownloadMapException
                                           (Errors.OSM_DOWNLOAD.toString()));
                }
                break;
                
            case REMOVE_TAZ:
                view.update(model, new Tuple<>(TableTypes.TAZType, removeTAZ((String[])obj)));
                break;
                
            case IMPORT_OD:
                
                try {
                    view.update(model, new Tuple<>(TableTypes.ODElementType, importOD((String)obj)));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MogenControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoODFileFormatFoundException ex) {
                    Logger.getLogger(MogenControl.class.getName()).log(Level.SEVERE, null, ex);
                }
        
                break;
                
            case EDIT_PYTHON:
                Config.setPython2((String)obj);
                break;
                
            case EDIT_SUMO:
                Config.setSumoLocation((String)obj);
                break;
                
        } 
    }
    
    
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
        converter.cleanCommand();
        converter.setMap(location, MapAPI.APIS.OSM);
        converter.pruneNodes(location, selection);
        String stream = converter.executeConvert(DEFAULT_MAP_NAME, roads);
        System.out.println("---" + location);
        model.setOSMMap(DEFAULT_MAP_NAME);
        model.getFlows().clear();
        
        model.setNetconvertMap(location);
        hasMap = true;
    }
    
    public void openMap(String location) throws ProtocolException, 
                                                            IOException,
                                                            InterruptedException,
                                                            FileNotFoundException{
        progress = Progress.OPEN_MAP;
        progress.initialize(OPEN_MAP.length);
        view.update(model, progress);
        converter.cleanCommand();
        // Modify second parameter to change the imported map type
        converter.setMap(location, MapAPI.APIS.OSM);
        progress.progress(OPEN_MAP);
        view.update(model, progress);
        String stream = converter.executeConvert(DEFAULT_MAP_NAME, roads);
        model.setOSMMap(DEFAULT_MAP_NAME);
        model.getFlows().clear();
        
        model.setNetconvertMap(location);
        progress.end();
        view.update(model, progress);
        hasMap = true;
    }
    
    public HashMap addFlow(Flow flow) throws NoRouteConnectionException, IOException, 
                                                        InterruptedException{
        // Fist we check if the edges are reachable
        checkConnection(flow.originEdge(), flow.destinationEdge());
        return model.addFlow(flow);
    }
    
    public HashMap editFlow(String id, Flow flow) throws NoRouteConnectionException, IOException, 
                                                        InterruptedException{
        // Fist we check if the edges are reachable
        checkConnection(flow.originEdge(), flow.destinationEdge());
        return model.addFlow(id, flow);
    }
    
    public void checkConnection(String origin, String destination)throws 
            NoRouteConnectionException, IOException, InterruptedException{
        LinkedList <String> command = new LinkedList(Arrays.asList(
                Config.python2,
                Config.sumoLocation + PYTHON_CHECK,
                model.getOSMMap() + FilesExtension.NETCONVERT,
                "--source",
                origin
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
        
        if (!output.contains( "'"+ destination + "'"))
                                throw new NoRouteConnectionException
                                          (Errors.NO_CONNECTION.getErrorMsg());
        System.out.println(output);
        process.waitFor();
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
        mobilityModel.export(location, model.getOSMMap(), vehiclesPath ,this);
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
        
        flowModel.export(location, model.getOSMMap(), vehiclesPath, this);
    }
    
    public void exportODMatrix(String location, int time) throws IOException, InterruptedException {
        
        String vehiclesPath = exportVehicles(DEFAULT_VTYPE_LOCATION 
                                        + FilesExtension.VEHICLES.getExtension());
        ODModel ODmodel = new ODModel(time, model.getTazs(), model.getElements());
        
        ODmodel.export(location, model.getOSMMap(), vehiclesPath, this);
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
        if (hasMap) openMap(model.getNetconvertMap());
        
    }
    
    public void setRoadsFiltered(RoadTypes[] roads){
        for(RoadTypes road : roads){
            this.roads.add(road.toString());
        }
    }
    
    public HashMap addODElemnt(ODElement element){
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
    
    public HashMap importOD(String path) throws FileNotFoundException, NoODFileFormatFoundException{
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("[\\n]");
        Format format = Format.None;
        //pattern to find the OD Elements in a O format file
        String patternStringO = ".*\\s+.*\\s+[0-9]+[.][0-9]+";
        System.out.println(patternStringO);
        
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            // if its a coment continue and ignore it
            if(data.startsWith("*"))continue;
            if(data.startsWith(Format.OType.toString())){
                format = Format.OType;
                break;
            } 
            if(data.startsWith(Format.VType.toString())){
                format = Format.VType;
                break;
            }
        }
        switch(format){
            case None:
                throw new NoODFileFormatFoundException();
            case OType:
                while (scanner.hasNextLine()){
                    if(scanner.hasNext(Pattern.compile(patternStringO))){
                        String data = scanner.nextLine();

                        System.out.println(data);
                    }else{
                        //System.out.println(scanner.nextLine());
                        scanner.nextLine();
                    }
                }
                break;
            case VType:
                break;
        }
        return null;
    }
    
    private void salir() {
        System.exit(0);
    }
    
    public  void handleError(Exception e, Errors error){
        switch(error){
            case OSM_DOWNLOAD:
                //logger.log(Level.SEVERE, error.toString(), e);
                view.update(model, e);
            case NETCONVERT_CMD:
                view.update(model, e);
        }
    }
    
    public static void main(String[] args) {
        new MogenControl(args);
    }   
}
