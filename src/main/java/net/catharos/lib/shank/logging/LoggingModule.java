package net.catharos.lib.shank.logging;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import net.catharos.engine.core.filesystem.Directory;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * Represents a LoggingModule
 */
public class LoggingModule implements Module {
    private final Directory directory;
    private final LoggerContext context;

    public LoggingModule(Directory directory, LoggerContext context) {
        this.directory = directory;
        this.context = context;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(org.apache.logging.log4j.spi.LoggerContext.class).toInstance(context);
        binder.bindListener(Matchers.any(), new Log4JTypeListener(directory, context));
    }

    public void stop() {
        context.stop();
    }
}
