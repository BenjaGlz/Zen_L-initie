package game;

import java.io.Serializable;

/**
 * Describes the squares of the grid
 * @author B.Guillouzo
 */

public class Square implements Serializable{

    private int x;
    private int y;
    private boolean free; 
    private static final int size = 11;
 

    /**
     * Constructor of the Square class
     * @param x : x coordinate of the square
     * @param y : y coordinate of the square
     */

    public Square(int x, int y) {

        if (x < 0 || x >= size) {
            System.err.println("Square : Error - x position out of range");
        }
        if (y < 0 || y >= size) {
            System.err.println("Square : Error - y position out of range");
        }
        if (x >= 0 && x < size && y >= 0 && y < size) {
            this.x = x;
            this.y = y;
        }

        this.free = true;

    }

    /**
     * Check if the square is free
     * @return true if so
     */

    public boolean isFree() {

        return this.free;

    }

    /**
     * Sets the case to busy
     */

    public void setBusy() {

        this.free = false;

    }

    /**
     * Sets the case to free
     */

    public void setFree() {

        this.free = true;

    }
    
}