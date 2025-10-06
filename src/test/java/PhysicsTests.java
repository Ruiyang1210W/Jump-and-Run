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

        // Land on ground first
        tick(s, 300);
        double groundY = s.player.pos.y;

        // --- Short jump: release early ---
        s.onJumpHeld(); s.onJumpPressed();
        double minYShort = s.player.pos.y;
        for (int i=0;i<4;i++) { tick(s,1); if (s.player.pos.y < minYShort) minYShort = s.player.pos.y; }
        s.onJumpReleased(); // early release
        for (int i=0;i<40;i++) { tick(s,1); if (s.player.pos.y < minYShort) minYShort = s.player.pos.y; }

        // Reset to ground
        s.softReset(); tick(s, 300);

        // --- Long jump: hold longer ---
        s.onJumpHeld(); s.onJumpPressed();
        double minYLong = s.player.pos.y;
        for (int i=0;i<20;i++) { tick(s,1); if (s.player.pos.y < minYLong) minYLong = s.player.pos.y; }
        s.onJumpReleased();
        for (int i=0;i<40;i++) { tick(s,1); if (s.player.pos.y < minYLong) minYLong = s.player.pos.y; }

        // Smaller Y = higher jump. Long jump should be higher than short jump by a decent margin.
        assertTrue("Long jump should reach higher than short jump", minYLong < minYShort - 6.0);
        assertTrue("Both jumps should leave ground", minYShort < groundY && minYLong < groundY);
    }

    @Test
    public void jumpBuffer_beforeLandingTriggersJump() {
        GameState s = new GameState(DemoLevels.basicDemo());

        // Start in air: player starts above ground -> falling
        // Just before landing, buffer jump; after landing, jump should fire (vel.y < 0 soon after).
        boolean buffered = false;
        double lastVy = s.player.vel.y;
        for (int i=0;i<400;i++){
            // When we get close to ground (vy positive and small), buffer
            if (!buffered && s.player.vel.y > 0 && Math.abs(s.player.vel.y) < 100) {
                s.onJumpHeld(); s.onJumpPressed();
                buffered = true;
            }
            s.tick(Constants.DT);
            lastVy = s.player.vel.y;
            if (buffered && lastVy < 0){ // upward happened after touchdown + buffer
                assertTrue(true);
                return;
            }
        }
        fail("Buffered jump did not trigger after landing.");
    }

    @Test
    public void horizontalSpeedIsClamped() {
        GameState s = new GameState(DemoLevels.basicDemo());
        // Hold right long enough to accelerate
        s.setMoveInput(1);
        tick(s, 180);
        assertTrue("Speed should not exceed MAX_RUN_SPEED",
                s.player.vel.x <= Constants.MAX_RUN_SPEED + 1e-6);
    }

    @Test
    public void ceilingCollisionStopsUpwardVelocity() {
        GameState s = new GameState(DemoLevels.basicDemo());
        // Place under a platform at row 12, columns 5..15
        double ceilingY = 12 * Constants.TILE_SIZE; // tile top in pixels
        s.player.pos.set(8 * Constants.TILE_SIZE, ceilingY + 2); // just below
        tick(s, 1); // settle

        s.onJumpHeld(); s.onJumpPressed();
        boolean hitHead = false;
        for (int i=0;i<30;i++){
            double vyBefore = s.player.vel.y;
            s.tick(Constants.DT);
            if (vyBefore < 0 && s.player.vel.y == 0) { // upward -> then zero (head bump)
                hitHead = true; break;
            }
        }
        assertTrue("Upward motion should stop on ceiling collision", hitHead);
        assertFalse("Player should not be on ground after head bump", s.player.onGround);
    }
}
