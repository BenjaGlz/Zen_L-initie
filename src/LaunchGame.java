import game.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Launches the whole game of Zen
 * @author B.Guillouzo
 */

 public class LaunchGame {

    public static void main(String[] args) {

      String name1 = "";
      String name2 = "";

      try {
         Scanner sc = new Scanner(System.in);

         System.out.println("Write the name of the first player");
         name1 = sc.next();
         System.out.println("Write the name of the second player");
         name2 = sc.next();

         sc.close();
     } catch(NoSuchElementException n){
         System.err.println("LaunchGame : Error - no token available");
     }

     Zen zen = new Zen(name1, name2);
        
    }

 }