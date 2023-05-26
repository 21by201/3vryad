import javax.swing.*;
import java.awt.*;

public class Ball extends JPanel {
    private Color color;
    private int row;
    private int col;
    private boolean selected;
    private boolean clicked;

    public Ball(int row, int col) {
        this.row = row;
        this.col = col;
        this.color = Color.RED;
        this.selected = false;
        this.clicked = false;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (clicked) {
            g2d.setColor(Color.GRAY);
            g2d.fillOval(0, 0, getWidth(), getHeight());
        } else {
            g2d.setColor(color);
            g2d.fillOval(0, 0, getWidth(), getHeight());
        }

        if (selected) {
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.BLACK);
            g2d.drawOval(0, 0, getWidth(), getHeight());
        }

        g2d.dispose();
    }
}
