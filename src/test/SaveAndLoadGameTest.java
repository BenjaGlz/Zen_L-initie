package test;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import game.*;
import save.*;

public class SaveAndLoadGameTest {

    Game g1;

    @Before()
    public void Initialize() {
        String name1 = "name1";
        String name2 = "name2";
        Pawn p1 = new Pawn(5,2,PawnType.WHITE);
        Pawn p2 = new Pawn(5,3,PawnType.BLACK);
        Pawn p3 = new Pawn(5,4,PawnType.BLACK);
        ArrayList<Pawn> whitePawns = new ArrayList<Pawn>();
        ArrayList<Pawn> blackPawns = new ArrayList<Pawn>();
        whitePawns.add(p1);
        blackPawns.add(p2);
        blackPawns.add(p3);
        g1 = new Game(blackPawns, whitePawns, name1, name2);
    }

    @After() 
    public void makeNull() {
        g1 = null;
    }

    @Test() 
    public void saveAndLoadTest() {
        SaveGame.saveGame(g1, "../data/save1.txt");
        Game g2 = LoadGame.loadGame("../data/save1.txt");
        assertSame(g1, g2);
    }  
}