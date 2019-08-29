/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author darkm
 */
public class Progress {
    private final int MAX;
    private int current;
    
    private String msg;
    
    public enum Type {
        EXPORT("Exporting trace..."),
        MAP("Downloadig map...");
        
        String msg;
        
        Type (String message){
            this.msg = message;
        }
    }
    
    public Progress(int MAX, Type type) {
        this.MAX = MAX;
        
        current = 0;
        msg = type.msg;
    }
    
    public void setCurrentProgress(int progress, String msg){
        this.current = progress;
        this.msg = msg;
    }
    
}
