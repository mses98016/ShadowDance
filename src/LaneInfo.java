import bagel.Image;

public class LaneInfo {
    private String type;
    private String direction;
    private int x_coordinate;
    //Get the info of lanes
    public LaneInfo(String type, String direction, int x_coordinate) {
        this.type = type;
        this.direction = direction;
        this.x_coordinate = x_coordinate;
    }
    public String getType() {
        return type;
    }
    public String getDirection() {
        return direction;
    }
    public int getXcoordinate() {
        return x_coordinate;
    }
}
