package test;
import org.junit.*;
import static org.junit.Assert.*;
import game.AutoPlayer;
import game.Pawn;
import game.PawnType;
import java.util.ArrayList;

public class AutoPlayerTest {

    AutoPlayer p;

    @After
    public void makeNull() {
        p = null;
    }

    @Test
    public void testInitial() {
        String name = "Alfred";
        Pawn p1 = new Pawn(5, 5, PawnType.WHITE);
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        pawns.add(p1);
        p = new AutoPlayer(name, pawns);
        assertNotNull(p);
    }

    @Test
    public void testAlignedTrue() {
        String name = "Alfred";
        Pawn p1 = new Pawn(5, 5, PawnType.WHITE);
        Pawn p2 = new Pawn(5, 6, PawnType.WHITE);
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        pawns.add(p1);
        pawns.add(p2);
        p = new AutoPlayer(name, pawns);
        assertTrue(p.aligned());
    }

    @Test
    public void testAlignedFalse() {
        String name = "Alfred";
        Pawn p1 = new Pawn(5, 5, PawnType.WHITE);
        Pawn p2 = new Pawn(2, 7, PawnType.WHITE);
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        pawns.add(p1);
        pawns.add(p2);
        p = new AutoPlayer(name, pawns);
        assertFalse(p.aligned());
    }
    
}