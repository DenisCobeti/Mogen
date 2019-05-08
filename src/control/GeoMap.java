package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        
        //InputStream in = api.getMap();
        File osmFile = new File(Config.osmMap + FilesExtension.OSM);
        File netconvertFile = new File(Config.sumoMap + FilesExtension.NETCONVERT);
        ProcessBuilder netconvert = new ProcessBuilder(api.netconvertCommand
               (osmFile.getCanonicalPath(), netconvertFile.getCanonicalPath()));
        //copyInputStreamToFile(in, osmFile);
        netconvertFile.createNewFile();
        try{
            
            netconvert.start();
            System.out.println(api.netconvertCommand
               (osmFile.getCanonicalPath(), netconvertFile.getCanonicalPath()));
        }catch(IOException e){
            C4R.handleError(e, Errors.NETCONVERT_CMD);
        }
    }
    
    //esto esta copiado, habra que cambiarlo
    private static void copyInputStreamToFile(InputStream inputStream, File file) 
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
