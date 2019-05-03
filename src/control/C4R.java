package control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.C4RModel;
import model.Config;
import model.MapAPI;
import model.OSMApi;
import model.constants.Errors;
import view.C4RView;

/**
 *
 * @author Denis C
 */
public class C4R implements ViewListener{
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    
    private C4RControl control;
    private C4RModel model;
    private C4RView view;
   
    public C4R(String[] args) {
        view = new C4RView(this);
        model = new C4RModel(control, view);
        control = new C4RControl(model, view);
        
        MapAPI api = null;
        
        try{
            api = new OSMApi(0, 0, 0, 0);
        }catch(ProtocolException | MalformedURLException ex){
            handleError(ex, Errors.OSM_DOWNLOAD);
            
        } catch (IOException ex) {
            handleError(ex, Errors.FILE_WRITE);
        }finally{
            try{ GeoMap map = new GeoMap(api);
            } catch (IOException ex) {
                handleError(ex, Errors.FILE_WRITE);
            }
        }
    }

    @Override
    public void producedEvent(Event evento, Object obj) {
        switch (evento) {
            case SALIR:
                salir();
                break;
        } 
    }

    private void salir() {
        System.exit(0);
    }
    
    private void handleError(Exception e, Errors error){
        switch(error){
            case OSM_DOWNLOAD:
                //logger.log(Level.SEVERE, error.toString(), e);
        }
    }
    public static void main(String[] args) {
        new control.C4R(args);
    } 
}
