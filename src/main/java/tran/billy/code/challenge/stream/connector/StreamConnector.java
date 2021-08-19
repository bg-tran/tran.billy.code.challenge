package tran.billy.code.challenge.stream.connector;

import reactor.core.publisher.Flux;

/**
 * Stream connector interface
 * A stream connector impl class should include below statement to register with the StreamConnectorManager class
 *
 * static {
 *         StreamConnectorManager.registerDataStream(new NewStreamConnectorImpl());
 * }
 *
 */
public interface StreamConnector {

    /**
     * Get data from stream
     * @param resourceURL resource stream URL
     * @param dataType a <T> class to deserialize JSON object
     * @return stream of <T>
     */
    <T>Flux<T> getData(String resourceURL, Class<T> dataType);
}
