// src/main/java/com/uplift/system/adapters/AdapterFactory.java
package com.uplift.system.adapters;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class AdapterFactory {

    private static final Map<String, Adapter> adapters = new HashMap<>();

    static {
        // Use ServiceLoader to discover and load Adapter implementations
        ServiceLoader<Adapter> loader = ServiceLoader.load(Adapter.class);
        for (Adapter adapter : loader) {
            adapters.put(adapter.getTechnology(), adapter);
        }
    }

    public static Adapter getAdapter(String technology) {
        return adapters.get(technology);
    }
}