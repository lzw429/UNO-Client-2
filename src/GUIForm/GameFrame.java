package GUIForm;

import javax.swing.*;

public class GameFrame {
    private JPanel mainPanel;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel infoPanel;
    private JPanel tablePanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameFrame");
        frame.setContentPane(new GameFrame().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
