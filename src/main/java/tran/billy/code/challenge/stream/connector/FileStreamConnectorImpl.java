package tran.billy.code.challenge.stream.connector;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * File stream connector implementation
 * using Jackson JsonParser and ObjectMapper to load data from a file
 *
 */
public class FileStreamConnectorImpl implements StreamConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStreamConnectorImpl.class);
    static {
        StreamConnectorManager.registerDataStream(new FileStreamConnectorImpl());
    }

    @Override
    public <T> Flux<T> getData(String resourceURL, Class<T> dataType) {

        return Flux.create(sink -> {
            BufferedReader reader;
            Path inputFile = Paths.get(resourceURL);
            try {

                ObjectMapper mapper = new ObjectMapper();
                reader = Files.newBufferedReader(inputFile, Charset.defaultCharset());
                JsonParser jsonParser = new JsonFactory().createParser(reader);
                // Check the first token
                if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                    throw new IllegalStateException("Expected content to be an array");
                }

                while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                    sink.next(mapper.readValue(jsonParser, dataType));
                }

                reader.close();
            } catch (IOException | IllegalStateException e) {
                sink.error(e);
                LOGGER.error(e.getMessage());
            }
            sink.complete();
        });
    }
}
