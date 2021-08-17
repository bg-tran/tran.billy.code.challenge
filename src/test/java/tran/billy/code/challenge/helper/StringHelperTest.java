package tran.billy.code.challenge.helper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class StringHelperTest {

    @Test
    void testAddRightPadding(){
        String testVal = StringHelper.addRightPadding("foo", 10);
        Assertions.assertEquals(10, testVal.length());
        Assertions.assertTrue(testVal.startsWith("foo"));
        Assertions.assertEquals("foo", testVal.trim());
    }

    @Test
    void testAddLeftPadding(){
        String testVal = StringHelper.addLeftPadding("foo", 10);
        Assertions.assertEquals(10, testVal.length());
        Assertions.assertTrue(testVal.endsWith("foo"));
        Assertions.assertEquals("foo", testVal.trim());
    }

    @Test
    void testPrintKeyValue(){

        String expectedVal = StringHelper.addRightPadding("prefix_0", StringHelper.RIGHT_PADDING_WIDTH) + "foo" + "\n" ;
        String actualVal = StringHelper.printKeyValue("prefix_0","foo");
        Assertions.assertEquals(expectedVal,actualVal);
    }

    @Test
    void printListAsStringTest(){
        ArrayList<String> input = new ArrayList<>(Arrays.asList("foo", "bar"));
        String expectedVal = StringHelper.addRightPadding("prefix_0", StringHelper.RIGHT_PADDING_WIDTH) + "foo" + "\n" + StringHelper.addRightPadding("prefix_1", StringHelper.RIGHT_PADDING_WIDTH) + "bar" + "\n";
        String actualVal = StringHelper.printListAsString("prefix_",input);
        Assertions.assertEquals(expectedVal,actualVal);
    }
}
