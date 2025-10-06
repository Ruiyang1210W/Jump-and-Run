package view;

import javax.swing.*;
import java.awt.*;


import model.*;
import util.Constants;


public class GamePanel extends JPanel implements java.beans.PropertyChangeListener {
    private final GameState model;
    private final HUD hud;
    private boolean finalShown = false;

    public GamePanel(GameState model){
        this.model = model; this.hud = new HUD(model);
        setPreferredSize(new Dimension(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT));
        setBackground(new Color(120, 190, 255));
        setDoubleBuffered(true);
        model.addListener(this);
    }


    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        // If the game stopped AND lives == 0, show the FinalPage once
        if (!model.running && model.lives == 0 && !finalShown) {
            finalShown = true;
            java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
            new FinalPage(owner, model).setVisible(true);
        }

        // If a reset happened and game is running again, allow future show
        if (model.running && finalShown) {
            finalShown = false;
        }

        repaint();
    }



    @Override protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


// camera offset
        int ox = (int)Math.floor(model.camX);
        int oy = (int)Math.floor(model.camY);


        drawLevel(g2, model.level, ox, oy);
        drawPlayer(g2, model.player, ox, oy);
        hud.draw(g2);
    }


    private void drawLevel(Graphics2D g2, Level level, int ox, int oy){
        for (int ty=0; ty<level.height; ty++){
            for (int tx=0; tx<level.width; tx++){
                Tile t = level.getTileAt(tx, ty);
                int x = tx*Constants.TILE_SIZE - ox;
                int y = ty*Constants.TILE_SIZE - oy;
                switch (t){
                    case SOLID -> { g2.setColor(new Color(60,60,60)); g2.fillRect(x,y,Constants.TILE_SIZE,Constants.TILE_SIZE); }
                    case SPIKE -> { g2.setColor(Color.RED); g2.fillPolygon(new int[]{x,x+Constants.TILE_SIZE/2,x+Constants.TILE_SIZE}, new int[]{y+Constants.TILE_SIZE,y,y+Constants.TILE_SIZE}, 3); }
                    case GOAL -> { g2.setColor(Color.YELLOW); g2.fillRect(x+8, y+8, Constants.TILE_SIZE-16, Constants.TILE_SIZE-16); }
                    default -> {}
                }
            }
        }
    }


    private void drawPlayer(Graphics2D g2, Player p, int ox, int oy){
        int x = (int)Math.round(p.pos.x - ox);
        int y = (int)Math.round(p.pos.y - oy);
        g2.setColor(new Color(40, 80, 200));
        g2.fillRoundRect(x, y, (int)p.w, (int)p.h, 6, 6);
        // simple shadow
        g2.setColor(new Color(0,0,0,50));
        g2.fillOval(x-4, y+(int)p.h-4, (int)p.w+8, 8);
    }
}
