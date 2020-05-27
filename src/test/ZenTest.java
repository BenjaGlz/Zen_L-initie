package test;
import org.junit.*;
import static org.junit.Assert.*;
import game.*;

public class ZenTest {

    Zen z;

    @Test()
    public void Initialize() {
        String name1 = "name1";
        String name2 = "name2";
        z = new Zen(name1, name2);
        assertNotNull(z);
    }
    
}