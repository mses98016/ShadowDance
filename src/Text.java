import bagel.Font;
import bagel.DrawOptions;
import bagel.util.Colour;

public class Text {
    private Font font;
    private DrawOptions drawOptions;
    public Text() {
        font = new Font("res/FSO8BITR.ttf", 48); // Only pass font path and size
        drawOptions = new DrawOptions();
        drawOptions.setBlendColour(Colour.WHITE); // Set font color here (optional)
    }
    public void draw(String text, int y, int fontSize) {
        Font customFont = new Font("res/FSO8BITR.ttf", fontSize);
        double textWidth = customFont.getWidth(text);
        double x = 512 - (textWidth / 2.0);
        // Create a custom font with the specified size
        customFont.drawString(text, x, y, drawOptions);
    }
    public void draw(String text, int x, int y, int fontSize) {
        Font customFont = new Font("res/FSO8BITR.ttf", fontSize);
        // Create a custom font with the specified size
        customFont.drawString(text, x, y, drawOptions);
    }
    public void draw(String text, int fontSize) {
        Font customFont = new Font("res/FSO8BITR.ttf", fontSize);
        double textWidth = customFont.getWidth(text);
        double textHeight = fontSize;
        double x = 512 - (textWidth / 2.0);
        double y = 384 - (textHeight / 2.0);
        // Create a custom font with the specified size
        customFont.drawString(text, x, y, drawOptions);
    }
}
