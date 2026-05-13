import bagel.Image;
import java.util.HashMap;

public class ImageManager {
    private final HashMap<String, Image> images;
    String direction;
    String type;
    public ImageManager() {
        images = new HashMap<>();
        loadImages();
    }
    // Load all the images
    private void loadImages() {
        images.put("Left", new Image("res/laneLeft.png"));
        images.put("Right", new Image("res/laneRight.png"));
        images.put("Up", new Image("res/laneUp.png"));
        images.put("Down", new Image("res/laneDown.png"));
        images.put("noteLeft", new Image("res/noteLeft.png"));
        images.put("noteRight", new Image("res/noteRight.png"));
        images.put("noteUp", new Image("res/noteUp.png"));
        images.put("noteDown", new Image("res/noteDown.png"));
        images.put("holdNoteLeft", new Image("res/holdNoteLeft.png"));
        images.put("holdNoteRight", new Image("res/holdNoteRight.png"));
        images.put("holdNoteUp", new Image("res/holdNoteUp.png"));
        images.put("holdNoteDown", new Image("res/holdNoteDown.png"));
    }
    // Draw images based on ImageInfo
    public void drawImageBasedOnType(ImageInfo imageInfo, int yCoordinate) {
        String imageName = getImageName(imageInfo.getDirection(), imageInfo.getType());
        Image image = images.get(imageName);
        if (image != null) {
            image.draw(imageInfo.getXcoordinate(), yCoordinate);
        }
    }
    // Draw lanes based on LaneInfo
    public void drawLane(LaneInfo laneInfo){
        String imageName = getLaneName(laneInfo.getDirection());
        Image image = images.get(imageName);
        if (image != null) {
            image.draw(laneInfo.getXcoordinate(), 384);
        }
    }
    // Map image names based on direction and type
    private String getImageName(String direction, String type) {
        return type + direction;
    }
    private String getLaneName(String direction) {
        return direction;
    }
    private String getImageDirection(){
        return direction;
    }
    private String getImageType(){
        return type;
    }
}
