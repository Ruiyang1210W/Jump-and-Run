# Java 2D Platformer (Swing MVC)

## Team Members
- Ruiyang Wang (016087383)
- Colin Shiung (017426461)

## How to Run
1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/platformer-game.git
2. Open the project in IntelliJ IDEA (or Eclipse).
3. Ensure the Project SDK is Java 17 or later.
4. Run the main file: src/main/java/Main.java
5. Mark src/main/resources as a resources root if it won't show assets. 


## Features Implemented

- MVC Architecture: Clean separation of model (logic), view (rendering), and controller (input).

- Physics System: Gravity, horizontal acceleration/deceleration, jumping, and collision detection.

- Levels: Two demo levels with solid platforms, pits, hazards, and goals.

- Game HUD: Displays score, lives, timer, and on-screen instructions.

- Save/Load System: Press S to save and L to load from a save.json file.

- Ending Panel: Appears when you reach the goal score; allows Next Level or Restart.

- Scoring System: Reach 5000 points to pass a level; faster times give higher bonuses.

- Restart Function: Press R to restart current level or resume after death.

- Keyboard Input: Smooth, responsive movement with coyote time and jump buffering.

- Level Switching: “Next Level” button on win screen loads the next map.

- JSON Save/Load: Persistent player stats and progress (Gson).

- Multiple Levels: DemoLevels class handles nextLevel and byIndex for loading different maps.

- Instruction HUD: Displays live control guide and score goals.

- UI: Centered restart message, right-aligned help box, and background visuals (May add soon).

## Controls

- A / D or ← / → – Move left / right

- W / ↑ / Space – Jump

- R – Restart current level

- S – Save progress

- L – Load saved progress

## Known Issues
Save may be not really clear.

## External Libraries
- Gson 2.10.1 – JSON serialization for Save/Load
- JUnit 4.13.2 – Unit testing (extra credit)
  
