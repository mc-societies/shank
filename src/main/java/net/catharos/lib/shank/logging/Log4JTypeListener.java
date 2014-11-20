package net.catharos.lib.shank.logging;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;


/**
 * Represents a Log4JTypeListener
 */
public class Log4JTypeListener implements TypeListener {

    private final Logger logger;

    public Log4JTypeListener(Logger logger) {this.logger = logger;}

    @Override
    public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {

        //beautify cache
        for (Field field : typeLiteral.getRawType().getDeclaredFields()) {

            InjectLogger annotation = field.getAnnotation(InjectLogger.class);
            if (annotation != null) {
                if (field.getType() == Logger.class) {

                    Log4JInjector<T> memberInjector;

                    if (annotation.name().isEmpty()) {
                        memberInjector = new Log4JInjector<T>(field, typeLiteral.getRawType(), logger);
                    } else {
                        memberInjector = new Log4JInjector<T>(field, annotation.name(), logger);
                    }

                    typeEncounter.register(memberInjector);
                } else if (field.getType().getSimpleName().equalsIgnoreCase("Logger")) {
                    typeEncounter.addError("Only log4j loggers are supported!");
                }
            }
        }
    }
}
