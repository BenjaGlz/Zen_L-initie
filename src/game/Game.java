package game;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.FileReader;
import java.io.Serializable;
import java.io.FileNotFoundException;
import save.SaveGame;
import java.io.IOException;

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
        System.out.println(this.rules());

        //game loop
        do {
            System.out.println("\n\n====================[ It's your turn to play " +this.currentPlayer.getName()+ " ]====================\n");
            //check if the zen pawn is still in the game
            boolean zen = false;
            for (Pawn p : this.getCurrentPawns()) {
                if (p.isZen()) {
                    zen = true;
                }
            }
            if (!zen) {
                for (Pawn p : this.getOpponentPawns()) {
                    if (p.isZen()) {
                        zen = true;
                    }
                }
            }
            if (zen) {
                this.changeZen();
            }

            this.move();
            won = this.aligned(this.currentPlayer);
            this.changeCurrentPlayer();
            if (this.currentPlayer.equals(this.player1) && !won) {
                this.asks();
            }
        } while (!won);

        //check who won or if there is an equality
        if (this.currentPlayer.equals(this.player2)) {
            boolean won2 = this.aligned(this.player1);
            if (won2) {
                this.end(this.player1);
            }
            else {
                this.end(this.player1, this.player2);
            }
        }
        else {
            boolean won2 = this.aligned(this.player2);
            if (won2) {
                this.end(this.player2);
            }
            else {
                this.end(this.player1, this.player2);
            }
        }
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

        System.out.println("\n\n====================[ " + this.player1.getName() + " you have the X pawns ]====================");
        System.out.println("====================[ " + this.player2.getName() + " you have the O pawns ]====================");

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

        clear();
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
                //calculate the number of pawns on the column
                if (pawn.getX() == coordinates[0] && pawn.getY() < coordinates[1]) {
                    int y = 0;
                    for (Pawn p : this.getCurrentPawns()) {
                        if (p.getX() == pawn.getX()) {
                            y++;
                        }
                    }
                    for (Pawn p : this.getOpponentPawns()) {
                        if (p.getX() == pawn.getX()) {
                            y++;
                        }
                    }
                    if (y < coordinates[1] - pawn.getY()) {
                        possible = false;
                    }
                    if (possible) {
                        //check if the move in one of these cases to cancel it
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
                }
                //calculate the number of pawns on the column
                else if (pawn.getX() == coordinates[0] && pawn.getY() > coordinates[1]) {
                    int y = 0;
                    for (Pawn p : this.getCurrentPawns()) {
                        if (p.getX() == pawn.getX()) {
                            y++;
                        }
                    }
                    for (Pawn p : this.getOpponentPawns()) {
                        if (p.getX() == pawn.getX()) {
                            y++;
                        }
                    }
                    if (y < pawn.getY() - coordinates[1]) {
                        possible = false;
                    }
                    if (possible) {
                        //check if the move in one of these cases to cancel it
                        for (int i = pawn.getY()-1; i >= coordinates[1]; i--) {
                            if (!this.grid[i][coordinates[0]].isFree()) {
                                for (Pawn p : this.getCurrentPawns()) {
                                    if (p.contains(coordinates[0], i) && i == 0) {
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
                }
                //calculate the number of pawns on the line
                else if (pawn.getY() == coordinates[1] && pawn.getX() < coordinates[0]) {
                    int y = 0;
                    for (Pawn p : this.getCurrentPawns()) {
                        if (p.getY() == pawn.getY()) {
                            y++;
                        }
                    }
                    for (Pawn p : this.getOpponentPawns()) {
                        if (p.getY() == pawn.getY()) {
                            y++;
                        }
                    }
                    if (y < coordinates[0] - pawn.getX()) {
                        possible = false;
                    }
                    if (possible) {
                        //check if the move in one of these cases to cancel it
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
                }
                //calculate the number of pawns on the line
                else if (pawn.getY() == coordinates[1] && pawn.getX() > coordinates[0]) {
                    int y = 0;
                    for (Pawn p : this.getCurrentPawns()) {
                        if (p.getY() == pawn.getY()) {
                            y++;
                        }
                    }
                    for (Pawn p : this.getOpponentPawns()) {
                        if (p.getY() == pawn.getY()) {
                            y++;
                        }
                    }
                    if (y < pawn.getX() - coordinates[0]) {
                        possible = false;
                    }
                    if (possible) {
                        //check if the move in one of these cases to cancel it
                        for (int i = pawn.getX()-1; i >= coordinates[0]; i--) {
                            if (!this.grid[coordinates[1]][i].isFree()) {
                                for (Pawn p : this.getCurrentPawns()) {
                                    if (p.contains(i, coordinates[1]) && i == 0) {
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
                }
                //calculate the number of pawns on the right diagonal
                else if ((coordinates[1]-pawn.getY() == coordinates[0]-pawn.getX()) && (coordinates[1] > pawn.getY()) && (coordinates[0] > pawn.getX())) {
                    int i = 0;
                    int j = 0;
                    int k = 0;
                    if (pawn.getX() > pawn.getY()) {
                        i = pawn.getY()-pawn.getX();
                        while (i < size) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            i++;
                            j++;
                        }
                    }
                    else {
                        j = pawn.getX()-pawn.getY();
                        while (j < size) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            i++;
                            j++;
                        }
                    }
                    if (k < coordinates[1] - pawn.getY()) {
                        possible = false;
                    }
                    if (possible) {
                        //check if the move in one of these cases to cancel it
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
                }
                //calculate the number of pawns on the left diagonal
                else if ((Math.abs(coordinates[1]-pawn.getY()) == coordinates[0]-pawn.getX()) && (coordinates[1] < pawn.getY()) && (coordinates[0] > pawn.getX())) {
                    int i = 0;
                    int j = 0;
                    int k = 0;
                    if (pawn.getX() > pawn.getY()) {
                        System.out.println("test");
                        i = pawn.getX() + pawn.getY();
                        while (i >= 0) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            i--;
                            j++;
                        }
                    }
                    else {
                        j = pawn.getX() + pawn.getY();
                        while (j >= 0) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            i++;
                            j--;
                        }
                    }
                    if (k < pawn.getY() - coordinates[1]) {
                        possible = false;
                    }
                    if (possible) {
                        //check if the move in one of these cases to cancel it
                        int x = pawn.getX() +1;
                        int y = pawn.getY() -1;
                        while (x <= coordinates[0] || y >= coordinates[1]) {
                            if (!this.grid[y][x].isFree()) {
                                for (Pawn p : this.getCurrentPawns()) {
                                    if (p.contains(x, y) && (x == size -1 || y == 0)) {
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
                }
                //calculate the number of pawns on the left diagonal
                else if ((coordinates[1]-pawn.getY() == Math.abs(coordinates[0]-pawn.getX())) && (coordinates[1] > pawn.getY()) && (coordinates[0] < pawn.getX())) {
                    int i = 0;
                    int j = 0;
                    int k = 0;
                    if (pawn.getX() > pawn.getY()) {
                        i = pawn.getX() + pawn.getY();
                        while (i >= 0) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            i--;
                            j++;
                        }
                    }
                    else {
                        j = pawn.getX() + pawn.getY();
                        while (j >= 0) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            i++;
                            j--;
                        }
                    }
                    if (k < coordinates[1] - pawn.getY()) {
                        possible = false;
                    }
                    if (possible) {
                        //check if the move in one of these cases to cancel it
                        int x = pawn.getX() -1;
                        int y = pawn.getY() +1;
                        while (x >= coordinates[0] || y <= coordinates[1]) {
                            if (!this.grid[y][x].isFree()) {
                                for (Pawn p : this.getCurrentPawns()) {
                                    if (p.contains(x, y) && (x == 0 || y == size -1)) {
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
                }
                //calculate the number of pawns on the right diagonal
                else if ((coordinates[1]-pawn.getY() == coordinates[0]-pawn.getX()) && (coordinates[1] < pawn.getY()) && (coordinates[0] < pawn.getX())) {
                    int i = 0;
                    int j = 0;
                    int k = 0;
                    if (pawn.getX() > pawn.getY()) {
                        i = pawn.getX()-pawn.getY();
                        while (i < size) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            i++;
                            j++;
                        }
                    }
                    else {
                        j = pawn.getY()-pawn.getX();
                        while (j < size) {
                            for (Pawn p : this.getCurrentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            for (Pawn p : this.getOpponentPawns()) {
                                if (p.getY() == j && p.getX() == i) {
                                    k++;
                                }
                            }
                            i++;
                            j++;
                        }
                    }
                    if (k < pawn.getY() - coordinates[1]) {
                        possible = false;
                    }
                    if (possible) {
                        //check if the move in one of these cases to cancel it
                        int x = pawn.getX() -1;
                        int y = pawn.getY() -1;
                        while (x >= coordinates[0] || y >= coordinates[1]) {
                            if (!this.grid[y][x].isFree()) {
                                for (Pawn p : this.getCurrentPawns()) {
                                    if (p.contains(x, y) && (x == 0 || y == 0)) {
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
                if (this.currentPlayer.getHuman()) {
                    this.displayGrid();
                }
                pawn = this.readPawn(this.currentPlayer);
                coordinates = this.readMove(this.currentPlayer);
                possible = movePossible(pawn, coordinates);
                if (!possible && this.currentPlayer.getHuman()) {
                    System.out.println("\n####################################################################\n#You can't move this pawn at these coordinates, please choose again#\n####################################################################\n");
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

            //check if one opponent pawn is on the new square of the moved pawn
            while (!good && i < this.getOpponentPawns().size()) {
                if (this.getOpponentPawns().get(i).getX() == coordinates[0] && this.getOpponentPawns().get(i).getY() == coordinates[1]) {
                    this.getOpponentPawns().remove(i);
                    good = true;
                }
                i++;
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

    /**
     * Prompt displayed every round to aks the players
     * if they want to continu, see the rules, save or quit
     */

    public void asks() {

        String x = "";
        String filename = "";

        do {
            System.out.println("What do you want to do ?\n*'C' to Continue\n*'R' to see the Rules\n*'S' to Save and quit the game\n*'Q' to Quit the game");
            x = SC.next();
        } while (!x.equalsIgnoreCase("c") && !x.equalsIgnoreCase("r") && !x.equalsIgnoreCase("s") && !x.equalsIgnoreCase("q"));

        if (x.equalsIgnoreCase("r")) {
            System.out.println(this.rules());
        }
        else if (x.equalsIgnoreCase("s")) {
            System.out.println("Write the name of the file you want to save the game in");
            filename = "../data/saves/" + SC.next() + ".bin";
            SaveGame.saveGame(this, filename);
            System.exit(0);
        }
        else if (x.equalsIgnoreCase("q")) {
            System.exit(0);
        }
    }

    /**
     * Clears the terminal
     * Credits to J.Rouillier for helping me doing this method
     */
    
    public static void clear() {
        if (System.getProperty("os.name").contains("Windows")) {
            try{
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch(IOException e){
                e.printStackTrace();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
  
        } 
        else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
     }

    /**
     * End the game
     * @param player : player who won the game
     */

    public void end(Player player) {

        System.out.println("\n\n========================================================================================================================================");
        System.out.println("Both of you fought well but at the end a winner has to be chosen ! Please give a warm applause to " + player.getName() + " for his win !\nHe will now have his name written in the Hall of Fame !");
        System.out.println("========================================================================================================================================\n\n");    
        System.exit(0);

    }

    /**
     * End the game in case of an equality
     * @param player1 : player who won
     * @param player2 : player who also won
     */

    public void end(Player player1, Player player2) {

        System.out.println("\n\n=====================================================================================================");
        System.out.println("You both have been amazing players and no one has been better than the other. So that's an equality !\nPlease give a warm applause to " + player1.getName() + " and " + player2.getName() + " for their incredible game !");
        System.out.println("=====================================================================================================\n\n");    
        System.exit(0);

    }
}