import java.util.ArrayList;

public class ImageInfo {
    private String direction;
    private String type;
    private int frame;
    private int xCoordinate;
    public boolean status;
    private ArrayList<LaneInfo> laneInfoList = new ArrayList<>();
    public ImageInfo(String direction, String type, int frame, int xCoordinate, boolean status) {
        this.direction = direction;
        this.type = type;
        this.frame = frame;
        this.xCoordinate = xCoordinate;
        this.status = status;
    }
    public void setStatus(boolean status){
        this.status = status;
    }
    public String getDirection() {
        return direction;
    }
    public String getType() {
        if(type.equals("Normal")){
            return "note";
        }else{
            return "holdNote";
        }
    }
    public int getFrame() {
        return frame;
    }
    public int getXcoordinate(){
        return xCoordinate;
    }
    public boolean getStatus(){return status;}
}
