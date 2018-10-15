package GUI;

import Service.GameService;
import Service.GameServiceImpl;
import Util.OnlineUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HallFrame {
    private JButton enterRoomButton; // 进入房间
    private JButton goBackButton; // 返回到 选择模式
    private JPanel panel;
    private JTable gameTableList;
    private JScrollPane scrollPane;
    private static Object[][] data; // 构造 gameTableList 所用数据
    private static GameService gameService = new GameServiceImpl();
    private static String[] columnNames = {"玩家 1", "玩家 2", "状态"};
    private static DefaultTableModel gameTableModel;

    /* getter & setter */

    public static Object[][] getData() {
        return data;
    }

    public static void setData(Object[][] data) {
        HallFrame.data = data;
    }

    public HallFrame() {
        enterRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                // 进入房间 按钮 被按下
                try {
                    String roomStatus = data[gameTableList.getSelectedRow()][2].toString();
                    int roomNum = gameTableList.getSelectedRow();

                    if (OnlineUtil.getRoomNum() != null && roomNum == Integer.parseInt(OnlineUtil.getRoomNum())) { // 已位于该房间
                        JOptionPane.showMessageDialog(null, "已位于该房间", "进入房间", JOptionPane.INFORMATION_MESSAGE);
                    }

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
                // todo 关闭游戏大厅
                // todo 断开与服务器的连接
                // todo 重置联机信息
                System.exit(0);
            }
        });
    }

    /* 构造方法 */

    public static DefaultTableModel getGameTableModel() {
        return gameTableModel;
    }

    public static void main(String[] args) throws InterruptedException {
        synchronized (OnlineUtil.messageLock) {
            gameService.getGameTablesData();
            OnlineUtil.messageLock.wait(3000); // 等待服务器返回结果
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
        gameTableModel = new DefaultTableModel(data, columnNames);
        gameTableList = new JTable(gameTableModel) {
            public boolean isCellEditable(int row, int column) {
                // 禁止用户编辑大厅数据
                return false;
            }
        };
    }
}
