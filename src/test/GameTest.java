package test;
import org.junit.*;
import static org.junit.Assert.*;
import game.*;
import java.util.ArrayList;

public class GameTest {

    Game g;
    Player play1;
    Player play2;

    @Before()
    public void initialize() {
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
        g = new Game(blackPawns, whitePawns, name1, name2);
        play1 = new HumanPlayer(name1, blackPawns);
        play2 = new HumanPlayer(name2, whitePawns);
    }

    @After()
    public void makeNull() {
        g = null;
    }

    @Test()
    public void InitializeTest() {
        assertNotNull(g);
    }

    @Test()
    public void alignedTest() {
        boolean align = true;           //result of the call of the method aligned of player
        assertTrue(g.aligned(play1));
    }

    @Test() 
    public void getWhitePawnsTest() {
        Pawn p1 = new Pawn(5,2,PawnType.WHITE);
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        pawns.add(p1);
        assertEquals(pawns, g.getWhitePawns());
    }

    @Test() 
    public void getBlackPawnsTest() {
        Pawn p2 = new Pawn(5,3,PawnType.BLACK);
        Pawn p3 = new Pawn(5,4,PawnType.BLACK);
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        pawns.add(p2);
        pawns.add(p3);
        assertEquals(pawns, g.getBlackPawns());
    }

    @Test()
    public void changeCurrentTest() {
        if (g.getCurrentPlayer().equals(play1)) {
            g.changeCurrentPlayer();
            assertSame(play2, g.getCurrentPlayer());
        }
        else {
            g.changeCurrentPlayer();
            assertSame(play1, g.getCurrentPlayer());
        }  
    }

    @Test()
    public void readMoveTest() {
        int[] tab = play1.newMove();    //table containing the coordinate the player wants to move his pawn
        assertArrayEquals(tab, g.readMove(play1));
    }

    @Test()
    public void readPawnTest() {
        Pawn pawn = play1.choosePawn();     //pawn chosen by te player
        assertEquals(pawn, g.readPawn(play1));
    }

    @Test()
    public void readZenTest() {
        ZenType type = play1.chooseZen();   //type of the zen pawn chosen by the player  
        assertEquals(type, g.readZen(play1));
    }

    @Test()
    public void movePossibleTest() {
        int[] tab1 = {7, 7};
        Pawn pawn = new Pawn(7,9, PawnType.WHITE);
        ZenType type = ZenType.FRIEND;
        Pawn zen = new Pawn(7, 8, PawnType.ZEN);
        assertFalse(g.movePossible(pawn, tab1, type));
        int[] tab2 = {7, 10};
        assertTrue(g.movePossible(pawn, tab2, type));
    }
    
    @Test()
    public void moveTest() {
        Pawn pawn = new Pawn(5,4,PawnType.WHITE);
        int[] tab = {7,8};
        g.move(pawn, tab);
        assertEquals(7, pawn.getX());
        assertEquals(8, pawn.getY());
    }

    @Test()
    public void changeZenTest() {
        if (g.readZen(play1).equals(ZenType.FRIEND)) {
            g.changeZen();
            assertSame(ZenType.OPPONENT, g.readZen(play1));
        }
        else {
            g.changeZen();
            assertSame(ZenType.FRIEND, g.readZen(play1));
        }
    }
}