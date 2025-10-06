package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.GameState;


public class InputHandler extends KeyAdapter {
    private final GameState model;
    private boolean left, right, jump;


    public InputHandler(GameState model){ this.model = model; }


    private void updateMove(){
        double dir = 0; if (left) dir -= 1; if (right) dir += 1; model.setMoveInput(dir);
    }

    /** Clear any stuck keys when  reset / refocus. */
    public void clear() {
        left = right = jump = false;
        model.setMoveInput(0);
        model.onJumpReleased();
    }


    @Override public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> { left = true; updateMove(); }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> { right = true; updateMove(); }
            case KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_SPACE -> {
                if (!jump){ model.onJumpHeld(); model.onJumpPressed(); jump = true; }
            }
            case KeyEvent.VK_R -> model.reset();
            case KeyEvent.VK_S -> {
                try { io.GameSaver.save(model, "save.json"); }
                catch (Exception ex) { ex.printStackTrace(); }
            }
            case KeyEvent.VK_L -> {
                try { io.GameLoader.loadInto(model, "save.json"); }
                catch (Exception ex) { ex.printStackTrace(); }
            }
        }
    }


    @Override public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> { left = false; updateMove(); }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> { right = false; updateMove(); }
            case KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_SPACE -> { jump = false; model.onJumpReleased(); }
        }
    }
}
