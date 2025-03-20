
// src/main/java/com/uplift/system/gate/ConnectorFactory.java
package com.uplift.system.gate;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class ConnectorFactory {

    private static final Map<String, Connector> connectors = new HashMap<>();

    static {
        // Use ServiceLoader to discover and load Connector implementations
        ServiceLoader<Connector> loader = ServiceLoader.load(Connector.class);
        for (Connector connector : loader) {
            // Assuming each connector has a method to get its technology name
            if (connector instanceof  JavaConnector) {
                connectors.put("Java", connector);
            } else if (connector instanceof CppConnector) {
                connectors.put("C++", connector);
            }
        }
    }

    public static Connector getConnector(String technology) {
        return connectors.get(technology);
    }
}