package model.sumo;

/**
 *
 * @author Neblis
 */
public class Lane {
    private final double length;
    private final int[] shape;
    
    public final static String TAG = "lane";
    public final static String LENGTH = "length";
    public final static String SHAPE = "shape";

    public Lane(String length, String shape) {
        this.length = Double.valueOf(length);
        this.shape = parseShape(shape.replace(".", "").split("[\\s,]+"));
    }

    @Override
    public String toString() {
        return "Lane{ " + length + ", " + shape + '}';
    }
    
    private static int[] parseShape(String[] shapes){
        int[] shapeNum = new int[shapes.length];
         
        for (int i = 0; i < shapeNum.length; i++){
            shapeNum[i] = Integer.parseInt(shapes[i]);
        }
        return shapeNum;
    }
}
