package game;
import java.util.*;
import java.io.*;
import javax.swing.*;

import save.LoadGame;
import save.SaveGame;

/**
 * Class wich describes all the actions of the game
 * @author B.Guillouzo
 */

public class Game implements Serializable {

    private static final long serialVersionUID = -5457227605666196967L;
    private ArrayList<Pawn> blackPawns;
    private ArrayList<Pawn> whitePawns;
    private Player player1;
    private Player player2;
    private String name1;
    private String name2;
    private Mode mode;
    private Player currentPlayer;
    private Square[][] grid; 
    private static final int size = 11;
    private static final Scanner SC = new Scanner(System.in);

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

        boolean won = false;
        
        this.initializeGrid();
        this.displayGrid();

        do {
            boolean zen = false;
            for (Pawn p : this.getCurrentPawns()) {
                if (p.getType().equals(PawnType.ZEN)) {
                    zen = true;
                }
            }
            if (!zen) {
                for (Pawn p : this.getOpponentPawns()) {
                    if (p.getType().equals(PawnType.ZEN)) {
                        zen = true;
                    }
                }
            }
            if (zen) {
                this.changeZen();
            }

            this.move();
            //won = this.aligned(this.currentPlayer);
            this.changeCurrentPlayer();
            if (this.currentPlayer.equals(this.player1)) {
                this.asks();
            }
        } while (!won);
        
        //System.out.println(this.rules());

    }

    /**
     * Find the pawn which contains these coordinates
     * @param x : x coordinate of the square checked
     * @param y : y coordinate of the square checked
     * @return the pawn which has the x and y coordinates
     */

    public Pawn contains(int x, int y) {

        Pawn pawn = new Pawn();
        boolean good = false;

        if (x < 0 || x >= size) {
            System.err.println("Contains : Error - x position out of range");
        }
        if (y < 0 || y >= size) {
            System.err.println("Contains : Error - y position out of range");
        }
        if (x >= 0 && x < size || y >= 0 || y < size) {
            for (Pawn p : this.blackPawns) {
                if (p.contains(x, y)) {
                    pawn = p;
                    good = true;
                }
            }

            if (!good) {
                for (Pawn p : this.whitePawns) {
                    if (p.contains(x, y)) {
                        pawn = p;
                    }
                } 
            }
        }
        
        return pawn;
    }

    /**
     * Initilialize the grid of the game
     */

    public void initializeGrid() {

        this.grid = new Square[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                this.grid[y][x] = new Square(x, y);
            }
        }
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                for (Pawn p : this.blackPawns) {
                    if (p.contains(x, y)) {
                        this.grid[y][x].setBusy();
                    }
                }
                for (Pawn p : this.whitePawns) {
                    if (p.contains(x, y)) {
                        this.grid[y][x].setBusy();
                    }
                }
            }
        }
    }

    /**
     * Display the grid of the game
     */
    
    public void displayGrid() {

        System.out.print("\n\n######################################\n\n   ");
        for (int i = 0; i < 11; i++) {
            System.out.print(" " +i+ " ");      
        }
        System.out.println("");
        System.out.print("   ");
        for (int i = 0; i < 11; i++) {
            System.out.print("---");
        }
        System.out.println("");
        for (int y = 0; y < size; y++) {
            if (y <= 9 ){
                System.out.print((y) + " |");
            }
            else {
                System.out.print((y) + "|");
            }
            for (int x = 0; x < size; x++) {
                if (!this.grid[y][x].isFree()) {
                    Pawn p = this.contains(x, y);
                    if (p.getType().equals(PawnType.BLACK)) {
                        System.out.print(" X ");
                    }
                    else if (p.getType().equals(PawnType.WHITE)) {
                        System.out.print(" O ");
                    }
                    else if (p.isZen()) {
                        System.out.print(" Z ");
                    }
                }
                else {
                    System.out.print(" * ");
                }
                if (x == size -1) {
                    System.out.println("");
                }
            }
        }
        System.out.print("\n######################################\n\n\n");
     
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

        if (this.currentPlayer.equals(this.player1)) {
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
            pawn = player.choosePawn();
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
     * Get the pawns of the current player
     * @return the arraylist containing his pawns
     */

    public ArrayList<Pawn> getCurrentPawns() {

        if (this.currentPlayer.equals(this.player1)) {
            return this.blackPawns;
        }
        else {
            return this.whitePawns;
        }
    }

    /**
     * Get the pawns of the opponent player
     * @return the arraylist containing his pawns
     */

    public ArrayList<Pawn> getOpponentPawns() {

        if (this.currentPlayer.equals(this.player1)) {
            return this.whitePawns;
        }
        else {
            return this.blackPawns;
        }
    }

    /**
     * Check if the pawn the player wants to move can be moved to the given coordinates
     * @param pawn : the pawn which is moved
     * @param coordinates : table containing the coordinates of the square the player want to move the pawn to
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
            if (pawn.getType().equals(PawnType.ZEN) && this.grid[pawn.getY()-1][pawn.getX()-1].isFree() && this.grid[pawn.getY()][pawn.getX()-1].isFree() && this.grid[pawn.getY()-1][pawn.getX()+1].isFree() && this.grid[pawn.getY()][pawn.getX()+1].isFree() && this.grid[pawn.getY()+1][pawn.getX()+1].isFree() && this.grid[pawn.getY()+1][pawn.getX()].isFree() && this.grid[pawn.getY()+1][pawn.getX()-1].isFree() && this.grid[pawn.getY()][pawn.getX()-1].isFree()) {
                possible = false;
            }
            else {
                if (pawn.getX() == coordinates[0] && pawn.getY() < coordinates[1]) {
                    for (int i = pawn.getY()+1; i <= coordinates[1]; i++) {
                        if (!this.grid[i][coordinates[0]].isFree()) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.contains(coordinates[0], i) && i == size -1) {
                                    possible = false;
                                }
                                if (p.contains(coordinates[0], coordinates[1])) {
                                    possible = false;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.contains(coordinates[0], i) && i < coordinates[1]) {
                                    possible = false;
                                }
                            }
                        } 
                    } 
                }
                else if (pawn.getX() == coordinates[0] && pawn.getY() > coordinates[1]) {
                    for (int i = pawn.getY()-1; i >= coordinates[1]; i--) {
                        if (!this.grid[i][coordinates[0]].isFree()) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.contains(coordinates[0], i) && i == 1) {
                                    possible = false;
                                }
                                if (p.contains(coordinates[0], coordinates[1])) {
                                    possible = false;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.contains(coordinates[0], i) && i > coordinates[1]) {
                                    possible = false;
                                }
                            }
                        } 
                    } 
                }
                else if (pawn.getY() == coordinates[1] && pawn.getX() < coordinates[0]) {
                    for (int i = pawn.getX()+1; i <= coordinates[0]; i++) {
                        if (!this.grid[coordinates[1]][i].isFree()) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.contains(i, coordinates[1]) && i == size -1) {
                                    possible = false;
                                }
                                if (p.contains(coordinates[0], coordinates[1])) {
                                    possible = false;
                                }
                            }
                             for (Pawn p : this.getOpponentPawns()) {
                                if (p.contains(i, coordinates[1]) && i < coordinates[0]) {
                                    possible = false;
                                }
                            }
                        } 
                    } 
                }
                else if (pawn.getY() == coordinates[1] && pawn.getX() > coordinates[0]) {
                    for (int i = pawn.getX()-1; i >= coordinates[0]; i--) {
                        if (!this.grid[coordinates[1]][i].isFree()) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.contains(i, coordinates[1]) && i == 1) {
                                    possible = false;
                                }
                                if (p.contains(coordinates[0], coordinates[1])) {
                                    possible = false;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.contains(i, coordinates[1]) && i > coordinates[0]) {
                                    possible = false;
                                }
                            }
                        } 
                    } 
                }
                else if ((coordinates[1]-pawn.getY() == coordinates[0]-pawn.getX()) && (coordinates[1] > pawn.getY()) && (coordinates[0] > pawn.getX())) {
                    int x = pawn.getX() +1;
                    int y = pawn.getY() +1;
                    while (x <= coordinates[0] || y <= coordinates[1]) {
                        if (!this.grid[y][x].isFree()) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.contains(x, y) && (x == size -1 || y == size -1)) {
                                    possible = false; 
                                }
                                if (p.contains(coordinates[0], coordinates[1])) {
                                    possible = false;
                                } 
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.contains(x, y) && x < coordinates[0] && y < coordinates[1]) {
                                    possible = false;
                                }
                            }
                        }
                        x++;
                        y++;
                    }  
                }
                else if ((Math.abs(coordinates[1]-pawn.getY()) == coordinates[0]-pawn.getX()) && (coordinates[1] < pawn.getY()) && (coordinates[0] > pawn.getX())) {
                    int x = pawn.getX() +1;
                    int y = pawn.getY() -1;
                    while (x <= coordinates[0] || y >= coordinates[1]) {
                        if (!this.grid[y][x].isFree()) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.contains(x, y) && (x == size -1 || y == 1)) {
                                    possible = false; 
                                }
                                if (p.contains(coordinates[0], coordinates[1])) {
                                    possible = false;
                                } 
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.contains(x, y) && x < coordinates[0] && y > coordinates[1]) {
                                    possible = false;
                                }
                            }
                        }
                        x++;
                        y--;
                    }  
                }
                else if ((coordinates[1]-pawn.getY() == Math.abs(coordinates[0]-pawn.getX())) && (coordinates[1] > pawn.getY()) && (coordinates[0] < pawn.getX())) {
                    int x = pawn.getX() -1;
                    int y = pawn.getY() +1;
                    while (x >= coordinates[0] || y <= coordinates[1]) {
                        if (!this.grid[y][x].isFree()) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.contains(x, y) && (x == 1 || y == size -1)) {
                                    possible = false; 
                                }
                                if (p.contains(coordinates[0], coordinates[1])) {
                                    possible = false;
                                } 
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.contains(x, y) && x > coordinates[0] && y < coordinates[1]) {
                                    possible = false;
                                }
                            }
                        }
                        x--;
                        y++;
                    }    
                }
                else if ((coordinates[1]-pawn.getY() == coordinates[0]-pawn.getX()) && (coordinates[1] < pawn.getY()) && (coordinates[0] < pawn.getX())) {
                    int x = pawn.getX() -1;
                    int y = pawn.getY() -1;
                    while (x >= coordinates[0] || y >= coordinates[1]) {
                        if (!this.grid[y][x].isFree()) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.contains(x, y) && (x == 1 || y == 1)) {
                                    possible = false; 
                                }
                                if (p.contains(coordinates[0], coordinates[1])) {
                                    possible = false;
                                } 
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.contains(x, y) && x > coordinates[0] && y > coordinates[1]) {
                                    possible = false;
                                }
                            }
                        }
                        x--;
                        y--;
                    }
                }
                else {
                    possible = false;
                }
            }
        }

        return possible;
    }

    /**
     * Move the pawn to the given coordinates
     * @param pawn : pawn the player wants to move
     * @param coordinate : coordinates of the square 
     */

    public void move() {

        boolean possible = true;

        Pawn pawn = new Pawn();
        int[] coordinates = new int[2];

        if (coordinates[0] < 0 || coordinates[0] >= size || coordinates[1] < 0 || coordinates[1] >= size) {
            System.out.println("movePossible : Error - coordinates of out range");
        }
        if (pawn != null && coordinates[0] >= 0 && coordinates[0] < size && coordinates[1] >= 0 && coordinates[1] < size) {
            do {
                pawn = this.readPawn(this.currentPlayer);
                coordinates = this.readMove(this.currentPlayer);
                possible = movePossible(pawn, coordinates);
                if (!possible) {
                    System.out.println("\n#############################################################################################\n#The pawn you want to move or the coordinates you have chosen are wrong, please choose again#\n#############################################################################################\n");
                }
            } while (!possible);

            this.grid[coordinates[1]][coordinates[0]].setBusy();
            this.grid[pawn.getY()][pawn.getX()].setFree();

            for (Pawn p : this.getCurrentPawns()) {
                if (p.contains(pawn.getX(), pawn.getY())) {
                    p.setX(coordinates[0]);
                    p.setY(coordinates[1]);
                }
            }

            int i = 0;
            boolean good = false;

            while (!good && i < this.getOpponentPawns().size()) {
                if (this.getOpponentPawns().get(i).getX() == coordinates[0] && this.getOpponentPawns().get(i).getY() == coordinates[1]) {
                    this.getOpponentPawns().remove(i);
                    good = true;
                }
                i++;
            }

            for (Pawn p : this.getOpponentPawns()) {
                System.out.println(p.getX() + " " + p.getY());
            }

            this.displayGrid();
        }
    }

    /**
     * Change the arraylist of the pawn weither it is a 'friend' or an 'opponent' of the player
     */

    public void changeZen() {

        boolean pawned = false;
        int i = this.getCurrentPawns().size()-1;
        int j = this.getOpponentPawns().size()-1;
        Pawn zen1 = new Pawn();
        Pawn zen2 = new Pawn();
        ZenType type = this.readZen(this.currentPlayer);
        System.out.println("");

        //if (this.currentPlayer.equals(this.player1)) {
            if (this.getCurrentPawns().get(i).isZen()) {
                zen1 = this.getCurrentPawns().get(i);
                pawned = true;
            }
            if (pawned) {
                if (type.equals(ZenType.OPPONENT)) {
                    this.getCurrentPawns().remove(i);
                    this.getOpponentPawns().add(zen1);
                }
            }
            else {
                if (type.equals(ZenType.FRIEND)) {
                    zen2 = this.getOpponentPawns().get(j);
                    this.getOpponentPawns().remove(j);
                    this.getCurrentPawns().add(zen2);
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

    public void asks() {

        int x = 0;
        String filename = "";

        do {
            System.out.println("What do you want to do ?\n*'1' to continue\n*'2' to see the rules\n*'3' to save and quit the game\n*'4' to quit the game");
            x = SC.nextInt();
        } while (x != 1 && x != 2 && x != 3 && x != 4);

        if (x == 2) {
            System.out.println(this.rules());
        }
        else if (x == 3) {
            System.out.println("Write the name of the file you want to save the game in");
            filename = "../data/" + SC.next();
            SaveGame.saveGame(this, filename);
            System.exit(0);
        }
        else if (x == 4) {
            System.exit(0);
        }
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