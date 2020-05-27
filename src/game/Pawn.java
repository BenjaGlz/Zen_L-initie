package game;

/**
 * Creates a Pawn object 
 * @author B.Guillouzo
 */

public class Pawn {

    private int y;
    private int x;
    private PawnType type;
    private static final int size = 11;


    /**
     * First Constructor of the Pawn class
     * @param x : x coordinate of the pawn
     * @param y : y coordinate of the pawn
     * @param type : type of the Pawn
     */

    public Pawn(int x, int y, PawnType type) {

    }

    /**
     * Second constructor of the Pawn class
     */

    public Pawn() {}

    /**
     * Check if the pawn is a zen
     * @return true if yes
     */

    public boolean isZen() {

        boolean isZen = false;

        return isZen;

    }

    /**
     * Gets the x coordinate of the pawn
     * @return the x coordinate as an int
     */

    public int getX() {
        return this.x;
    }

     /**
     * Gets the y coordinate of the pawn
     * @return the y coordinate as an int
     */

    public int getY() {
        return this.y;
    }

    /**
     * Gets the type of the pawn
     * @return the type as a PawnType
     */

    public PawnType getType() {
        return this.type;
    }

    /**
     * Set the x coordinate of the pawn
     * @param x : x coordinate 
     */

    public void setX(int x) {

    }

     /**
     * Set the y coordinate of the pawn
     * @param y : y coordinate 
     */

    public void setY(int y) {

    }

    /**
     * Set the type of the pawn
     * @param type : type of the pawn
     */

    public void setType(PawnType type) {

    }

    
}