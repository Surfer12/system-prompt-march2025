package com.uplift.system.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.uplift.system.interop.adapters.JavaEntity;
import com.uplift.system.interop.adapters.MojoEntityAdapter;

import java.io.IOException;

public class DataProcessor {
    // Add dependency management
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final EventBus eventBus = new EventBus();

    public void processData(String json) {
        try {
            // Deserialize data into a JavaEntity
            JavaEntity entity = mapper.readValue(json, JavaEntity.class);
            // Register an event on the event bus if processing succeeds
            eventBus.post(entity);
        } catch (IOException e) {
            // Improved error handling
            System.err.println("Error processing data: " + e.getMessage());
        }
    }

    public void handleMojoData(String json) {
        try {
            // Ensure MojoEntityAdapter has a processData method
            MojoEntityAdapter adapter = new MojoEntityAdapter();
            adapter.processData(json); // Assumes this method exists in MojoEntityAdapter
        } catch (Exception e) {
            System.err.println("Error handling Mojo data: " + e.getMessage());
        }
    }
}