package model.followingmodels;

/**
 *
 * @author Neblis
 */
public class Kerner implements FollowingModel{
    private final static String FORMAT = "carFollowModel=\"%s\" minGap=\"%d\"  "
                                        + "phi=\"%d\" k=\"%d\"";
    private final static String NAME = "BKerner";
    public final static String EXPLANATION = "BKerner Model \n"
                            + "In addition "
                            + "to the free flow traffic phase (F), there are two traffic "
                            + "phases in congested traffic: the synchronized flow traffic "
                            + "phase (S) and the wide moving jam phase (J).";
    
    private int minGap;
    private int k;
    private int phi;

    private static final int MIN_GAP_DFLT = 1;
    private static final int K_DFLT = 1;
    private static final int PHI_DFLT = 1;
    
    public Kerner (int minGap, int phi, int k) {
        this.minGap = minGap;
        this.phi = phi;
        this.k = k;
    }
    
    public Kerner () {
        this.minGap = MIN_GAP_DFLT;
        this.phi = PHI_DFLT;
        this.k = K_DFLT;
    }

    public int getMinGap() {
        return minGap;
    }

    public int getK() {
        return k;
    }

    public int getPhi() {
        return phi;
    }

    public void setMinGap(int minGap) {
        this.minGap = minGap;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setPhi(int phi) {
        this.phi = phi;
    }
    
    @Override
    public String toSimulation() {
        return  String.format(FORMAT, NAME, minGap, phi, k);
    }
}
