package model.followingmodels;

import java.util.Iterator;
import javax.xml.stream.events.Attribute;

/**
 *
 * @author Neblis
 */
public class PW2009 implements FollowingModel{
    private final static String FORMAT = "carFollowModel=\"%s\" minGap=\"%d\" ";
    public final static String NAME = "PWagner2009";
    public final static String EXPLANATION = "PWagner model \n "
                                        + "A model by Peter Wagner, using "
                                        + "Todosiev's action points ";
    
    private final static String MIN_GAP = "minGap";
    
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

    @Override
    public void importAttributes(Iterator<Attribute> attributes) {
        while (attributes.hasNext()){
            Attribute attribute = attributes.next();

            switch (attribute.getName().toString()){
                case MIN_GAP:
                    this.setMinGap(Integer.valueOf(attribute.getValue()));
                    break;  
            }
        }
    }
}
