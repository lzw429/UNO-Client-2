package Util;

import Model.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OnlineUtil {
    public static final Object messageLock = new Object();
    static Reader reader = null;
    private static final String IP_ADDR = "syh-pc"; // 服务器地址
    private static final int PORT = 4290; // 服务器端口号
    private static Socket socket = null;
    private static PrintStream writer = null;
    static String username = null; // 用户名
    static String roomNum = null; // 房间号
    static boolean readyToReceive = false;
    static boolean readyToProcess = false;
    private static BlockingQueue<String> messageList = new LinkedBlockingQueue<>(); // 阻塞队列

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

    public static boolean isReadyToProcess() {
        return readyToProcess;
    }

    public static BlockingQueue<String> getMessageList() {
        return messageList;
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

    public static boolean isThisClient(Player player) {
        return player.getUsername().equals(getUsername());
    }

}
