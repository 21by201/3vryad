import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    private JFrame frame;
    private GameBoard gameBoard;

    public GamePanel(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();

        gameBoard = new GameBoard();
        add(gameBoard, BorderLayout.CENTER);


        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showMainMenuPanel() {
        frame.getContentPane().removeAll();
        MainMenuPanel mainMenuPanel = new MainMenuPanel(frame);
        frame.getContentPane().add(mainMenuPanel);
        frame.revalidate();
        frame.repaint();
    }



    private void resetGame() {
        gameBoard.reset();
        gameBoard.startGame();
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        GamePanel gamePanel = new GamePanel(frame);
        frame.add(gamePanel);

        frame.setVisible(true);
        gamePanel.startGame();
    }

    public void startGame() {
        gameBoard.startGame();
    }
}
