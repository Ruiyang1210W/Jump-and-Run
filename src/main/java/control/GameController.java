package control;

import javax.swing.Timer;
import model.GameState;
import util.Constants;


public class GameController {
    private final GameState model;
    private final Timer timer;


    public GameController(GameState model){
        this.model = model;
        int delayMs = (int)Math.round(1000.0 * Constants.DT);
        this.timer = new Timer(delayMs, e -> this.model.tick(Constants.DT));
    }


    public void start(){ timer.start(); }
    public void stop(){ timer.stop(); }
}
