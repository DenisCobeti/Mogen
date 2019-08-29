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
public enum Progress {
    EXPORT("Exporting trace..."),
    MAP("Downloadig map...");
    
    private int max;
    private int current  = 0;
    private boolean starting = false;
    
    private String msg;
    
    
    Progress(String message) {
        this.msg = message;
    }

    public void setCurrent(int current, int max) {
        this.current = current;
        this.max = max;
    }
    
    public void initialize(int max){
        current = 0;
        starting = true;
        this.max = max;
    }
    
    public void setCurrentProgress(int progress, String msg){
        this.current = progress;
        this.msg = msg;
    }
    
    public void progress(String msg){
        starting = false;
        current++;
        this.msg = msg;
    }
    public void progress(){
        starting = false;
        current++;
    }

    public static Progress getEXPORT() {
        return EXPORT;
    }

    public static Progress getMAP() {
        return MAP;
    }

    public int getMax() {
        return max;
    }

    public int getCurrent() {
        return current;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
