package model.followingmodels;

/**
 *
 * @author Neblis
 */
public class IDM implements FollowingModel{
    private final static String FORMAT = "carFollowModel=\"%s\" minGap=\"%d\" "
                                        + "stepping=\"%d\" delta=\"%.1f\"";
    private final static String NAME = "PWagner2009";
    
    private final int minGap;
    private final int stepping;
    private final double delta;

    public IDM(int minGap, int stepping, double delta) {
        this.minGap = minGap;
        this.stepping = stepping;
        this.delta = delta;
    }
    
    
    @Override
    public String toSimulation() {
        return  String.format(FORMAT, NAME, minGap, stepping, delta);
    }
}
