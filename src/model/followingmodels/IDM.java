package model.followingmodels;

/**
 *
 * @author Neblis
 */
public class IDM implements FollowingModel{
    private final static String FORMAT = "carFollowModel=\"%s\" minGap=\"%d\" "
                                        + "stepping=\"%d\" delta=\"%.1f\"";
    private final static String NAME = "IDM";
    public static final String EXPLANATION = "IDM model\n"
                                + "the intelligent driver model (IDM) is a "
                                + "time-continuous car-following model for the "
                                + "simulation of freeway and urban traffic.";
    
    private int minGap;
    private int stepping;
    private double delta;

    private static final int MIN_GAP_DFLT = 1;
    private static final int STEPPING_DFLT = 3;
    private static final double DELTA_DFLT = 1.5;
    
    public IDM(int minGap, int stepping, double delta) {
        this.minGap = minGap;
        this.stepping = stepping;
        this.delta = delta;
    }
    
    public IDM() {
        this.minGap = MIN_GAP_DFLT;
        this.stepping = STEPPING_DFLT;
        this.delta = DELTA_DFLT;
    }

    public void setMinGap(int minGap) {
        this.minGap = minGap;
    }

    public void setStepping(int stepping) {
        this.stepping = stepping;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public int getMinGap() {
        return minGap;
    }

    public int getStepping() {
        return stepping;
    }

    public double getDelta() {
        return delta;
    }
    
    @Override
    public String toSimulation() {
        return  String.format(FORMAT, NAME, minGap, stepping, delta);
    }
}
