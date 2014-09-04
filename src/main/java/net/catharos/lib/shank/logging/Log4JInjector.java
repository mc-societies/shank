package net.catharos.lib.shank.logging;

import com.google.inject.MembersInjector;
import net.catharos.lib.shank.ModuleDescription;
import net.catharos.lib.shank.loader.ModuleClassLoader;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.apache.logging.log4j.spi.LoggerContext;

import java.lang.reflect.Field;

/**
 * Represents a Log4JInjector
 */
public class Log4JInjector<T> implements MembersInjector<T> {
    private static final String DEFAULT_LOGGER = "default";
    public static final StringFormatterMessageFactory DEFAULT_MESSAGE_FACTORY = StringFormatterMessageFactory.INSTANCE;

    private final Field field;
    private final Logger logger;

    Log4JInjector(Field field, Class clazz, LoggerContext context) {
       this(field, getName(clazz), context);
    }

    Log4JInjector(Field field, String name, LoggerContext context) {
        this.field = field;
        logger = context.getLogger(name, DEFAULT_MESSAGE_FACTORY);
//        LoggingHelper.addUniqueFileAppender(context, name, new File(logFolder, name + ".log"));
    }

    private static String getName(Class clazz) {
        ClassLoader classLoader = clazz.getClassLoader();

        String name = DEFAULT_LOGGER;

        if (classLoader instanceof ModuleClassLoader) {
            ModuleDescription moduleDesc = ((ModuleClassLoader) classLoader).getModule();
            name = moduleDesc.getName();
        }

        return name;
    }

    @Override
    public void injectMembers(T t) {
        try {
            FieldUtils.writeField(field, t, logger, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
