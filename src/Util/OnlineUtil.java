package Util;

import GUI.HallFrame;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class OnlineUtil extends Thread {
    private static final String IP_ADDR = "syh-pc"; // 服务器地址
    private static final int PORT = 4290; // 服务器端口号
    private static Socket socket = null;
    private static PrintStream writer = null;
    private static Reader reader = null;
    private static String username = null; // 用户名
    private static String roomNum = null; // 房间号
    public static final Object messageLock = new Object();

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

    public void run() {
        while (!connectServer()) {
            try {
                System.out.println("OnlineClient: The connection failed and will be retried after 5 seconds");
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        StringBuilder builder = new StringBuilder();
        while (true) {
            try {
                char chars[] = new char[GameConstants.BUFSIZ];
                int len = reader.read(chars);
                if (len < 0)
                    continue;

                builder.append(new String(chars, 0, len));
                String msg = builder.toString();
                System.out.println("[" + TimeUtil.getTimeInMillis() + "] Receive from server: " + msg);

                if (msg.startsWith("uno01 login")) {
                    login(msg);
                } else if (msg.startsWith("uno02 hall")) {
                    setGameTablesData(msg);
                } else if (msg.startsWith("uno02 enterroom")) {
                    enterRoom(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("OnlineClient: receive message exception");
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
     * 接收来自服务器的消息
     *
     * @return 返回消息；若异常，返回 null
     */
    public static String receiveMsg() {
        if (!connectServer()) return null;
        StringBuilder builder = new StringBuilder();
        try {
            // todo 保持与服务器的连接
            char chars[] = new char[GameConstants.BUFSIZ];
            int len = reader.read(chars);
            builder.append(new String(chars, 0, len));

            if (builder.toString().equals("")) {
                System.out.println("[" + TimeUtil.getTimeInMillis() + "] Receive from server: empty string");
            } else {
                System.out.println("[" + TimeUtil.getTimeInMillis() + "] Receive from server: " + builder.toString());
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OnlineClient: receive message exception");
            return null;
        }
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
            System.out.println("[" + TimeUtil.getTimeInMillis() + "] Send to server: " + msg);
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
            String[] msg_split = msg.split(" ");
            if (msg_split[1].equals("login")) {
                if (msg_split[3].equals("1")) { // 登录成功
                    username = msg_split[2];
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
            String[] msg_split = msg.split("\r\n\r\n");
            int i = 0;

            if (msg_split.length == 1) {
                // 大厅数据为空
                return;
            }

            // 大厅数据非空
            String[] content = msg_split[1].split("\r\n");
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

    private void enterRoom(String msg) {
        msg = msg.substring(0, msg.length() - 2); // 去除字符串末尾 \r\n
        String[] msg_split = msg.split(" ");

        if (msg_split[3].equals("1")) { // 服务器：进入房间成功
            setRoomNum(String.valueOf(roomNum)); // 设置客户端房间号
            // todo 修改 JTable 中的房间状态
            // todo 将用户名添加到 JTable
        } else if (msg_split[3].equals("0")) { // 服务器：进入房间失败
            // todo GUI 提示重试 JOptionPane.showMessageDialog(null, "请重试...", "进入房间", JOptionPane.ERROR_MESSAGE);
        }
    }
}
