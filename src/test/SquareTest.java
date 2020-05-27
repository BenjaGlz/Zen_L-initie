package test;
import org.junit.*;
import static org.junit.Assert.*;
import game.Square;

public class SquareTest {

    Square s;

    @After() 
    public void makeNull() {
        s = null;
    }

    @Test()
    public void testInitial1() {
        Square s = new Square(5,4);
        assertNotNull(s);
    }

    @Test()
    public void testInitial2() {
        Square s = new Square(0,0);
        assertNotNull(s);
    }

    @Test() 
    public void testInitial3() {
        Square s = new Square(11,11);
        assertNotNull(s);
    }

    @Test() 
    public void testFree() {
        Square s = new Square(5,4);
        assertTrue(s.isFree());
    }

    @Test() 
    public void testBusy() {
        Square s = new Square(5,4);
        s.setBusy();
        assertFalse(s.isFree());
    }

}