// src/main/java/com/uplift/system/adapters/Adapter.java
package com.uplift.system.adapters;

import java.util.Map;

public interface Adapter {
    String getTechnology(); // e.g., "Java", "C++", "Mojo", "Swift"
    Map<String, Object> execute(String methodName, Object... args);
}