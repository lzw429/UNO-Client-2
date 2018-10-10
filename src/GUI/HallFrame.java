package GUI;

import Service.GameService;
import Service.GameServiceImpl;
import Util.OnlineUtil;
import Util.TimeUtil;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

public class HallFrame {
    private JButton enterRoomButton; // 进入房间
    private JButton goBackButton; // 返回到 选择模式
    private JPanel panel;
    private JTable gameTableList;
    private JScrollPane scrollPane;
    private static Object[][] data; // 构造 gameTableList 所用数据
    private static GameService gameService = new GameServiceImpl();
    private static String[] columnNames = {"玩家 1", "玩家 2", "状态"};

    /* getter & setter */

    public static Object[][] getData() {
        return data;
    }

    public static void setData(Object[][] data) {
        HallFrame.data = data;
    }

    /* 构造方法 */

    public HallFrame() {
        enterRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                // 进入房间 按钮 被按下
                try {
                    String roomStatus = data[gameTableList.getSelectedRow()][2].toString();
                    int roomNum = gameTableList.getSelectedRow();
                    if (roomStatus.equals("空闲") || roomStatus.equals("等待")) { // 判断是否选择可进入的房间
                        String msg = "uno02 enterroom " + OnlineUtil.getUsername() + " " + roomNum + "\r\n";
                        OnlineUtil.sendMsg(msg);

                    } else { // 不可进入房间
                        JOptionPane.showMessageDialog(null, "该房间已满", "进入房间", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("HallFrame: enter room exception");
                }
            }
        });

        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                // 返回 按钮 被按下
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        synchronized (OnlineUtil.messageLock) {
            gameService.getGameTablesData();
            OnlineUtil.messageLock.wait(3000);
        }

        JFrame frame = new JFrame("游戏大厅");
        frame.setContentPane(new HallFrame().panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500, 218);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // 在此处放置自定义组件创建代码
        // 展示游戏大厅数据
        gameTableList = new JTable(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                // 禁止用户编辑
                return false;
            }
        };
    }
}
