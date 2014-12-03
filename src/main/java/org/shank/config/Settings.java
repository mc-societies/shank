package org.shank.config;

import java.lang.annotation.Annotation;

/**
 * Represents a Configs
 */
public class Settings {

    public static ConfigSetting create(String name) {
        return new BasicConfig(name);
    }

    private static final class BasicConfig implements ConfigSetting {

        private final String value;

        private BasicConfig(String value) {this.value = value;}

        @Override
        public String value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ConfigSetting)) {
                return false;
            }

            ConfigSetting other = (ConfigSetting) o;
            return value.equals(other.value());
        }

        @Override
        public int hashCode() {
            return (127 * "value".hashCode()) ^ value.hashCode();
        }

        @Override
        public String toString() {
            return "Config{" + value() + "}";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ConfigSetting.class;
        }
    }
}
