package control;


import java.io.IOException;
import model.OsmAPI;
import model.constants.Errors;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import model.C4RModel;
import model.MapAPI;
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
        
        
    }
    
    private void obtainMap(double minLon, double minLat, double maxLat, double maxLon){
        
        MapAPI api = null;
        
        try{
            api = new OsmAPI(minLon, minLat, maxLat, maxLon);
        }catch(ProtocolException | MalformedURLException ex){
            C4R.handleError(ex, Errors.OSM_DOWNLOAD);
            
        } catch (IOException ex) {
            C4R.handleError(ex, Errors.FILE_WRITE);
            
        }finally{
            try{ 
                GeoMap map = new GeoMap(api);
            
            } catch (IOException ex) {
                C4R.handleError(ex, Errors.FILE_WRITE);
            }finally{
                convertMap();
            }
        }
    }
    
    private void convertMap(){
        
    }
  
    
}
