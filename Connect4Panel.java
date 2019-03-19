import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Connect4Panel. Connect4 game's panel and its funtionality. To be continued...
 * @author Talha Şen
 * @version 20.06.2018
 * -------------------
 * Added the panel's general look
 * Added columns, where the pieces are played.
 * Added hover display which shows where the piece will be played if played, in the selected column.
 * Added play functionality which plays the hovered piece.
 * To be added - Players.
 * To be added - Turned plays.
 * To be added - 4 piece checking (Recursion).
 * To be fixed - If a piece is played on a fully played column, the last hovered piece is played.
 * To be fixed - Latency after a piece is played.
 * To be fixed - Inconsistency of the piece display, hover and played.
 * -------------------
 * @version 21.06.2018
 * -------------------
 * Added - Players and turned plays.
 * Partially Added - The functionality of the game, now if the player can make a 4 in a row piece play, he wins. Vertical
 * and horizontal comparisons work but diagonal comparison needs work. Same with edge plays.
 * Fixed - If a piece is played on a fully played column, the last hovered piece is played.
 * Fixed - Latency after a piece is played.
 * Fixed - Inconsistency of the piece display, hover and played.
 * -------------------
 * @version 22.06.2018
 * -------------------
 * Game is now fully functional. Win the game by placing your pieces in a way that 4 pieces get in a line either vertical,
 * horizontal or diagonal. While trying to achieve that, try to block your opponent's 4 in a row progression by placing
 * your pieces around theirs.
 * To be added - Players and their turns' displays.
 * To be added - A menu before start.
 * To be added - A timer for piece plays.
 */

public class Connect4Panel extends JPanel
{
    // Properties
    ArrayList<Column> columns;
    Player[][] pieceAssignments;
    Column selectedColumn;
    Piece hovered;
    int selectedColumnPlace;
    int hoveredPlace;
    Player player1;
    Player player2;

    // Constructor
    public Connect4Panel( String player1s, String player2s)
    {
        setPreferredSize( new Dimension( 500, 500) );
        setSize( new Dimension( 500, 500) );
        setBackground( Color.BLUE);
        columns = new ArrayList<>();
        pieceAssignments = new Player[7][6];
        selectedColumn = null;
        selectedColumnPlace = 0;
        hoveredPlace = 0;
        player1 = new Player( player1s, Color.YELLOW);
        player2 = new Player( player2s, Color.RED);
        player1.setTurn( true);
        fillColumns();
        addMouseListener( new MyMouseAdapter());
        addMouseMotionListener( new MyMouseAdapter());
    }

    // Methods
    public void fillColumns()
    {
        for ( int i = 0; i < 7; i++)
        {
            Column column = new Column( getWidth() / 7, 500, (getWidth() / 7 ) * i, 0);
            columns.add( column);
        }
    }

    public void assignHovered()
    {
        if ( selectedColumn.getCircles().size() != 0) {
            hovered = selectedColumn.getCircles().get(selectedColumn.getCircles().size() - 1);
            hoveredPlace = selectedColumn.getCircles().size() - 1;
            if ( player1.isTurn())
            {
                hovered.setColor( Color.ORANGE);
            }
            else
            {
                hovered.setColor( Color.PINK);
            }
        }
        else
        {
            hovered = null;
        }
    }

    public int check4InRowN( Player player, int hoveredPlace)
    {
        if ( hoveredPlace >= 0)
        {
            if ( pieceAssignments[ selectedColumnPlace][ hoveredPlace] == player)
            {
                return 1 + check4InRowN( player, hoveredPlace - 1);
            }
        }
        return 0;
    }

    public int check4InRowW( Player player, int selectedColumnPlace)
    {
        if ( selectedColumnPlace >= 0 )
        {
            if ( pieceAssignments[ selectedColumnPlace][ hoveredPlace] == player)
            {
                return 1 + check4InRowW( player, selectedColumnPlace - 1);
            }
        }
        return 0;
    }

    public int check4InRowS( Player player, int hoveredPlace)
    {
        if ( hoveredPlace < 6)
        {
            if ( pieceAssignments[ selectedColumnPlace][ hoveredPlace] == player)
            {
                return 1 + check4InRowS( player, hoveredPlace + 1);
            }
        }
        return 0;
    }

    public int check4InRowE( Player player, int selectedColumnPlace)
    {
        if ( selectedColumnPlace < columns.size() )
        {
            if ( pieceAssignments[ selectedColumnPlace][ hoveredPlace] == player)
            {
                return 1 + check4InRowE( player, selectedColumnPlace + 1);
            }
        }
        return 0;
    }

    public int check4InRowNW( Player player, int selectedColumnPlace, int hoveredPlace)
    {
        if ( selectedColumnPlace >= 0 && hoveredPlace >= 0)
        {
            if ( pieceAssignments[ selectedColumnPlace][ hoveredPlace] == player)
            {
                return 1 + check4InRowNW( player, selectedColumnPlace - 1, hoveredPlace - 1);
            }
        }
        return 0;
    }

    public int check4InRowNE( Player player, int selectedColumnPlace, int hoveredPlace)
    {
        if ( selectedColumnPlace < columns.size() && hoveredPlace >= 0)
        {
            if ( pieceAssignments[ selectedColumnPlace][ hoveredPlace] == player)
            {
                return 1 + check4InRowNE( player, selectedColumnPlace + 1, hoveredPlace - 1);
            }
        }
        return 0;
    }

    public int check4InRowSW( Player player, int selectedColumnPlace, int hoveredPlace)
    {
        if ( selectedColumnPlace >= 0 && hoveredPlace < 6 )
        {
            if ( pieceAssignments[ selectedColumnPlace][ hoveredPlace] == player)
            {
                return 1 + check4InRowSW( player, selectedColumnPlace - 1, hoveredPlace + 1);
            }
        }
        return 0;
    }

    public int check4InRowSE( Player player, int selectedColumnPlace, int hoveredPlace)
    {
        if ( selectedColumnPlace < columns.size() && hoveredPlace < 6 )
        {
            if ( pieceAssignments[ selectedColumnPlace][ hoveredPlace] == player)
            {
                return 1 + check4InRowSE( player, selectedColumnPlace + 1, hoveredPlace + 1);
            }
        }
        return 0;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent( g);
        for ( Column column : columns)
        {
            column.draw( g);
        }
        if ( hovered != null)
        {
            hovered.draw( g);
        }
    }

    private class MyMouseAdapter extends MouseAdapter
    {
        @Override
        public void mouseMoved(MouseEvent e) {
            for ( int i = 0; i < columns.size(); i++)
            {
                if ( e.getX() >= columns.get( i).getX() && e.getX() <= columns.get( i).getX() + columns.get( i).getWidth() )
                {
                    selectedColumn = columns.get( i);
                    selectedColumnPlace = i;
                }
            }

            if ( hovered != null) {
                hovered.setColor(Color.WHITE);
                repaint();
            }

            assignHovered();
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if ( hovered != null) {
                if ( player1.isTurn) {
                    selectedColumn.playPiece(hovered, player1);
                    pieceAssignments[ selectedColumnPlace][ hoveredPlace] = player1;

                    if ( check4InRowE( player1, selectedColumnPlace) + check4InRowW( player1, selectedColumnPlace) >= 5 ||
                            check4InRowN( player1, hoveredPlace) + check4InRowS( player1, hoveredPlace) >= 5 ||
                            check4InRowNW( player1, selectedColumnPlace, hoveredPlace) + check4InRowSE( player1, selectedColumnPlace, hoveredPlace) >= 5 ||
                            check4InRowNE( player1, selectedColumnPlace, hoveredPlace) + check4InRowSW( player1, selectedColumnPlace, hoveredPlace) >= 5)
                    {
                        JOptionPane.showMessageDialog( null, "" + player1.getName() + " WON!");
                        System.exit( 0);
                    }
                    else {
                        player2.setTurn(true);
                        player1.setTurn(false);
                    }
                }
                else
                {
                    selectedColumn.playPiece(hovered, player2);
                    pieceAssignments[ selectedColumnPlace][ hoveredPlace] = player2;

                    if ( check4InRowE( player2, selectedColumnPlace) + check4InRowW( player2, selectedColumnPlace) >= 5 ||
                            check4InRowN( player2, hoveredPlace) + check4InRowS( player2, hoveredPlace) >= 5 ||
                            check4InRowNW( player2, selectedColumnPlace, hoveredPlace) + check4InRowSE( player2, selectedColumnPlace, hoveredPlace) >= 5 ||
                            check4InRowNE( player2, selectedColumnPlace, hoveredPlace) + check4InRowSW( player2, selectedColumnPlace, hoveredPlace) >= 5)
                    {
                        JOptionPane.showMessageDialog( null, "" + player2.getName() + " WON!");
                        System.exit( 0);
                    }
                    else {
                        player2.setTurn(false);
                        player1.setTurn(true);
                    }
                }
            }
            assignHovered();
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hovered.setColor( Color.WHITE);
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame( "Connect4");
        ImageIcon ımageIcon = new ImageIcon( "C:\\Users\\USER\\Desktop\\4icon.png");
        frame.add( new Connect4Panel( "Talha", "Ahmet") );
        frame.pack();
        frame.setIconImage( ımageIcon.getImage() );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo( null);
        frame.setVisible( true);
    }
}
