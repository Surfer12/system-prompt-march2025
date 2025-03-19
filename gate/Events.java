package gate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Gate Events: Event Management and Handling
 *
 * This module manages events and interactions within the technological gate,
 * providing a flexible event-driven architecture.
 */
public class Events {
    /**
     * Enumeration of possible event types within the gate.
     */
    public enum EventType {
        TECHNOLOGY_CONNECTION,
        DATA_TRANSFER,
        SYSTEM_NOTIFICATION,
        ERROR
    }

    /**
     * Represents an event within the system.
     */
    public static class Event {
        private EventType type;
        private LocalDateTime timestamp;
        private String source;
        private String destination;
        private Map<String, Object> payload;
        private Map<String, Object> metadata;

        public Event(EventType type) {
            this(type, "", "");
        }

        public Event(EventType type, String source, String destination) {
            this.type = type;
            this.timestamp = LocalDateTime.now();
            this.source = source;
            this.destination = destination;
            this.payload = new HashMap<>();
            this.metadata = new HashMap<>();
        }

        // Getters and setters
        public EventType getType() { return type; }
        public void setType(EventType type) { this.type = type; }

        public LocalDateTime getTimestamp() { return timestamp; }

        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }

        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }

        public Map<String, Object> getPayload() { return payload; }
        public void setPayload(Map<String, Object> payload) { this.payload = payload; }

        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    }

    /**
     * Manages event registration, dispatching, and handling.
     */
    public static class EventManager {
        private Map<EventType, List<Consumer<Event>>> eventHandlers;

        public EventManager() {
            this.eventHandlers = new HashMap<>();
        }

        /**
         * Register an event handler for a specific event type.
         *
         * @param eventType Type of event to handle
         * @param handler Function to handle the event
         */
        public void registerHandler(EventType eventType, Consumer<Event> handler) {
            eventHandlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
        }

        /**
         * Dispatch an event to all registered handlers.
         *
         * @param event Event to dispatch
         */
        public void dispatchEvent(Event event) {
            List<Consumer<Event>> handlers = eventHandlers.get(event.getType());
            if (handlers != null) {
                for (Consumer<Event> handler : handlers) {
                    handler.accept(event);
                }
            }
        }

        /**
         * Create a technology connection event.
         *
         * @param source Source technology
         * @param destination Destination technology
         * @param payload Additional event data
         * @return Created technology connection event
         */
        public Event createTechnologyConnectionEvent(String source, String destination, Map<String, Object> payload) {
            Event event = new Event(EventType.TECHNOLOGY_CONNECTION, source, destination);
            if (payload != null) {
                event.setPayload(payload);
            }
            return event;
        }

        /**
         * Create a data transfer event.
         *
         * @param source Source of the data
         * @param destination Destination for the data
         * @param data Data being transferred
         * @return Created data transfer event
         */
        public Event createDataTransferEvent(String source, String destination, Object data) {
            Event event = new Event(EventType.DATA_TRANSFER, source, destination);
            Map<String, Object> payload = new HashMap<>();
            payload.put("data", data);
            event.setPayload(payload);
            return event;
        }
    }

    /**
     * Create a default EventManager with some basic logging handlers.
     *
     * @return Configured event manager with logging handlers
     */
    public static EventManager createDefaultEventManager() {
        EventManager eventManager = new EventManager();

        // Log technology connection events
        eventManager.registerHandler(EventType.TECHNOLOGY_CONNECTION, event -> 
            System.out.println("Technology Connection: " + event.getSource() + " → " + event.getDestination())
        );

        // Log data transfer events
        eventManager.registerHandler(EventType.DATA_TRANSFER, event -> 
            System.out.println("Data Transfer: " + event.getSource() + " → " + event.getDestination())
        );

        return eventManager;
    }
} 