import java.util.ArrayList;
/**
 * Utility methods for building a binary Max Heap and heap operations.
 *
 * @author Emily Zhou
 * @version 01-08-2019
 * @sicne 12-16-2018
 */
public class HeapUtils
{
    private static boolean debug = false;
    /**
     * Build a Max Heap with a specified complete binary tree.
     * 
     * Algorithm:
     * - find the right most non-leaf node using the formula
     *   when a complete binary tree is represented by an array
     *   that stores node values using level order traversal
     * - call heapify() on the right most non-leaf node
     * - do the same by finding the second right most non-leaf node;
     *   then the third right most non-leaf node; so-on until
     *   reaches the root node of binary tree and do the same
     *   for the root node
     *   
     * Big O Analysis:
     * - The performance is determined by the number of calls
     *   to heapify and heapify performance
     * - The number calls to heapify is ~ non-leaf nodes
     *   which is ~ number of nodes in the heap - heapSize
     * - The heapify performance is O(subtree height)
     * - Hence the total performance is ~
     *   sum of heights of ~ number of nodes in the heap
     *   and according to a known theorem that sum is ~
     *   the number of nodes
     * - Hence the performancen is O(heapSize)
     * 
     * @precondition: the values in the nodes of heap is arranged
     *                arbitrary way and stored in a level order
     *                tranversal array starting at index 1
     * @postcondition: the heap contains a complete binary tree
     *                 satisfies the Max Heap property
     * @param heap a complete binary tree
     * @param heapSize the number of nodes in the heap
     */
    public static void buildHeap(Comparable[] heap, int heapSize)
    {
        if (heapSize <= 1)
        {
            return;
        }
        //keep track non-leaf nodes that have been heapified
        ArrayList < Integer > heapified = new ArrayList < Integer > ();

        int index = heapSize;
        int indexParent = index / 2;
        int indexRoot = 1;
        boolean stop = false;
        while (!stop) 
        {
            if (indexParent == indexRoot)
            {
                stop = true;
            }
            if (!heapified.contains(indexParent))
            {
                heapify(heap, indexParent, heapSize);
                heapified.add(indexParent);
            }
            if (!stop) 
            {
                index--;
                indexParent = index / 2;
            }
        }
    }

    /**
     * Rearrange nodes in a substree so that it satisfies the
     * Max Heap property such that value in a parent node is greater
     * than or equal to any of the values in its two children nodes.
     * 
     * Algorithm:
     * - compare the value at the specified index - the root of the
     *   substree to be heapified - with values of its 2 children
     * - if any of its children's value is greater than the value
     *   at index, swap that child's value with the value at index
     * - after the swap, recursively call heapify with the index
     *   of the swapped child
     * 
     * Big O Analysis:
     * - The worst case is when swap happens and if happens
     *   at each recursion call as well.
     * - The maximum recursion calls at worst case is
     *    ~ height of the substree rooted at the index
     * - The method itself (excluding the recursion call)
     *   takes O(1) time
     * Therefore the performance of this method is
     *   O(height of the substree rooted at index)
     *   
     * @param heap array that contains the heap data with
     *        level order tranversal starting at index 1
     * @param index the index in the heap for the root
     *        of the substree to be heapified
     * @param heapSize the size of the heap (excluding index 0)
     */
    public static void heapify(Comparable[] heap, int index, int heapSize)
    {
        int indexChildL = 2 * index;
        int indexChildR = 2 * index + 1;
        Comparable largest = heap[index];
        int largestIndex = index;
        if (indexChildL <= heapSize) 
        {
            if (heap[indexChildL].compareTo(largest) > 0) 
            {
                largest = heap[indexChildL];
                largestIndex = indexChildL;
            }
        }
        if (indexChildR <= heapSize)
        {
            if (heap[indexChildR].compareTo(largest) > 0)
            {
                largest = heap[indexChildR];
                largestIndex = indexChildR;
            }
        }
        if (largestIndex != index) {
            swap(heap, index, largestIndex);
            heapify(heap, largestIndex, heapSize);
        }
    }

    /**
     * Swap the values at two indices in an array of heap data.
     * 
     * @precondition: index1 and index2 are within the array range
     * 
     * @param heap the array of heap data
     * @param index1 first index
     * @param index2 second index
     */
    private static void swap(Comparable[] heap, int index1, int index2)
    {
        Comparable tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }

    /**
     * Insert an item to a Max Heap and return the new heap.
     * 
     * @precondition: a heap meets Max Heap property  stored
     *                in a level order tranversal array
     *                starting at index 1
     * @postcondition: a new heap meets Max Heap property with
     *                 heap size of original heap size + 1
     *     
     * Algorithem:
     * - allocate an new array with original heap size + 1
     * - copy items from original heap to the new array
     * - add the new item at original heap size + 1 position
     * - build heap for the new array
     * - return the new array
     * 
     * Big O Analysis:
     * - Space: allocate array which is O(heapSize)
     * - Although build heap performance is O(heapSize), since
     *   the original heap is already a binary heap that meets
     *   the Max Heap property, the worst case cost is 
     *   ~ actual heapify operations
     *   to have the added item rise to top which is 
     *   ~ height of heap => log(heapSize)
     * Therefore without counting array copy, the performance is
     * O(log(heapSize))
     * 
     * @param heap the heap
     * @param item the item to add
     * @param heapSize the size of the heap
     * @return the new heap
     */
    public static Comparable[] insert (Comparable[] heap, 
    Comparable item, int heapSize)
    {
        Comparable[] newHeap = new Comparable[heap.length + 1];
        for (int i = 0; i < heap.length; i++)
        {
            newHeap[i] = heap[i];
        }
        newHeap[heapSize + 1] = item;
        buildHeap(newHeap, heapSize + 1);
        return newHeap;
    }

    /**
     * Remove and return the root element in a Max Heap.
     * 
     * @precondition: a heap meets the Max Heap property stored
     *                in a level order tranversal array
     *                starting at index 1
     * @postcondition: the new heap meets the Max Heap property
     *                with heap size of original heap size - 1
     * 
     * Algorithem:
     * - remove swap the last item in the heap with root
     * - heapify the heap with heap size of original heap size - 1
     * - return the root item
     * 
     * Big O analysis:
     * - The swap root with last item takes constant time O(1)
     * - Although build heap performance is O(heapSize), since
     *   the original heap is already a binary heap that meets
     *   the Max Heap property, the worst case cost is 
     *   ~ actual "heapify" operations
     *   to have the new root move down to bottom 
     *   ~ height of heap => log(heapSize)
     * Therefore the performance is O(log(heapSize))
     * 
     * @param heap the heap
     * @param heapSize number of nodes in the heap
     */
    public static Comparable remove(Comparable[] heap, int heapSize)
    {
        Comparable root = heap[1];
        heap[1] = heap[heapSize];
        buildHeap(heap, heapSize - 1);
        return root;
    }

    /**
     * Sort array of data using Heap.
     * 
     * @precondition: the values in the input heap array is arranged
     *                arbitrary way and stored in a level order
     *                tranversal array starting at index 1
     * @postcondition: heap contains the sorted array starting at index 1
     * 
     * Algorithm:
     * - remove the root item from the binary heap
     *   by calling the remove() method
     * - repeat the above until no more items in the heap
     * 
     * Big O Analysis:
     * - Space: allocate the sorted array is 
     *          O(heapSize) + heapify swap O(1) => O(heapSize)
     * - There are total ~ O(heapSize) calls to remove()
     * - Each remove() call is O(log(heapSize))
     * - Copy sorted array back to original heap is O(heapSize)
     * - Therefore performance is O((heapSize)log(heapSize))
     * 
     * @param heap a binary heap stored in array
     *             of level order tranversal starting at index 1
     * @param heapSize the number of items in the heap
     */
    public static void heapSort(Comparable[] heap, int heapSize)
    {
        buildHeap(heap, heapSize);
        Comparable[] sorted = new Comparable[heapSize];
        int size = heapSize;
        int i = 0;
        while (size > 0) 
        {
            if (debug)
            {
                printHeap(heap, size);
            }
            sorted[i] = remove(heap, size);
            size = size - 1;
            i++;
        }
        for (int j = heapSize - 1; j >= 0; j--)
        {
            int indexToHeap = heapSize - j;
            heap[indexToHeap] = sorted[j];
        }
    }

    /**
     * Print the heap array.
     * 
     * @param heap a heap array start at index 1
     * @param heapSize number of heap elements
     */
    private static void printHeap(Comparable[] heap, int heapSize)
    {
        System.out.print("Heap(size=" + heapSize + "): [ ");
        for (int i = 1; i <= heapSize; i++)
        {
            System.out.print(heap[i] + " ");
        }
        System.out.println("]");
    }
}
