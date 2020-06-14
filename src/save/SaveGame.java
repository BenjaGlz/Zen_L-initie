package save;
import game.*;
import java.io.*;

/**
 * Save the game 
 * @author B.Guillouzo
 */

public class SaveGame {

    /**
     * Save the game in the give file
     * @param game : game which is saved
     * @param filename : file where is stored the game
     */

    public static void saveGame(Game game, String filename) {

        try {
            FileOutputStream output = new FileOutputStream(filename);
            ObjectOutputStream data = new ObjectOutputStream(output);

            data.writeObject(game);
            data.close();

        } catch (FileNotFoundException f) {
            System.out.println("saveGame : Error - File not found") ;
        } catch (IOException i) {
            System.out.println("read : Error - input/output exception");
        } 

    }
    
}