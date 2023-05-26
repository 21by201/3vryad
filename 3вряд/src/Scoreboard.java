import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JPanel {
    private int score;
    private int timeLeft;
    private JLabel scoreLabel;
    private JLabel timeLabel;

    public Scoreboard() {
        score = 0;
        timeLeft = 60;

        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(scoreLabel);

        timeLabel = new JLabel("Time: " + timeLeft);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(timeLabel);
    }

    public void updateScore(int newScore) {
        score = newScore;
        scoreLabel.setText("Score: " + score);
    }

    public void updateTime(int newTime) {
        timeLeft = newTime;
        timeLabel.setText("Time: " + timeLeft);
    }
}