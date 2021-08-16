package tran.billy.code.challenge.helper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonHelper {

    public static <T> Flux<T> readJSONFile(String filename, Class<T> valueType) {

        return Flux.<T>create(sink -> {
            BufferedReader reader = null;
            Path inputFile = Paths.get(filename);
            try {

                ObjectMapper mapper = new ObjectMapper();
                reader = Files.newBufferedReader(inputFile, Charset.defaultCharset());
                JsonParser jsonParser = new JsonFactory().createParser(reader);
                // Check the first token
                if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                    throw new IllegalStateException("Expected content to be an array");
                }

                while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                    sink.next(mapper.readValue(jsonParser, valueType));
                }

                reader.close();
            } catch (IOException e) {
                sink.error(e);
            } catch (IllegalStateException e) {
                sink.error(e);
            }
            sink.complete();
        });
    }
}
