package test;

import org.junit.*;
import static org.junit.Assert.*;
import game.*;
import java.util.ArrayList;

public class GameTest {

    Game g;
    Player play1;
    Player play2;
    String name1;
    String name2;
    Pawn p1;
    Pawn p2;
    Pawn p3;
    Pawn p4;
    ArrayList<Pawn> whitePawns;
    ArrayList<Pawn> blackPawns;
    Mode mode;

    @Before()
    public void initialize() {
        name1 = "name1";
        name2 = "name2";
        p1 = new Pawn(5,2,PawnType.WHITE);
        p2 = new Pawn(5,3,PawnType.BLACK);
        p3 = new Pawn(5,4,PawnType.BLACK);
        p4 = new Pawn(5,4, PawnType.ZEN);
        whitePawns = new ArrayList<Pawn>();
        blackPawns = new ArrayList<Pawn>();
        whitePawns.add(p1);
        whitePawns.add(p4);
        blackPawns.add(p2);
        blackPawns.add(p3);
        mode = Mode.HH;
        g = new Game(blackPawns, whitePawns, name1, name2, mode);
        play1 = new HumanPlayer(name1, blackPawns);
        play2 = new HumanPlayer(name2, whitePawns);
    }

    @After()
    public void makeNull() {
        name1 = null;
        name2 = null;
        p1 = null;
        p2 = null;
        p3 = null;
        p4 = null;
        whitePawns = null;
        blackPawns = null;
        mode = null;
        g = null;
        play1 = null;
        play2 = null;
    }

    @Test()
    public void InitializeTest() {
        assertNotNull(g);
    }

    @Test()
    public void alignedTest() {
        boolean align = true;           //result of the call of the method aligned of player 1
        assertTrue(g.aligned(play1));
    }

    @Test 
    public void containsTest() {
        Pawn p = g.contains(5, 2);
        assertSame(p, p1);
    }

    @Test 
    public void getCurrentPawntest() {
        assertSame(blackPawns, g.getCurrentPawns());
    }

    @Test 
    public void getOpponentPawntest() {
        assertSame(whitePawns, g.getOpponentPawns());
    }
}