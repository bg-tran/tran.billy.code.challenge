package tran.billy.code.challenge.stream.connector;

/**
 * Stream connector manager to return the registered connector
 *
 * A stream connector impl class should include below statement to register with the StreamConnectorManager class
 *
 * static {
 *         StreamConnectorManager.registerDataStream(new NewStreamConnectorImpl());
 * }
 *
 */
public class StreamConnectorManager {

    static private StreamConnector connector;

    /**
     * Register a connector
     * @param connector connector imp class
     */
    static public void registerDataStream(StreamConnector connector){
        StreamConnectorManager.connector = connector;
    }

    /**
     * Get the current connector
     * @return the registered connector
     */
    static public StreamConnector getStreamConnector(){
        return connector;
    }
}
