package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;
import model.constants.Netconvert;

/**
 *
 * @author Denis C
 */
public interface MapAPI {
    
    public InputStream getMap() throws MalformedURLException, ProtocolException, IOException;
    
    public List<String> netconvertCommand(List<Netconvert> options, 
                                        String osmFile, String netconvertFile );
    public List<String> netconvertCommand(String osmFile, String netconvertFile);
}
