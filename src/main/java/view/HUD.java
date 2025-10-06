package view;

import java.awt.*;
import model.GameState;


public class HUD {
    private final GameState model;
    public HUD(GameState model){ this.model = model; }



    public void draw(Graphics2D g2){
        g2.setColor(new Color(0,0,0,170));
        g2.fillRoundRect(8,8, 220, 64, 12,12);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.drawString(String.format("Time: %.2f", model.timer), 16, 30);
        g2.drawString("Score: " + model.score, 16, 48);
        int displayLives = Math.max(0, model.lives);
        g2.drawString("Lives: " + displayLives, 120, 48);
        drawInstructions(g2);

        if (!model.running){
            g2.setFont(new Font("SansSerif", Font.BOLD, 36));
            g2.setColor(Color.RED);                             // red text

            // Message
            String msg = "Press R to Restart";

            // Get drawing area size from Graphics2D clip bounds
            Rectangle bounds = g2.getClipBounds();
            int width = bounds.width;
            int height = bounds.height;

            // Center text
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(msg);
            int textHeight = fm.getAscent();
            int x = (width - textWidth) / 2;
            int y = (height + textHeight) / 2; // center vertically

            // Draw shadow and text
            g2.setColor(Color.BLACK);
            g2.drawString(msg, x + 3, y + 3);
            g2.setColor(Color.RED);
            g2.drawString(msg, x, y);

        }
    }


    private void drawInstructions(Graphics2D g2) {
        // Smooth text
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Your instruction lines (edit text anytime)
        String[] lines = {
                "Reach 5000 to pass the level",
                "Finish faster = higher bonus",
                "Yellow tile gives score",
                "S: Save   L: Load   R: Restart"
        };

        // Sizing/positioning
        java.awt.Rectangle bounds = g2.getClipBounds();
        int margin = 12;
        int pad = 10;
        int lineGap = 4;

        java.awt.Font font = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14);
        g2.setFont(font);
        java.awt.FontMetrics fm = g2.getFontMetrics();

        // Find the widest line for right alignment and background box
        int maxW = 0;
        for (String line : lines) {
            maxW = Math.max(maxW, fm.stringWidth(line));
        }

        int textHeight = lines.length * fm.getAscent() + (lines.length - 1) * lineGap;
        int boxW = maxW + pad * 2;
        int boxH = textHeight + pad * 2;

        int boxX = bounds.width - margin - boxW; // top-right corner
        int boxY = margin;

        // Background (translucent)
        g2.setColor(new java.awt.Color(0, 0, 0, 160));
        g2.fillRoundRect(boxX, boxY, boxW, boxH, 12, 12);
        // Border
        g2.setColor(new java.awt.Color(255, 255, 255, 180));
        g2.drawRoundRect(boxX, boxY, boxW, boxH, 12, 12);

        // Text (right-aligned inside the box)
        int xRight = boxX + boxW - pad;
        int y = boxY + pad + fm.getAscent();
        g2.setColor(java.awt.Color.WHITE);

        for (String line : lines) {
            int w = fm.stringWidth(line);
            g2.drawString(line, xRight - w, y);
            y += fm.getAscent() + lineGap;
        }
    }

}
