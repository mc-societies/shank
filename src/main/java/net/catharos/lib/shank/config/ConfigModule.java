package net.catharos.lib.shank.config;

import com.google.inject.AbstractModule;
import gnu.trove.map.hash.THashMap;
import gnu.trove.procedure.TObjectObjectProcedure;
import net.catharos.lib.core.util.CastSafe;

/**
 * Represents a ConfigModule
 */
public abstract class ConfigModule extends AbstractModule {

    private final THashMap<String, Object> config = new THashMap<String, Object>();

    @Override
    protected void configure() {
        config.forEachEntry(new TObjectObjectProcedure<String, Object>() {
            @Override
            public boolean execute(String a, Object b) {
                Class<Object> aClass = CastSafe.toGeneric(b.getClass());
                bind(aClass).annotatedWith(Settings.create(a)).toInstance(b);
                return true;
            }
        });
    }

    public void load(String name, Object obj) {
        config.put(name, obj);
    }
}
