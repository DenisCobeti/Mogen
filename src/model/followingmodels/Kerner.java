package model.followingmodels;

/**
 *
 * @author Neblis
 */
public class Kerner implements FollowingModel{
    private final static String FORMAT = "carFollowModel=\"%s\" minGap=\"%d\"  "
                                        + "phi=\"%d\" k=\"%d\"";
    private final static String NAME = "PWagner2009";
    
    private final int minGap;
    private final int k;
    private final int phi;

    public Kerner (int minGap, int phi, int k) {
        this.minGap = minGap;
        this.phi = phi;
        this.k = k;
    }
    
    
    @Override
    public String toSimulation() {
        return  String.format(FORMAT, NAME, minGap, phi, k);
    }
}
