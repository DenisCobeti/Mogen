/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 *
 * @author Neblis
 */
public class OSMApi {

   
    
    public OSMApi() {}
    
    public void getMapAPI(double lat, double lon) throws ProtocolException, IOException, MalformedURLException{
       
        HttpURLConnection connection = creteConnection(Config.API);
        connection.setRequestMethod("GET");
        BufferedReader rd = null;
           String linea;
        rd = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        while((linea = rd.readLine()) != null)
        System.out.println(linea);
        rd.close();
        
    }
    
    private HttpURLConnection creteConnection(String request) 
            throws MalformedURLException, IOException{
        
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        
        return connection;
    }
}
