package game;
import save.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
            JFrame frame = new JFrame();

            if (save) {  
                do {
                    gameFile = "../../data/" + JOptionPane.showInputDialog(frame, "Write the name of the file containing the game you wanna load");
                }  while (gameFile == null);
                this.game = LoadGame.loadGame(gameFile);
            }
            else {  
                this.configure();
                Game game = new Game(this.blackPawns, this.whitePawns, name1, name2, this.mode);
                game.start();
            }

        }

     }

     public boolean launch() {

        boolean launch = false;
        String saved = "";
        JFrame frame = new JFrame();
        
        do {
            saved = JOptionPane.showInputDialog(frame, "Write 'yes' if you want to launch a saved game, 'no' otherwise");
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
        JFrame frame = new JFrame();
        
        do {
            mode = JOptionPane.showInputDialog(frame, "Write 'HH' if you want to play against a friend, 'HA' for against a bot");
        } while (!mode.equalsIgnoreCase("HH") && !mode.equalsIgnoreCase("HA"));
        

        if (mode.equals("HH")) {
            this.mode = Mode.HH;
        }
        else {
            this.mode = Mode.HA;
        }
        

     }
}

/**
 * garder zentype
 * si zentype = opponent --> mettre dans arraylist du pas current player
 * pour trouver le zen --> parcourir liste jusqua trouver type = zen avec getType
 */