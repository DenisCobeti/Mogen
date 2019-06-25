package model.followingmodels;

/**
 *
 * @author Denis Florin Cobeti
 */
public class Krauss implements FollowingModel {

    private final static String FORMAT = "carFollowModel=\"%s\" sigma=\"%.1f\""
            + " minGap=\"%d\" ";
    private final static String NAME = "Krauss";
    public final static String EXPLANATION = "Krauss model\n" +
                                "The model developed by Krauß is a microscopic,\n"
                                + "space-continuous, car-following model based on\n"
                                + "the safe speed in 1997.";
    
    private double sigma;
    private int minGap;
    
    public final static double SIGMA_DFLT = 0.5;
    public static final int MIN_GAP_DFLT = 1;

    
    public Krauss(double sigma, int minGap) {
        if (sigma > 1) sigma = 1;
        if (sigma < 0) sigma = 0;
        this.sigma = sigma;
        this.minGap = minGap;
    }
    public Krauss() {
        this.sigma = SIGMA_DFLT;
        this.minGap = MIN_GAP_DFLT;
    }

    public double getSigma() {
        return sigma;
    }

    public int getMinGap() {
        return minGap;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public void setMinGap(int minGap) {
        this.minGap = minGap;
    }
    
    @Override
    public String toSimulation() {
        return  String.format(FORMAT, NAME, sigma, minGap);
    }

    
}
