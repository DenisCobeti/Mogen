package model.followingmodels;

/**
 *
 * @author Neblis
 */
public class PW2009 implements FollowingModel{
    private final static String FORMAT = "carFollowModel=\"%s\" minGap=\"%d\" ";
    private final static String NAME = "PWagner2009";
    private final static String EXPLANATION = "";
    
    private int minGap;

    public static final int MIN_GAP_DFLT = 1;
    
    public PW2009(int minGap) {
        this.minGap = minGap;
    }
    
    public PW2009() {
        this.minGap = MIN_GAP_DFLT;
    }

    public int getMinGap() {
        return minGap;
    }

    public void setMinGap(int minGap) {
        this.minGap = minGap;
    }
    
    @Override
    public String toSimulation() {
        return  String.format(FORMAT, NAME, minGap);
    }
}
