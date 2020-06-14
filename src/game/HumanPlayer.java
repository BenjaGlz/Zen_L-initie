package game;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
        int x = 0;
        int y = 0;
        JFrame frame = new JFrame();

        while (!good) {
            try {
                String xString = JOptionPane.showInputDialog(frame, "Write x coordinate of the square you wanna move the pawn to : ");
                String yString = JOptionPane.showInputDialog(frame, "Write y coordinate of the square you wanna move the pawn to : ");
                x = Integer.parseInt(xString);
                y = Integer.parseInt(yString);
                if (x >= 0 && x < size && y >= 0 && y < size) {
                    good = true;
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Coordinates have to belong to the grid");
                }  
            } catch(NumberFormatException n) {
                JOptionPane.showMessageDialog(frame, "Coordinates have to be integers");
            }
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
        JFrame frame = new JFrame();

        do {
            pawn = JOptionPane.showInputDialog(frame, "Write 'friend' if you want to use the Zen pawn as a team member, 'opponent' otherwise");
        } while (!pawn.equalsIgnoreCase("FRIEND") || !pawn.equalsIgnoreCase("OPPONENT"));

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
