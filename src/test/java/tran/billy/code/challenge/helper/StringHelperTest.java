package tran.billy.code.challenge.helper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringHelperTest {

    @Test
    void testAddRightPadding(){
        String testVal = StringHelper.addRightPadding("foo", 10);
        Assertions.assertEquals(10, testVal.length());
        Assertions.assertEquals(true,testVal.startsWith("foo"));
        Assertions.assertEquals(true,testVal.trim().equals("foo"));
    }

    @Test
    void testPrintKeyValue(){

        String expectedVal = StringHelper.addRightPadding("prefix_0", StringHelper.WIDTH) + "foo" + "\n" ;
        String actualVal = StringHelper.printKeyValue("prefix_0","foo");
        Assertions.assertEquals(expectedVal,actualVal);
    }
}
