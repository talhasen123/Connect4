import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

public class Column extends Rectangle
{
    // Properties
    ArrayList<Piece> circles;
    ArrayList<Piece> played;
    int x;
    int y;

    // Constructor
    public Column( int width, int height, int x, int y)
    {
        super( width, height);
        this.x = x;
        this.y = y;
        circles = new ArrayList<>();
        played = new ArrayList<>();
        fillCircles();
    }

    // Methods
    public void fillCircles()
    {
        for ( int i = 0; i < 6; i++)
        {
            Piece c;
            c = new Piece( width / 2 - 4, x + 5, ( height / 6 ) * i + 5, Color.WHITE);
            circles.add( c);
        }

    }

    public void draw( Graphics g)
    {
        g.setColor( Color.CYAN);
        g.drawRect( x, y, (int) getWidth(), (int) getHeight());
        for ( Piece c : circles)
        {
            c.draw( g);
        }
        for ( Piece h : played)
        {
            h.draw( g);
        }
    }

    public void playPiece( Piece piece, Player player)
    {
        piece.setPlayed( true);
        piece.setColor( player.getColor() );
        played.add( piece);
        circles.remove( piece);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public ArrayList<Piece> getCircles() {
        return circles;
    }

    public void setCircles(ArrayList<Piece> circles) {
        this.circles = circles;
    }
}
