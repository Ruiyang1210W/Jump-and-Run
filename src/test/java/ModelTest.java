
import org.junit.Test;
import static org.junit.Assert.*;
import model.*;
import view.DemoLevels;
import util.Constants;


public class ModelTest {
    @Test public void testGravityMakesPlayerFall(){
        GameState s = new GameState(DemoLevels.basicDemo());
        double y0 = s.player.pos.y;
        s.tick(Constants.DT);
        assertTrue(s.player.pos.y > y0);
    }


    @Test public void testCoyoteAndBufferEnableEdgeJump(){
        GameState s = new GameState(DemoLevels.basicDemo());
        DemoLevels.nextLevel();
        s = new GameState(DemoLevels.nextLevel());
// drop near ground and simulate just-walk-off then buffered jump
        for (int i=0;i<10;i++) s.tick(Constants.DT);
        s.setMoveInput(1);
        for (int i=0;i<60;i++) s.tick(Constants.DT);
        s.onJumpHeld(); s.onJumpPressed();
        for (int i=0;i<3;i++) s.tick(Constants.DT);
        assertTrue("Jump should initiate (vel.y < 0)", s.player.vel.y < 0);
    }
}
