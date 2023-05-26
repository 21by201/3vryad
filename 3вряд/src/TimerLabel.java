import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerLabel extends JLabel {
    private int time;

    public TimerLabel(int time) {
        this.time = time;
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 24));
        setText(Integer.toString(time));
    }

    public void setTime(int time) {
        this.time = time;
        setText(Integer.toString(time));
    }
}
