package game;
import java.util.*;

/**
 * Class wich describes all the actions of the game
 * @author B.Guillouzo
 */

public class Game {

    private ArrayList<Pawn> blackPawns;
    private ArrayList<Pawn> whitePawns;
    private Player player1;
    private Player player2;
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
     * @param name2 : nme of the second player
     */

    public Game(ArrayList<Pawn> blackPawns, ArrayList<Pawn> whitePawns, String name1, String name2) {

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

        return aligned;

    }

    /**
     * Get the black pawns list
     * @return the list of black pawns as an ArrayList
     */

    public ArrayList<Pawn> getBlackPawns() {
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        return pawns;
    }

    /**
     * Get the white pawns list
     * @return the list of white pawns as an ArrayList
     */

    public ArrayList<Pawn> getWhitePawns() {
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        return pawns;
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

    }

    /**
     Take the 2 coordinates of the next shot of the given player
     * @param player : the player who is doing a new shot
     * @return the coordinates of the shot
     */

    public int[] readMove(Player player) {
        int[] tab = new int[2];
        return tab;
    }

    /**
     * Take the pawn the given player wants to move
     * @param player : the player who is choosing the pawn
     * @return the chosen pawn
     */

    public Pawn readPawn(Player player) {
        Pawn pawn = new Pawn();
        return pawn;
    }

    /**
     * Take the type of the zen the given player chooses
     * @param player : the player who is choosing the type of the zen pawn
     * @return the type of the zen
     */

    public ZenType readZen(Player player) {
        ZenType type = ZenType.FRIEND;
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