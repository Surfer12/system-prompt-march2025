package com.uplift.system.interop.adapters;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uplift.system.interop.InteropEntityAdapter;
import com.uplift.system.interop.InteropException;

/**
 * Java Entity Adapter: Implementation of InteropEntityAdapter for Java entities
 * 
 * Provides interoperability with Java components using doubly linked data structures
 * for efficient data transformation and traversal.
 */
public class JavaEntityAdapter implements InteropEntityAdapter {
    private static final Logger logger = Logger.getLogger(JavaEntityAdapter.class.getName());
    
    private boolean connected;
    private String connectionId;
    private Map<String, Object> connectionConfig;
    private DoublyLinkedOperationHistory operationHistory;
    
    /**
     * Constructs a new JavaEntityAdapter
     */
    public JavaEntityAdapter() {
        this.connected = false;
        this.connectionId = null;
        this.connectionConfig = new HashMap<>();
        this.operationHistory = new DoublyLinkedOperationHistory();
    }
    
    @Override
    public String connect(Object config) throws InteropException {
        try {
            if (connected) {
                logger.info("Already connected with ID: " + connectionId);
                return connectionId;
            }
            
            // Process configuration
            if (config != null) {
                if (config instanceof Map) {
                    this.connectionConfig = new LinkedHashMap<>((Map<String, Object>) config);
                } else {
                    throw new IllegalArgumentException("Configuration must be a Map");
                }
            }
            
            // Generate connection ID
            this.connectionId = "java_" + System.currentTimeMillis();
            this.connected = true;
            
            // Record connection in history
            operationHistory.addOperation("connect", new Object[]{config}, connectionId);
            
            logger.info("Connected to Java entity with ID: " + connectionId);
            return connectionId;
            
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Invalid configuration", e);
            throw new InteropException("Invalid configuration: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Connection failed", e);
            throw new InteropException("Connection failed: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Object executeOperation(String operation, Object... params) throws InteropException {
        if (!connected) {
            throw new InteropException("Not connected. Call connect() first.", null);
        }
        
        try {
            logger.info("Executing operation: " + operation);
            
            // Execute the operation based on its name
            Object result;
            switch (operation) {
                case "processData":
                    result = processData(params);
                    break;
                case "queryData":
                    result = queryData(params);
                    break;
                case "transformData":
                    result = transformData(params);
                    break;
                default:
                    throw new InteropException("Unsupported operation: " + operation, null);
            }
            
            // Record operation in history
            operationHistory.addOperation(operation, params, result);
            
            return result;
            
        } catch (InteropException e) {
            // Record failed operation
            operationHistory.addFailedOperation(operation, params, e);
            throw e;
        } catch (Exception e) {
            InteropException ie = new InteropException("Operation failed: " + e.getMessage(), e);
            // Record failed operation
            operationHistory.addFailedOperation(operation, params, ie);
            throw ie;
        }
    }
    
    @Override
    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append("JavaEntityAdapter - ");
        
        if (connected) {
            status.append("Connected (ID: ")
                  .append(connectionId)
                  .append(")");
        } else {
            status.append("Disconnected");
        }
        
        status.append(", Operations: ")
              .append(operationHistory.size())
              .append(", Successful: ")
              .append(operationHistory.getSuccessCount())
              .append(", Failed: ")
              .append(operationHistory.getFailCount());
        
        return status.toString();
    }
    
    @Override
    public String disconnect() throws InteropException {
        if (!connected) {
            logger.info("Already disconnected");
            return "Already disconnected";
        }
        
        try {
            // Perform disconnection logic
            connected = false;
            
            // Record disconnection in history
            operationHistory.addOperation("disconnect", new Object[]{}, "Disconnected");
            
            logger.info("Disconnected from Java entity (ID: " + connectionId + ")");
            return "Disconnected from Java entity";
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Disconnection failed", e);
            throw new InteropException("Disconnection failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Process data operation implementation
     * 
     * @param params Operation parameters
     * @return Processed result
     * @throws InteropException If processing fails
     */
    private Object processData(Object[] params) throws InteropException {
        if (params == null || params.length < 1) {
            throw new InteropException("No data provided for processing", null);
        }
        
        Object data = params[0];
        
        // Simple string processing example
        if (data instanceof String) {
            String input = (String) data;
            return "Processed: " + input.toUpperCase();
        }
        
        throw new InteropException("Unsupported data type: " + (data != null ? data.getClass().getName() : "null"), null);
    }
    
    /**
     * Query data operation implementation
     * 
     * @param params Operation parameters
     * @return Query result
     * @throws InteropException If query fails
     */
    private Object queryData(Object[] params) throws InteropException {
        if (params == null || params.length < 1) {
            throw new InteropException("No query parameters provided", null);
        }
        
        // Simple query example
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("query", params[0]);
        result.put("timestamp", System.currentTimeMillis());
        result.put("adapter", "JavaEntityAdapter");
        
        return result;
    }
    
    /**
     * Transform data operation implementation
     * 
     * @param params Operation parameters
     * @return Transformed data
     * @throws InteropException If transformation fails
     */
    private Object transformData(Object[] params) throws InteropException {
        if (params == null || params.length < 1) {
            throw new InteropException("No data provided for transformation", null);
        }
        
        Object data = params[0];
        
        // Simple transformation example
        if (data instanceof String) {
            return ((String) data).replaceAll("\\s+", "_");
        }
        
        if (data instanceof List) {
            List<Object> resultList = new LinkedList<>();
            for (Object item : (List<?>) data) {
                resultList.add(item.toString().toUpperCase());
            }
            return resultList;
        }
        
        throw new InteropException("Unsupported data type for transformation: " + (data != null ? data.getClass().getName() : "null"), null);
    }
    
    /**
     * Implementation of a doubly linked list for operation history
     * Allows bidirectional traversal of operation history
     */
    private static class DoublyLinkedOperationHistory {
        private Node head;
        private Node tail;
        private int size;
        private int successCount;
        private int failCount;
        
        /**
         * Node representing an operation in the history
         */
        private static class Node {
            String operation;
            Object[] params;
            Object result;
            InteropException error;
            boolean successful;
            long timestamp;
            Node prev;
            Node next;
            
            Node(String operation, Object[] params, Object result) {
                this.operation = operation;
                this.params = params;
                this.result = result;
                this.error = null;
                this.successful = true;
                this.timestamp = System.currentTimeMillis();
                this.prev = null;
                this.next = null;
            }
            
            Node(String operation, Object[] params, InteropException error) {
                this.operation = operation;
                this.params = params;
                this.result = null;
                this.error = error;
                this.successful = false;
                this.timestamp = System.currentTimeMillis();
                this.prev = null;
                this.next = null;
            }
        }
        
        /**
         * Constructs an empty operation history
         */
        public DoublyLinkedOperationHistory() {
            this.head = null;
            this.tail = null;
            this.size = 0;
            this.successCount = 0;
            this.failCount = 0;
        }
        
        /**
         * Adds a successful operation to the history
         * 
         * @param operation Operation name
         * @param params Operation parameters
         * @param result Operation result
         */
        public void addOperation(String operation, Object[] params, Object result) {
            Node newNode = new Node(operation, params, result);
            
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
            
            size++;
            successCount++;
        }
        
        /**
         * Adds a failed operation to the history
         * 
         * @param operation Operation name
         * @param params Operation parameters
         * @param error Operation error
         */
        public void addFailedOperation(String operation, Object[] params, InteropException error) {
            Node newNode = new Node(operation, params, error);
            
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
            
            size++;
            failCount++;
        }
        
        /**
         * Gets the number of operations in the history
         * 
         * @return Size of the history
         */
        public int size() {
            return size;
        }
        
        /**
         * Gets the number of successful operations
         * 
         * @return Count of successful operations
         */
        public int getSuccessCount() {
            return successCount;
        }
        
        /**
         * Gets the number of failed operations
         * 
         * @return Count of failed operations
         */
        public int getFailCount() {
            return failCount;
        }
        
        /**
         * Gets the most recent operation
         * 
         * @return Most recent operation or null if history is empty
         */
        public Node getMostRecent() {
            return tail;
        }
    }
}