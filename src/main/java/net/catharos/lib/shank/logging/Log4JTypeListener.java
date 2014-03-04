package net.catharos.lib.shank.logging;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.lang.reflect.Field;


/**
 * Represents a Log4JTypeListener
 */
public class Log4JTypeListener implements TypeListener {

    private final File directory;
    private final LoggerContext context;

    public Log4JTypeListener(File directory, LoggerContext context) {
        this.directory = directory;
        this.context = context;
    }

    @Override
    public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {

        for (Field field : typeLiteral.getRawType().getDeclaredFields()) {

            InjectLogger annotation = field.getAnnotation(InjectLogger.class);
            if (annotation != null) {
                if (field.getType() == Logger.class) {

                    Log4JInjector<T> memberInjector;

                    if (annotation.name().isEmpty()) {
                        memberInjector = new Log4JInjector<T>(field, typeLiteral.getRawType(), directory, context);
                    }   else {
                        memberInjector = new Log4JInjector<T>(field, annotation.name(), directory, context);
                    }

                    typeEncounter.register(memberInjector);
                } else if (field.getType().getSimpleName().equalsIgnoreCase("Logger")) {
                    typeEncounter.addError("Only log4j loggers are supported!");
                }
            }
        }
    }
}
