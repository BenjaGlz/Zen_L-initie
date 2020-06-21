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
    protected boolean isHuman;

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
            this.isHuman = true;
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
     * Look for the state of the player
     * @return true if the player is a human, false otherwise
     */

    public boolean getHuman() {

        return this.isHuman;
    }

    /**
     * Find the pawn which contains these coordinates
     * @param x : x coordinate of the square checked
     * @param y : y coordinate of the square checked
     * @return the pawn which has the x and y coordinates
     */

    public Pawn contains(int x, int y) {

        Pawn pawn = new Pawn();

        if (x < 0 || x >= size) {
            System.err.println("contains : Error - x position out of range");
        }
        if (y < 0 || y >= size) {
            System.err.println("contains : Error - y position out of range");
        }
        if (x >= 0 && x < size || y >= 0 || y < size) {
            for (Pawn p : this.pawns) {
                if (p.contains(x, y)) {
                    pawn = p;
                }
            }
        }
        
        return pawn;
    }

    /**
     * Check if all the pawns of the player are aligned
     * @return true if so
     */

    public boolean aligned() {

        boolean aligned = false;
        int i = 0;
        int j = 0;
        ArrayList<Pawn> stack = new ArrayList<>();
        boolean[] tagged = new boolean[this.pawns.size()];

        stack.add(this.pawns.get(0));
        tagged[0] = true;

        //bfs algorithm
        while (!stack.isEmpty()) {
            Pawn x = stack.get(stack.size()-1);
            stack.remove(stack.size()-1);
            Pawn z = this.nextPawn(x, tagged);
            while (z != null) {
                stack.add(z);
                tagged[this.pawns.indexOf(z)] = true;
                z = this.nextPawn(x, tagged);
            }
        }
        for (boolean b : tagged) {
            if (b) {
                i++;
            }
        }

        for (Pawn p : this.pawns) {
            j++;
        }

        if (i == j) {
            aligned = true;
        }

        return aligned;

    }

    /**
     * Search if the given pawn has other pawns next to him
     * Credits to L.Desmonts for helping me doing this method
     * @param pawn : pawns which we look for its neighbours
     * @param tagged : a table containing the pawns already taken in account
     * @return the neightbour's pawn
     */

    public Pawn nextPawn(Pawn pawn, boolean[] tagged) {

        Pawn p = null;
        int x = pawn.getX();
        int y = pawn.getY();

        if (y > 0 && this.pawns.indexOf(this.contains(x, y-1))!=-1 && !tagged[this.pawns.indexOf(this.contains(x, y-1))]) {
            p = this.contains(x, y-1);
        }
        else if (y < size-1 && this.pawns.indexOf(this.contains(x, y+1))!=-1 && !tagged[this.pawns.indexOf(this.contains(x, y+1))]) {
            p = this.contains(x, y+1);
        }
        else if (x > 0 && this.pawns.indexOf(this.contains(x-1, y))!=-1 && !tagged[this.pawns.indexOf(this.contains(x-1, y))]) {
            p = this.contains(x-1, y);
        }
        else if (x < size -1 && this.pawns.indexOf(this.contains(x+1, y))!=-1 && !tagged[this.pawns.indexOf(this.contains(x+1, y))]) {
            p = this.contains(x+1, y);
        }
        else if (x < size -1 && this.pawns.indexOf(this.contains(x+1, y))!=-1 && !tagged[this.pawns.indexOf(this.contains(x+1, y))]) {
            p = this.contains(x+1, y);
        }
        else if ((y > 0 && x > 0) && this.pawns.indexOf(this.contains(x-1, y-1))!=-1  && !tagged[this.pawns.indexOf(this.contains(x-1, y-1))]) {
            p = this.contains(x-1, y-1);
        }
        else if ((y > 0 && x < size -1) && this.pawns.indexOf(this.contains(x+1, y-1))!=-1 && !tagged[this.pawns.indexOf(this.contains(x+1, y-1))]) {
            p = this.contains(x+1, y-1);
        }
        else if ((y < size -1 && x < size -1) && this.pawns.indexOf(this.contains(x+1, y+1))!=-1 && !tagged[this.pawns.indexOf(this.contains(x+1, y+1))]) {
            p = this.contains(x+1, y+1);
        }
        else if ((y<size-1 && x>0) && this.pawns.indexOf(this.contains(x-1, y+1))!=-1 && !tagged[this.pawns.indexOf(this.contains(x-1, y+1))]) {
            p = this.contains(x-1, y+1);
        }

        return p;
    }
}

