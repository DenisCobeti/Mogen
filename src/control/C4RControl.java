package control;


import java.io.IOException;
import model.OsmAPI;
import model.constants.Errors;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import model.C4RModel;
import model.Config;
import model.MapAPI;
import model.MapSelection;
import view.C4RView;

/**
 *
 * @author Denis C
 */
public class C4RControl {
    private C4RModel model;
    private C4RView view;

    public C4RControl(C4RModel model, C4RView view) {
        this.model = model;
        this.view = view;
        
        //obtainMap(0,0,0,0);
        Config.load();
        System.out.println(Config.osmMap +" " +Config.sumoMap);
    }
    
    public void obtainMap(MapSelection selection){
        
        MapAPI api = null;
        
        try{
            api = new OsmAPI(selection.minLon, selection.minLat, 
                                selection.maxLat, selection.maxLon);
        }catch(ProtocolException | MalformedURLException ex){
            C4R.handleError(ex, Errors.OSM_DOWNLOAD);
            
        } catch (IOException ex) {
            C4R.handleError(ex, Errors.FILE_WRITE);
            
        }finally{
            try{ 
                GeoMap map = new GeoMap(api);
                
            } catch (IOException ex) {
                C4R.handleError(ex, Errors.FILE_WRITE);
            }
        }
    }
    
    private void convertMap(){
        
    }
  
    
}
