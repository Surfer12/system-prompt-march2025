// src/main/java/com/uplift/system/gate/Connector.java
package com.uplift.system.gate;

import java.util.Map;

public interface Connector {
    Map<String, Object> connect(Object source, Object destination);
}