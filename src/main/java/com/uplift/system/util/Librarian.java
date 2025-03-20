package com.uplift.system.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Librarian: Central knowledge repository with bidirectional traversal capabilities
 * Implements concepts from the linked data structures approach
 */
public class Librarian {
    private Map<String, Object> registry;
    protected DoublyLinkedSet<String, Object> linkedRegistry;

    /**
     * Constructs a new Librarian with initialized registry and linked data structures
     */
    public Librarian() {
        this.registry = new LinkedHashMap<>(); // Using LinkedHashMap to maintain insertion order
        this.linkedRegistry = new DoublyLinkedSet<>();
        // Initialize with some default values
        initializeDefaultRegistry();
    }

    /**
     * Initialize the registry with default values and populate the linked structure
     */
    private void initializeDefaultRegistry() {
        // Example initialization with components and principles
        Map<String, Object> components = new LinkedHashMap<>();
        Map<String, Object> principles = new LinkedHashMap<>();
        
        registry.put("components", components);
        registry.put("creative_principles", principles);
        
        // Populate the linked registry with initial mappings
        for (Map.Entry<String, Object> entry : registry.entrySet()) {
            linkedRegistry.add(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Tell a story without specific context
     * @return A narrative description
     */
    public String tellStory() {
        return tellStory(null);
    }

    /**
     * Tell a story with the given context, traversing the linked registry as needed
     * @param context The specific context for the story
     * @return A narrative description
     */
    public String tellStory(String context) {
        StringBuilder story = new StringBuilder("A story of technological interoperability");
        
        if (context != null) {
            story.append(" focused on: ").append(context);
            // Use the linkedRegistry to find relevant information
            Object contextData = linkedRegistry.findByKey(context);
            if (contextData != null) {
                story.append("\n\nDetails: ").append(contextData.toString());
            }
        } else {
            // When no context is provided, traverse the registry to provide an overview
            story.append("\n\nRegistry Overview:");
            Iterator<DoublyLinkedSet.Node<String, Object>> iterator = linkedRegistry.iterator();
            while (iterator.hasNext()) {
                DoublyLinkedSet.Node<String, Object> node = iterator.next();
                story.append("\n- ").append(node.getKey()).append(": ")
                     .append(summarizeValue(node.getValue()));
            }
        }
        
        return story.toString();
    }

    /**
     * Provides a summary of the value, handling different types appropriately
     * @param value The object to summarize
     * @return A string summary
     */
    protected String summarizeValue(Object value) {
        if (value instanceof Map) {
            return "Contains " + ((Map<?, ?>) value).size() + " entries";
        } else if (value instanceof List) {
            return "Contains " + ((List<?>) value).size() + " items";
        }
        return value.toString();
    }

    /**
     * Get a safe copy of the registry
     * @return A copy of the registry map
     */
    public Map<String, Object> getRegistry() {
        return new HashMap<>(registry);
    }
    
    /**
     * Update or add an entry in both the registry and linked registry
     * @param key The key to update
     * @param value The new value
     */
    public void updateRegistry(String key, Object value) {
        registry.put(key, value);
        // Update or add to the linked registry
        linkedRegistry.remove(key); // Remove if exists
        linkedRegistry.add(key, value); // Add with new value
    }

    /**
     * Implementation of a doubly linked set that enables bidirectional traversal
     * and efficient key-value operations
     */
    public static class DoublyLinkedSet<K, V> implements Iterable<DoublyLinkedSet.Node<K, V>> {
        private Node<K, V> head;
        private Node<K, V> tail;
        private Map<K, Node<K, V>> nodeMap; // For O(1) key lookup
        private int size;
        
        /**
         * Constructs an empty DoublyLinkedSet
         */
        public DoublyLinkedSet() {
            head = null;
            tail = null;
            nodeMap = new HashMap<>();
            size = 0;
        }
        
        /**
         * Node class representing an element in the doubly linked set
         */
        public static class Node<K, V> {
            private K key;
            private V value;
            private Node<K, V> prev;
            private Node<K, V> next;
            private DoublyLinkedSet<K, V> nestedSet; // For hierarchical organization
            
            public Node(K key, V value) {
                this.key = key;
                this.value = value;
                this.prev = null;
                this.next = null;
                this.nestedSet = null;
            }
            
            public K getKey() { return key; }
            public V getValue() { return value; }
            public void setValue(V value) { this.value = value; }
            public Node<K, V> getPrev() { return prev; }
            public Node<K, V> getNext() { return next; }
            public DoublyLinkedSet<K, V> getNestedSet() { return nestedSet; }
            public void setNestedSet(DoublyLinkedSet<K, V> nestedSet) { this.nestedSet = nestedSet; }
            public boolean hasNestedSet() { return nestedSet != null; }
        }
        
        /**
         * Add a key-value pair to the set
         * @param key The key
         * @param value The value
         * @return true if added successfully
         */
        public boolean add(K key, V value) {
            if (nodeMap.containsKey(key)) {
                return false; // Already exists
            }
            
            Node<K, V> newNode = new Node<>(key, value);
            
            if (head == null) {
                // First node
                head = newNode;
                tail = newNode;
            } else {
                // Add to the end
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            
            nodeMap.put(key, newNode);
            size++;
            return true;
        }
        
        /**
         * Remove a node by key
         * @param key The key to remove
         * @return true if removed successfully
         */
        public boolean remove(K key) {
            Node<K, V> node = nodeMap.get(key);
            if (node == null) {
                return false; // Not found
            }
            
            // Update links
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                head = node.next; // Removing head
            }
            
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                tail = node.prev; // Removing tail
            }
            
            nodeMap.remove(key);
            size--;
            return true;
        }
        
        /**
         * Find a value by its key
         * @param key The key to look up
         * @return The value or null if not found
         */
        public V findByKey(K key) {
            Node<K, V> node = nodeMap.get(key);
            return node != null ? node.value : null;
        }
        
        /**
         * Get the size of the set
         * @return Number of elements
         */
        public int size() {
            return size;
        }
        
        /**
         * Returns a bidirectional iterator for the set
         */
        @Override
        public Iterator<Node<K, V>> iterator() {
            return new Iterator<Node<K, V>>() {
                private Node<K, V> current = head;
                
                @Override
                public boolean hasNext() {
                    return current != null;
                }
                
                @Override
                public Node<K, V> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    Node<K, V> node = current;
                    current = current.next;
                    return node;
                }
            };
        }
        
        /**
         * Returns a reverse iterator for the set
         * @return An iterator that traverses from tail to head
         */
        public Iterator<Node<K, V>> reverseIterator() {
            return new Iterator<Node<K, V>>() {
                private Node<K, V> current = tail;
                
                @Override
                public boolean hasNext() {
                    return current != null;
                }
                
                @Override
                public Node<K, V> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    Node<K, V> node = current;
                    current = current.prev;
                    return node;
                }
            };
        }
        
        /**
         * Create a nested set for hierarchical organization
         * 
         * @param key The key of the parent node
         * @param nestedSet The nested set to attach
         * @return true if successful, false if the key doesn't exist
         */
        public boolean createNestedSet(K key, DoublyLinkedSet<K, V> nestedSet) {
            Node<K, V> node = nodeMap.get(key);
            if (node == null) {
                return false;
            }
            
            node.setNestedSet(nestedSet);
            return true;
        }
        
        /**
         * Traverse the hierarchy depth-first and apply the consumer to each node
         * 
         * @param consumer The consumer to apply to each node
         */
        public void traverseHierarchy(java.util.function.Consumer<Node<K, V>> consumer) {
            traverseHierarchyHelper(head, consumer);
        }
        
        /**
         * Helper method for hierarchical traversal
         * 
         * @param startNode The node to start traversal from
         * @param consumer The consumer to apply to each node
         */
        private void traverseHierarchyHelper(Node<K, V> startNode, java.util.function.Consumer<Node<K, V>> consumer) {
            if (startNode == null) {
                return;
            }
            
            Node<K, V> current = startNode;
            while (current != null) {
                consumer.accept(current);
                
                // If there's a nested set, traverse it
                if (current.hasNestedSet()) {
                    current.getNestedSet().traverseHierarchy(consumer);
                }
                
                current = current.next;
            }
        }
    }
}