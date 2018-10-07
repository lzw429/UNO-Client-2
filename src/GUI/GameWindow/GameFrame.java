package GUI.GameWindow;

import javax.swing.*;

public class GameFrame {
    public static void main(String[] args) {
        0
        JFrame frame = new JFrame("GameFrame");
        frame.setContentPane(new GameFrame().gamePanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(960, 720);
        frame.setVisible(true);
    }
}
