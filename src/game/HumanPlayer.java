package game;
import java.util.*;

/**
 * Define all what a human player do
 * @author B.Guillouzo
 */

public class HumanPlayer extends Player {

    private String name;
    private ArrayList<Pawn> pawns;
    private ZenType type;

    /**
     * Constructor of the HumanPlayer class
     * @param name : name of the player
     * @param pawns : list containing all the pawns of the player
     */

    public HumanPlayer(String name, ArrayList<Pawn> pawns) {

        super(name, pawns);

    }

    /**
     * Let the player choose the pawn he wants to move
     * @return return the pawn he wants to move
     */

    public Pawn choosePawn() {

        Pawn pawn = new Pawn();

        return pawn;

    }

    /**
     * Let the player choose the type of the zen he prefers
     * @return return the type he prefers
     */

    public ZenType chooseZen() {

        ZenType type = ZenType.FRIEND;

        return type;

    }

    /**
     * Let the player choose the coordinate he wants the pawn to move to
     * @return a table containing the x and y coordinate of the square
     */

    public int[] newMove() {

        int[] move = {0,0};

        return move;

    }

}