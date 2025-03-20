package com.uplift.system.library;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.uplift.system.interop.EntityAdapter;

/**
 * DataProcessor: Processes data between different entity adapters
 * Implements linked data structures for efficient data transformation
 */
public class DataProcessor {
    private PriorityQueue<ProcessingTask> taskQueue;
    private Map<String, EntityAdapter> adapterRegistry;
    private Map<String, Object> processingResults;
    private AtomicInteger processedCount;
    
    /**
     * Constructs a new DataProcessor with initialized adapters and processing structures
     */
    public DataProcessor() {
        this.taskQueue = new PriorityQueue<>(
            Comparator.comparingInt(ProcessingTask::getPriority)
        );
        this.adapterRegistry = new HashMap<>();
        this.processingResults = new LinkedHashMap<>();
        this.processedCount = new AtomicInteger(0);
    }
    
    /**
     * Register an entity adapter for a specific technology
     * 
     * @param technology The technology identifier
     * @param adapter The adapter for the technology
     */
    public void registerAdapter(String technology, EntityAdapter adapter) {
        adapterRegistry.put(technology, adapter);
    }
    
    /**
     * Queue data for processing with a specific priority
     * 
     * @param technology The technology to process with
     * @param data The data to process
     * @param priority The priority level (lower values = higher priority)
     */
    public void queueDataWithPriority(String technology, String data, int priority) {
        String taskId = "task_" + System.currentTimeMillis();
        ProcessingTask task = new ProcessingTask(taskId, technology, data, priority);
        taskQueue.add(task);
    }
    
    /**
     * Process the next highest priority task
     * 
     * @return The result of processing, or null if no tasks
     * @throws Exception If processing fails
     */
    public Object processNextTask() throws Exception {
        ProcessingTask task = taskQueue.poll();
        if (task == null) {
            return null;
        }
        
        // Get the appropriate adapter
        EntityAdapter adapter = adapterRegistry.get(task.getTechnology());
        if (adapter == null) {
            throw new IllegalStateException("No adapter registered for technology: " + task.getTechnology());
        }
        
        // Process the data
        Object result = adapter.processData(task.getData());
        processedCount.incrementAndGet();
        
        // Store the result
        processingResults.put(task.getTaskId(), result);
        
        return result;
    }
    
    /**
     * Get the number of processed tasks
     * 
     * @return The count of processed tasks
     */
    public int getProcessedCount() {
        return processedCount.get();
    }
    
    /**
     * Task class for priority-based processing
     */
    private static class ProcessingTask {
        private final String taskId;
        private final String technology;
        private final String data;
        private final int priority;
        
        public ProcessingTask(String taskId, String technology, String data, int priority) {
            this.taskId = taskId;
            this.technology = technology;
            this.data = data;
            this.priority = priority;
        }
        
        public String getTaskId() { return taskId; }
        public String getTechnology() { return technology; }
        public String getData() { return data; }
        public int getPriority() { return priority; }
    }
}