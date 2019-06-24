package model.followingmodels;

/**
 *
 * @author Denis Florin Cobeti
 */
public class Krauss implements FollowingModel {

    private final static String FORMAT = "carFollowModel=\"%s\" sigma=\"%.1f\""
            + " minGap=\"%d\" ";
    private final static String NAME = "Krauss";
    
    private final double sigma;
    private final int minGap;

    public Krauss(double sigma, int minGap) {
        if (sigma > 1) sigma = 1;
        if (sigma < 0) sigma = 0;
        this.sigma = sigma;
        this.minGap = minGap;
    }
    
    
    @Override
    public String toSimulation() {
        return  String.format(FORMAT, NAME, sigma, minGap);
    }

    
}
