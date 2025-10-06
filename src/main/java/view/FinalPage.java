package view;
import util.Constants;
import javax.swing.*;
import java.awt.*;
import model.GameState;


/** shown when all lives are used. */
public class FinalPage extends JDialog {
    public FinalPage(Window owner, GameState model) {
        super(owner, "Game Over", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JLabel title = new JLabel("Game Over!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel score = new JLabel("Total Score: " + model.score, SwingConstants.CENTER);
        score.setFont(new Font("SansSerif", Font.PLAIN, 18));

        int displayLives = Math.max(0, model.lives);
        String livesText = (displayLives == 0) ? "No lives left" : ("Lives: " + displayLives);
        JLabel lives = new JLabel(livesText, SwingConstants.CENTER);
        lives.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JPanel center = new JPanel(new GridLayout(0,1,8,8));
        center.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        center.add(title);
        center.add(score);

        JButton playAgain = new JButton("Play Again");
        playAgain.addActionListener(e -> {
            model.lives = Constants.STARTING_LIVES;            // reset lives
            model.score = 0;            // clear score for a new run
            model.running = true;       // allow ticking again
            model.reset();         // reposition player, reset per-life timer
            dispose();
        });

        JButton close = new JButton("Close");
        close.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel();
        buttons.add(playAgain);
        buttons.add(close);

        add(center, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }
}
