package game;
import save.*;
import java.util.*;

/**
 * Class which set up the game all along it
 * @author B.Guillouzo
 */

public class Zen {

    private static final int size = 11;
    private Game game;
    private Mode mode;
    private ArrayList<Pawn> blackPawns;
    private ArrayList<Pawn> whitePawns;
    private static final Scanner SC = new Scanner(System.in);

    /**
     * Constructor of the Zen class
     */
    
     public Zen(String name1, String name2) {

        if (name1 == null) {
            System.err.println("Zen : Error - null value for name1");
        }
        if (name2 == null) {
            System.err.println("Zen : Error - null value for name2");
        }
        if (name1 != null && name2 != null) {

            boolean save = this.launch();
            String gameFile = "";

            if (save) {  
                do {
                    System.out.println("Write the name of the file containing the game you wanna load :");
                    gameFile = "../data/" + SC.nextLine();
                }  while (gameFile == null && SC.hasNextLine());
                this.game = LoadGame.loadGame(gameFile);
                this.game.start();
            }
            else {  
                this.configure();
                this.game = new Game(this.blackPawns, this.whitePawns, name1, name2, this.mode);
                this.game.start();
            }
        }
     }

     public boolean launch() {

        boolean launch = false;
        String saved = "";
        
        do {
            System.out.println("\n\nNew game or saved game ?\n* 'Yes' to launch a saved game\n* 'No' to start a new game");
            saved = SC.nextLine();
        } while (!saved.equalsIgnoreCase("YES") && !saved.equalsIgnoreCase("NO"));    

        if (saved.equalsIgnoreCase("YES")) {
            launch = true;
        }

        return launch;

     }

     /**
      * Set up all the game
      */

     public void configure() {

        blackPawns = new ArrayList<Pawn>();
        whitePawns = new ArrayList<Pawn>();

        this.blackPawns.add(new Pawn(0, 0, PawnType.BLACK));
        this.blackPawns.add(new Pawn(4, 1, PawnType.BLACK));
        this.blackPawns.add(new Pawn(6, 1, PawnType.BLACK));
        this.blackPawns.add(new Pawn(2, 3, PawnType.BLACK));
        this.blackPawns.add(new Pawn(8, 3, PawnType.BLACK));
        this.blackPawns.add(new Pawn(0, 5, PawnType.BLACK));
        this.blackPawns.add(new Pawn(10, 5, PawnType.BLACK));
        this.blackPawns.add(new Pawn(2, 7, PawnType.BLACK));
        this.blackPawns.add(new Pawn(8, 7, PawnType.BLACK));
        this.blackPawns.add(new Pawn(4, 9, PawnType.BLACK));
        this.blackPawns.add(new Pawn(6, 9, PawnType.BLACK));
        this.blackPawns.add(new Pawn(10, 10, PawnType.BLACK));
        this.blackPawns.add(new Pawn(5, 5, PawnType.ZEN));

        this.whitePawns.add(new Pawn(5, 0, PawnType.WHITE));
        this.whitePawns.add(new Pawn(10, 0, PawnType.WHITE));
        this.whitePawns.add(new Pawn(3, 2, PawnType.WHITE));
        this.whitePawns.add(new Pawn(7, 2, PawnType.WHITE));
        this.whitePawns.add(new Pawn(1, 4, PawnType.WHITE));
        this.whitePawns.add(new Pawn(9, 4, PawnType.WHITE));
        this.whitePawns.add(new Pawn(1, 6, PawnType.WHITE));
        this.whitePawns.add(new Pawn(9, 6, PawnType.WHITE));
        this.whitePawns.add(new Pawn(3, 8, PawnType.WHITE));
        this.whitePawns.add(new Pawn(7, 8, PawnType.WHITE));
        this.whitePawns.add(new Pawn(0, 10, PawnType.WHITE));
        this.whitePawns.add(new Pawn(5, 10, PawnType.WHITE));

        String mode = "";
        
        do {
            System.out.println("\n\nMode of the game\n* 'HH' for a multiplayer game\n* 'HA' for a game against a bot");
            mode = SC.nextLine();
        } while (!mode.equalsIgnoreCase("HH") && !mode.equalsIgnoreCase("HA") && SC.hasNextLine());

        if (mode.equalsIgnoreCase("HH")) {
            this.mode = Mode.HH;
        }
        else {
            this.mode = Mode.HA;
        }
        

     }
}