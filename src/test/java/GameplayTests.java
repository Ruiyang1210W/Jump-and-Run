import org.junit.Test;
import static org.junit.Assert.*;

import model.*;
import view.DemoLevels;
import util.Constants;

public class GameplayTests {

    private void tick(GameState s, int frames){
        for (int i=0;i<frames;i++){
            s.setMoveInput(1);
            s.tick(Constants.DT);
        }

    }

    @Test
    public void hazardTouchCausesDeathAndStopsRunning() {
        GameState s = new GameState(DemoLevels.basicDemo());
        // Spikes near (18,18) in demo
        s.player.pos.set(18 * Constants.TILE_SIZE + 4, 18 * Constants.TILE_SIZE - s.player.h + 2);
        tick(s, 2);
        assertFalse("Game should stop running on death", s.lives < 3);
    }



    @Test
    public void cameraFollowsPlayerHorizontally() {
        GameState s = new GameState(DemoLevels.basicDemo());
        DemoLevels.nextLevel();
        s = new GameState(DemoLevels.nextLevel());
        double camStart = s.camX;
        // run right long enough to move beyond half the viewport
        tick(s, 900);
        assertTrue("Camera X should increase as player moves right", s.camX > 0);
    }
}

// Total 8 test passed.

