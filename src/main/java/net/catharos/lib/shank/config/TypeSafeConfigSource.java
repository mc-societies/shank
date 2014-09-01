package net.catharos.lib.shank.config;

import com.google.common.base.Function;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

import java.util.Map;

/**
 * Represents a PropertiesFunction
 */
public class TypeSafeConfigSource implements Function<Map<String, Object>, Map<String, Object>> {

    private final Config config;

    public TypeSafeConfigSource(Config config) {
        this.config = config;
    }

    @Override
    public Map<String, Object> apply(Map<String, Object> input) {

        for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
            input.put(entry.getKey().replace('.', '-'), entry.getValue().unwrapped());
        }

        return input;
    }
}
