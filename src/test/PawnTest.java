package test;
import org.junit.*;
import static org.junit.Assert.*;
import game.Pawn;
import game.PawnType;

public class PawnTest {

    Pawn p;

    @After()
    public void makeNull() {
        p = null;
    }

    @Test()
    public void testInitilial1() {
        p = new Pawn(5, 4, PawnType.WHITE);
        assertNotNull(p);
    }

    @Test() 
    public void testInitilial2() {
        p = new Pawn(11, 11, PawnType.ZEN);
        assertNotNull(p);
    }

    @Test() 
    public void testInitilial3() {
        p = new Pawn(0, 0, PawnType.BLACK);
        assertNotNull(p);
    }

    @Test() 
    public void testGet() {
        p = new Pawn(5, 4, PawnType.WHITE);
        assertEquals(5, p.getX());
        assertEquals(4, p.getY());
        assertSame(PawnType.WHITE, p.getType());
    }

    @Test() 
    public void testSet() {
        p = new Pawn(5, 4, PawnType.WHITE);

        p.setX(8);
        assertEquals(8, p.getX());
        p.setX(0);
        assertEquals(0, p.getX());
        p.setX(11);
        assertEquals(11, p.getX());

        p.setY(8);
        assertEquals(8, p.getY());
        p.setY(0);
        assertEquals(0, p.getY());
        p.setY(11);
        assertEquals(11, p.getY());

        p.setType(PawnType.BLACK);
        assertSame(PawnType.BLACK, p.getType());
        p.setType(PawnType.ZEN);
        assertSame(PawnType.ZEN, p.getType());
    }

    @Test() 
    public void testZen() {
        p = new Pawn(5, 4, PawnType.ZEN);
        assertTrue(p.isZen());
        p.setType(PawnType.WHITE);
        assertFalse(p.isZen());
    }

}