package util;

public final class Constants {
    private Constants() {}


    public static final int TILE_SIZE = 32; // px
    public static final int VIEW_WIDTH = 960; // px
    public static final int VIEW_HEIGHT = 540; // px
    public static final double DT = 1.0 / 60.0; // seconds per tick


    // Physics
    public static final double GRAVITY = 1800; // px/s^2
    public static final double MOVE_ACCEL = 8000; // px/s^2
    public static final double MOVE_DECEL = 7000; // px/s^2
    public static final double MAX_RUN_SPEED = 500; // px/s
    public static final double MAX_RUN_SPEED_BOOSTED = 850; // px/s
    public static final double JUMP_VELOCITY = -900; // px/s (negative is up)
    public static final double MOVE_ACCEL_BOOSTED = 20000;
    public static final double JUMP_VELOCITY_BOOSTED = -1200;
    public static final double WEB_VELOCITY_JUMP = -100;
    public static final double WEB_MAX_VELOCITY = 120;
    public static final double OVERSPEED_DECEL = 140;


    // QoL
    public static final double COYOTE_TIME = 0.1; // seconds
    public static final double JUMP_BUFFER = 0.1; // seconds

    public static final int STARTING_LIVES = 3;
    public static final int GOAL_SCORE = 5000;  // reach this to win the game


}