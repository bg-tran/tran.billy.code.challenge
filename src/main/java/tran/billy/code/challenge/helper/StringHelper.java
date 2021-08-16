package tran.billy.code.challenge.helper;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class StringHelper {

    public static final int WIDTH = 50;

    /**
     * Format a string to fixed size column
     *
     * @param text
     * @param length
     * @return
     */
    public static String addRightPadding(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    /**
     * Format key:value pair for printing
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> String printKeyValue(String key, String value) {

        return StringHelper.addRightPadding(key, StringHelper.WIDTH) + value  + "\n";

    }
}
