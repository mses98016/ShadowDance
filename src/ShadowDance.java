import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2023
 * Please enter your name below
 * @Yung-Hsuan, Chen
 */
public class ShadowDance extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private final Text text;
    private final ImageManager imageManager;
    private final ArrayList<LaneInfo> laneInfoList = new ArrayList<>();
    private ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
    private AudioPlayer audioPlayer;
    private boolean spacePressed = false;
    private boolean gameEnd = false;
    private int currentFrame = 0;
    private int score = 0;
    private int textFrameCount = 0;
    int pressCount = 0;
    int length = 0;
    String distance = "Start";
    boolean noteHold = false;

    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        text = new Text();
        imageManager = new ImageManager();
        audioPlayer = new AudioPlayer();
        readCSV();
        length = imageInfoList.size();
    }
    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV() {
        String filePath = "res/level1.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                // Split the CSV line into fields
                String[] fields = line.split(",");
                count ++;
                if (fields.length == 3) {
                    //Add the first four row of data in laneInfo List and the rest add in the imageInfoList
                    if(count <=4) {
                        String lane = fields[0].trim();
                        String direction = fields[1].trim();
                        int xCoordinate = Integer.parseInt(fields[2].trim());
                        laneInfoList.add(new LaneInfo(lane, direction, xCoordinate));
                    } else {
                        String direction = fields[0].trim();
                        String type = fields[1].trim();
                        int frame = Integer.parseInt(fields[2].trim());
                        for(LaneInfo laneInfo : laneInfoList) {
                            if (laneInfo.getDirection().equals(direction)) {
                                int xCoordinate = laneInfo.getXcoordinate();
                                imageInfoList.add(new ImageInfo(direction, type, frame, xCoordinate, false));
                            }
                        }
                    }
                }
                // Process the fields as needed
                for (String field : fields) {
                    System.out.print(field + "\t");
                }
                System.out.println(); // Move to the next line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }
    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        int getScore = 0;
        int count = 0;
        boolean action = false;
        int yCoordinate;
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        if (!spacePressed && gameEnd == false) {
            text.draw(GAME_TITLE, 220, 250, 64);
            text.draw("Press space to start\nUse arrow keys to play", 320, 440, 24);// Display start text
            if (input.wasPressed(Keys.SPACE)) {
                spacePressed = true; // Set the flag to true when space is pressed
            }
        } else if(gameEnd == false){
            //Play the music at the start of the game
            if(currentFrame == 0){
                audioPlayer.play("res/track1.wav");
            }
            currentFrame++;
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            //Draw lane
            for (LaneInfo laneInfo : laneInfoList) {
                imageManager.drawLane(laneInfo);
            }
            //Draw image
            ImageDistanceManager distanceManager = new ImageDistanceManager();
            for (ImageInfo imageInfo : imageInfoList) {
                count++;
                if (currentFrame >= imageInfo.getFrame() && !imageInfo.getStatus()) {
                    if(imageInfo.getType().equals("note")){
                        yCoordinate = 100 + (currentFrame - imageInfo.getFrame()) * 2;
                    }else{
                        yCoordinate = 24 + (currentFrame - imageInfo.getFrame()) * 2;
                    }
                    if (input.wasPressed(getKeyForDirection(imageInfo.getDirection())) && !action && !noteHold) {
                        if(imageInfo.getType().equals("note")) {
                            distance = distanceManager.noteDistance(yCoordinate);
                        }else{
                            distance = distanceManager.noteDistance(yCoordinate + 82);
                        }
                        getScore = distanceManager.noteScore(distance);
                        action = true;
                        pressCount++;
                        if(imageInfo.getType().equals("note")) {
                            imageInfo.setStatus(true);
                        }else{
                            noteHold = true;
                        }
                    }else if(imageInfo.getType().equals("holdNote") && input.wasReleased(getKeyForDirection(imageInfo.getDirection())) && noteHold){
                        distance = distanceManager.noteDistance(yCoordinate - 82);
                        getScore = distanceManager.noteScore(distance);
                        pressCount++;
                        action = true;
                        imageInfo.setStatus(true);
                        noteHold = false;
                    } else if (yCoordinate >= 768) {
                        imageInfo.setStatus(true);
                        pressCount++;
                        getScore = -5;
                        distance = "Miss";
                    }
                    // Draw the note if necessary (only if status is still false)
                    if (!imageInfo.getStatus()  && yCoordinate <= 768) {
                        imageManager.drawImageBasedOnType(imageInfo, yCoordinate);
                    }
                    //When the last note has been processed, game end
                    if(count == length && imageInfo.getStatus() == true){
                        gameEnd = true;
                    }
                }
            }
            score = score + getScore;
            text.draw("Score " + score, 10, 50, 36);
            //Displayed the accuracy text for 30 frames
            if (textFrameCount < 30 && distance != "Start") {
                text.draw(distance, 384, 64);
                textFrameCount++;
            }
            if(textFrameCount == 30){
                textFrameCount = 0;
                distance = "Start";
            }
        } else if(gameEnd){
            if(score >= 150) {
                text.draw("CLEAR!",64);
            }else{
                text.draw("TRY AGAIN", 64);
            }
        }
    }
    //Get the corresponding arrow key with the direction of notes
    private Keys getKeyForDirection(String direction) {
        switch (direction) {
            case "Left":
                return Keys.LEFT;
            case "Right":
                return Keys.RIGHT;
            case "Up":
                return Keys.UP;
            case "Down":
                return Keys.DOWN;
            default:
                return null; // Return an invalid key code if direction is not recognized
        }
    }
}