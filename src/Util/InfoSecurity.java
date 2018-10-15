package Util;

import java.util.Random;

import static java.lang.Math.pow;

public class InfoSecurity {
    public static long power(long a, long b, long P) {
        if (b == 1) return a;
        return ((long) pow(a, b) % P);
    }

    public long randomLong() {
        return new Random().nextLong(); // 与时间相关
    }
}