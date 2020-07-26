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
    SERVICE ("highway.service"),
    PEDESTRIAN ("highway.pedestrian"),
    PRIMARY ("highway.primary"),
    RACEWAY ("highway.raceway"),
    RESIDENTIAL ("highway.residential"),
    STAIRS ("highway.stairs"),
    STEPS ("highway.steps"),
    STEP ("highway.step"),
    BUS ("highway.bus_guideway"),
    
    // railways
    RAIL ("railway.rail"),
    SUBWAY ("railway.subway"),
    TRAM ("railway.tram"),
    FORD ("railway.ford"),
    NARROW ("railway.narrow_gauge"),
    MONORAIL ("railway.monorail"),
    FUNICULAR ("railway.funicular");
    
    private String name;
    
    private RoadTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
