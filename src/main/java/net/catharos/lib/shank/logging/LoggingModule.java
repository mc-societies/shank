package net.catharos.lib.shank.logging;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import org.apache.logging.log4j.spi.LoggerContext;

import java.io.File;

/**
 * Represents a LoggingModule
 */
public class LoggingModule implements Module {
    private final File directory;
    private final LoggerContext context;

    public LoggingModule(File directory, LoggerContext context) {
        this.directory = directory;
        this.context = context;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(org.apache.logging.log4j.spi.LoggerContext.class).toInstance(context);
        binder.bindListener(Matchers.any(), new Log4JTypeListener(directory, context));
    }

    public void stop() {
//        context.stop();
    }
}
