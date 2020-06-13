package game;
import java.util.*;

/**
 * Define all what a human player do
 * @author B.Guillouzo
 */

public class AutoPlayer extends Player {

    /**
     * Constructor of the HumanPlayer class
     * @param name : name of the player
     * @param pawns : list containing all the pawns of the player
     */

    public AutoPlayer(String name, ArrayList<Pawn> pawns) {

        super(name, pawns);

    }

    /**
     * Let the player choose the pawn he wants to move
     * @return return the pawn he wants to move
     */

    public Pawn choosePawn() {

        int num = (int) (Math.random()*this.pawns.size());

        return this.pawns.get(num);
       
    }

    /**
     * Let the player choose the type of the zen he prefers
     * @return return the type he prefers
     */

    public ZenType chooseZen() {

        ZenType type = ZenType.FRIEND;

        int num = (int) Math.round(Math.random());
        if (num == 0) {
            type = ZenType.OPPONENT;
        }

        return type;

    }

    /**
     * Let the player choose the coordinate he wants the pawn to move to
     * @return a table containing the x and y coordinate of the square
     */

    public int[] newMove() {

        int[] move = new int[2];

        int x = (int)(Math.random()*size);
        int y = (int)(Math.random()*size);

        move[0] = x;
        move[1] = y;

        return move;

    }

}