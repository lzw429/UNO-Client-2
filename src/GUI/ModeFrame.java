package GUI;

import Service.UserService;
import Service.UserServiceImpl;
import Util.GameConstants;
import Util.OnlineUtil;

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
                // 要求键入用户名
                new OnlineUtil().start(); // 连接服务器
                String username = JOptionPane.showInputDialog("用户登录");
                username = GameConstants.removeIllegalChar(username);
                if (username == null || username.equals("")) {
                    System.out.println("ModeFrame: illegal username");
                    return;
                }
                System.out.println("ModeFrame: username " + username);
                // 使用用户名登录
                UserService userService = new UserServiceImpl();
                if (userService.login(username)) {// 登录成功
                    System.out.println("ModeFrame: login succeed");
                    OnlineUtil.setUsername(username); // 设定当前用户的用户名
                    try {
                        HallFrame.main(new String[10]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else { // 登录失败
                    System.out.println("ModeFrame: login failed");
                    JOptionPane.showMessageDialog(null, "登录失败");
                }
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
