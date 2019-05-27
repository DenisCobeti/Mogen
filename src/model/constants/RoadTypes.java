package model.constants;

/**
 *
 * @author Neblis
 */
public enum RoadTypes {
    
    TRACK ("highway.track"),
    SERVICES ("highway.services"),
    UNSURFACED ("highway.unsurfaced"),
    BRIDLEWAY ("highway.bridleway"),
    CYCLEWAY ("highway.cycleway"),
    FOOTWAY ("highway.footway"),
    LIVING_STREET ("highway.living_street"),
    MOTORWAY ("highway.motorway"), 
    MOTORWAY_LINK ("highway.motorway_link"),
    PATH ("highway.path"),
    PEDESTRIAN ("highway.pedestrian"),
    PRIMARY ("highway.primary"),
    RACEWAY ("highway.raceway"),
    RESIDENTIAL ("highway.residential"),
    STAIRS ("highway.stairs"),
    
    // railways
    RAIL ("railway.rail"),
    SUBWAY ("railway.subway"),
    TRAM ("railway.tram"),
    FORD ("railway.ford");
    
    private String name;
    
    private RoadTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
