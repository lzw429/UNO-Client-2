package Util;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class OnlineUtil {
    private static final String IP_ADDR = "syh-pc"; // 服务器地址
    private static final int PORT = 4290; // 服务器端口号
    private static Socket socket = null;
    private static PrintStream writer = null;
    private static Reader reader = null;

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
            char chars[] = new char[GameConstants.BUFSIZ];
            int len = reader.read(chars);
            builder.append(new String(chars, 0, len));

            if (builder.toString().equals("")) {
                System.out.println("Receive from server: empty string");
            } else {
                System.out.println("Receive from server: " + builder.toString());
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
            System.out.println("Send to server: " + msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OnlineClient: send message exception");
            return false;
        }
    }
}
