import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard extends JPanel {
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private final int ROWS = 10;
    private final int COLS = 10;
    private final int BALL_SIZE = 60;

    private Ball[][] balls;
    private int score;
    private int timeLeft;
    private Timer timer;
    private Ball selectedBall;
    private List<Color> initialColors;

    public GameBoard() {
        setLayout(null);

        balls = new Ball[ROWS][COLS];
        score = 0;
        timeLeft = 60;
        selectedBall = null;
        initialColors = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW); // Set the initial colors

        generateInitialBalls();

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(10, 10, 100, 30);
        add(scoreLabel);

        timeLabel = new JLabel("Time: " + formatTime(timeLeft));
        timeLabel.setBounds(10, 50, 100, 30);
        add(timeLabel);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    updateTime();
                } else {
                    timer.stop();
                    gameOver();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int row = mouseY / BALL_SIZE;
                int col = mouseX / BALL_SIZE;

                if (selectedBall == null) {
                    if (balls[row][col] != null) {
                        selectedBall = balls[row][col];
                        selectedBall.setSelected(true);
                    }
                } else {
                    if (balls[row][col] != null && isAdjacent(selectedBall, balls[row][col])) {
                        swapBalls(selectedBall, balls[row][col]);

                        Color tempColor = balls[row][col].getColor();
                        balls[row][col].setColor(selectedBall.getColor());
                        selectedBall.setColor(tempColor);
                    }
                    selectedBall.setSelected(false);
                    selectedBall.setClicked(true);
                    selectedBall = null;

                    checkAndReplaceThreeInARow();
                }

                repaint();
            }
        });
    }

    private Ball generateRandomBall(int row, int col) {
        Random random = new Random();
        int colorIndex = random.nextInt(initialColors.size());
        Color randomColor = initialColors.get(colorIndex);


        Ball newBall = new Ball(row, col);
        newBall.setColor(randomColor);

        return newBall;
    }

    private Color getRandomColor() {

        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }

    private void generateInitialBalls() {
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

        Color[][] randomColors = new Color[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                boolean isValidColor = false;
                Color randomColor = null;

                while (!isValidColor) {
                    randomColor = colors[(int) (Math.random() * colors.length)];

                    if (j >= 2 && randomColors[i][j - 1] == randomColor && randomColors[i][j - 2] == randomColor) {
                        continue;
                    }

                    if (i >= 2 && randomColors[i - 1][j] == randomColor && randomColors[i - 2][j] == randomColor) {
                        continue;
                    }

                    isValidColor = true;
                }

                randomColors[i][j] = randomColor;
            }
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                balls[i][j] = new Ball(i, j);
                balls[i][j].setColor(randomColors[i][j]);
            }
        }
    }

    private void swapBalls(Ball ball1, Ball ball2) {
        int row1 = ball1.getRow();
        int col1 = ball1.getCol();
        int row2 = ball2.getRow();
        int col2 = ball2.getCol();

        balls[row1][col1] = ball2;
        balls[row2][col2] = ball1;

        ball1.setRow(row2);
        ball1.setCol(col2);
        ball2.setRow(row1);
        ball2.setCol(col1);
    }

    private boolean isAdjacent(Ball ball1, Ball ball2) {
        int row1 = ball1.getRow();
        int col1 = ball1.getCol();
        int row2 = ball2.getRow();
        int col2 = ball2.getCol();

        return (Math.abs(row1 - row2) == 1 && col1 == col2) || (Math.abs(col1 - col2) == 1 && row1 == row2);
    }

    private void checkAndReplaceThreeInARow() {
        List<Ball> ballsToRemove = new ArrayList<>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS - 2; j++) {
                Ball ball1 = balls[i][j];
                Ball ball2 = balls[i][j + 1];
                Ball ball3 = balls[i][j + 2];

                if (ball1.getColor().equals(ball2.getColor()) && ball2.getColor().equals(ball3.getColor())) {
                    ballsToRemove.add(ball1);
                    ballsToRemove.add(ball2);
                    ballsToRemove.add(ball3);
                }
            }
        }

        for (int i = 0; i < ROWS - 2; i++) {
            for (int j = 0; j < COLS; j++) {
                Ball ball1 = balls[i][j];
                Ball ball2 = balls[i + 1][j];
                Ball ball3 = balls[i + 2][j];

                if (ball1.getColor().equals(ball2.getColor()) && ball2.getColor().equals(ball3.getColor())) {
                    ballsToRemove.add(ball1);
                    ballsToRemove.add(ball2);
                    ballsToRemove.add(ball3);
                }
            }
        }

        for (Ball ball : ballsToRemove) {
            int row = ball.getRow();
            int col = ball.getCol();

            balls[row][col] = null;
        }

        replaceBalls();

        int ballsRemoved = ballsToRemove.size();
        score += ballsRemoved;
        updateScore(score);

        repaint();
    }

    private void replaceBalls() {
        for (int j = 0; j < COLS; j++) {
            int emptySpaces = 0;

            for (int i = ROWS - 1; i >= 0; i--) {
                if (balls[i][j] == null) {
                    emptySpaces++;
                } else if (emptySpaces > 0) {
                    balls[i + emptySpaces][j] = balls[i][j];
                    balls[i][j].setRow(i + emptySpaces);
                    balls[i][j] = null;
                }
            }

            for (int i = 0; i < emptySpaces; i++) {
                balls[i][j] = generateRandomBall(i, j);
            }
        }
    }

    private String formatTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    private void updateTime() {
        String timeString = formatTime(timeLeft);
        timeLabel.setText("Time: " + timeString);
    }

    private void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
    private void gameOver() {

        timer.stop();


        JOptionPane.showMessageDialog(this, "Game Over! Your Score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);


        reset();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (balls[i][j] != null) {
                    Ball ball = balls[i][j];

                    int x = ball.getCol() * BALL_SIZE;
                    int y = ball.getRow() * BALL_SIZE;

                    g.setColor(ball.getColor());
                    g.fillOval(x, y, BALL_SIZE, BALL_SIZE);

                    if (ball.isSelected()) {
                        g.setColor(Color.BLACK);
                        g.drawOval(x, y, BALL_SIZE, BALL_SIZE);
                    }

                    if (ball.isClicked()) {
                        g.setColor(Color.WHITE);
                        g.drawLine(x + BALL_SIZE / 2, y + BALL_SIZE / 2 - 5, x + BALL_SIZE / 2, y + BALL_SIZE / 2 + 5);
                        g.drawLine(x + BALL_SIZE / 2 - 5, y + BALL_SIZE / 2, x + BALL_SIZE / 2 + 5, y + BALL_SIZE / 2);
                    }
                }
            }
        }
    }

    public void startGame() {
        score = 0;
        timeLeft = 60;
        timer.start();
        generateInitialBalls();
        repaint();
    }

    public void reset() {
        timer.stop();
        removeAll();
        selectedBall = null;
        generateInitialBalls();
        score = 0;
        timeLeft = 60;
        repaint();
    }
}
