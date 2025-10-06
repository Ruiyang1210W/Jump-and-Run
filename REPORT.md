# Project Report

## Design Decisions

### Architecture
This project uses a **Model–View–Controller (MVC)** structure for clarity, modularity, and testability.

- **Model:**  
  Contains all game logic and state (`GameState`, `Player`, `Level`, `Tile`, `Constants`).  
  It has no references to any UI classes. It could run in a console.
- **View:**  
  Responsible for rendering game state using Swing (`GamePanel`, `HUD`, `EndingPanel`, `MainFrame`).  
  Listens to model updates via the Observer pattern.
- **Controller:**  
  Handles keyboard input and game loop timing (`InputHandler`, `GameController`).  
  Translates user actions into model method calls.

**Why Swing:**  
We chose **Swing** instead of JavaFX for because it’s lighter, requires no module configuration, and integrates easily with standard Java projects. Also Swing’s `Graphics2D` API was sufficient for drawing simple 2D graphics at ~60 FPS.

---

### Data Structures
- **Game state:** Stored as Java objects (`GameState`, `Player`, `Level`) with numeric fields for position, velocity, and score.
- **Levels:** Represented as a 2D integer array (`int[][] tiles`) where each number maps to a `Tile` enum (`EMPTY`, `SOLID`, `SPIKE`, `GOAL`).
- **Persistence:** Save/Load system uses `Gson` to serialize a simple `SaveData` object to JSON (`save.json`).
- **Observer pattern:** Implemented through `ObservableModel` to notify the View whenever the Model changes.

**Why this design:**  
2D arrays are efficient and simple for collision checks (O(1) tile lookup).  
Objects for entities like Player make it easy to expand (add enemies, power-ups, etc.).

---

### Algorithms
1. **Collision Detection:**  
   Implemented as **AABB (Axis-Aligned Bounding Box)** between player and tiles.  
   Uses tile rasterization to check nearby solid tiles → O(k) per frame where k = few tiles (constant in practice).
2. **Physics Simulation:**  
   Gravity and horizontal motion computed each frame (`vel += accel * dt`), with clamped max velocity.  
   Frame-independent using delta time (dt = 1/60s).
3. **Coyote Time / Jump Buffer:**  
   Small timers that allow jump input slightly before/after leaving a platform.
4. **Save/Load:**  
   Serialization via Gson → O(1) for current state.
5. **Scoring Bonus:**  
   Score += (1000 + max(0, 5000 – timer * 100)) for reaching goal.

---

## Challenges Faced

1. **Player continuing to move after restart**
    - **Problem:** Input flags were not cleared after reset.
    - **Solution:** Added `setMoveInput(0)` and `player.setJumpHeld(false)` in `GameState.reset()` and `InputHandler.clear()`.

2. **Lives decreasing twice on death**
    - **Problem:** Lives were decremented both on death and restart.
    - **Solution:** Moved life decrement into a single `die()` helper method called from `tick()` only.

3. **Focus loss after switching panels**
    - **Problem:** Player couldn’t move after “Next Level” or “Restart.”
    - **Solution:** Moved `KeyListener` to `gamePanel` and called `requestFocusInWindow()` after card switch.

---

## What We Learned
- Reinforced **Object-Oriented Programming** principles and class design.
- Applied the **MVC** pattern and the **Observer pattern** for responsive updates.
- Learned to serialize and deserialize game state using **Gson**.
- Practiced incremental testing using **JUnit** for physics and collisions.

---

## If We Had More Time
- Add **animated sprites** for player, hazards, and goals.
- Include **background music** and sound effects for jumps, deaths, and goals.
- Implement a **checkpoint system** and moving platforms.
- Build a simple **level editor** that exports/imports levels as JSON.
- Refactor drawing code to support dynamic scaling and fullscreen.

---

### 💡 Summary
This project demonstrates a full MVC-based 2D platformer with real physics, level progression, and persistence.  
It shows practical application of OOP, Swing graphics, and JSON data handling in a well-structured Java project.
