package net.catharos.lib.shank.logging;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import org.apache.logging.log4j.spi.LoggerContext;

/**
 * Represents a LoggingModule
 */
public class LoggingModule implements Module {
    private final LoggerContext context;

    public LoggingModule(LoggerContext context) {
        this.context = context;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(org.apache.logging.log4j.spi.LoggerContext.class).toInstance(context);
        binder.bindListener(Matchers.any(), new Log4JTypeListener(context));
    }

    public void stop() {
//        context.stop();
    }
}
