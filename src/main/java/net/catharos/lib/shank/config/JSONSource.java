package net.catharos.lib.shank.config;

import com.google.common.base.Function;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents a PropertiesFunction
 */
public class JSONSource implements Function<Map<String, Object>, Map<String, Object>> {

    private final ObjectMapper mapper = new ObjectMapper();

    private final File file;

    public JSONSource(File file) {
        this.file = file;
    }

    @Override
    public Map<String, Object> apply(Map<String, Object> input) {
        if (!file.exists()) {
            return input;
        }

        JsonFactory f = new JsonFactory();

        try {

            JsonParser jp = f.createJsonParser(file);
            jp.nextToken();
            walk(input, new LinkedList<String>(), jp);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    private void walk(Map<String, Object> output, LinkedList<String> stack, JsonParser jp) throws IOException {
        JsonToken token;

        while ((token = jp.nextToken()) != null) {
            String name = jp.getCurrentName();

            if (name == null) {
                jp.skipChildren();
                continue;
            }

            switch (token) {
                case START_ARRAY:
                case VALUE_EMBEDDED_OBJECT:
                case VALUE_STRING:
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                case VALUE_TRUE:
                case VALUE_FALSE:
                case VALUE_NULL:
                    Object value = mapper.readValue(jp, Object.class);
                    output.put(key(stack, name), value);
                    break;

                case START_OBJECT:
                    stack.push(name);
                    break;
                case END_OBJECT:
                    stack.pollLast();
                    break;

            }
        }
    }

    private String key(LinkedList<String> stack, String name) {
        StringBuilder builder = new StringBuilder();

        for (String key : stack) {
            builder.append(key).append('.');
        }

        if (name == null) {
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
        } else {
            builder.append(name);
        }

        return builder.toString();
    }
}
