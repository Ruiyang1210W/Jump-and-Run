package model;
import util.Constants;
import util.ObservableModel;

public class GameState extends ObservableModel{
    public Level level;
    public Player player = new Player();



    public int score = 0;
    public int lives = Constants.STARTING_LIVES;
    public double timer = 0;
    public boolean running = true;

    // camera
    public double camX = 0, camY = 0;

    // input cache from controller
    private double moveInput = 0;

    public GameState(Level level){
        this.level = level;
        reset();
    }

    public void reset() {
        player.pos.set(96, 128);
        player.vel.set(0, 0);

        // clear any input so it don't drift on restart
        setMoveInput(0);
        player.setJumpHeld(false);

        timer = 0;
        running = true;
        fireChange("reset", null, null);
    }

    // Allow switching to a new Level object (for "Next Level" button)
    public void setLevel(Level newLevel) {
        this.level = newLevel;
        reset();   // reuse the same reset() method to place the player and restart the timer
    }


    private boolean checkScoreWin() {
        if (score >= Constants.GOAL_SCORE) {
            running = false;                // pause game
            util.Sound.play("/assets/sounds/win.wav"); // win sound
            fireChange("gamewin", null, null);
            return true;
        }
        return false;
    }




    public void setMoveInput(double dir){ this.moveInput = Math.max(-1, Math.min(1, dir)); }
    public void onJumpPressed(){ player.bufferJump(); }
    public void onJumpReleased(){ player.setJumpHeld(false); }
    public void onJumpHeld(){ player.setJumpHeld(true); }


    public void tick(double dt){
        if (!running) return;

        timer += dt;
        player.tick(level, dt, moveInput);

        // 1) Pit / fall death (use feet position)
        double feetY = player.pos.y + player.h;
        double killY = (level.height + 2) * Constants.TILE_SIZE; // margin below map
        if (feetY > killY) {
            die();
            return;
        }

        // 2) Hazard death (spikes, etc.)
        if (level.rectHitsHazard(player.bounds())) {
            die();
            return;
        }

        // 3) Goal reached (time bonus included)
        if (atGoal()) {
            util.Sound.play("/assets/sounds/goal.wav"); // reach nest tweet sound!
            int timeBonus = (int) Math.max(0, 5000 - timer * 100);
            score += 1000 + timeBonus;
            checkScoreWin();
            running = false;
            fireChange("win", null, null);
            return;
        }

        // 4) Camera + regular tick update
        camX = Math.max(0, player.pos.x - Constants.VIEW_WIDTH / 2.0 + player.w / 2.0);
        camY = Math.max(0, player.pos.y - Constants.VIEW_HEIGHT / 2.0 + player.h / 2.0);
        fireChange("tick", null, null);

        if (checkScoreWin()) return; // Check win-by-score even if points come from other sources
    }

    // ---- Helpers ----
    private void die() {
        if (!running) return;                // guard against double-calls
        lives = Math.max(0, lives - 1);      // decrement FIRST so HUD sees 0 immediately
        running = false;                     // stop loop

        util.Sound.play("/assets/sounds/hurt.wav");
        fireChange("death", null, null);     // notify view after state is updated
    }


    private boolean atGoal() {
        int left = (int)Math.floor(player.pos.x / Constants.TILE_SIZE);
        int right = (int)Math.floor((player.pos.x + player.w) / Constants.TILE_SIZE);
        int top = (int)Math.floor(player.pos.y / Constants.TILE_SIZE);
        int bottom = (int)Math.floor((player.pos.y + player.h) / Constants.TILE_SIZE);

        for (int ty = top; ty <= bottom; ty++) {
            for (int tx = left; tx <= right; tx++) {
                if (level.getTileAt(tx, ty) == Tile.GOAL) return true;
            }
        }
        return false;
    }






}
