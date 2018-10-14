package Util;

import GUI.HallFrame;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.IntStream;

public class OnlineUtil extends Thread {
    private static final String IP_ADDR = "syh-pc"; // 服务器地址
    private static final int PORT = 4290; // 服务器端口号
    private static Socket socket = null;
    private static PrintStream writer = null;
    private static Reader reader = null;
    private static String username = null; // 用户名
    private static String roomNum = null; // 房间号
    public static final Object messageLock = new Object();
    private static boolean readyToReceive = false;


    /* getter & setter */

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        OnlineUtil.username = username;
    }

    public static String getRoomNum() {
        return roomNum;
    }

    public static void setRoomNum(String roomNum) {
        OnlineUtil.roomNum = roomNum;
    }

    public static boolean isReadyToReceive() {
        return readyToReceive;
    }

    public void run() {
        while (!connectServer()) {
            try {
                System.out.println("OnlineClient: The connection failed and will be retried after 5 seconds");
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        StringBuilder builder;
        char chars[] = new char[GameConstants.BUFSIZ];
        System.out.println("[" + TimeUtil.getTimeInMillis() + "] OnlineClient: listening thread has started");
        while (true) {
            try {
                readyToReceive = true;
                IntStream.range(0, GameConstants.BUFSIZ).forEach(i -> chars[i] = '\0');
                int len = reader.read(chars);
                if (len < 0)
                    continue;
                builder = new StringBuilder();
                builder.append(new String(chars, 0, len));
                String msg = builder.toString();
                System.out.println("[" + TimeUtil.getTimeInMillis() + "] OnlineClient: Receive from server, len = " + msg.length() + ": " + msg);

                if (msg.startsWith("uno01 login")) { // 登录反馈
                    login(msg);
                } else if (msg.startsWith("uno02 hall")) { // 服务器单播的大厅消息
                    setGameTablesData(msg);
                } else if (msg.startsWith("uno02 enterroom")) { // 进入房间反馈
                    enterRoom(msg);
                } else if (msg.startsWith("uno02 roomstatus")) { // 服务器广播的房间状态，如果进入房间失败不会广播
                    setRoomStatus(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("[" + TimeUtil.getTimeInMillis() + "] OnlineClient: receive message exception");
                return;
            }
        }
    }

    /**
     * 连接服务器
     *
     * @return 成功 true；失败 false
     */
    public static boolean connectServer() {
        try {
            if (socket != null && reader != null && writer != null)
                return true;
            socket = new Socket(IP_ADDR, PORT);
            reader = new InputStreamReader(socket.getInputStream());
            writer = new PrintStream(socket.getOutputStream(), true);
            return true;
        } catch (ConnectException ce) {
            ce.printStackTrace();
            System.out.println("OnlineClient: connect server failed");
            return false;
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
            System.out.println("OnlineClient: unknown host");
            return false;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("OnlineClient: io stream exception");
            return false;
        }
    }

    /**
     * 关闭 socket 与输入输出流
     *
     * @return 成功 true；失败 false
     */
    public static boolean closeSocket() {
        try {
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OnlineClient: close socket failed");
            return false;
        }
        return true;
    }

    /**
     * 向服务器发送消息
     *
     * @param msg 消息内容
     * @return 成功 true；失败 false
     */
    public static boolean sendMsg(String msg) {
        if (!connectServer()) return false;
        try {
            writer.print(msg);
            writer.flush();
            System.out.println("[" + TimeUtil.getTimeInMillis() + "] OnlineClient: Send to server, len = " + msg.length() + ": " + msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OnlineClient: send message exception");
            return false;
        }
    }

    /**
     * 登录反馈
     *
     * @param msg 消息
     */
    private void login(String msg) {
        synchronized (OnlineUtil.messageLock) {
            msg = msg.substring(0, msg.length() - 2); // 去除字符串末尾 \r\n
            String[] msgSplit = msg.split(" ");
            if (msgSplit[1].equals("login")) {
                if (msgSplit[3].equals("1")) { // 登录成功
                    username = msgSplit[2];
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
    private void setGameTablesData(String msg) {
        synchronized (messageLock) {
            String[][] data = new String[GameConstants.roomNum][3];
            String[] msgSplit = msg.split("\r\n\r\n");
            int i = 0;

            if (msgSplit.length == 1) {
                // 大厅数据为空
                messageLock.notify();
                return;
            }

            // 大厅数据非空
            String[] content = msgSplit[1].split("\r\n");
            for (String line : content) {
                String[] line_split = line.split(",");
                decodeRoomStatus(line_split);
                System.arraycopy(line_split, 0, data[i], 0, 3);
                i++;
            }
            HallFrame.setData(data);
            messageLock.notify();
        }

    }

    /**
     * 解析房间状态
     *
     * @param line_split 截取的消息
     */
    private void decodeRoomStatus(String[] line_split) {
        switch (line_split[2]) {
            case "0":
                line_split[2] = "空闲";
                break;
            case "1":
                line_split[2] = "等待";
                break;
            case "2":
                line_split[2] = "游戏中";
                break;
            default:
                break;
        }
    }

    /**
     * 处理对进入房间请求的反馈
     *
     * @param msg 进入房间反馈
     */
    private void enterRoom(String msg) {
        msg = msg.substring(0, msg.length() - 2); // 去除字符串末尾 \r\n
        String[] msgSplit = msg.split(" ");

        if (msgSplit[3].equals("1")) { // 服务器：进入房间成功
            setRoomNum(String.valueOf(roomNum)); // 设置客户端房间号
            // 进一步处理在 setRoomStatus 方法中
        } else if (msgSplit[3].equals("0")) { // 服务器：进入房间失败
            // todo GUI 提示重试 JOptionPane.showMessageDialog(null, "请重试...", "进入房间", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 修改游戏房间状态
     *
     * @param msg 服务器广播的房间状态
     */
    private void setRoomStatus(String msg) {
        msg = msg.substring(0, msg.length() - 2); // 去除字符串末尾 \r\n
        String[] msgSplit = msg.split(" ");

        String roomStatus = msgSplit[3];
        int roomNum = Integer.parseInt(msgSplit[2]);
        String[] roomStatusSplit = roomStatus.split(",");
        // todo 修改 JTable 中的房间信息，包括用户名和房间状态
        Object[][] data = HallFrame.getData();
        data[roomNum] = roomStatusSplit;
    }
}
