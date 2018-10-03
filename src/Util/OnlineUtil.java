package Util;

import java.io.*;
import java.net.Socket;

public class OnlineUtil {
    private static final String IP_ADDR = "syh-pc"; // 服务器地址
    private static final int PORT = 4290; // 服务器端口号
    private static boolean isSocketOpen = false;
    private static Socket socket = null;
    private static PrintWriter printWriter = null;
    private static BufferedReader bufferedReader = null;
    private static Reader reader = null;
    private static DataInputStream dataInputStream = null;

    /**
     * 连接服务器
     *
     * @return 成功 true；失败 false
     */
    public static boolean connectServer() {
        try {
            if (isSocketOpen)
                return true;
            socket = new Socket(IP_ADDR, PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            //  bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            reader = new InputStreamReader(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            isSocketOpen = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OnlineClient: connect server failed");
            return false;
        }
        return true;
    }

    /**
     * 关闭 socket 与输入输出流
     *
     * @return 成功 true；失败 false
     */
    public static boolean closeSocket() {
        try {
            printWriter.close();
            // bufferedReader.close();
            reader.close();
            dataInputStream.close();
            socket.close();
            isSocketOpen = false;
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
        // String ret;
        StringBuilder builder = new StringBuilder();
        try {
            //   ret = bufferedReader.readLine();
            char chars[] = new char[1024];
            int len;
            while ((len = reader.read(chars)) != -1) {
                builder.append(new String(chars, 0, len));
            }
            System.out.println("Receive from server: " + builder);
            //    ret = dataInputStream.readUTF();
            //  System.out.println("Server: " + ret);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OnlineClient: receive message exception");
            return null;
        }
        return builder.toString();
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
            printWriter.write(msg);
            printWriter.flush();
            System.out.println("Send to server: " + msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OnlineClient: send message exception");
            return false;
        }
        return true;
    }
}
