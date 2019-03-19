import javafx.scene.shape.Circle;

public class Piece extends Circle{
    // Properties
    boolean played;
    Color color;

    // Constructor
    public Piece( int radius, int centerX, int centerY, Color color)
    {
        super( centerX, centerY, radius);
        this.color = color;
        played = false;
    }

    // Methods
    public void draw( Graphics g)
    {
        g.setColor( color);

        g.fillOval( (int) getCenterX(), (int) getCenterY(), 2 * (int) getRadius(), 2 * (int) getRadius() );
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
