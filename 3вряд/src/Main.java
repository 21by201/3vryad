import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowMainMenu();
            }
        });
    }

    private static void createAndShowMainMenu() {
        JFrame frame = new JFrame("Главное меню");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(frame);
        frame.add(mainMenuPanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
