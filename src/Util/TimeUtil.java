package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static final SimpleDateFormat debugTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static String getTimeInMillis() {
        return debugTimeFormat.format(new Date());
    }
}
