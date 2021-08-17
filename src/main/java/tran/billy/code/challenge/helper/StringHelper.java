package tran.billy.code.challenge.helper;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class StringHelper {

    public static final int RIGHT_PADDING_WIDTH = 50;
    public static final int LEFT_PADDING_WIDTH = 30;

    /**
     * Format a string to fixed size column adding space in the right
     *
     * @param text text to format
     * @param length padding length
     * @return formatted string
     */
    public static String addRightPadding(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    /**
     * Format a string to fixed size column adding space in the right
     *
     * @param text text to format
     * @param length padding length
     * @return formatted string
     */
    public static String addLeftPadding(String text, int length) {
        return String.format("%" + length + "." + length + "s", text);
    }

    /**
     * Format key:value pair for printing
     *
     * @param key key to print
     * @param value value to print
     * @return formatted string
     */
    public static String printKeyValue(String key, String value) {

        return StringHelper.addRightPadding(key, StringHelper.RIGHT_PADDING_WIDTH) + value  + "\n";

    }


    /**
     * Format a list of strings into key/value format that having fixed size key column
     *
     * @param prefixTerm key prefix
     * @param list list to be formatted
     * @return formatted string lines
     */
    public static <T> String printListAsString(String prefixTerm, ArrayList<T> list) {

        StringBuffer retVal = new StringBuffer();

        IntStream.range(0, list.size())
                .forEach(idx -> retVal.append(printKeyValue(prefixTerm + idx, list.get(idx).toString())));

        return retVal.toString();
    }
}
