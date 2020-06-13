package game;
import java.util.*;

import java.io.*;

/**
 * Class wich describes all the actions of the game
 * @author B.Guillouzo
 */

public class Game implements Serializable {

    private ArrayList<Pawn> blackPawns;
    private ArrayList<Pawn> whitePawns;
    private Player player1;
    private Player player2;
    private String name1;
    private String name2;
    private Mode mode;
    private Player currentPlayer;
    private ArrayList<Square> square; 

    /**
     * First constructor of the Game class
     */

    public Game() {}

    /**
     * Second constructor of the Game class
     * @param blackPawns : list containing all the black pawns
     * @param whitePawns : list containing all the white pawns
     * @param name1 : name of the first player
     * @param name2 : name of the second player
     * @param mode : mode of the game
     */

    public Game(ArrayList<Pawn> blackPawns, ArrayList<Pawn> whitePawns, String name1, String name2, Mode mode) {

        if (blackPawns == null) {
            System.err.println("Game : Error - null value for blackPawns");
        }
        if (whitePawns == null) {
            System.err.println("Game : Error - null value for whitePawns");
        }
        if (name1 == null) {
            System.err.println("Game : Error - null value for the first name");
        }
        if (name2 == null) {
            System.err.println("Game : Error - null value for the second name");
        }
        if (mode == null) {
            System.err.println("Game : Error - null value for mode");
        }
        if (blackPawns != null && whitePawns != null && name1 != null && name2 != null && mode != null) {
            this.blackPawns = blackPawns;
            this.whitePawns = whitePawns;
            this.name1 = name1;
            this.name2 = name2;
            this.mode = mode;

            if (this.mode.equals(Mode.HH)) {
                this.player1 = new HumanPlayer(this.name1, this.blackPawns);
                this.player2 = new HumanPlayer(this.name2, this.whitePawns);
            }
            else {
                this.player1 = new HumanPlayer(this.name1, this.blackPawns);
                this.player2 = new AutoPlayer(this.name2, this.whitePawns);
            }

            this.currentPlayer = this.player1;
        }
    }

    /**
     * Start the game
     */

    public void start() {

    }

    /**
     * Initilialize the grid of the game
     */

    public void initializeGrid() {

    }

    /**
     * Display the grid of the game
     */
    
    public void displayGrid() {

    }

    /**
     * Check if the pawns of the player are aligned
     * @param player : player which pawns are checked
     * @return true if so
     */

    public boolean aligned(Player player) {

        boolean aligned = false;

        if (player.aligned()) {
            aligned = true;
        }

        return aligned;

    }

    /**
     * Get the black pawns list
     * @return the list of black pawns as an ArrayList
     */

    public ArrayList<Pawn> getBlackPawns() {

        return this.blackPawns;
    }

    /**
     * Get the white pawns list
     * @return the list of white pawns as an ArrayList
     */

    public ArrayList<Pawn> getWhitePawns() {
        
        return this.whitePawns;

    }



    /**
     * Get the current player
     * @return the current player
     */

    public Player getCurrentPlayer() {

        return this.currentPlayer;
    }

    /**
     * Change the current player
     */

    public void changeCurrentPlayer() {

        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        }
        else {
            this.currentPlayer = this.player1;
        }

    }

    /**
     Take the 2 coordinates of the next shot of the given player
     * @param player : the player who is doing a new shot
     * @return the coordinates of the shot
     */

    public int[] readMove(Player player) {
        
        int[] tab = player.newMove();
        return tab;

    }

    /**
     * Take the pawn the given player wants to move
     * @param player : the player who is choosing the pawn
     * @return the chosen pawn
     */

    public Pawn readPawn(Player player) {
        
        Pawn pawn = player.choosePawn();
        return pawn;
    }

    /**
     * Take the type of the zen the given player chooses
     * @param player : the player who is choosing the type of the zen pawn
     * @return the type of the zen
     */

    public ZenType readZen(Player player) {

        ZenType type = player.chooseZen();
        return type;
    }

    /**
     * Check if the pawn the player wants to move can be moved to the given coordinates
     * @param pawn : pawn the player wants to move
     * @param coordinates : coordinates of the square 
     * @param type : state of the zen pawn
     * @return true if the square is reachable
     */

    public boolean movePossible(Pawn pawn, int[] coordinates, ZenType type) {
        boolean possible = false;
        return possible;
    }

    /**
     * Move the pawn to the given coordinates
     * @param pawn : pawn the player wants to move
     * @param coordinate : coordinates of the square 
     */

    public void move(Pawn pawn, int[] coordinate) {

    }

    /**
     * Change the arraylist of the pawn weither it is a 'friend' or an 'opponent' of the player
     */

    public void changeZen() {

        boolean pawned = false;
        int i = this.blackPawns.size()-1;
        int j = this.whitePawns.size()-1;
        Pawn zen1 = new Pawn();
        Pawn zen2 = new Pawn();

        if (this.currentPlayer == this.player1) {
            if (this.blackPawns.get(i).getType().equals(PawnType.ZEN)) {
                zen1 = this.blackPawns.get(i);
                pawned = true;
            }
            if (pawned) {
                if (this.readZen(this.currentPlayer).equals(ZenType.OPPONENT)) {
                    this.blackPawns.remove(i);
                    this.whitePawns.add(zen1);
                }
            }
            else {
                if (this.readZen(this.currentPlayer).equals(ZenType.FRIEND)) {
                    zen2 = this.whitePawns.get(j);
                    this.whitePawns.remove(j);
                    this.blackPawns.add(zen2);
                }
            }
        }
        else {
            if (this.whitePawns.get(j).getType().equals(PawnType.ZEN)) {
                zen1 = this.whitePawns.get(j);
                pawned = true;
            }
            if (pawned) {
                if (this.readZen(this.player2).equals(ZenType.OPPONENT)) {
                    this.whitePawns.remove(j);
                    this.blackPawns.add(zen1);
                }
            }
            else {
                if (this.readZen(this.player2).equals(ZenType.FRIEND)) {
                    zen2 = this.blackPawns.get(i);
                    this.blackPawns.remove(i);
                    this.whitePawns.add(zen2);
                }
            }
        }
    }

    /**
     * Define the rules of the game
     * @return the rules of the game as a String
     */

    public String rules() {
        return "";
    }

    /**
     * End the game
     */

    public void end() {

    }

}