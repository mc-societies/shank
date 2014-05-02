package net.catharos.lib.shank.config;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * Represents a Configs
 */
public class Settings {

    public static Setting create(String name) {
        return new BasicConfig(name);
    }

    private static final class BasicConfig implements Setting {

        private final String name;

        private BasicConfig(String name) {this.name = name;}

        @Override
        public String name() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Setting that = (Setting) o;


            return Objects.equals(name, that.name());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

        @Override
        public String toString() {
            return "Config{" + name() + "}";
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return Setting.class;
        }
    }
}
