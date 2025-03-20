// Add support for hierarchical nesting in DoublyLinkedSet
public class DoublyLinkedSet<K, V> implements Iterable<DoublyLinkedSet.Node<K, V>> {
    // Existing implementation...
    
    /**
     * Creates a nested set structure by associating a child set with a key
     * @param parentKey The parent key
     * @param childSet The child DoublyLinkedSet to nest
     * @return true if successful
     */
    public boolean createNestedSet(K parentKey, DoublyLinkedSet<K, V> childSet) {
        Node<K, V> parentNode = nodeMap.get(parentKey);
        if (parentNode == null) {
            return false;
        }
        
        @SuppressWarnings("unchecked")
        Map<String, DoublyLinkedSet<K, V>> nestedSets = 
            (Map<String, DoublyLinkedSet<K, V>>) parentNode.getValue();
            
        if (!(parentNode.getValue() instanceof Map)) {
            // Convert to a map if it's not already
            nestedSets = new HashMap<>();
            parentNode.setValue((V) nestedSets);
        }
        
        // Add the child set to the parent's value map
        String childKey = "nested_" + System.currentTimeMillis();
        nestedSets.put(childKey, childSet);
        return true;
    }
    
    /**
     * Traverse the set in a hierarchical manner, applying a consumer to each node
     * @param consumer The operation to apply to each node
     */
    public void traverseHierarchy(Consumer<Node<K, V>> consumer) {
        traverse(head, consumer, 0);
    }
    
    private void traverse(Node<K, V> node, Consumer<Node<K, V>> consumer, int depth) {
        if (node == null) return;
        
        // Process the current node
        consumer.accept(node);
        
        // If node contains nested sets, traverse them
        if (node.getValue() instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, DoublyLinkedSet<K, V>> nestedSets = 
                (Map<String, DoublyLinkedSet<K, V>>) node.getValue();
                
            for (DoublyLinkedSet<K, V> childSet : nestedSets.values()) {
                // Recursively traverse the child set
                childSet.traverseHierarchy(consumer);
            }
        }
        
        // Continue with next node at current level
        traverse(node.next, consumer, depth);
    }
}