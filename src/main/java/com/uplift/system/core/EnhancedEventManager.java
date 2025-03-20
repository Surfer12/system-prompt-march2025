package com.uplift.system.core;
public class EnhancedEventManager extends EventManager {
    // Priority queue for event processing based on importance/urgency
    private PriorityQueue<Event> priorityEventQueue;
    
    // Background thread for processing events
    private Thread eventProcessorThread;
    private volatile boolean running = false;
    
    public EnhancedEventManager() {
        super();
        // Create a priority queue with a custom comparator that sorts by event priority
        this.priorityEventQueue = new PriorityQueue<>(
            Comparator.comparingInt((Event e) -> getPriority(e))
        );
    }
    
    /**
     * Determine the priority of an event (lower values = higher priority)
     */
    private int getPriority(Event event) {
        // Check if explicit priority is set in metadata
        if (event.getMetadata().containsKey("priority")) {
            return (Integer) event.getMetadata().get("priority");
        }
        
        // Default priorities based on event type
        switch(event.getType()) {
            case ERROR: return 0; // Highest priority
            case SYSTEM_NOTIFICATION: return 5;
            case TECHNOLOGY_CONNECTION: return 10;
            case DATA_TRANSFER: return 15;
            default: return 100; // Lowest priority
        }
    }
    
    /**
     * Queue an event with priority for processing
     */
    public void queueEvent(Event event) {
        priorityEventQueue.add(event);
    }
    
    /**
     * Start the background event processor
     */
    public void startEventProcessor() {
        if (running) return;
        
        running = true;
        eventProcessorThread = new Thread(() -> {
            while (running) {
                processNextEvent();
                
                // Small delay to prevent CPU thrashing
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        
        eventProcessorThread.setDaemon(true);
        eventProcessorThread.start();
    }
    
    /**
     * Process the highest priority event in the queue
     */
    private void processNextEvent() {
        Event event = priorityEventQueue.poll();
        if (event != null) {
            // Use the existing event dispatch mechanism
            dispatchEvent(event);
        }
    }
    
    /**
     * Stop the event processor
     */
    public void stopEventProcessor() {
        running = false;
        if (eventProcessorThread != null) {
            eventProcessorThread.interrupt();
        }
    }
    
    /**
     * Create an event with specific priority
     */
    public Event createPriorityEvent(EventType type, String source, String destination, int priority) {
        Event event = new Event(type, source, destination);
        event.getMetadata().put("priority", priority);
        return event;
    }
}