package GUIForm;

import Service.GameService;
import Service.GameServiceImpl;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HallFrame {
    private JButton enterRoomButton; // 进入房间
    private JButton goBackButton; // 返回到 选择模式
    private JPanel panel;
    private JTable gameTableList;
    private JScrollPane scrollPane;
    private static Object[][] data;
    private static GameService gameService;
    private static String[] columnNames = {"玩家 1", "玩家 2", "状态"};


    public HallFrame() {
        enterRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                // 进入房间 按钮 被按下

            }
        });
    }

    public static void main(String[] args) {
        gameService = new GameServiceImpl();
        data = gameService.getGameTablesData();
        JFrame frame = new JFrame("游戏大厅");
        frame.setContentPane(new HallFrame().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 218);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // 在此处放置自定义组件创建代码
        // 展示游戏大厅数据
        gameTableList = new JTable(data, columnNames);
    }
}
