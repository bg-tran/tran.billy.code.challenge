package tran.billy.code.challenge.helper;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class StringHelper {

    public static final int WIDTH = 100;

    public static String addRightPadding(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    public static <T> String printListAsString(ArrayList<T> list, String prefixTerm) {

        StringBuffer retVal = new StringBuffer();

        IntStream.range(0, list.size())
                .forEach(idx -> retVal.append(StringHelper.addRightPadding(prefixTerm + idx, StringHelper.WIDTH)
                        + list.get(idx)  + "\n"));
        return retVal.toString();
    }
}
