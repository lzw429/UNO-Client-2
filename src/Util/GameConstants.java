package Util;

public class GameConstants {
    public static final int roomNum = 10; // 大厅房间数量
    static final int BUFSIZ = 8192; // buffer size

    public static String removeIllegalChar(String str) {
        try {
            str = str.replace(",", "");
            str = str.replace("\r", "");
            str = str.replace("\n", "");
            str = str.replace(" ", "");
            str = str.replace("\f", "");
            return str;
        } catch (NullPointerException npe) {
            System.out.println("GameConstants: the string parameter is null ");
            return null;
        }
    }
}
