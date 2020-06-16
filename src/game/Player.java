package game;
import java.io.Serializable;
import java.util.*;

/**
 * Define what a player can and has to do in an abstract class
 * @author B.Guillouzo
 */

public abstract class Player implements Serializable {

    protected String name;
    protected ArrayList<Pawn> pawns;
    protected static final int size = 11;
    protected static final Scanner SC = new Scanner(System.in);
    private ArrayList<Pawn> tagged;

    /**
     * Constructor of the Player class
     * @param name : name of the player
     * @param pawns : list of the pawns of the player
     */

    public Player(String name, ArrayList<Pawn> pawns) {

        if (name == null) {
            System.err.println("Player : Error - null value for name");
        }
        if (pawns == null) {
            System.err.println("Player : Error - null value for pawns");
        }
        if (name != null && pawns != null) {
            this.name = name;
            this.pawns = pawns;
        }

    }

    /**
     * Get the name of the player
     * @return the name as a String
     */

    public String getName() {
        return this.name;
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
        int j = 0;
        this.tagged = new ArrayList<Pawn>();
        ArrayList<Pawn> stack = new ArrayList<>();

        tagged.add(this.pawns.get(0));
        stack.add(this.pawns.get(0));

        while (!stack.isEmpty()) {
            stack.remove(0);
            Pawn p = this.nextPawn(stack.get(0));
            while (p != null) {
                Pawn z = p;
                stack.add(z);
                this.tagged.add(z);
            }
        }

        int i = this.tagged.size();
    
        System.out.println(i);

        for (Pawn p : this.pawns) {
            j++;
        }
        System.out.println(j);

        if (i == j) {
            aligned = true;
        }

        return aligned;

    }

    public Pawn nextPawn(Pawn pawn) {

        Pawn p = new Pawn();
        boolean contained = false;

        if (pawn == null) {
            System.out.println("nextTo : Error - null value for pawn");
        }
        else {
            for (Pawn pa : this.pawns) {
                for (Pawn paw : this.tagged) {
                    if (pa.equals(paw)) {
                        contained = true;
                    }
                }
                if (((pawn.getY()-1 >= 0 && p.contains(pawn.getX(), pawn.getY()-1)) || (pawn.getX()+1 < size && pawn.getY()-1 >= 0 && p.contains(pawn.getX()+1, pawn.getY()-1)) || (pawn.getX()+1 < size && p.contains(pawn.getX()+1, pawn.getY())) || (pawn.getX()+1 < size && pawn.getY()+1 < size && p.contains(pawn.getX()+1, pawn.getY()+1)) || (pawn.getY()+1 < size && p.contains(pawn.getX(), pawn.getY()+1)) || (pawn.getX()-1 >= 0 && pawn.getY()+1 < size && p.contains(pawn.getX()-1, pawn.getY()+1)) || (pawn.getX()-1 >= 0 && p.contains(pawn.getX()-1, pawn.getY())) || (pawn.getX()-1 >= 0 && pawn.getY()-1 >= 0 && p.contains(pawn.getX()-1, pawn.getY()-1))) && !contained) {
                    p = pa;
                }
            }
        }
        return p;
    }
}

