package GUI.GameWindow;

import javax.swing.*;

public class GameFrame {
    private JPanel gamePanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameFrame");
        frame.setContentPane(new GameFrame().gamePanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(960, 720);
        frame.setVisible(true);
    }
}
