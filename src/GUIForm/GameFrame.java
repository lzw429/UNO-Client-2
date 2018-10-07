package GUIForm;

import javax.swing.*;

public class GameFrame {
    private JPanel mainPanel;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel infoPanel;
    private JPanel tablePanel;
    private JLabel remainingCards; // 剩余牌数
    private JLabel countLabel;
    private JLabel turnLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameFrame");
        frame.setContentPane(new GameFrame().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 720);
        frame.setVisible(true);
    }
}
