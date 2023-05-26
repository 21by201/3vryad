import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ResetKeyListener extends KeyAdapter {
    private JFrame frame;

    public ResetKeyListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
            frame.getContentPane().removeAll();
            frame.add(new MainMenuPanel(frame));
            frame.revalidate();
            frame.repaint();
        }
    }
}