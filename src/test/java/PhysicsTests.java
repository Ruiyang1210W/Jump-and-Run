import org.junit.Test;
import static org.junit.Assert.*;

import model.*;
import view.DemoLevels;
import util.Constants;

public class PhysicsTests {

    private void tick(GameState s, int frames){
        for (int i=0;i<frames;i++) s.tick(Constants.DT);
    }

    @Test
    public void variableJumpHeight_shortVsLong() {
        GameState s = new GameState(DemoLevels.basicDemo());
        // land
        tick(s, 300);
        double groundY = s.player.pos.y;

        // short jump (release early)
        s.onJumpHeld(); s.onJumpPressed();
        double minYShort = s.player.pos.y;
        for (int i=0;i<4;i++){ tick(s,1); minYShort = Math.min(minYShort, s.player.pos.y); }
        s.onJumpReleased();
        for (int i=0;i<40;i++){ tick(s,1); minYShort = Math.min(minYShort, s.player.pos.y); }

        // reset to ground
        s.reset(); tick(s, 300);

        // long jump (hold)
        s.onJumpHeld(); s.onJumpPressed();
        double minYLong = s.player.pos.y;
        for (int i=0;i<24;i++){ tick(s,1); minYLong = Math.min(minYLong, s.player.pos.y); }
        s.onJumpReleased();

        assertTrue("Both jumps should leave ground", minYShort < groundY && minYLong < groundY);
        assertTrue("Held jump should go higher than tap", minYLong < minYShort - 6.0);
    }

    @Test
    public void horizontalSpeedIsClamped() {
        GameState s = new GameState(DemoLevels.basicDemo());
        s.setMoveInput(1);
        tick(s, 200); // accelerate
        assertTrue("Should not exceed MAX_RUN_SPEED",
                s.player.vel.x <= Constants.MAX_RUN_SPEED + 1e-6);
    }

    @Test
    public void ceilingCollisionStopsUpwardVelocity() {
        DemoLevels.basicDemo();
        GameState s = new GameState(DemoLevels.nextLevel());
        // There’s a platform row around ty=12 in the demo; place just below it.
        //double ceilingY = 12 * Constants.TILE_SIZE;
        s.player.pos.set(40 * Constants.TILE_SIZE, 20);

        // jump up and detect head bump (vy goes from negative to 0)
        boolean bumped = false;
        for (int i=0;i<900;i++){
            s.onJumpHeld(); s.onJumpPressed();
            double vyBefore = s.player.vel.y;
            s.tick(Constants.DT);
            if (vyBefore < 0 && s.player.vel.y == 0){ bumped = true; break; }
        }
        assertTrue("Upward motion should zero on ceiling hit", bumped);
        assertFalse("Should be airborne after bump (not grounded)", s.player.onGround);
    }
}

