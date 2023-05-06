package ru.skqwk.zettedelebackend.util;

/**
 * Вспомогательный класс для форматирования строк
 */
public class StringUtils {

    /**
     * Прибавить слева символов symbol до размера times
     * @param target строка, к которой добавляются символы слева
     * @param times какой длины должна быть строка
     * @param symbol какой символ надо добавить
     */
    public static String leftPad(String target, int times, String symbol) {
        int count = Math.max(times - target.length(), 0);
        String add = symbol.repeat(count);
        return add + target;
    }
}
