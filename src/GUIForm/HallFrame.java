package GUIForm;

import Service.GameService;
import Service.GameServiceImpl;
import Util.GameConstants;
import Util.OnlineUtil;

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
    private static Object[][] data;
    private static GameService gameService;
    private static String[] columnNames = {"玩家 1", "玩家 2", "状态"};

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
                        TimeUnit.MILLISECONDS.sleep(GameConstants.timeLimit); // 等待
                        String receive = OnlineUtil.receiveMsg();
                        if (receive == null) {
                            System.out.println("UserService: message timeout");
                            return;
                        }
                        receive = receive.substring(0, receive.length() - 2); // 去除字符串末尾 \r\n
                        if (receive.startsWith("uno02 enterroom ")) {
                            String[] receive_split = receive.split(" ");
                            if (receive_split[3].equals("1")) {                            // 服务器：进入房间成功
                                GameFrame.main(new String[10]); // 打开游戏窗口
                            } else if (receive_split[3].equals("0")) { // 服务器：进入房间失败
                                JOptionPane.showMessageDialog(null, "请重试...", "进入房间", JOptionPane.ERROR_MESSAGE);
                            }
                        } else { // 消息错误
                            System.out.println("HallFrame: receive the wrong message when entering room");
                        }
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
        gameTableList = new JTable(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                // 禁止用户编辑
                return false;
            }
        };
    }
}
