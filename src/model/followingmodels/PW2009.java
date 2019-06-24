package model.followingmodels;

/**
 *
 * @author Neblis
 */
public class PW2009 implements FollowingModel{
    private final static String FORMAT = "carFollowModel=\"%s\" minGap=\"%d\" ";
    private final static String NAME = "PWagner2009";
    
    private final int minGap;

    public PW2009(int minGap) {
        this.minGap = minGap;
    }
    
    
    @Override
    public String toSimulation() {
        return  String.format(FORMAT, NAME, minGap);
    }
}
