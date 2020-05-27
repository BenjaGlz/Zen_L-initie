package game;
import java.util.*;

/**
 * Define what a player can and has to do in an abstract class
 * @author B.Guillouzo
 */

public abstract class Player {

    private String name;
    private ArrayList<Pawn> pawns;
    private static final int size = 11;

    /**
     * Constructor of the Player class
     * @param name : name of the player
     * @param pawns : list of the pawns of the player
     */

    public Player(String name, ArrayList<Pawn> pawns) {

    }

    /**
     * Let the player choose the pawn he wants to move
     * @return return the pawn he wants to move
     */

    public abstract Pawn choosePawn();

    /**
     * Let the player choose the type of the zen he prefers
     * @return return the type he prefers
     */

    public abstract ZenType chooseZen();

    /**
     * Let the player choose the coordinate he wants the pawn to move to
     * @return a table containing the x and y coordinate of the square
     */

    public abstract int[] newMove();

    /**
     * Check if all the pawns of the player are aligned
     * @return true if so
     */

    public boolean aligned() {

        boolean aligned = false;

        return aligned;

    }
    
}