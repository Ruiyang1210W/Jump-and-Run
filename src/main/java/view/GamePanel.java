package view;

import javax.swing.*;
import java.awt.*;

import model.*;
import util.Constants;

import java.awt.image.BufferedImage;
import util.ImageLoader;


public class GamePanel extends JPanel implements java.beans.PropertyChangeListener {
    private final GameState model;
    private final HUD hud;
    private boolean finalShown = false;
    private final BufferedImage playerSheet =
            ImageLoader.load("/assets/sprites/player_sheet.png");

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
                //determine color of tiles and shape/size
                switch (t){
                    case SOLID -> { g2.setColor(new Color(34, 139, 34)); g2.fillRect(x,y,Constants.TILE_SIZE,Constants.TILE_SIZE/2); g2.setColor(new Color(107,84,40)); g2.fillRect(x,y+Constants.TILE_SIZE/2,Constants.TILE_SIZE,Constants.TILE_SIZE/2); }
                    case SPIKE -> { g2.setColor(Color.RED); g2.fillPolygon(new int[]{x,x+Constants.TILE_SIZE/2,x+Constants.TILE_SIZE}, new int[]{y+Constants.TILE_SIZE,y,y+Constants.TILE_SIZE}, 3); }
                    case GOAL -> { g2.setColor(Color.YELLOW); g2.fillRect(x+8, y+8, Constants.TILE_SIZE-16, Constants.TILE_SIZE-16); }
                    case SPEED_BOOST -> {g2.setColor(Color.BLUE); g2.fillRect(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);}
                    case JUMP_BOOST -> { g2.setColor(Color.GREEN); g2.fillRect(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);}
                    case WEB -> { g2.setColor(Color.WHITE); g2.fillRect(x, y, Constants.TILE_SIZE+4, Constants.TILE_SIZE); }
                    case WOOD -> { g2.setColor(new Color(150, 111, 51)); g2.fillRect(x,y,Constants.TILE_SIZE,Constants.TILE_SIZE);}
                    case LEAF -> { g2.setColor(new Color(109,179,63)); g2.fillRect(x,y,Constants.TILE_SIZE,Constants.TILE_SIZE);}
                    default -> {}
                }
                g2.drawString( ty + " " + tx, x ,y);
            }
        }
    }


    private void drawPlayer(Graphics2D g2, Player p, int ox, int oy){
        int sx = (int) Math.round(p.pos.x - ox);
        int sy = (int) Math.round(p.pos.y - oy);

        double scale = 1.5;
        int dw = (int) Math.round(p.frameW * scale);
        int dh = (int) Math.round(p.frameH * scale);

        if (playerSheet != null) {
            // Pick current frame from player's animation state
            int frame = p.currentFrame();     // added in Player.java
            int fw = p.frameW;                // 32
            int fh = p.frameH;                // 32
            int cols = p.cols;                // 2

            int srcX1 = (frame % cols) * fw;
            int srcY1 = (frame / cols) * fh;
            int srcX2 = srcX1 + fw;
            int srcY2 = srcY1 + fh;

            // face direction by mirroring when moving left
            boolean facingLeft = p.vel.x < -1;
            int dx1 = facingLeft ? sx + dw : sx;
            int dx2 = facingLeft ? sx : sx + dw;

            g2.drawImage(playerSheet,
                    dx1, sy, dx2, sy + dh,   // destination rectangle
                    srcX1, srcY1, srcX2, srcY2, // source frame rectangle
                    null);

            // soft shadow (optional)
            g2.setColor(new Color(0,0,0,50));
            g2.fillOval(sx - 4, sy + dh - 4, dw + 8, 8);
        } else {
            // Just in case: old rectangle if image missing
            g2.setColor(new Color(40, 80, 200));
            g2.fillRoundRect(sx, sy, dw, dh, 6, 6);
            g2.setColor(new Color(0,0,0,50));
            g2.fillOval(sx - 4, sy + dh - 4, dw + 8, 8);
        }
    }

}
