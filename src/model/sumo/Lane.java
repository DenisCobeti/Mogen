package model.sumo;

import javafx.scene.shape.Polyline;

/**
 *
 * @author Neblis
 */
public class Lane  {
    private final double length;
    private final String name;
    private final Polyline polyline;
    
    public final static String TAG = "lane";
    public final static String LENGTH = "length";
    public final static String SHAPE = "shape";

    public Lane(String name, String length, String shape) {
        this.length = Double.valueOf(length);
        this.name = name;
        //int[] shapeInt = parseShape(shape.replace(".", "").split("[\\s,]+"));
        double[] shapePoints = parseShape(shape.split("[\\s,]+"));
        polyline = new Polyline(shapePoints);
        
    }

    @Override
    public String toString() {
        return "Lane " + name + " { " + length + ", " + polyline + '}';
    }
    /*
    private static int[] parseShape(String[] shapes){
        int[] shapeNum = new int[shapes.length];
         
        for (int i = 0; i < shapeNum.length; i++){
            shapeNum[i] = Integer.parseInt(shapes[i]);
        }
        return shapeNum;
    }*/
    private static double[] parseShape(String[] shapes){
        double[] shapeNum = new double[shapes.length];
         
        for (int i = 0; i < shapeNum.length; i++){
            shapeNum[i] = Double.parseDouble(shapes[i]);
            
        }
        return shapeNum;
    }

    public String getName() {
        return name;
    }
    
    public Polyline getPolyline() {
        return polyline;
    }
    
}
