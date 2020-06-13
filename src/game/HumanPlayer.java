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
        int x = 0;
        int y = 0;

        try {
            Scanner sc = new Scanner(System.in);
            do {
                System.out.println("Write the x coordinate of the pawn you wanna move");
                x = sc.nextInt();      
            } while (x < 0 || x >= size );

            do {
                System.out.println("Write the x coordinate of the pawn you wanna move");
                y = sc.nextInt();      
            } while (y < 0 || y >= size );
            sc.close();
        } catch(InputMismatchException i) {
            System.err.println("newMove : Error - token is not an integer expression");
        } catch(NoSuchElementException n) {
            System.err.println("chooseZen : Error - no token available");
        }

        for (Pawn p : this.pawns) {
            if (p.contains(x, y)) {
                pawn = p;
            }
        }

        return pawn;

    }

    /**
     * Let the player choose the type of the zen he prefers
     * @return return the type he prefers
     */

    public ZenType chooseZen() {

        ZenType type = ZenType.FRIEND;
        String pawn = "";

        try {
            Scanner sc = new Scanner(System.in);
            do {
                System.out.println("Write 'friend' if you want to use the Zen pawn as a team member, 'opponent' otherwise");
                pawn = sc.next().toUpperCase();
            } while (!pawn.equals("FRIEND") || !pawn.equals("OPPONENT"));
            sc.close();
        } catch(NoSuchElementException n){
            System.err.println("chooseZen : Error - no token available");
        }

        if (pawn.equals("FRIEND")) {
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

        try {
            Scanner sc = new Scanner(System.in);
            do {
                System.out.println("Write the x coordinate you wanna move to");
                x = sc.nextInt();      
            } while (x < 0 || x >= size );

            do {
                System.out.println("Write the y coordinate you wanna move to");
                y = sc.nextInt();      
            } while (y < 0 || y >= size );
            sc.close();
        } catch(InputMismatchException i) {
            System.err.println("newMove : Error - token is not an integer expression");
        } catch(NoSuchElementException n) {
            System.err.println("chooseZen : Error - no token available");
        } 

        move[0] = x;
        move[1] = y;

        return move;

    }

}
