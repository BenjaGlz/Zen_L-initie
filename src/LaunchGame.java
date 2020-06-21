import game.*;
import java.util.Scanner;
import java.io.IOException;

/**
 * Launches the whole game of Zen
 * @author B.Guillouzo
 */

 public class LaunchGame {

   private static final Scanner SC = new Scanner(System.in);

   public static void main(String[] args) { 

      Game.clear();

      System.out.println("\n\n   ▄▄▄▄▄▄   ▄███▄      ▄       █    ▄█    ▄   ▄█    ▄▄▄▄▀ ▄█ ▄███▄\n  ▀   ▄▄▀   █▀   ▀      █      █    ██     █  ██ ▀▀▀ █    ██ █▀   ▀  \n   ▄▀▀   ▄▀ ██▄▄    ██   █     █    ██ ██   █ ██     █    ██ ██▄▄    \n   ▀▀▀▀▀▀   █▄   ▄▀ █ █  █     ███▄ ▐█ █ █  █ ▐█    █     ▐█ █▄   ▄▀ \n            ▀███▀   █  █ █         ▀ ▐ █  █ █  ▐   ▀       ▐ ▀███▀   \n                    █   ██             █   ██\n\n                        ");

      System.out.println("\n==================[ Write the name of the first player ]==================");
      String name1 = SC.next();
      System.out.println("\n==================[ Write the name of the second player ]=================");
      String name2 = SC.next();

      Zen zen = new Zen(name1, name2);
        
   }

}