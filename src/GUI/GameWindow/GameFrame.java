package GUI.GameWindow;

import Util.TimeUtil;

import javax.swing.*;

/**
 * 游戏窗口
 */
public class GameFrame {
    private static GamePanel gamePanel;

    public static GamePanel getGamePanel() {
        return gamePanel;
    }

    public static void setGamePanel(GamePanel gamePanel) {
        GameFrame.gamePanel = gamePanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UNO");
        if (gamePanel == null) {
            System.out.println("[" + TimeUtil.getTimeInMillis() + "] GameFrame: gamepanel hasn't been initialized");
            return;
        }
        frame.setContentPane(gamePanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(960, 720);
        frame.setVisible(true);
    }
}