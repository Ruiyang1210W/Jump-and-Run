
import org.junit.Test;
import static org.junit.Assert.*;
import model.*;
import view.DemoLevels;
import util.Constants;


public class CollisionTest {
    @Test public void testLandOnGround(){
        GameState s = new GameState(DemoLevels.basicDemo());
// Fall long enough to land
        for (int i=0;i<300;i++) s.tick(Constants.DT);
        assertTrue(s.player.onGround);
        assertEquals(0.0, s.player.vel.y, 1e-6);
    }
}
