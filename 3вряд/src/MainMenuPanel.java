import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private JFrame frame;

    public MainMenuPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGamePanel();
            }
        });

        add(startButton, BorderLayout.CENTER);
    }

    private void showGamePanel() {
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(frame);
        frame.getContentPane().add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.startGame();
    }
}
