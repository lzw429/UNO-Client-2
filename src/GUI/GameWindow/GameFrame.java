package GUI.GameWindow;

import javax.swing.*;

/**
 * 游戏窗口
 */
public class GameFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("UNO");
        frame.setContentPane(new GamePanel());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(960, 720);
        frame.setVisible(true);
    }
}