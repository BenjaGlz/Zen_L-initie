import game.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Launches the whole game of Zen
 * @author B.Guillouzo
 */

 public class LaunchGame {

    public static void main(String[] args) {  

    JFrame frame = new JFrame();

    String name1 = JOptionPane.showInputDialog(frame, "Write the name of the first player");
    String name2 = JOptionPane.showInputDialog(frame, "Write the name of the second player");

     Zen zen = new Zen(name1, name2);
        
    }

 }