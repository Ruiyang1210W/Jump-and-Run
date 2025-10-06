package view;

import javax.swing.*;
import java.awt.*;
import model.GameState;

public class EndingPanel extends JPanel {
    private final GameState model;
    private final Runnable onNextLevel;
    private final Runnable onRestart;
    private final Runnable onQuit;

    public EndingPanel(GameState model, Runnable onNextLevel, Runnable onRestart, Runnable onQuit) {
        this.model = model;
        this.onNextLevel = onNextLevel;   // store the callbacks
        this.onRestart = onRestart;
        this.onQuit = onQuit;

        setOpaque(true);
        setPreferredSize(new Dimension(util.Constants.VIEW_WIDTH, util.Constants.VIEW_HEIGHT));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("YOU WIN!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 36));

        JLabel score = new JLabel("Final Score: " + model.score, SwingConstants.CENTER);
        score.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel target = new JLabel("Target: " + util.Constants.GOAL_SCORE, SwingConstants.CENTER);
        target.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JPanel center = new JPanel(new GridLayout(0,1,8,8));
        center.setBorder(BorderFactory.createEmptyBorder(24,24,24,24));
        center.add(title);
        center.add(score);
        center.add(target);

        JButton next = new JButton("Next Level");
        next.addActionListener(e -> safeRun(this.onNextLevel, "onNextLevel"));

        JButton restart = new JButton("Restart");
        restart.addActionListener(e -> safeRun(this.onRestart, "onRestart"));

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> safeRun(this.onQuit, "onQuit"));

        JPanel buttons = new JPanel();
        buttons.add(next);
        buttons.add(restart);
        buttons.add(quit);

        add(center, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void safeRun(Runnable r, String name) {
        try {
            if (r == null) {
                System.err.println("EndingPanel: callback is null: " + name);
                return;
            }
            r.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
