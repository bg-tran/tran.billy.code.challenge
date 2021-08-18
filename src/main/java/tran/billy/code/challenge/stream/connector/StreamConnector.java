package tran.billy.code.challenge.stream.connector;

import reactor.core.publisher.Flux;

public interface StreamConnector {
    <T>Flux<T> getData(String resourceURL, Class<T> dataType);
}
