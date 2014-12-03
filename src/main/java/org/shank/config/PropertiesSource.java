package org.shank.config;

import com.google.common.base.Function;

import java.util.Map;
import java.util.Properties;

/**
 * Represents a PropertiesFunction
 */
public class PropertiesSource implements Function<Map<String, Object>, Map<String, Object>> {

    private final Properties properties;

    public PropertiesSource(Properties properties) {this.properties = properties;}

    @Override
    public Map<String, Object> apply(Map<String, Object> input) {

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (entry.getKey() instanceof String) {
                input.put((String) entry.getKey(), entry.getValue());
            }
        }

        return input;
    }
}
