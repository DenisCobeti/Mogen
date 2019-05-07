package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import model.Config;
import model.MapAPI;
import model.constants.Errors;
import model.constants.FilesExtension;
import model.constants.Netconvert;

/**
 * Map once already converted using netconvert
 * @author Denis C
 */
public class GeoMap {
    
    //private static final Logger logger = Logger.getLogger(GeoMap.class.getName());
    
    private static  String convertString;
    private MapAPI api;
    

    public GeoMap(MapAPI api) throws IOException {
        
        File file = new File(Config.OSM_MAP + FilesExtension.OSM);
        InputStream in = api.getMap();
        ProcessBuilder netconvert = new ProcessBuilder(api.netconvertCommand());
       
        copyInputStreamToFile(in, file);
        
        try{
            netconvert.start();
        }catch(IOException e){
            C4R.handleError(e, Errors.NETCONVERT_CMD);
        }
    }
    
    //esto esta copiado, habra que cambiarlo
    private static void copyInputStreamToFile(InputStream inputStream, File file) 
		throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
    
    
}
