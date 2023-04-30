package ru.skqwk.zettedelebackend.util;

/**
 * Описание класса
 */
public class StringUtils {
    public static String leftPad(String target, int times, String symbol) {
        int count = Math.max(times - target.length(), 0);
        String add = symbol.repeat(count);
        return add + target;
    }
}
