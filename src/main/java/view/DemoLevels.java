package view;

import model.Level;
import model.Tile;

/**
 * Simple hardcoded demo levels.
 * Tiles index as tiles[y][x], where y = row (down), x = col (right).
 */
public class DemoLevels {

    // Track which level is active (used by nextLevel and save/load)
    private static int current = 0;

    // ---------- Level 1 ----------
    public static Level level1() {
        int w = 70, h = 20;
        Level L = new Level(w, h);

        // Ground with pits
        for (int x = 0; x < w; x++) {
            boolean pit = (x >= 12 && x <= 16) || (x >= 28 && x <= 30) || (x >= 55 && x <= 60);
            if (!pit) {
                L.tiles[h - 2][x] = Tile.SOLID.code;
                L.tiles[h - 1][x] = Tile.SOLID.code;
            }
        }

        // Platforms
        for (int x = 5;  x < 15; x++) L.tiles[12][x] = Tile.SOLID.code;
        for (int x = 20; x < 30; x++) L.tiles[10][x] = Tile.SOLID.code;
        for (int x = 35; x < 45; x++) L.tiles[8][x]  = Tile.SOLID.code;

        // Spikes
        L.tiles[h - 2][18] = Tile.SPIKE.code;
        L.tiles[h - 2][19] = Tile.SPIKE.code;

        // Goal
        L.tiles[6][48] = Tile.GOAL.code;

        return L;
    }

    // ---------- Level 2 ----------
    public static Level level2() {
        int w = 80, h = 22;
        Level L = new Level(w, h);

        // Ground with pits (ensure ranges < w)
        for (int x = 0; x < w; x++) {
            boolean pit = (x >= 8  && x <= 12)
                    || (x >= 24 && x <= 29)
                    || (x >= 40 && x <= 41)
                    || (x >= 63 && x <= 70);  // removed out-of-bounds 82..84
            if (!pit) {
                L.tiles[h - 2][x] = Tile.SOLID.code;
                L.tiles[h - 1][x] = Tile.SOLID.code;
            }
        }

        // --- platforms (some higher) ---
        for (int x = 15; x < 22; x++) L.tiles[15][x] = Tile.SOLID.code;
        for (int x = 20; x < 28; x++) L.tiles[11][x] = Tile.SOLID.code;
        for (int x = 30; x < 39; x++) L.tiles[9][x]  = Tile.SOLID.code;
        for (int x = 40; x < 53; x++) L.tiles[10][x]  = Tile.SOLID.code;
        for (int x = 55; x < 63; x++) L.tiles[12][x] = Tile.SOLID.code;
        for (int x = 63; x < 70; x++) L.tiles[9][x] = Tile.SOLID.code;
        for (int x = 70; x < 80; x++) L.tiles[12][x] = Tile.SOLID.code;

        // Spikes
        L.tiles[h - 3][23] = Tile.SPIKE.code;
        L.tiles[h - 3][42] = Tile.SPIKE.code;
        L.tiles[12][21]    = Tile.SPIKE.code;

        // Goal
        L.tiles[7][78] = Tile.GOAL.code;

        return L;
    }

    // ---------- Level helpers ( for Save/Load + Next Level) ----------

    /** Number of levels available. */
    public static int levelCount() { return 2; }

    /** Current level index (0-based) for saving. */
    public static int getCurrentIndex() { return current; }

    /** Restore which level is active (for loading). */
    public static void setCurrentIndex(int idx) {
        current = Math.max(0, Math.min(levelCount() - 1, idx));
    }

    /** Build a level by index. Used by loader. */
    public static Level byIndex(int idx) {
        return (idx == 1) ? level2() : level1();
    }

    /** Advance to the next level and return it. Used by EndingPanel. */
    public static Level nextLevel() {
        current = (current + 1) % levelCount();
        return byIndex(current);
    }

    /** Start the game from level 1. Used by MainFrame on startup. */
    public static Level basicDemo() {
        current = 0;
        return level1();
    }
}


