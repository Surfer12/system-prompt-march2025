package com.uplift.system.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uplift.system.interop.InteropEntityAdapter;
import com.uplift.system.interop.InteropException;

/**
 * Data Processor: Processes data between different entity adapters
 * 
 * Implements linked data structures concepts for efficient data transformation and processing.
 */
public class DataProcessor {
    private static final Logger logger = Logger.getLogger(DataProcessor.class.getName());
    
    // Using IndexedLinkedMap to implement initial mapping and transformation concepts
    private IndexedLinkedMap<String, Object> processingQueue;
    private Map<String, InteropEntityAdapter> entityAdapters;
    
    /**
     * Constructs a new DataProcessor with initialized data structures
     */
    public DataProcessor() {
        this.processingQueue = new IndexedLinkedMap<>();
        this.entityAdapters = new HashMap<>();
    }
    
    /**
     * Register an entity adapter for a specific technology
     * 
     * @param technology The technology name
     * @param adapter The adapter instance
     */
    public void registerAdapter(String technology, InteropEntityAdapter adapter) {
        entityAdapters.put(technology, adapter);
        logger.info("Registered adapter for technology: " + technology);
    }
    
    /**
     * Process data using the appropriate entity adapter
     * 
     * @param technology The technology to use for processing
     * @param data The data to process (JSON or other format)
     * @return Processed result
     * @throws InteropException If processing fails
     */
    public Object processData(String technology, String data) throws InteropException {
        try {
            // Validate inputs
            if (technology == null || technology.isEmpty()) {
                throw new IllegalArgumentException("Technology cannot be empty");
            }
            
            if (data == null) {
                throw new IllegalArgumentException("Data cannot be null");
            }
            
            // Find the appropriate adapter
            InteropEntityAdapter adapter = entityAdapters.get(technology);
            if (adapter == null) {
                throw new InteropException("No adapter found for technology: " + technology);
            }
            
            // Initial mapping - Add to processing queue with metadata
            Map<String, Object> metadata = new LinkedHashMap<>();
            metadata.put("timestamp", System.currentTimeMillis());
            metadata.put("technology", technology);
            metadata.put("size", data.length());
            
            // Add to processing queue using the linked structure
            String processId = "process_" + System.currentTimeMillis();
            processingQueue.put(processId, data, metadata);
            
            // Connect to the entity
            adapter.connect(null);
            
            // Execute the operation on the entity
            Object result = adapter.executeOperation("processData", data);
            
            // Mark as processed in the queue
            processingQueue.markAsProcessed(processId);
            
            return result;
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Invalid input for processing", e);
            throw new InteropException("Invalid input for processing: " + e.getMessage(), e);
        } catch (InteropException e) {
            logger.log(Level.SEVERE, "Error during processing", e);
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error during processing", e);
            throw new InteropException("Unexpected error during processing: " + e.getMessage(), e);
        }
    }
    
    /**
     * Process a batch of data items using doubly linked sets for traversal
     * 
     * @param technology The technology to use
     * @param dataItems List of data items to process
     * @return List of results
     * @throws InteropException If processing fails
     */
    public List<Object> processBatch(String technology, List<String> dataItems) throws InteropException {
        // Create a doubly linked list for results (enables efficient insertion and traversal)
        LinkedList<Object> results = new LinkedList<>();
        
        // Set up a consumer that processes each item and adds to results
        Consumer<String> processor = data -> {
            try {
                Object result = processData(technology, data);
                results.add(result);
            } catch (InteropException e) {
                // Add exception as a result to maintain order
                results.add(e);
            }
        };
        
        // Process each item using the consumer
        dataItems.forEach(processor);
        
        return results;
    }
    
    /**
     * Get current processing queue status
     * @return A map with processing statistics
     */
    public Map<String, Object> getProcessingStatus() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("queueSize", processingQueue.size());
        status.put("processedCount", processingQueue.getProcessedCount());
        status.put("pendingCount", processingQueue.getPendingCount());
        return status;
    }
    
    /**
     * Implementation of an Indexed Linked Map that combines initial mapping with 
     * index-based transformation and bidirectional traversal capabilities
     */
    private static class IndexedLinkedMap<K, V> {
        private final Map<K, Node<K, V>> nodeMap; // For direct key lookup (O(1))
        private final Map<Integer, Node<K, V>> indexMap; // For index-based lookup (O(1))
        private Node<K, V> head;
        private Node<K, V> tail;
        private int size;
        private int processedCount;
        
        /**
         * Node in the doubly-linked structure
         */
        private static class Node<K, V> {
            K key;
            V value;
            Map<String, Object> metadata;
            boolean processed;
            int index;
            Node<K, V> prev;
            Node<K, V> next;
            
            Node(K key, V value, Map<String, Object> metadata, int index) {
                this.key = key;
                this.value = value;
                this.metadata = metadata != null ? new LinkedHashMap<>(metadata) : new LinkedHashMap<>();
                this.processed = false;
                this.index = index;
                this.prev = null;
                this.next = null;
            }
        }
        
        /**
         * Constructs an empty IndexedLinkedMap
         */
        public IndexedLinkedMap() {
            this.nodeMap = new HashMap<>();
            this.indexMap = new HashMap<>();
            this.head = null;
            this.tail = null;
            this.size = 0;
            this.processedCount = 0;
        }
        
        /**
         * Puts a key-value pair with metadata in the map
         * 
         * @param key The key
         * @param value The value
         * @param metadata Associated metadata
         * @return Previous value or null
         */
        public V put(K key, V value, Map<String, Object> metadata) {
            // Check if key already exists
            Node<K, V> existingNode = nodeMap.get(key);
            if (existingNode != null) {
                V oldValue = existingNode.value;
                existingNode.value = value;
                existingNode.metadata = metadata != null ? new LinkedHashMap<>(metadata) : new LinkedHashMap<>();
                return oldValue;
            }
            
            // Create new node
            Node<K, V> newNode = new Node<>(key, value, metadata, size);
            
            // Link node
            if (head == null) {
                // First node
                head = newNode;
                tail = newNode;
            } else {
                // Add to end
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            
            // Add to maps
            nodeMap.put(key, newNode);
            indexMap.put(size, newNode);
            
            size++;
            return null;
        }
        
        /**
         * Gets a value by key
         * 
         * @param key The key
         * @return The value or null if key not found
         */
        public V get(K key) {
            Node<K, V> node = nodeMap.get(key);
            return node != null ? node.value : null;
        }
        
        /**
         * Gets a value by index
         * 
         * @param index The index
         * @return The value or null if index is out of bounds
         */
        public V getByIndex(int index) {
            Node<K, V> node = indexMap.get(index);
            return node != null ? node.value : null;
        }
        
        /**
         * Mark an item as processed
         * 
         * @param key The key to mark
         * @return true if marked, false if key not found
         */
        public boolean markAsProcessed(K key) {
            Node<K, V> node = nodeMap.get(key);
            if (node != null && !node.processed) {
                node.processed = true;
                processedCount++;
                return true;
            }
            return false;
        }
        
        /**
         * Gets the number of items in the map
         * 
         * @return Size of the map
         */
        public int size() {
            return size;
        }
        
        /**
         * Gets the number of processed items
         * 
         * @return Count of processed items
         */
        public int getProcessedCount() {
            return processedCount;
        }
        
        /**
         * Gets the number of pending (unprocessed) items
         * 
         * @return Count of pending items
         */
        public int getPendingCount() {
            return size - processedCount;
        }
        
        /**
         * Apply a processor function to all items in the map
         * 
         * @param processor Function to apply to each item
         */
        public void forEach(Consumer<Node<K, V>> processor) {
            Node<K, V> current = head;
            while (current != null) {
                processor.accept(current);
                current = current.next;
            }
        }
    }
} 
