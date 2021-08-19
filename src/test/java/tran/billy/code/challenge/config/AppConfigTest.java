package tran.billy.code.challenge.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AppConfigTest {

    @Test
    public void shouldFailInitAppConfig_ClassLoadingFailed() {

        String configFile = "./src/test/foo.config.properties";
        Assertions.assertThrows(ClassNotFoundException.class,() -> AppConfig.init(configFile));
    }

    @Test
    public void shouldFailInitAppConfig_ConfigFileNotFound() {

        String configFile = "foo.properties";
        Assertions.assertThrows(IOException.class,() -> AppConfig.init(configFile));
    }

    @Test
    public void shouldInitAppConfig_Success() throws Exception {

        String configFile = "./src/test/config.properties";
        AppConfig.init(configFile);
        Assertions.assertEquals("organizations.json", AppConfig.get(AppConfig.ORG_DATA_STREAM_RESOURCE_PATH));
        Assertions.assertEquals("tickets.json", AppConfig.get(AppConfig.TICKET_DATA_STREAM_RESOURCE_PATH));
        Assertions.assertEquals("users.json", AppConfig.get(AppConfig.USER_DATA_STREAM_RESOURCE_PATH));
    }

}