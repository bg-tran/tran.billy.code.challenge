package tran.billy.code.challenge.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    public static final String DATA_STREAM_CONNECTOR_IMPLEMENTATION = "data.stream.connector.implementation";
    public static final String ORG_DATA_STREAM_RESOURCE_PATH = "organization.data.stream.resource.path";
    public static final String USER_DATA_STREAM_RESOURCE_PATH = "user.data.stream.resource.path";
    public static final String TICKET_DATA_STREAM_RESOURCE_PATH = "ticket.data.stream.resource.path";

    private Properties config;

    public Properties getConfig() {
        return config;
    }

    private static AppConfig appConfig;

    public AppConfig(Properties config){
        this.config = config;
    }

    public static void init(String configFile) throws IOException, ClassNotFoundException {

        InputStream input = new FileInputStream(configFile );
        Properties config = new Properties();
        config.load(input);
        appConfig = new AppConfig(config);
        Class.forName(config.getProperty(DATA_STREAM_CONNECTOR_IMPLEMENTATION));
    }

    public static String get(String configName) {
        return appConfig.getConfig().getProperty(configName);
    }
}
