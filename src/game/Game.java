package game;
import java.util.*;
import java.io.*;
import javax.swing.*;

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
    private static final int size = 11;

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

        this.displayGrid();
        System.out.println(this.rules());

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

        String[][] grid = new String[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = "  *  ";
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (Pawn p : this.blackPawns) {
                    if (p.contains(i, j)) {
                        grid[i][j] = "  X  ";
                    }
                }
                for (Pawn p : this.whitePawns) {
                    if (p.contains(i, j)) {
                        grid[i][j] = "  O  ";
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j]);
                if (j == size -1) {
                    System.out.println("\n");
                }
            }
        }        

    }

    /**
     * Check if the pawns of the player are aligned
     * @param player : player which pawns are checked
     * @return true if so
     */

    public boolean aligned(Player player) {

        boolean aligned = false;

        if (player == null) {
            System.out.println("aligned : Error - null value for player");
        }
        else {
            if (player.aligned()) {
                aligned = true;
            }
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

        int[] tab = new int[2];

        if (player == null) {
            System.out.println("readMove : Error - null value for player");
        }
        else {
            tab = player.newMove();
        }
        
        return tab;

    }

    /**
     * Take the pawn the given player wants to move
     * @param player : the player who is choosing the pawn
     * @return the chosen pawn
     */

    public Pawn readPawn(Player player) {
        
        Pawn pawn = new Pawn();
        
        if (player == null) {
            System.out.println("readPawn : Error - null value for player");
        }
        else {
            player.choosePawn();
        }

        return pawn;
    }

    /**
     * Take the type of the zen the given player chooses
     * @param player : the player who is choosing the type of the zen pawn
     * @return the type of the zen
     */

    public ZenType readZen(Player player) {

        ZenType type = ZenType.FRIEND;

        if (player == null) {
            System.out.println("readPawn : Error - null value for player");
        }
        else {
            type = player.chooseZen();
        }

        return type;
    }

    /**
     * Check if the pawn the player wants to move can be moved to the given coordinates
     * @param pawn : pawn the player wants to move
     * @param coordinates : coordinates of the square 
     * @param type : state of the zen pawn
     * @return true if the square is reachable
     */

    public boolean movePossible(Pawn pawn, int[] coordinates) {
        
        boolean possible = true;

        if (pawn == null) {
            System.out.println("movePossible : Error - null value for pawn");
        }
        if (coordinates[0] < 0 || coordinates[0] >= size || coordinates[1] < 0 || coordinates[1] >= size) {
            System.out.println("movePossible : Error - coordinates of out range");
        }
        if (pawn != null && coordinates[0] >= 0 && coordinates[0] < size && coordinates[1] >= 0 && coordinates[1] < size) {
            if (coordinates[0] == pawn.getX()) {
                if (coordinates[1] > pawn.getY()) {
                    for (int i = pawn.getY()+1; i <= coordinates[1] ; i++) {
                        boolean contains = false;
                        if (this.currentPlayer == this.player1) {
                            for (Pawn p : this.blackPawns) {
                                if (p.contains(coordinates[0], i)) {
                                    contains = true;
                                }
                            }
                        }
                        else {
                            for (Pawn p : this.whitePawns) {
                                if (p.contains(coordinates[0], i)) {
                                    contains = true;
                                }
                            }
                        }
                        if (contains) {
                            possible = false;
                        }
                    }
                }
                else if (coordinates[1] < pawn.getY()) {
                    for (int i = pawn.getY()-1; i >= coordinates[1] ; i--) {
                        boolean contains = false;
                        if (this.currentPlayer == this.player1) {
                            for (Pawn p : this.blackPawns) {
                                if (p.contains(coordinates[0], i)) {
                                    contains = true;
                                }
                            }
                        }
                        else {
                            for (Pawn p : this.whitePawns) {
                                if (p.contains(coordinates[0], i)) {
                                    contains = true;
                                }
                            }
                        }
                        if (contains) {
                            possible = false;
                        }
                    }
                }
                
            }
            else if (coordinates[1] == pawn.getY()) {
                if (coordinates[0] > pawn.getX()) {
                    for (int i = pawn.getX()+1; i <= coordinates[0] ; i++) {
                        boolean contains = false;
                        if (this.currentPlayer == this.player1) {
                            for (Pawn p : this.blackPawns) {
                                if (p.contains(i, coordinates[1])) {
                                    contains = true;
                                }
                            }
                        }
                        else {
                            for (Pawn p : this.whitePawns) {
                                if (p.contains(i,coordinates[1])) {
                                    contains = true;
                                }
                            }
                        }
                        if (contains) {
                            possible = false;
                        }
                    }
                }
                else if (coordinates[0] < pawn.getX()) {
                    for (int i = pawn.getX()-1; i >= coordinates[0] ; i--) {
                        boolean contains = false;
                        if (this.currentPlayer == this.player1) {
                            for (Pawn p : this.blackPawns) {
                                if (p.contains(i, coordinates[1])) {
                                    contains = true;
                                }
                            }
                        }
                        else {
                            for (Pawn p : this.whitePawns) {
                                if (p.contains(i, coordinates[1])) {
                                    contains = true;
                                }
                            }
                        }
                        if (contains) {
                            possible = false;
                        }
                    }
                }
            }
            else{
                possible = false;
            }
        }

        return possible;
    }

    /**
     * Move the pawn to the given coordinates
     * @param pawn : pawn the player wants to move
     * @param coordinate : coordinates of the square 
     */

    public void move(Pawn pawn, int[] coordinate) {

        boolean possible = true;

        if (pawn == null) {
            System.out.println("move : Error - null value for pawn");
        }
        if (coordinate[0] < 0 || coordinate[0] >= size || coordinate[1] < 0 || coordinate[1] >= size) {
            System.out.println("move : Error - coordinates of out range");
        }
       /**  if (pawn != null && coordinate[0] >= 0 && coordinate[0] < size && coordinate[1] >= 0 && coordinate[1] < size) {
            do {
                this.currentPlayer.
            }
        }*/


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

        ArrayList<String> infos = new ArrayList<String>();
        String rules = "";

        try { 
            Scanner sc = new Scanner(new FileReader("../data/regles.txt"));
            sc.useDelimiter("\\s*/\\s*");
            while (sc.hasNext()) {
                infos.add(sc.next());
            }
            sc.close();

            for (int i = 0; i < infos.size(); i++) {
                rules += infos.get(i) + "\n";
            }

        } catch(FileNotFoundException f){
            System.out.println("rules : Error - file not found");
        } catch(NoSuchElementException n) {
            System.out.println("rules : Error - no more token available");
        }

        return rules;
    }

    /**
     * End the game
     * @param player : player who won the game
     */

    public void end(Player player) {

        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Both of you fought well but at the end a winner has to be chosen !\nPlease give a warm applause to " + player.getName() + " for his win !\nHe will now have his name written in the Hall of Fame !");
        System.exit(0);

    }

}