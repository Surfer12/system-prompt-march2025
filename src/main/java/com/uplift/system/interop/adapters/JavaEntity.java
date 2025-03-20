package com.uplift.system.interop.adapters;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class JavaEntity implements InteropEntityAdapter {
    private static final Logger logger = LoggerFactory.getLogger(JavaEntity.class);
    
    private final JavaEntityConfig config;
    private final JavaEntityMetrics metrics;
    private final OperationRegistry operationRegistry;
    
    private boolean isConnected = false;
    private String connectionId;

    public JavaEntity(JavaEntityConfig config) {
        this.config = config;
        this.metrics = new JavaEntityMetrics();
        this.operationRegistry = new OperationRegistry();
    }

    @Override
    public String connect() throws InteropException {
        try {
            config.validate();
            
            // Simulate connection logic
            this.connectionId = "connection_" + System.currentTimeMillis();
            this.isConnected = true;
            
            metrics.recordConnection();
            logger.info("Connected to JavaEntity with config: {}", config);
            
            return this.connectionId;
        } catch (Exception e) {
            logger.error("Connection failed: {}", e.getMessage(), e);
            throw new InteropException("Connection failed: " + e.getMessage(), e);
        }
    }

    @Override
    public CompletableFuture<Object> executeOperationAsync(String operation, Object... params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return executeOperation(operation, params);
            } catch (InteropException e) {
                throw new CompletionException(e);
            }
        });
    }

    @Override
    public Object executeOperation(String operation, Object... params) throws InteropException {
        if (!isConnected) {
            throw new InteropException("Not connected. Call connect() first.");
        }

        try {
            Object result = RetryHandler.executeWithRetry(
                () -> operationRegistry.executeOperation(this, operation, params),
                3,  // max retries
                1000 // delay between retries
            );
            
            metrics.recordSuccessfulOperation();
            return result;
        } catch (Exception e) {
            metrics.recordFailedOperation();
            logger.error("Operation {} failed: {}", operation, e.getMessage(), e);
            throw new InteropException("Operation failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String getStatus() {
        return isConnected 
            ? "JavaEntity connected (ID: " + connectionId + ")" 
            : "JavaEntity not connected";
    }

    @Override
    public String disconnect() throws InteropException {
        if (!isConnected) {
            logger.warn("Already disconnected");
            return "Already disconnected";
        }

        try {
            // Simulate disconnection logic
            this.isConnected = false;
            logger.info("Disconnected from JavaEntity (ID: {})", connectionId);
            return "Disconnected successfully";
        } catch (Exception e) {
            logger.error("Disconnection failed: {}", e.getMessage(), e);
            throw new InteropException("Disconnection failed", e);
        }
    }

    // Expose metrics for monitoring
    public Map<String, Integer> getMetrics() {
        return metrics.getMetrics();
    }

    @Data
    public static class JavaEntityConfig {
        private String host;
        private int port;
        private Map<String, Object> additionalProperties = new HashMap<>();

        public void validate() throws InteropException {
            if (host == null || host.trim().isEmpty()) {
                throw new InteropException("Host cannot be blank");
            }
            if (port <= 0) {
                throw new InteropException("Invalid port number");
            }
        }
    }

    // Example usage
    public static void main(String[] args) {
        JavaEntityConfig config = new JavaEntityConfig();
        config.setHost("localhost");
        config.setPort(8080);

        try {
            JavaEntity entity = new JavaEntity(config);
            entity.connect();
            
            Object result = entity.executeOperation("getData", "123");
            System.out.println("Result: " + result);
            
            // Async operation example
            entity.executeOperationAsync("processData", "async data")
                .thenAccept(System.out::println)
                .exceptionally(ex -> {
                    System.err.println("Async operation failed: " + ex.getMessage());
                    return null;
                });
            
            System.out.println("Metrics: " + entity.getMetrics());
            
            entity.disconnect();
        } catch (InteropException e) {
            e.printStackTrace();
        }
    }
}