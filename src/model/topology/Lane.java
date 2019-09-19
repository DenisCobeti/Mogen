package model.topology;

import javafx.scene.shape.Polyline;

/**
 *
 * @author Neblis
 */
public class Lane  {
    private final double length;
    private final String name;
    private final Polyline polyline;
    private final boolean internal;
    
    public final static String TAG = "lane";
    public final static String LENGTH = "length";
    public final static String SHAPE = "shape";

    private final static double WIDTH = 2;
    
    public Lane(String name, String length, String shape) {
        this.length = Double.valueOf(length);
        this.name = name;
        //int[] shapeInt = parseShape(shape.replace(".", "").split("[\\s,]+"));
        double[] shapePoints = parseShape(shape.split("[\\s,]+"));
        polyline = new Polyline(shapePoints);
        polyline.setStrokeWidth(Lane.WIDTH);
        internal = false;
    }
    
    public Lane(String name, String length, String shape, boolean internal) {
        this.length = Double.valueOf(length);
        this.name = name;
        this.internal = internal;
        //int[] shapeInt = parseShape(shape.replace(".", "").split("[\\s,]+"));
        double[] shapePoints = parseShape(shape.split("[\\s,]+"));
        polyline = new Polyline(shapePoints);
        polyline.setStrokeWidth(Lane.WIDTH);
    }

    @Override
    public String toString() {
        return name;
    }
    
    public String description() {
        return "Lane " + name + " { " + length + ", " + polyline + '}';
    }

    public boolean isInternal() {
        return internal;
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
