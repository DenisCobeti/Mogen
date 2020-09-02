package model.followingmodels;

import java.util.Iterator;
import javax.xml.stream.events.Attribute;

/**
 *
 * @author Denis Florin Cobeti
 */
public class Krauss implements FollowingModel {

    private final static String FORMAT = "carFollowModel=\"%s\" sigma=\"%.1f\""
                                        + " minGap=\"%d\" ";
    public final static String NAME = "Krauss";
    public final static String EXPLANATION = "Krauss model\n" +
                                "The model developed by Krauß is a microscopic, "
                                + "space-continuous, car-following model based on "
                                + "the safe speed in 1997.";
    
    private final static String MIN_GAP = "minGap";
    private final static String SIGMA = "sigma";
    
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

    @Override
    public void importAttributes(Iterator<Attribute> attributes) {
        while (attributes.hasNext()){
            Attribute attribute = attributes.next();

            switch (attribute.getName().toString()){
                case MIN_GAP:
                    this.setMinGap(Integer.valueOf(attribute.getValue()));
                    break;
                    
                case SIGMA:
                    this.setSigma(Double.valueOf(attribute.getValue()));
                    break;   
            }
        }
    }

    
}
