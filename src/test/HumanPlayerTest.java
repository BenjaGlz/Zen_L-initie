package test;
import org.junit.*;
import static org.junit.Assert.*;
import game.HumanPlayer;
import game.Pawn;
import game.PawnType;
import java.util.ArrayList;

public class HumanPlayerTest {

    HumanPlayer p;

    @After
    public void makeNull() {
        p = null;
    }

    @Test
    public void testInitial1() {
        String name = "Pierre";
        Pawn p1 = new Pawn(5, 5, PawnType.WHITE);
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        pawns.add(p1);
        p = new HumanPlayer(name, pawns);
        assertNotNull(p);
    }

    @Test
    public void testAlignedTrue() {
        String name = "Pierre";
        Pawn p1 = new Pawn(5, 5, PawnType.WHITE);
        Pawn p2 = new Pawn(5, 6, PawnType.WHITE);
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        pawns.add(p1);
        pawns.add(p2);
        p = new HumanPlayer(name, pawns);
        assertTrue(p.aligned());
    }

    @Test
    public void testAlignedFalse() {
        String name = "Pierre";
        Pawn p1 = new Pawn(5, 5, PawnType.WHITE);
        Pawn p2 = new Pawn(2, 7, PawnType.WHITE);
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        pawns.add(p1);
        pawns.add(p2);
        p = new HumanPlayer(name, pawns);
        assertFalse(p.aligned());
    }
    
}