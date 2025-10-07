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
        int w = 80, h = 22;
        Level L = new Level(w, h);

        //Wood tree
        for(int y = h-1; y >= 4; y--){
            for(int x = 0; x <= 2; x++){
                L.tiles[y][x] = Tile.WOOD.code;
            }
        }
        //Leafs
        for(int y = 4; y >= 0; y--){
            for(int x = 0; x <= 6; x++){
                L.tiles[y][x] = Tile.LEAF.code;
            }
        }

        for (int x = 0; x < w; x++) {
            boolean pit = (x >= 12 && x <= 17) || (x >= 44 && x <= 49);
            if (!pit) {
                L.tiles[h - 2][x] = Tile.SOLID.code; // row 20
                L.tiles[h - 1][x] = Tile.SOLID.code; // row 21
            }
        }
        // Gentle hazard: pit floors are spikes (forces jump/boost but still short).
        for (int x = 12; x <= 17; x++) L.tiles[h - 2][x] = Tile.SPIKE.code; // row 20
        for (int x = 44; x <= 49; x++) L.tiles[h - 2][x] = Tile.SPIKE.code; // row 20

        // ---------- SIMPLE, FORGIVING PLATFORMS ----------
        // Low mid platform (easy jump from ground with a JUMP_BOOST helper).
        for (int x = 18; x <= 30; x++) L.tiles[10][x] = Tile.SOLID.code;  // row 16
        // Next platform slightly higher but reachable (or you can stay low).
        for (int x = 35; x <= 47; x++) L.tiles[13][x] = Tile.SOLID.code;  // row 13
        // Final runway to the goal.
        for (int x = 65; x <= 79; x++) L.tiles[12][x] = Tile.SOLID.code;  // row 12

        // ---------- SPECIAL SOLID TILES: SPEED & JUMP ----------
        // 1) Speed pads before the first pit: run and you’ll clear it easily.
        for (int x = 8; x <= 11; x++) L.tiles[20][x] = Tile.SPEED_BOOST.code; // row 20

        // 2) A tiny "ladder" via jump boosts to pop onto the row-16 platform.
        L.tiles[20][18] = Tile.JUMP_BOOST.code; // ground pop
        L.tiles[20][19] = Tile.JUMP_BOOST.code;

        // 3) A couple helpful boosts along the top route (still optional).
        for (int x = 27; x <= 30; x++) L.tiles[10][x] = Tile.SPEED_BOOST.code; // glide toward next shelf
        for (int x = 26; x <= 28; x++) L.tiles[16][x] = Tile.SPEED_BOOST.code; // glide toward next shelf
        for( int x = 44; x <= 46; x++) L.tiles[13][x] = Tile.SPEED_BOOST.code;
        L.tiles[13][47] = Tile.JUMP_BOOST.code;                                // easy hop to the right
        L.tiles[20][63] = Tile.JUMP_BOOST.code;

        // 4) Final stretch: comfy speed into goal area.
        for (int x = 72; x <= 75; x++) L.tiles[12][x] = Tile.SPEED_BOOST.code; // row 12

        // ---------- NON-SOLID WEBS (slow zones in the air) ----------
        // Place webs ONLY in empty air for a gentle “training” effect.
        // Over flat ground—lets the player feel slowdown safely.
        for (int x = 22; x <= 26; x++) if (L.tiles[17][x] == Tile.EMPTY.code) L.tiles[17][x] = Tile.WEB.code; // row 17
        // Between the two mid platforms—encourages a slightly higher jump.
        for (int x = 43; x <= 47; x++) if (L.tiles[14][x] == Tile.EMPTY.code) L.tiles[14][x] = Tile.WEB.code; // row 14
        // Before the goal—shows that webs don’t block, just slow.
        for (int x = 72; x <= 76; x++) if (L.tiles[11][x] == Tile.EMPTY.code) L.tiles[11][x] = Tile.WEB.code; // row 11

        // ---------- GOAL ----------
        L.tiles[11][78] = Tile.GOAL.code; // high but reachable from the final runway

        return L;
    }


    public static Level level2(){
        int w = 80, h = 22;
        Level L = new Level(w, h);

        // Ground with pits (ensure ranges < w)
        for (int x = 0; x < w; x++) {
            boolean pit = (x >= 8  && x <= 12)
                    || (x >= 24 && x <= 29)
                    || (x >= 40 && x <= 41)
                    || (x >= 63 && x <= 70); // removed out-of-bounds 82..84
            if (!pit) {
                L.tiles[h - 2][x] = Tile.SOLID.code;
                L.tiles[h - 1][x] = Tile.SOLID.code;
            }
        }

        // --- platforms (some higher) ---
        for (int x = 15; x < 22; x++) L.tiles[15][x] = Tile.SOLID.code;
        for (int x = 20; x < 28; x++) L.tiles[7][x] = Tile.SOLID.code;
        for (int x = 30; x < 39; x++) L.tiles[9][x]  = Tile.SOLID.code;
        for (int x = 40; x < 53; x++) L.tiles[10][x] = Tile.SOLID.code;
        for (int x = 55; x < 63; x++) L.tiles[12][x] = Tile.SOLID.code;
        for (int x = 63; x < 70; x++) L.tiles[9][x]  = Tile.SOLID.code;
        for (int x = 70; x < 80; x++) L.tiles[12][x] = Tile.SOLID.code;

        // Spikes (from provided layout)
        L.tiles[h - 3][23] = Tile.SPIKE.code;
        L.tiles[h - 3][42] = Tile.SPIKE.code;
        L.tiles[12][21]    = Tile.SPIKE.code;
        L.tiles[14][32] = Tile.SPIKE.code;
        L.tiles[15][33] = Tile.SPIKE.code;
        L.tiles[16][34] = Tile.SPIKE.code;
        L.tiles[16][35] = Tile.SPIKE.code;
        L.tiles[15][36] = Tile.SPIKE.code;
        L.tiles[14][37] = Tile.SPIKE.code;

        // Goal (from provided layout)
        L.tiles[7][78] = Tile.GOAL.code;

        // ---------- Extra hazards & items to make it challenging ----------

        // 1) Fill the bottoms of pits with spikes (forces clean clears)
        // pit: 8..12
        for (int x = 8; x <= 12; x++) L.tiles[h - 2][x] = Tile.SPIKE.code;
        // pit: 24..29
        for (int x = 24; x <= 29; x++) L.tiles[h - 2][x] = Tile.SPIKE.code;
        // pit: 40..41
        for (int x = 40; x <= 41; x++) L.tiles[h - 2][x] = Tile.SPIKE.code;
        // pit: 63..70
        for (int x = 63; x <= 70; x++) L.tiles[h - 2][x] = Tile.SPIKE.code;

        // 2) Speed boost run-ups before big gaps
        // Before 24..29 gap
        for (int x = 20; x <= 23; x++) L.tiles[h - 2][x] = Tile.SPEED_BOOST.code; // >>>>
        // Between platforms 39->40 (tiny gap) and leading into 40..53 platform
        for (int x = 36; x <= 39; x++) L.tiles[9][x] = Tile.SPEED_BOOST.code; // floor of that platform segment
        // Before 63..70 gap (on 55..63 platform tail)
        for (int x = 59; x <= 62; x++) L.tiles[12][x] = Tile.SPEED_BOOST.code;

        // 3) Jump boost ladders to reach higher shelves cleanly
        // To help reach 11-high platform from ground/15-high platform
        for (int x = 18; x <= 20; x++) L.tiles[15][x] = Tile.JUMP_BOOST.code; // on the air just above 15-high ledge edge
        // A small chain on the 40..53 platform to reach the 63..70 at row 9
        for (int x = 46; x <= 48; x++) L.tiles[10][x] = Tile.JUMP_BOOST.code;
        for (int x = 59; x <= 62; x++) L.tiles[20][x] = Tile.JUMP_BOOST.code;

        // 4) Sticky webs in traversal corridors (slow but don't block)
        // Under the 30..39 platform to punish low arcs
        for (int x = 30; x <= 39; x++) L.tiles[10][x] = (L.tiles[10][x] == Tile.SOLID.code ? L.tiles[10][x] : Tile.WEB.code);

        for (int x = 31; x <= 38; x++) L.tiles[11][x] = Tile.WEB.code;
        L.tiles[19][13] = Tile.WEB.code;
        for(int y = 20; y >= 10; y--) L.tiles[y][10] = Tile.WEB.code; //wall of webs

        // Between 40..53 platform and 55..63 platform (create a sticky gap window)
        for (int x = 53; x <= 55; x++) {
            L.tiles[11][x] = Tile.WEB.code; // air at row 11 slows mid-jump
            L.tiles[10][x] = (L.tiles[10][x] == Tile.SOLID.code ? L.tiles[10][x] : Tile.WEB.code);
        }
        // Web veil near the final stretch
        for (int x = 70; x <= 77; x++) L.tiles[11][x] = Tile.WEB.code;

        // 5) Mid-air spike checks (precision)
        // A ceiling spike just after first boost (forces a low, fast trajectory)
        L.tiles[h - 6][26] = Tile.SPIKE.code; // around row 16

        // Ensure we didn't overwrite the GOAL
        L.tiles[7][78] = Tile.GOAL.code;

        return L;
    }

    // ---------- Level 3 ----------
    public static Level level3() {
        int w = 80, h = 50;
        Level L = new Level(w, h);

        for (int x = 0; x < w; x++) {
            boolean pit = (x >= 10 && x <= 16) || (x >= 28 && x <= 35) || (x >= 50 && x <= 58);
            if (!pit) {
                L.tiles[h - 2][x] = Tile.SOLID.code; // row 20
                L.tiles[h - 1][x] = Tile.SOLID.code; // row 21
            }
        }

        // Fill the big pits with deep WEB curtains (multi-row, non-solid)
        for (int y = h-8; y <= h-3; y++) L.tiles[y][13] = Tile.WEB.code;
        for (int x = 28; x <= 35; x++) for (int r = h - 5; r <= h - 1; r++) L.tiles[r][x] = Tile.WEB.code;
        for (int x = 50; x <= 54; x++) for (int r = h - 3; r <= h - 1; r++) L.tiles[r][x] = Tile.WEB.code;

        // --------------- Early/Mid Platforms (flow + staging before gaps) ----------
        for (int x =  6; x <=  9; x++) L.tiles[h - 10][x] = Tile.SOLID.code;  // small shelf before first gap
        for (int x = 17; x <= 22; x++) L.tiles[h - 7][x] = Tile.SOLID.code;  // landing after first gap
        for (int x = 24; x <= 27; x++) L.tiles[h - 8][x] = Tile.SOLID.code;  // staging before second gap
        for (int x = 36; x <= 43; x++) L.tiles[h - 10][x] = Tile.SOLID.code;  // landing after second gap
        for (int x = 46; x <= 49; x++) L.tiles[h - 11][x] = Tile.SOLID.code;  // elevated run-up to third gap

        // ---------------------- Speed Boosts (solid) for big clears -----------------
        for (int x =  5; x <=  9; x++) L.tiles[h - 2][x] = Tile.SPEED_BOOST.code; // approach to 10..16
        for (int x = 22; x <= 27; x++) if (L.tiles[h - 2][x] == Tile.SOLID.code) L.tiles[h - 2][x] = Tile.SPEED_BOOST.code; // to 28..35
        for (int x = 46; x <= 49; x++) L.tiles[11][x] = Tile.SPEED_BOOST.code;   // elevated sprint to 50..58
        for (int x = 36; x <= 39; x++) L.tiles[12][x] = Tile.SPEED_BOOST.code;   // carries momentum mid-level

        // Gentle mid-air web veil to encourage higher jumps between mid platforms
        for (int x = 31; x <= 34; x++) if (L.tiles[13][x] == Tile.EMPTY.code) L.tiles[13][x] = Tile.WEB.code;

// ---------------------- Light spike teeth (optional spice) ------------------
        L.tiles[h - 3][16] = Tile.SPIKE.code; // tooth at edge after first pit
        L.tiles[13][43]    = Tile.SPIKE.code; // ceiling tooth near mid landing
        L.tiles[10][69]    = Tile.SPIKE.code; // between zig-zag steps (jump clean or use boost)

        // Tree AREA
        for (int r = 2; r < h; r++) {
            L.tiles[r][60] = Tile.WOOD.code;
            L.tiles[r][78] = Tile.WOOD.code;
        }
        L.tiles[47][60] = Tile.EMPTY.code;
        L.tiles[46][60] = Tile.EMPTY.code;

        for(int x = 75; x <= 77; x++){
            L.tiles[45][x] = Tile.WOOD.code;
        }

        for(int x = 66; x <= 69; x++){
            L.tiles[41][x] = Tile.WOOD.code;
        }
        L.tiles[41][66] = Tile.JUMP_BOOST.code;
        for(int x = 65; x <= 75; x++){
            boolean air = x > 66 && x < 71;
            if(!air) L.tiles[35][x] = Tile.SPIKE.code;
        }
        L.tiles[33][61] = Tile.WOOD.code;
        L.tiles[33][62] = Tile.SPEED_BOOST.code;
        for(int x = 75; x <= 77; x++){
            L.tiles[34][x] = Tile.JUMP_BOOST.code;
        }
        for (int y = h-17; y >= h-25; y--) L.tiles[y][70] = Tile.WEB.code;

        // ---------------------- Zig-zag Final Climb (solid steps) -------------------
        // Back-and-forth steps rising to the goal. Each step has a JUMP_BOOST.
        for (int x = 74; x <= 77; x++) L.tiles[23][x] = Tile.WOOD.code; // Step A (left)
        for (int x = 61; x <= 64; x++) L.tiles[26][x] = Tile.WOOD.code; // Step A (left)
        for (int x = 61; x <= 64; x++) L.tiles[18][x] = Tile.WOOD.code; // Step B (right, higher)
        for (int x = 71; x <= 74; x++) L.tiles[14][x] = Tile.WOOD.code; // Step C (left, higher)
        for (int x = 75; x <= 78; x++) L.tiles[11][x] = Tile.WOOD.code; // Step D (right, near goal)

        L.tiles[26][62] = Tile.JUMP_BOOST.code;  // Step A boost
        L.tiles[18][62] = Tile.JUMP_BOOST.code;  // Step B boost
        L.tiles[14][72] = Tile.JUMP_BOOST.code;  // Step C boost

        // ---------------------- Goal ----------------------
        L.tiles[10][77] = Tile.GOAL.code; // high goal after the final jump boost

        // Leaf decoration
        for(int y = 0; y <= 6; y++){
            for(int x = 50; x < 80; x++){
                L.tiles[y][x] = Tile.LEAF.code;
            }
        }

        return L;
    }

    // ---------- Level helpers ( for Save/Load + Next Level) ----------

    /** Number of levels available. */
    public static int levelCount() { return 3; }

    /** Current level index (0-based) for saving. */
    public static int getCurrentIndex() { return current; }

    /** Restore which level is active (for loading). */
    public static void setCurrentIndex(int idx) {
        current = Math.max(0, Math.min(levelCount() - 1, idx));
    }

    /** Build a level by index. Used by loader. */
    public static Level byIndex(int idx) {
        if(idx == 0){
            return level1();
        }else if(idx == 1){
            return level2();
        }else{
            return level3();
        }
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


