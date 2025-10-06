package view;

import javax.swing.*;
import java.awt.*;

import control.GameController;
import control.InputHandler;
import model.GameState;
import model.Level;

public class MainFrame extends JFrame implements java.beans.PropertyChangeListener {
    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);

    private final GamePanel gamePanel;
    private EndingPanel endingPanel;

    private final GameState model;
    private final InputHandler input;
    private final GameController controller;

    public MainFrame() {
        setTitle("Platformer MVC Starter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Model
        Level level = DemoLevels.basicDemo();
        model = new GameState(level);
        model.addListener(this);

        // Views
        gamePanel = new GamePanel(model);
        gamePanel.setFocusable(true);
        gamePanel.setFocusTraversalKeysEnabled(false);
        root.add(gamePanel, "game");

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Controller: attach KeyListener to the game panel
        input = new InputHandler(model);
        gamePanel.addKeyListener(input);
        gamePanel.requestFocusInWindow();

        // Game loop
        controller = new GameController(model);
        controller.start();

        layout.show(root, "game");
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        String p = evt.getPropertyName();

        // Show ending panel when score goal reached
        if ("gamewin".equals(p)) {
            // Build a fresh panel each time (remove previous to avoid duplicates)
            if (endingPanel != null) {
                root.remove(endingPanel);
                endingPanel = null;
            }

            endingPanel = new EndingPanel(
                    model,
                    // NEXT LEVEL
                    () -> {
                        model.score = 0;                         // avoid instant re-win
                        Level next = DemoLevels.nextLevel();      // must return a valid Level
                        model.setLevel(next);                     // swaps level + reset()
                        layout.show(root, "game");
                        root.remove(endingPanel);
                        endingPanel = null;
                        root.revalidate();
                        root.repaint();
                        // Ensure focus returns to the game panel AFTER card switch
                        SwingUtilities.invokeLater(() -> {
                            input.clear();
                            gamePanel.requestFocusInWindow();
                        });
                    },
                    // RESTART SAME LEVEL
                    () -> {
                        model.score = 0;                          // avoid instant re-win
                        model.reset();
                        layout.show(root, "game");
                        root.remove(endingPanel);
                        endingPanel = null;
                        root.revalidate();
                        root.repaint();
                        SwingUtilities.invokeLater(() -> {
                            input.clear();
                            gamePanel.requestFocusInWindow();
                        });
                    },
                    // QUIT
                    () -> System.exit(0)
            );

            root.add(endingPanel, "ending");
            layout.show(root, "ending");
            root.revalidate();
            root.repaint();
        }

        // When the model resets normally, restore focus
        if ("reset".equals(p)) {
            SwingUtilities.invokeLater(() -> {
                input.clear();
                gamePanel.requestFocusInWindow();
            });
        }

        // Keep game panel painting smoothly
        gamePanel.repaint();
    }
}
