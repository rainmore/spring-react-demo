package au.com.rainmore.centus.configs.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public byte[] convertObjectToJsonBytes(Object data) throws IOException {
        return objectMapper.writeValueAsBytes(data);
    }

    public String toJsonString(Object data) throws IOException {
        if (data == null) return null;
        try {
            return new String(convertObjectToJsonBytes(data), StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            throw new IOException(String.format("An error occurred while converting %s object to JSON", data), ex);
        }
    }

    public <T> T convertJsonToObject(String json, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(json, clazz);
        }
        catch (IOException ex) {
            throw new IOException(String.format("An error occurred while converting %s object to JSON", json), ex);
        }
    }

}
