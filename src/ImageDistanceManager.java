public class ImageDistanceManager {
    private static final int PERFECT_DISTANCE = 15;
    private static final int GOOD_DISTANCE = 50;
    private static final int BAD_DISTANCE = 100;
    private static final int MISS_DISTANCE = 200;
    private int distance;
    private int textFrameCount;
    private boolean textDisplayed;
    public ImageDistanceManager() {
        distance = 0;
        textFrameCount = 0;
        textDisplayed = false;
    }
    //Return true if the note leave the bottom of the screen
    public boolean isMiss(int yCoordinate) {
        return yCoordinate >= 768;
    }
    //Get the pressed accuracy based on the differeny condition
    public String noteDistance(int yCoordinate){
        int distance = Math.abs(yCoordinate - 657);
        if(distance <= PERFECT_DISTANCE){
            return "Perfect";
        }else if(PERFECT_DISTANCE < distance && distance <= GOOD_DISTANCE){
            return "Good";
        }else if(GOOD_DISTANCE < distance && distance <= BAD_DISTANCE){
            return "Bad";
        }else if(BAD_DISTANCE < distance && distance <= MISS_DISTANCE){
            return "Miss";
        }
        return null;
    }
    //Get the note score based on its accuracy
    public int noteScore(String distance){
        if(distance.equals("Perfect")){
            return 10;
        }else if(distance.equals("Good")){
            return 5;
        }else if(distance.equals("Bad")){
            return -1;
        }else if(distance.equals("Miss")){
            return -5;
        }else{
            return 0;
        }
    }
}
