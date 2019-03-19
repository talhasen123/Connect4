import java.awt.*;

public class Player
{
    // Properties
    String name;
    Color color;
    boolean isTurn;

    // Constructor
    public Player( String name, Color color)
    {
        this.name = name;
        this.color = color;
        isTurn = false;
    }

    // Methods
    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean isTurn() {
        return isTurn;
    }
}
