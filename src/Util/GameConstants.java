package Util;

public class GameConstants {
    public static final int roomNum = 10; // 大厅房间数量
    public static final int timeLimit = 500; // 超时上限
    static final int BUFSIZ = 1024; // buffer size

    public static String removeIllegalChar(String str) {
        str = str.replace(",", "");
        str = str.replace("\r", "");
        str = str.replace("\n", "");
        str = str.replace(" ", "");
        return str;
    }
}
