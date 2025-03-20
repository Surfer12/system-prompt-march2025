# Comprehensive Guide to Heaps in Java

## Introduction

Heaps are a type of binary tree-based data structure that satisfy the heap property: in a max-heap, 
every parent node is greater than or equal to its child nodes; in a min-heap, every parent node is 
less than or equal to its child nodes. This guide provides an overview of heaps in Java, focusing 
on their implementation using `PriorityQueue`, and practical use cases.

## Understanding Heaps

### Properties of Heaps
- **Complete Binary Tree:** A heap is a complete binary tree, meaning all levels are fully filled 
except possibly the last level, which is filled from left to right.
- **Heap Property:**
  - **Max-Heap:** The value of each node is greater than or equal to the values of its children.
  - **Min-Heap:** The value of each node is less than or equal to the values of its children.

### Types of Heaps
- **Max-Heap:** Used when you need quick access to the largest element.
- **Min-Heap:** Useful for tasks requiring quick access to the smallest element, such as 
scheduling.

## Implementation in Java

Java provides a built-in `PriorityQueue` class that implements the heap data structure. Here’s how 
it can be used:

### Basic Usage
```java
import java.util.PriorityQueue;

public class HeapExample {
    public static void main(String[] args) {
        // Creating a min-heap using default constructor
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Adding elements to the heap
        minHeap.add(10);
        minHeap.add(5);
        minHeap.add(20);

        // Accessing the minimum element (root of the heap)
        System.out.println("Min: " + minHeap.peek());

        // Removing and returning the minimum element
        while (!minHeap.isEmpty()) {
            System.out.println(minHeap.poll());
        }
    }
}
```

### Custom Comparator for Max-Heap
To implement a max-heap, you can use a custom comparator:

```java
import java.util.Comparator;
import java.util.PriorityQueue;

public class MaxHeapExample {
    public static void main(String[] args) {
        // Creating a max-heap using a custom comparator
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        // Adding elements to the heap
        maxHeap.add(10);
        maxHeap.add(5);
        maxHeap.add(20);

        // Accessing the maximum element (root of the heap)
        System.out.println("Max: " + maxHeap.peek());

        // Removing and returning the maximum element
        while (!maxHeap.isEmpty()) {
            System.out.println(maxHeap.poll());
        }
    }
}
```

## Practical Use Cases

1.  **Priority Queues:** Heaps are the fundamental data structure for implementing priority queues. In a priority queue, each element has a "priority," and elements with higher priority are served (or processed) before elements with lower priority. Min-heaps are typically used to implement priority queues where the element with the smallest value has the highest priority (e.g., representing tasks with the earliest deadlines). Conversely, max-heaps can be used where the largest value has the highest priority. The heap structure ensures that the element with the highest priority is always readily available (at the root), allowing for efficient retrieval of the next element to process.

2.  **Dijkstra’s Algorithm:** Dijkstra's algorithm is a classic algorithm for finding the shortest paths between nodes in a graph. Heaps are crucial for the algorithm's efficiency. During the algorithm's execution, a priority queue (often implemented using a min-heap) stores nodes along with their tentative distances from the starting node. The algorithm repeatedly extracts the node with the smallest tentative distance from the heap, explores its neighbors, and updates their tentative distances if a shorter path is found. The heap ensures that the node with the smallest tentative distance is always selected next, leading to an efficient exploration of the graph and ultimately finding the shortest paths.

3.  **Heap Sort:** Heap sort is an in-place comparison-based sorting algorithm that leverages the heap data structure. It works in two main phases: first, it builds a heap (either a max-heap or a min-heap) from the input array. Then, it repeatedly extracts the root element (which is the largest or smallest element, depending on whether it's a max-heap or min-heap) and places it at the end of the sorted portion of the array. This process continues until the entire array is sorted. Heap sort has a time complexity of O(n log n) for all cases (best, average, and worst), making it a relatively efficient sorting algorithm, especially when in-place sorting is required.

4.  **Kth Largest/Smallest Elements:** Heaps provide an efficient way to find the k-th largest or smallest element in an unsorted array. For finding the k-th largest element, a min-heap of size k is maintained. Iterate through the array; if an element is larger than the smallest element in the heap (the root), replace the root with the current element and heapify. After processing all elements, the root of the min-heap will be the k-th largest element. Similarly, for finding the k-th smallest element, a max-heap of size k is used. This approach avoids sorting the entire array, resulting in a time complexity of O(n log k), which is more efficient than sorting the entire array (O(n log n)) when k is much smaller than n.

## Advantages of Using Heaps

-   **Efficiency:** Heaps offer efficient time complexity for several key operations. Insertion and deletion of elements typically take O(log n) time, where n is the number of elements in the heap. Accessing the minimum or maximum element (the root) takes O(1) time because it's always readily available. These logarithmic time complexities make heaps suitable for applications where frequent insertions, deletions, and retrieval of the extreme elements are required.

-   **Dynamic Data Handling:** Heaps excel at handling dynamic data, meaning data that changes frequently. Elements can be inserted or deleted from a heap efficiently without requiring a complete re-sort of the data. This makes heaps well-suited for scenarios where data is constantly being updated, such as in priority queues or real-time systems. The ability to maintain the heap property (the order of elements) after each insertion or deletion ensures that the extreme elements are always readily accessible.

-   **Memory Efficiency:** Heap sort, which utilizes a heap data structure, is an in-place sorting algorithm. This means that it sorts the array directly within its original memory space without requiring significant extra memory allocation. The space complexity of heapsort is O(1), indicating constant extra space usage, making it memory-efficient, especially when dealing with large datasets where memory constraints are a concern.

## Conclusion

Heaps provide a robust and efficient approach to managing ordered data. In Java, the `PriorityQueue` class offers a convenient and readily available implementation of both min-heaps and max-heaps, leveraging custom comparators to define the ordering. Understanding the principles of heap data structures and their practical applications can significantly enhance the performance and efficiency of applications that require priority-based data handling, dynamic data management, or efficient sorting algorithms.

This guide has comprehensively explored the core concepts of heap operations in Java, including practical examples and diverse use cases. It aims to equip you with the knowledge and practical skills necessary to effectively implement and leverage this versatile data structure in your software development projects. The provided examples and explanations should serve as a solid foundation for incorporating heaps into your code, optimizing performance, and tackling complex data management challenges.

# Generic Heap Sort Algorithm

Heap sort is a highly efficient comparison-based sorting algorithm that leverages the properties of a binary heap data structure. It achieves its efficiency by strategically organizing the input data into a heap, which allows for the rapid identification and extraction of the largest (or smallest) elements. The algorithm then iteratively extracts these extreme elements to construct a sorted array. The core idea is to transform the unsorted input into a heap (either a max-heap or a min-heap) and then repeatedly extract the root element, placing it in its correct sorted position.

The following Java code provides a generic implementation of the heap sort algorithm:

```java
public class HeapSort {

    /**
     * Performs the heapify operation on a subtree rooted at the given index.
     * This method ensures that the subtree satisfies the heap property (max-heap in this case).
     *
     * @param arr The array representing the heap.
     * @param n   The size of the heap (or the portion of the array being considered).
     * @param i   The index of the root of the subtree to heapify.
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int leftChildIdx = 2 * i + 1; // Index of the left child
        int rightChildIdx = 2 * i + 2; // Index of the right child

        // If the left child is larger than the current largest
        if (leftChildIdx < n && arr[leftChildIdx] > arr[largest]) {
            largest = leftChildIdx;
        }

        // If the right child is larger than the current largest (or the left child)
        if (rightChildIdx < n && arr[rightChildIdx] > arr[largest]) {
            largest = rightChildIdx;
        }

        // If the largest element is not the root, swap and recursively heapify
        if (largest != i) {
            // Swap arr[i] and arr[largest]
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected subtree
            heapify(arr, n, largest);
        }
    }

    /**
     * Sorts an array using the heap sort algorithm.
     *
     * @param arr The array to be sorted.
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Build a max-heap from the input array.  This is the "heap construction" phase.
        // We start from the last non-leaf node (n/2 - 1) and work upwards.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract an element from the heap (the largest element)
        // and place it at the end of the sorted portion of the array.
        for (int i = n - 1; i >= 0; i--) {
            // Move the current root (largest element) to the end of the array
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Call heapify on the reduced heap (excluding the last element, which is now sorted)
            heapify(arr, i, 0); // i represents the current size of the heap
        }
    }

    /**
     * Utility function to print an array.  Useful for demonstrating the algorithm.
     *
     * @param arr The array to print.
     */
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    /**
     * Driver code to test the heap sort implementation.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};

        System.out.println("Original array:");
        printArray(arr);

        heapSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);
    }
}
```

## Detailed Explanation of the Algorithm

1.  **The `heapify` Function: Maintaining the Heap Property**

    *   The `heapify` function is the cornerstone of the heap sort algorithm. Its primary responsibility is to maintain the heap property within a binary tree (represented as an array). The heap property dictates the ordering of elements within the heap. In a max-heap (as used here), the value of each node must be greater than or equal to the values of its children.
    *   **How it Works:** Given an index `i` representing the root of a subtree, `heapify` ensures that this subtree adheres to the max-heap property. It does this by:
        *   Comparing the root node (`arr[i]`) with its left child (`arr[2 * i + 1]`) and right child (`arr[2 * i + 2]`).
        *   Identifying the largest element among the root and its children.
        *   If a child is larger than the root, swapping the root with the larger child.
        *   Recursively calling `heapify` on the subtree rooted at the swapped child's index. This recursive step is crucial because swapping the root with a child might violate the heap property in the child's subtree, and this needs to be corrected.
    *   **Purpose:** The `heapify` function is used both to build the initial heap and to maintain the heap property after elements are removed or swapped during the sorting process.

2.  **Building the Max-Heap: The Heap Construction Phase**

    *   The first phase of the `heapSort` algorithm involves transforming the input array into a max-heap. This is achieved by calling the `heapify` function on all non-leaf nodes of the binary tree representation of the array.
    *   **Non-Leaf Nodes:** In a binary tree, non-leaf nodes are those that have at least one child. In an array representation, the non-leaf nodes are located at indices from `n/2 - 1` down to `0`, where `n` is the size of the array.
    *   **Process:** The algorithm iterates through the non-leaf nodes in reverse order (from the last non-leaf node to the root). For each non-leaf node, it calls `heapify`. This process ensures that the subtree rooted at each non-leaf node satisfies the max-heap property. As `heapify` is called on each node, it propagates the largest element upwards, eventually resulting in the largest element being at the root of the heap.
    *   **Efficiency:** Building the heap in this manner has a time complexity of O(n), which is surprisingly efficient.
. **Heapify Function:**
   - The `heapify` function is responsible for 
   maintaining the heap property. Given an index, it 
ensures that the subtree rooted at this index satisfies 
the max-heap condition.
   - It compares the root with its children and swaps if 
   necessary, then recursively ensures that 
any affected subtrees also satisfy the heap property.
3.  **Extracting Elements and Sorting: The Sorting Phase**

    *   Once the max-heap is built, the sorting phase begins. This phase involves repeatedly extracting the largest element (the root of the heap) and placing it at the end of the sorted portion of the array.
    *   **Steps:**
        1.  **Swap:** Swap the root element (`arr[0]`) with the last element of the heap (`arr[i]`). This places the largest element in its correct sorted position at the end of the array.
        2.  **Reduce Heap Size:** Reduce the size of the heap by one (effectively excluding the last element, which is now sorted).
        3.  **Heapify:** Call `heapify` on the root (`arr[0]`) to restore the max-heap property. This is crucial because swapping the root with the last element might violate the heap property. The `heapify` function ensures that the remaining elements form a valid max-heap.
        4.  **Repeat:** Repeat steps 1-3 until the heap size becomes 1. At this point, the entire array is sorted.
    *   **Efficiency:** Each extraction and re-heapification takes O(log n) time. Since this process is repeated `n` times, the overall time complexity of the sorting phase is O(n log n).

3. **Extracting Elements:**
   - After building the max-heap, the largest element (root) is swapped with the last element in 
the heap.
   - The size of the heap is reduced by one, and `heapify` is called on the new root to restore the 
heap property.
   - This process repeats until all elements are sorted.
. **Time Complexity:**
   - Building the heap takes O(n) time.
   - Each extraction from the heap (and subsequent 
   re-heapification) takes O(log n) time, and there 
are `n` such extractions.
   - Therefore, the overall time complexity is O(n log n)
    *   **Building the Heap:** O(n)
    *   **Extracting Elements and Sorting:** O(n log n)
    *   **Overall Time Complexity:** O(n log n)
    *   **Space Complexity:** Heap sort is an in-place sorting algorithm, meaning it sorts the array directly within its original memory space without requiring significant extra memory allocation. The space complexity is O(1) (constant extra space).
This generic implementation of heap sort works for any 
integer array. The same logic can be adapted 
for other data types by modifying the comparison 
operations and potentially using a custom 
comparator or similar mechanism if needed.
This detailed explanation provides a comprehensive understanding of the heap sort algorithm, its implementation, and its efficiency. The code is designed to be clear, concise, and easy to understand, making it a valuable resource for learning and applying heap sort in various programming scenarios. The comments within the code further clarify the purpose of each step, enhancing its readability and educational value.


Creating a generic priority queue implementation from scratch involves designing both a max-heap 
and min-heap functionality using generics in Java. We'll implement a basic priority queue with 
essential operations: `insert`, `extractMin`/`extractMax`, and `peek`.

Here's a step-by-step guide to implementing a generic priority queue using a binary heap:

```java
import java.util.Comparator;
import java.util.NoSuchElementException;

public class GenericPriorityQueue<T> {
    private T[] heap;
    private int size;
    private Comparator<? super T> comparator;

    @SuppressWarnings("unchecked")
    public GenericPriorityQueue(int capacity, Comparator<? super T> comparator) {
        this.heap = (T[]) new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    // Check if the priority queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Return the number of elements in the priority queue
    public int size() {
        return size;
    }

    // Insert a new element into the priority queue
    public void insert(T value) {
        if (size == heap.length) throw new IllegalStateException("Priority Queue is full");
        heap[size] = value;
        int current = size;
        size++;

        while (current > 0 && compare(heap[current], heap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    // Extract the minimum element from the priority queue
    public T extractMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue is empty");
        T min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapify(0);
        return min;
    }

    // Peek at the minimum element without removing it
    public T peekMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority Queue is empty");
        return heap[0];
    }

    private void heapify(int index) {
        int smallest = index;
        int leftChildIdx = left(index);
        int rightChildIdx = right(index);

        if (leftChildIdx < size && compare(heap[leftChildIdx], heap[smallest]) < 0) {
            smallest = leftChildIdx;
        }

        if (rightChildIdx < size && compare(heap[rightChildIdx], heap[smallest]) < 0) {
            smallest = rightChildIdx;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return 2 * index + 1;
    }

    private int right(int index) {
        return 2 * index + 2;
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int compare(T a, T b) {
        if (comparator == null)
            throw new IllegalStateException("Comparator not provided");
        return comparator.compare(a, b);
    }
}
```

### Key Features

1. **Generics**: The `GenericPriorityQueue` class uses generics (`<T>`) to allow for any object 
type.

2. **Custom Comparator**: A custom comparator is used to determine the ordering of elements. This 
allows flexibility in defining what "priority" means for different types of objects.

3. **Core Operations**:
   - **Insertion**: Places a new element at the end and then bubbles it up to maintain heap order.
   - **Extract-Min**: Removes and returns the smallest element (root) and restores heap property 
via `heapify`.
   - **Peek**: Allows viewing of the minimum element without removing it.

### Using Java Tools

To complement this implementation, we can use some favorite Java tools:

- **JUnit for Testing**:
  JUnit is a popular testing framework in Java that helps ensure our priority queue works 
correctly. Below is an example test class using JUnit:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GenericPriorityQueueTest {

    @Test
    public void testInsertAndExtractMin() {
        Comparator<Integer> comparator = Integer::compareTo;
        GenericPriorityQueue<Integer> pq = new GenericPriorityQueue<>(10, comparator);

        assertThrows(IllegalStateException.class, () -> {
            for (int i = 0; i < 11; i++) { // Exceed capacity
                pq.insert(i);
            }
        });

        pq.insert(5);
        pq.insert(3);
        pq.insert(8);

        assertEquals(3, pq.extractMin());
        assertEquals(5, pq.extractMin());
        assertEquals(8, pq.extractMin());

        assertThrows(NoSuchElementException.class, () -> {
            pq.extractMin(); // Empty queue
        });
    }
}
```

- **Lombok for Boilerplate Code Reduction**:
  Lombok can simplify the creation of data classes by automatically generating getters, setters, 
constructors, and more. While not directly needed here, it's a useful tool in larger projects to 
keep code clean.

This generic priority queue implementation is flexible and adaptable, making use of Java's powerful 
features like generics and custom comparators, while also integrating well with other popular tools 
for testing and code maintenance.