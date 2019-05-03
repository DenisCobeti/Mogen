package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import model.Config;
import model.MapAPI;
import model.constants.Errors;
import model.constants.FilesExtension;

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
        ProcessBuilder netconvert = new ProcessBuilder();
        InputStream in = api.getMap();
        
        copyInputStreamToFile(in,  file);
   
    }
    
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
