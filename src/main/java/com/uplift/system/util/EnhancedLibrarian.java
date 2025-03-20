package com.uplift.system.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.uplift.system.gate.EnhancedEventManager;
import com.uplift.system.gate.Events.Event;
import com.uplift.system.gate.Events.EventType;

public class EnhancedLibrarian extends Librarian {
    // Priority queue for knowledge retrieval requests
    private PriorityQueue<KnowledgeRequest> requestQueue;
    // Enhanced event manager for priority-based event handling
    private EnhancedEventManager eventManager;
    
    public EnhancedLibrarian() {
        super();
        this.requestQueue = new PriorityQueue<>(
            Comparator.comparingInt(KnowledgeRequest::getPriority)
        );
        this.eventManager = new EnhancedEventManager();
        this.eventManager.startEventProcessor();
    }
    
    // Request class for knowledge retrieval with priority
    private static class KnowledgeRequest {
        private String requestId;
        private String context;
        private int priority;
        
        public KnowledgeRequest(String requestId, String context, int priority) {
            this.requestId = requestId;
            this.context = context;
            this.priority = priority;
        }
        
        public String getRequestId() { return requestId; }
        public String getContext() { return context; }
        public int getPriority() { return priority; }
    }
    
    /**
     * Queue a knowledge request with priority
     */
    public void queueKnowledgeRequest(String context, int priority) {
        String requestId = "request_" + System.currentTimeMillis();
        KnowledgeRequest request = new KnowledgeRequest(requestId, context, priority);
        requestQueue.add(request);
        
        // Also log the request as an event
        Map<String, Object> payload = new HashMap<>();
        payload.put("context", context);
        payload.put("priority", priority);
        
        Event event = eventManager.createPriorityEvent(
            EventType.SYSTEM_NOTIFICATION,
            "Librarian",
            "RequestQueue",
            priority
        );
        event.setPayload(payload);
        eventManager.queueEvent(event);
    }
    
    /**
     * Process the highest priority knowledge request
     */
    public String processNextKnowledgeRequest() {
        KnowledgeRequest request = requestQueue.poll();
        if (request == null) {
            return "No pending requests";
        }
        
        // Use the existing tellStory method with the context
        return tellStory(request.getContext());
    }
    
    /**
     * Create a hierarchical knowledge structure
     */
    public void createKnowledgeHierarchy(String parentKey, Map<String, Object> childData) {
        // Create a child set
        DoublyLinkedSet<String, Object> childSet = new DoublyLinkedSet<>();
        
        // Populate the child set
        for (Map.Entry<String, Object> entry : childData.entrySet()) {
            childSet.add(entry.getKey(), entry.getValue());
        }
        
        // Attach the child set to the parent
        linkedRegistry.createNestedSet(parentKey, childSet);
        
        // Log the hierarchy creation
        Map<String, Object> payload = new HashMap<>();
        payload.put("parentKey", parentKey);
        payload.put("childCount", childData.size());
        
        Event event = eventManager.createPriorityEvent(
            EventType.SYSTEM_NOTIFICATION,
            "Librarian",
            "KnowledgeHierarchy",
            5
        );
        event.setPayload(payload);
        eventManager.queueEvent(event);
    }
    
    /**
     * Traverse and describe the knowledge hierarchy
     */
    public String describeKnowledgeHierarchy() {
        StringBuilder description = new StringBuilder("Knowledge Hierarchy:\n");
        final int[] level = {0}; // Counter for tracking hierarchy level
        
        linkedRegistry.traverseHierarchy(node -> {
            // Indent based on level
            description.append("  ".repeat(level[0]))
                      .append("- ")
                      .append(node.getKey())
                      .append(": ");
            
            if (node.getValue() instanceof Map) {
                description.append("(Contains nested knowledge)\n");
                level[0]++; // Increase level for children
            } else {
                description.append(summarizeValue(node.getValue())).append("\n");
            }
        });
        
        return description.toString();
    }
}