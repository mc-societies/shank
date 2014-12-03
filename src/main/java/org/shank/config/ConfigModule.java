package org.shank.config;

import com.google.common.base.Function;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.name.Names;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.procedure.TObjectObjectProcedure;
import net.catharos.lib.core.util.CastSafe;

import java.util.Map;

/**
 * Represents a ConfigModule
 */
public class ConfigModule extends AbstractModule {

    private final TMap<String, Object> config = new THashMap<String, Object>();

    public ConfigModule(Function<Map<String, Object>, Map<String, Object>> source) {
        source.apply(config);
    }

    @Override
    protected void configure() {
        config.forEachEntry(new TObjectObjectProcedure<String, Object>() {
            @Override
            public boolean execute(String a, Object b) {
                Class<Object> clazz = CastSafe.toGeneric(b.getClass());
                Key<Object> settingKey = Key.get(clazz, Settings.create(a));
                Key<Object> namedKey = Key.get(clazz, Names.named(a));

                bind(settingKey).toInstance(b);
                bind(namedKey).toInstance(b);
                return true;
            }
        });
    }

    public void load(String name, Object obj) {
        config.put(name, obj);
    }
}
