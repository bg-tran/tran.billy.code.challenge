package tran.billy.code.challenge;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import tran.billy.code.challenge.services.HelpdeskService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


class HelpdeskAppTest {


    private ByteArrayOutputStream byteArrayOutputStream;
    private PrintStream console;

    private HelpdeskService helpdeskService;


    @BeforeEach
    public void setup() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        console = System.out;
        helpdeskService = Mockito.mock(HelpdeskService.class);
        Mockito.doNothing().when(helpdeskService).searchUsers(Mockito.anyString(), Mockito.anyString());
        Mockito.doNothing().when(helpdeskService).searchTickets(Mockito.anyString(), Mockito.anyString());
        Mockito.doNothing().when(helpdeskService).searchOrganizations(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @ExpectSystemExitWithStatus(HelpdeskApp.ERROR_CODE_BAD_ARGUMENTS)
    public void shouldPrintUsage() {
        String[] args = {};
        runTest("", args);
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Usage"));
    }

    @Test
    @ExpectSystemExitWithStatus(HelpdeskApp.ERROR_CODE_BAD_CONFIG_FILE)
    public void shouldFailInitAppConfig_InvalidConfigFile() {
        String[] args = {"foo.properties"};
        runTest("", args);
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Unable to load config file"));
    }

    @Test
    @ExpectSystemExitWithStatus(HelpdeskApp.ERROR_CODE_CLASS_LOADING_FAILED)
    public void shouldFailInitAppConfig_ClassLoadingFailed() {

        String[] args = {"./src/test/foo.config.properties"};
        runTest("", args);
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Unable to initialize app config"));
    }

    @Test
    public void shouldPrintWelcomeThenExit() {

        String[] args = {"./src/test/config.properties"};
        runTest("\nquit\n", args);

        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Welcome"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Exiting ..."));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back"));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Invalid input"));

    }

    @Test
    public void shouldPrintInvalidInput() {

        String[] args = {"./src/test/config.properties"};
        runTest("\n5\nquit\n", args);

        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Welcome"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Invalid input"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Exiting ..."));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back"));

    }

    @Test
    public void shouldPrintHelp() {

        String[] args = {"./src/test/config.properties"};
        runTest("\n2\nquit\n", args);

        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Welcome"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Search Users with"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Search Tickets with"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Search Organizations with"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Exiting ..."));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Invalid Input"));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back"));

    }

    @Test
    public void shouldPrintSearchMenu_GoBack_Exit() {

        String[] args = {"./src/test/config.properties"};
        runTest("\n1\n4\nquit\n", args);

        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Welcome"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Exiting ..."));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Invalid Input"));

    }

    @Test
    public void shouldPrintSearchMenu_InvalidInput_GoBack_Exit() {

        String[] args = {"./src/test/config.properties"};
        runTest("\n1\n5\n4\nquit\n", args);

        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Welcome"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Invalid input"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Exiting ..."));


    }

    @Test
    public void shouldCallSearchUsers_WithInput_GoBack_Exit() {

        String[] args = {"./src/test/config.properties"};
        runTestWithMockedService("\n1\n1\nfoo\nbar\n4\nquit\n", args);
        Mockito.verify(helpdeskService, Mockito.times(1)).searchUsers("foo","bar");
        Mockito.verify(helpdeskService, Mockito.times(0)).searchTickets("foo","bar");
        Mockito.verify(helpdeskService, Mockito.times(0)).searchOrganizations("foo","bar");
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Welcome"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back"));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Invalid input"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Exiting ..."));
    }

    @Test
    public void shouldCallSearchTickets_WithInput_GoBack_Exit() {

        String[] args = {"./src/test/config.properties"};
        runTestWithMockedService("\n1\n2\nfoo\nbar\n4\nquit\n", args);
        Mockito.verify(helpdeskService, Mockito.times(0)).searchUsers("foo","bar");
        Mockito.verify(helpdeskService, Mockito.times(1)).searchTickets("foo","bar");
        Mockito.verify(helpdeskService, Mockito.times(0)).searchOrganizations("foo","bar");

        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Welcome"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back"));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Invalid input"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Exiting ..."));
    }


    @Test
    public void shouldCallSearchOrganizations_WithInput_GoBack_Exit() {

        String[] args = {"./src/test/config.properties"};
        runTestWithMockedService("\n1\n3\nfoo\nbar\n4\nquit\n", args);
        Mockito.verify(helpdeskService, Mockito.times(0)).searchUsers("foo","bar");
        Mockito.verify(helpdeskService, Mockito.times(0)).searchTickets("foo","bar");
        Mockito.verify(helpdeskService, Mockito.times(1)).searchOrganizations("foo","bar");

        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Welcome"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back"));
        Assertions.assertFalse(byteArrayOutputStream.toString().contains("Invalid input"));
        Assertions.assertTrue(byteArrayOutputStream.toString().contains("Exiting ..."));
    }


    private void runTest(final String data, final String[] args) {

        final InputStream input = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        final InputStream old = System.in;

        try {
            System.setOut(new PrintStream(byteArrayOutputStream));
            System.setIn(input);
            HelpdeskApp.main(args);

        } finally {
            System.setOut(console);
            System.setIn(old);
        }
    }

    private void runTestWithMockedService(final String data, final String[] args) {

        final InputStream input = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        final InputStream old = System.in;

        try {
            System.setOut(new PrintStream(byteArrayOutputStream));
            System.setIn(input);
            HelpdeskApp helpdeskApp = new HelpdeskApp(args, helpdeskService);
            helpdeskApp.run();

        } finally {
            System.setOut(console);
            System.setIn(old);
        }
    }
}