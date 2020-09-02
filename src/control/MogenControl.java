package control;

import static control.ViewListener.TableTypes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
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
import model.warnings.NoNamedDistrictsWarning;

import view.MogenView;


/**
 *
 * @author Denis C
 */
public class MogenControl implements ViewListener{
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    
    private final MogenModel model;
    private final MogenView view;
    
    private static final String VEHICLE_START_XML = "vType";
    private static final String VEHICLE_ID_XML = "id";
    private static final String VEHICLE_ACCEL_XML = "accel";
    private static final String VEHICLE_DECEL_XML = "decel";
    private static final String VEHICLE_TAU_XML = "tau";
    private static final String VEHICLE_LENGTH_XML = "length";
    private static final String VEHICLE_SPEED_XML = "maxSpeed";
    private static final String VEHICLE_FOLLOW_XML = "carFollowModel";
    private static final String VEHICLE_PROB_XML = "probability";
    
   
    public static final String DEFAULT_MAP_NAME = "mapNetconvert";
    public static final String DEFAULT_VTYPE_LOCATION = "vehicles";
    private final static String PYTHON_CHECK = "\\tools\\net\\netcheck.py";
    
    private static final String LOADING_EXPORT = "Exporting file nº";
    private static final String[] LOADING_MAP = {"Connecting to server..." ,
                                                 "Saving file...", 
                                                 "Opening map..."};
    private static final String[] OPEN_MAP = { "Converting map..."};
    
        
    
    private final MapConverter converter;
    private HashSet<String> roads;
    //private HashMap<String, Simulation> simulations;
    private boolean hasMap = false;
    
    private Progress progress;

    public MogenControl(String[] args) {
        
        view = new MogenView(this);
        model = new MogenModel(view);
        roads = new HashSet();
        converter = new MapConverter();
        
        
        File vehicles = new File(DEFAULT_VTYPE_LOCATION 
                    + FilesExtension.VEHICLES.getExtension());
        
        try {
            this.importVehicles(vehicles);
        } catch (FileNotFoundException | XMLStreamException ex) {
            model.defaultVTypes();
        }
        
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
                } catch (NoRouteConnectionException | IOException | 
                            InterruptedException ex) {
                    view.update(model, ex);
                } 
                break;
           case EDIT_FLOW:
                tuple = (Tuple)obj;
                try {
                    
                    view.update(model, new Tuple<>(TableTypes.FlowType, 
                                editFlow((String)tuple.obj1, (Flow)tuple.obj2)));
                } catch (NoRouteConnectionException | IOException | 
                            InterruptedException ex) {
                    view.update(model, ex);
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
                    view.update(model, ex);
                }
                break;
                
            case EXPORT_FLOW:
                tuple = (Tuple)obj;
                try{
                    exportFlows((String)tuple.obj1, (int)tuple.obj2);
                } catch (IOException | InterruptedException ex) {
                    view.update(model, ex);
                }
                break; 
                
            case EXPORT_ODMATRIX:
                tuple = (Tuple)obj;
                try{
                    exportODMatrix((String)tuple.obj1, (int)tuple.obj2);
                } catch (IOException | InterruptedException ex) {
                    view.update(model, ex);
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
                HashSet roadsFiltered = (HashSet)obj;
        
                try {
                    setRoadsFiltered(roadsFiltered);
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
                } catch (FileNotFoundException | NoODFileFormatFoundException ex) {
                    view.update(model, ex);
                }
        
                break;
                
            case REMOVE_OD_ELEMENT:
                view.update(model, new Tuple<>(TableTypes.ODElementType, removeODElement((String[])obj)));
                break;
                
            case EDIT_PYTHON:
                Config.setPython2((String)obj);
                break;
                
            case EDIT_SUMO:
                Config.setSumoLocation((String)obj);
                break;
                
            case IMPORT_VEHICLES:
                
                try {
                    importVehicles(new File((String)obj));
                } catch (FileNotFoundException | XMLStreamException ex) {
                    view.update(model, ex);
                } finally{
                    model.getvTypes().forEach((k, v) -> view.update
                                                (model, new Tuple(k, v)));
                }
                break;
                
            case EXPORT_VEHICLES:
        
                try {
                    exportVehicles((String)obj);
                } catch (IOException ex) {
                    view.update(model, ex);
                }
                break;
        
        } 
    }
    
    
    public void saveMap(MapSelection selection, String location) throws 
                                                            ProtocolException, 
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
    
    public void importVehicles(File vehicles) throws FileNotFoundException, 
                                            XMLStreamException{
        
        
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(vehicles);
        XMLEventReader reader = inputFactory.createXMLEventReader(in);
        
        XMLEvent event;
        
        while (reader.hasNext()){
            event = reader.nextEvent();
            
            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                
                if(startElement.getName().getLocalPart().equals(VEHICLE_START_XML)){
                    VType vehicle = new VType();
                    String  idVehicle = "default";
                    
                    Iterator<Attribute> attributes = startElement.getAttributes();
                    
                    while (attributes.hasNext()){
                        Attribute attribute = attributes.next();
                        
                        switch (attribute.getName().toString()){
                            case VEHICLE_ID_XML:
                                idVehicle = attribute.getValue();
                                break;
                            case VEHICLE_ACCEL_XML:
                                vehicle.setAccel(Double.valueOf(attribute.getValue()));
                                break;
                            case VEHICLE_DECEL_XML:
                                vehicle.setDecel(Double.valueOf(attribute.getValue()));
                                break;
                            case VEHICLE_TAU_XML:
                                vehicle.setTau(Double.valueOf(attribute.getValue()));
                                break;
                            case VEHICLE_LENGTH_XML:
                                vehicle.setLength(Integer.valueOf(attribute.getValue()));
                                break;
                            case VEHICLE_SPEED_XML:
                                vehicle.setMaxSpeed(Integer.valueOf(attribute.getValue()));
                                break;
                            case VEHICLE_FOLLOW_XML:
                                vehicle.assignFollowingModel(attribute.getValue(), startElement.getAttributes());
                                break;
                            case VEHICLE_PROB_XML:
                                vehicle.setProbability(Double.valueOf(attribute.getValue()));
                                break;
                        }
                    }
                    model.addElement(idVehicle, vehicle);
                   
                }
                
            }
        }
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
            progress.progress(LOADING_EXPORT + num);
            view.update(model, progress);
        }
    }
    
    public void endExport(){
        progress.end();
        view.update(model, progress);
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
    
    public HashMap removeODElement(String[] ODEid) {
        for(String id : ODEid){
            model.removeODE(id);
        }
        return model.getElements();
    }
    
    public HashMap importOD(String path) throws FileNotFoundException, 
                                                NoODFileFormatFoundException{
        File file = new File(path);
        HashMap<String, ODElement> map = new HashMap();
        
        try (Scanner scanner = new Scanner(file)) {
            Format format = Format.None;
            //pattern to find the OD Elements in a O format file
            String patternStringO = ".*\\s+.*\\s+[0-9]+[\\.][0-9]+";
            
            while (scanner.hasNextLine()){
                String data = scanner.nextLine();
                // if its a comment continue and ignore it
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
                // we scan the file differently deppending on the format
                case None:
                    throw new NoODFileFormatFoundException(Errors.INCORRECT_MATRIX_FORMAT);
                case OType:
                    while (scanner.hasNextLine()){
                        // get the line based on regular expresion
                        scanner.nextLine();
                        String result = scanner.findInLine(patternStringO);
                        
                        if(result != null){
                            // Split the line to get the individual elements
                            String[] elements = result.trim().split("\\s+");
                            
                            if(elements.length == 3){
                                // unique ID for the hash map
                                String uniqueID = UUID.randomUUID().toString();
                                
                                ODElement ODelement = new ODElement(elements[0], 
                                    elements[1], Double.parseDouble(elements[2]));
                                
                                map.put(uniqueID, ODelement);
                            }
                        }
                    }
                    break;
                case VType:
                    break;
            }
            HashSet<String> notFoundTAZ = new HashSet();
            Set <String> namedTAZ = model.getTazs().keySet();
            
            // we search if any of the destinations or origins are definied in the model
            map.values().forEach(element -> {
                if(!namedTAZ.contains(element.getOrigin()) && 
                   !notFoundTAZ.contains(element.getOrigin())) 
                    
                        notFoundTAZ.add(element.getOrigin());
                if(!namedTAZ.contains(element.getDestination()) && 
                   !notFoundTAZ.contains(element.getDestination())) 
                    
                        notFoundTAZ.add(element.getDestination());
            });
            
            if(!notFoundTAZ.isEmpty()){
                view.update(model, new NoNamedDistrictsWarning(notFoundTAZ));
            }
            return model.addODElement(map);
        }
    }
    
    private void salir() {
        try {
            exportVehicles(DEFAULT_VTYPE_LOCATION + FilesExtension.VEHICLES.getExtension());
        } catch (IOException ex) {}
        
        System.exit(0);
    }
    
    public static void main(String[] args) {
        new MogenControl(args);
    }   
}
