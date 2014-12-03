package org.shank.logging;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matchers;
import org.apache.logging.log4j.Logger;

/**
 * Represents a LoggingModule
 */
public class LoggingModule implements Module {

    private final Logger logger;

    public LoggingModule(Logger logger) {this.logger = logger;}

    @Override
    public void configure(Binder binder) {
//        binder.bind(org.apache.logging.log4j.spi.LoggerContext.class).toInstance(context);
        binder.bindListener(Matchers.any(), new Log4JTypeListener(logger));
    }

    public void stop() {
//        context.stop();
    }
}
