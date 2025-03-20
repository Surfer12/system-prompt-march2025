// src/main/java/com/uplift/system/gate/JavaConnector.java
package com.uplift.system.gate;

import java.util.HashMap;
import java.util.Map;

public class JavaConnector implements Connector {
    @Override
    public Map<String, Object> connect(Object source, Object destination) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "connected");
        result.put("source_type", "Java");
        result.put("destination_type", destination.getClass().getSimpleName());
        return result;
    }
}