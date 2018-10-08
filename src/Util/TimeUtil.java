package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static final int timeLimit = 0; // 接收消息前等待
    private static final SimpleDateFormat debugTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static String getTimeInMillis() {
        return debugTimeFormat.format(new Date());
    }
}
