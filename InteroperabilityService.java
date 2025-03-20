```java
package com.uplift.system.service;

import com.uplift.system.interop.EnhancedInteroperabilityManager;
import com.uplift.system.interop.adapters.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;

/**
 * Service layer that provides a unified API for working with entities
 * across different languages and data stores.
 */
public class InteroperabilityService {
    private final EnhancedInteroperabilityManager interop;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public InteroperabilityService(DataSource dataSource) {
        this.interop = new EnhancedInteroperabilityManager();
        
        // Register adapters
        interop.registerAdapter("java", new JavaEntityAdapter());
        interop.registerAdapter("python", new PythonEntityAdapter());
        interop.registerAdapter("go", new GoEntityAdapter());
        interop.registerAdapter("swift", new SwiftEntityAdapter());
        interop.registerAdapter("mojo", new MojoEntityAdapter());
        interop.registerAdapter("postgres", new PostgresEntityAdapter(dataSource));
        
        // Set up optimization schedule
        scheduler.scheduleAtFixedRate(
            this::performOptimization, 
            10, 60, TimeUnit.MINUTES
        );
        
        // Set up monitoring
        setupMonitoring();
    }
    
    // API methods for working with entities
    
    public <T> T createEntity(Object entity, String sourceLanguage, String targetLanguage) {
        return interop.executeInTransaction(() -> 
            interop.createEntity(entity, sourceLanguage, targetLanguage)
        );
    }
    
    public <T> T getEntity(Object id, String sourceLanguage, String targetLanguage) {
        return interop.getEntity(id, sourceLanguage, targetLanguage);
    }
    
    public <T> List<T> getAllEntities(String sourceLanguage, String targetLanguage, int pageSize, String pageToken) {
        // Example implementation - would need to be adapted for actual pagination
        List<T> results = new ArrayList<>();
        
        // In a real implementation, we would query the source system
        // For this example, we'll create placeholder entities
        for (int i = 0; i < pageSize; i++) {
            Object sourceEntity = createDummyEntity(i, sourceLanguage);
            T convertedEntity = interop.convertEntity(sourceEntity, sourceLanguage, targetLanguage);
            results.add(convertedEntity);
        }
        
        return results;
    }
    
    private Object createDummyEntity(int id, String language) {
        Map<String, Object> normalized = new HashMap<>();
        normalized.put("id", id);
        normalized.put("name", "Entity " + id);
        normalized.put("data", Map.of("attribute", "value" + id));
        
        return interop.getAdapter(language).fromNormalizedForm(normalized);
    }
    
    // System management methods
    
    private void performOptimization() {
        interop.optimizeSystem();
    }
    
    private void setupMonitoring() {
        // Subscribe to error events for alerting
        interop.getEventBus().subscribe("entity.conversion.error", event -> {
            // In a real system, this might send alerts or log to monitoring system
            System.err.println("Conversion error: " + event);
        });
        
        // Subscribe to performance events for metrics collection
        interop.getEventBus().subscribe("transaction.success", event -> {
            long duration = (Long)event.get("duration");
            if (duration > 100) { // More than 100ms is slow
                System.out.println("Slow transaction detected: " + duration + "ms");
            }
        });
    }
    
    // Metrics and monitoring
    
    public Map<String, Object> getSystemHealth() {
        Map<String, Object> health = new HashMap<>();
        
        Map<String, Object> metrics = interop.getDetailedMetrics();
        health.put("metrics", metrics);
        
        // Determine system health based on metrics
        boolean healthy = (double)metrics.get("errorRate") < 0.01 && // Less than 1% errors
                           (double)metrics.get("transactionSuccessRate") > 0.99; // Over 99% success
        
        health.put("status", healthy ? "healthy" : "degraded");
        health.put("timestamp", System.currentTimeMillis());
        
        return health;
    }
    
    // Shutdown hook
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}