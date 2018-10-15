package Util;

import GUI.HallFrame;
import com.google.gson.Gson;

import javax.swing.*;

public class ProcessThread extends Thread {
    private Gson gson;

    /**
     * 处理消息
     *
     * @param msg 消息
     */
    private static void processMsg(String msg) {
        if (msg.startsWith("uno01 login")) { // 登录反馈
            login(msg);
        } else if (msg.startsWith("uno02 hall")) { // 服务器单播的大厅消息
            setGameTablesData(msg);
        } else if (msg.startsWith("uno02 enterroom")) { // 进入房间反馈
            enterRoom(msg);
        } else if (msg.startsWith("uno02 roomstatus")) { // 服务器广播的房间状态，如果进入房间失败不会广播
            setRoomStatus(msg);
        } else if (msg.startsWith("uno02 gamestart")) {
            gameStart(msg);
        }
    }

    private static void gameStart(String msg) {
        msg = msg.substring(0, msg.length() - 2); // 去除字符串末尾 \r\n
        String[] msgSplit = msg.split(" ");

        // todo 解析 JSON   msgSplit[2];
    }

    /**
     * 登录反馈
     *
     * @param msg 消息
     */
    private static void login(String msg) {
        synchronized (OnlineUtil.messageLock) {
            msg = msg.substring(0, msg.length() - 2); // 去除字符串末尾 \r\n
            String[] msgSplit = msg.split(" ");
            if (msgSplit[1].equals("login")) {
                if (msgSplit[3].equals("1")) { // 登录成功
                    OnlineUtil.username = msgSplit[2];
                }
            }
            OnlineUtil.messageLock.notify();
        }
    }

    /**
     * 如果未收到大厅数据，不置 data
     *
     * @param msg 大厅数据
     */
    private static void setGameTablesData(String msg) {
        synchronized (OnlineUtil.messageLock) {
            String[][] data = new String[GameConstants.roomNum][3];
            String[] msgSplit = msg.split("\r\n\r\n");
            int i = 0;

            if (msgSplit.length == 1) {
                // 大厅数据为空
                OnlineUtil.messageLock.notify();
                return;
            }

            // 大厅数据非空
            String[] content = msgSplit[1].split("\r\n");
            for (String line : content) {
                String[] line_split = line.split(",");
                line_split[2] = decodeRoomStatus(line_split[2]);
                System.arraycopy(line_split, 0, data[i], 0, 3);
                i++;
            }
            HallFrame.setData(data);
            OnlineUtil.messageLock.notify();
        }

    }

    /**
     * 解析房间状态
     *
     * @param status 截取的消息
     * @return 房间状态
     */
    private static String decodeRoomStatus(String status) {
        switch (status) {
            case "0":
                return "空闲";
            case "1":
                return "等待";
            case "2":
                return "游戏中";
            default:
                break;
        }
        return "未知";
    }

    /**
     * 处理对进入房间请求的反馈
     *
     * @param msg 进入房间反馈
     */
    private static void enterRoom(String msg) {
        msg = msg.substring(0, msg.length() - 2); // 去除字符串末尾 \r\n
        String[] msgSplit = msg.split(" ");

        if (msgSplit[3].equals("1")) { // 服务器：进入房间成功
            OnlineUtil.setRoomNum(String.valueOf(OnlineUtil.roomNum)); // 设置客户端房间号
            // 进一步处理在 setRoomStatus 方法中
        } else if (msgSplit[3].equals("0")) { // 服务器：进入房间失败
            JOptionPane.showMessageDialog(null, "请稍后重试...", "进入房间", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 修改游戏房间的用户名
     *
     * @param msg 服务器广播的房间状态
     */
    private static void setRoomStatus(String msg) {
        msg = msg.substring(0, msg.length() - 2); // 去除字符串末尾 \r\n
        String[] msgSplit = msg.split(" ");

        String roomStatus = msgSplit[3];
        int roomNum = Integer.parseInt(msgSplit[2]);
        String[] roomStatusSplit = roomStatus.split(",");
        // 修改 JTable 中的房间信息，包括用户名和房间状态
        roomStatusSplit[2] = decodeRoomStatus(roomStatusSplit[2]);
        for (int i = 0; i < roomStatusSplit.length; i++) {
            HallFrame.getGameTableModel().setValueAt(roomStatusSplit[i], roomNum, i);
        }
    }

    /**
     * 处理消息队列的线程主循环
     */
    public void run() {
        OnlineUtil.readyToProcess = true;
        gson = new Gson();
        System.out.println("[" + TimeUtil.getTimeInMillis() + "] ProcessThread has started");
        //noinspection InfiniteLoopStatement
        while (true) {
            String msg = OnlineUtil.getMessageList().poll(); // 阻塞队列
            if (msg != null)
                processMsg(msg);
        }
    }
}
