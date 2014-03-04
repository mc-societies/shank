package net.catharos.lib.shank.service;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Represents a ServiceAdapter
 */
public class ServiceAdapter implements Service {

    @Inject
    private Injector injector;
    @Inject
    @ServiceExecutor
    private ExecutorService executorService;

    @Override
    public void init() {}

    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public final Future initService() {
        return submit(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    @Override
    public final Future startService() {
        return submit(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
    }

    @Override
    public final Future stopService() {
        return submit(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        });
    }

    private Future submit(Runnable runnable) {
        return executorService.submit(runnable);
    }

    public final Injector injector() {
        return injector;
    }

    @Override
    public final <T> T get(Class<T> type) {
        return injector.getInstance(type);
    }

    @Override
    public final <T> T get(Key<T> key) {
        return injector.getInstance(key);
    }

    @Override
    public final <T> T get(TypeLiteral<T> literal) {
        return get(Key.get(literal));
    }

    @Override
    public final <T> T getNamed(Class<T> type, String name) {
        return injector.getInstance(Key.get(type, Names.named(name)));
    }
}
