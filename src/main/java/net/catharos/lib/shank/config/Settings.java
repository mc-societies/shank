package net.catharos.lib.shank.config;

import java.lang.annotation.Annotation;

/**
 * Represents a Configs
 */
public class Settings {

    public static Setting create(String name) {
        return new BasicConfig(name);
    }

    private static final class BasicConfig implements Setting {

        private final String value;

        private BasicConfig(String value) {this.value = value;}

        @Override
        public String value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Setting that = (Setting) o;


            return value.equals(that.value());
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return "Config{" + value() + "}";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return Setting.class;
        }
    }
}
