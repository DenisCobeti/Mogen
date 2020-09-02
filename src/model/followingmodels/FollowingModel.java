package model.followingmodels;

import java.util.Iterator;
import javax.xml.stream.events.Attribute;

/**
 *
 * @author Denis Florin Cobeti
 */
public interface  FollowingModel {
    
    public String toSimulation();
    public void importAttributes(Iterator<Attribute> attributes);
    
}
