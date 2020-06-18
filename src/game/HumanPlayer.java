package game;
import java.util.*;

/**
 * Define all what a human player do
 * @author B.Guillouzo
 */

public class HumanPlayer extends Player {

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
        boolean good = false;

        do {
            System.out.println("\nWrite the x coordinate of the pawn you want to move");
            int x = SC.nextInt();
            System.out.println("\nWrite the y coordinate of the pawn you want to move");
            int y = SC.nextInt();

            if (x < 0 || x >= size || y < 0 || y >= size) {
                System.out.println("\n#####################################################\n#x or y coordinate out of range, please choose again#\n#####################################################\n");
            }
            else {
                for (Pawn p : this.pawns) {
                    if (p.contains(x, y)) {
                        pawn = p;
                        good = true;
                    }
                }
                if (!good) {
                    System.out.println("\n#######################################################################################\n#The pawn you want to move doesn't belong to you or doesn't exist, please choose again#\n#######################################################################################\n");
                }
            }
        } while(!good);

        return pawn;

    }

    /**
     * Let the player choose the type of the zen he prefers
     * @return return the type he prefers
     */

    public ZenType chooseZen() {

        ZenType type = ZenType.FRIEND;
        String pawn = "";

        do {
            System.out.println("\nWrite 'friend' if you want to use the Zen pawn as a team member, 'opponent' otherwise");
            pawn = SC.next();
            if (!pawn.equalsIgnoreCase("FRIEND") && !pawn.equalsIgnoreCase("OPPONENT")) {
                System.out.println("\n###################################\n#Wrong choice, please choose again#\n###################################\n");
            }
        } while (!pawn.equalsIgnoreCase("FRIEND") && !pawn.equalsIgnoreCase("OPPONENT"));

        if (pawn.equalsIgnoreCase("FRIEND")) {
            type = ZenType.FRIEND;
        }
        else {
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
        int x = 0;
        int y = 0;

        do {
            System.out.println("\nWrite the x coordinate you wanna move to");
            x = SC.nextInt(); 
            if (x < 0 || x >= size) {
                System.out.println("\n################################################\n#x coordinate out of range, please choose again#\n################################################\n");
            }     
        } while (x < 0 || x >= size );

        do {
            System.out.println("\nWrite the y coordinate you wanna move to");
            y = SC.nextInt();     
            if (y < 0 || y >= size) {
                System.out.println("\n################################################\n#y coordinate out of range, please choose again#\n################################################\n");
            } 
        } while (y < 0 || y >= size );

        move[0] = x;
        move[1] = y;

        return move;

    }

}
