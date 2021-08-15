package tran.billy.code.challenge.helper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;



public class StringHelperTest {

    @Test
    void addRightPaddingTest(){
        String testVal = StringHelper.addRightPadding("foo", 10);
        Assertions.assertEquals(10, testVal.length());
        Assertions.assertEquals(true,testVal.startsWith("foo"));
        Assertions.assertEquals(true,testVal.trim().equals("foo"));
    }

    @Test
    void printListAsStringTest(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("foo", "bar"));
        String expectedVal = StringHelper.addRightPadding("prefix_0", StringHelper.WIDTH) + "foo" + "\n" +
                StringHelper.addRightPadding("prefix_1", StringHelper.WIDTH) + "bar" + "\n";
        String actualVal = StringHelper.printListAsString(input, "prefix_");
        Assertions.assertEquals(expectedVal,actualVal);
    }
}
