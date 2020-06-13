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

        if (x < 0 || x >= size) {
            System.err.println("Pawn : Error - x position out of range");
        }
        if (y < 0 || y >= size) {
            System.err.println("Pawn : Error - y position out of range");
        }
        if (type == null) {
            System.err.println("Pawn : Error - null value for type");
        }
        if (x >= 0 && x < size && y >= 0 && y < size && type != null) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

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

        if (this.type == PawnType.ZEN) {
            isZen = true;
        }

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

        if (x < 0 || x >= size) {
            System.err.println("setX : Error - x position out of range");
        }
        else {
            this.x = x;
        }

    }

     /**
     * Set the y coordinate of the pawn
     * @param y : y coordinate 
     */

    public void setY(int y) {

        if (y < 0 || y >= size) {
            System.err.println("setY : Error - x position out of range");
        }
        else {
            this.y = y;
        }

    }

    /**
     * Set the type of the pawn
     * @param type : type of the pawn
     */

    public void setType(PawnType type) {

        if (type == null) {
            System.err.println("setType : Error - null value for type");
        }
        else {
            this.type = type;
        }

    }

    /**
     * check weither the case with x and y coordonates belongs to the ship or not
     * @param x : x coordinate of the square checked
     * @param y : y coordinate of the square checked
     * @return true if so
     */

    public boolean contains(int x, int y) {

        boolean contains = false;

        if (x < 0 || x >= size) {
            System.err.println("Contains : Error - x position out of range");
        }
        if (y < 0 || y >= size) {
            System.err.println("Contains : Error - y position out of range");
        }
        if (x >= 0 && x < size || y >= 0 || y < size) {
            if (x == this.x && y == this.y) {
                contains = true;
            }
        }

        return contains;

    }

    
}