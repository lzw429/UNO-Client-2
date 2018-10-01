package GUIForm;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModeFrame {
    private JButton onePlayerButton;
    private JButton onlineGameButton;
    private JButton twoPlayersButton;
    private JPanel panel;
    private JLabel label1;

    public ModeFrame() {
        onlineGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                // 在线游戏 按钮 被按下

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("选择模式");
        frame.setContentPane(new ModeFrame().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(180, 160); // 窗口大小
        frame.setVisible(true);
    }
}
