import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 * A program that exercises and tests Heap operations.
 * 
 * The test checks the heap built meets the Heap property,
 * insert and remove item as expected and compares heap
 * sort result with Java Collections.sort().
 *
 * @author Emily Zhou
 * @version 01-08-2019
 * @since 12-16-2018
 */
public class Main
{
    //item value used for insert test
    private static final int INSERT_TEST_VALUE = 55;

    /**
     * Test buildHeap().
     * - first build the heap
     * - check the result heap has the Max Heap property
     * 
     * @param heap the array that contains heap data to build heap
     * @param heapSize the number of elements in heap
     * 
     * @throw RuntimeException if test fail
     */
    private static void testBuildHeap(Comparable[] heap, int heapSize)
    {
        System.out.println("Test buildHeap()");
        HeapUtils.buildHeap(heap, heapSize);
        HeapDisplay display = new HeapDisplay();
        display.displayHeap(heap, heapSize);
        if (checkHeapProperty(heap, heapSize))
        {
            System.out.println("Test buildHeap() PASS");
        }
        else
        {
            System.out.println("Test buildHeap() FAILED");
            throw new RuntimeException("Test buildHeap() failed");
        }
    }

    /**
     * Test insert().
     * - first build the heap
     * - check the result heap has the Max Heap property
     * - insert the item to the heap
     * - check the result heap contains the item
     * - check the result heap has the Max Heap property
     * 
     * @param heap the array that contains heap data to build heap
     * @param heapSize the number of elements in heap
     * 
     * @throw RuntimeException if test fail
     */
    private static void testInsert(
    Comparable[] heap, Comparable item, int heapSize)
    {
        System.out.println("Test insert()");
        HeapUtils.buildHeap(heap, heapSize);
        HeapDisplay display = new HeapDisplay();
        display.displayHeap(heap, heapSize);
        if (!checkHeapProperty(heap, heapSize))
        {
            System.out.println("Test insert at buildHeap() FAILED");
            throw new RuntimeException("Test insert failed");
        }
        Comparable[] result = HeapUtils.insert(heap, item, heapSize);
        HeapDisplay display1 = new HeapDisplay();
        display1.displayHeap(result, heapSize + 1);
        boolean found = false;
        for (int i = 1; i <= (heapSize + 1); i++)
        {
            if (result[i] == item)
            {
                found = true;
            }
        }
        if (!found)
        {
            System.out.println("Test insert() FAILED: inserted item " +
                item + " not found in heap");
            throw new RuntimeException("Test insert() failed");   
        }
        if (!checkHeapProperty(result, heapSize + 1))
        {
            System.out.println("Test insert() FAILED");
            throw new RuntimeException("Test insert() failed");

        }
        System.out.println("Test insert() PASS");
    }

    /**
     * Test remove().
     * - first build the heap
     * - remove 1 item from the heap
     * - check the removed item has expected value
     * - check the remaining heap still has the Max Heap property
     * 
     * @param heap the array that contains heap data
     * @param heapSize the number of elements in heap
     * 
     * @throw RuntimeException if test fail
     */
    private static void testRemove(Comparable[] heap, int heapSize)
    {
        System.out.println("Test remove()");
        HeapUtils.buildHeap(heap, heapSize);
        HeapDisplay display = new HeapDisplay();
        display.displayHeap(heap, heapSize);
        if (!checkHeapProperty(heap, heapSize))
        {
            System.out.println("Test remove at buildHeap() FAILED");
            throw new RuntimeException("Test remove failed");
        }
        Comparable root = heap[1];
        Comparable item = HeapUtils.remove(heap, heapSize);
        HeapDisplay display1 = new HeapDisplay();
        display1.displayHeap(heap, heapSize - 1);
        if (root.compareTo(item) != 0)
        {
            System.out.println("Test remove() FAILED: " +
                " removed item has value " + item + ", expected " + root);
            throw new RuntimeException("Test remove() failed");
        }
        for (int i = 1; i <= (heapSize - 1); i++)
        {
            if (heap[i] == root)
            {
                System.out.println("Test remove() FAILED: " +
                    "removed item still found");
                throw new RuntimeException("Test remove() failed");
            }
        }
        if (checkHeapProperty(heap, heapSize))
        {
            System.out.println("Test remove() PASS");
        }
        else
        {
            System.out.println("Test remomve() FAILED");
            throw new RuntimeException("Test remove() failed");
        }
    }

    /**
     * Test heapSort().
     * - call heapSort()
     * - compare the result with Java Collections.sort()
     * 
     * @param heap the array that contains heap data
     * @param heapSize the number of elements in heap
     * 
     * @throw RuntimeException if test fail
     */
    private static void testHeapSort(Comparable[] heap, int heapSize)
    {
        System.out.println("Test heapSort()");
        ArrayList < Comparable > original = 
            new ArrayList < Comparable > (Arrays.asList(heap));
        original.remove(0);
        HeapUtils.heapSort(heap, heapSize);
        Collections.sort(original);

        for (int i = 1; i <= heapSize; i++)
        {
            if (heap[i].compareTo(original.get(i - 1)) != 0)
            {
                throw new RuntimeException(
                    "Test heapSort() FAILED: index " +
                    i + " has value " + heap[i] + ", expected " +
                    original.get(i - 1));
            }
        }
    }

    /**
     * Check if the specified heap meets Max Heap property.
     * 
     * It uses the similar algorithm as in buildHeap() to find
     * all non-leaf nodes to compare with their children, starting
     * with the lowest right-most.
     * 
     * @param heap the heap to check
     * @param heapSize number of nodes in heap
     * @return true if meets Max Heap property otherwise false
     */
    private static boolean checkHeapProperty(Comparable[] heap, int heapSize)
    {
        if (heapSize <= 1)
        {
            return true;
        }
        //keep track non-leaf nodes that have been checked
        ArrayList < Integer > checked = new ArrayList < Integer > ();

        int index = heapSize;
        int indexParent = index / 2;
        int indexChildL = 2 * indexParent;
        int indexChildR = 2 * indexParent + 1;
        int indexRoot = 1;
        boolean stop = false;
        while (!stop) 
        {
            if (indexParent == indexRoot)
            {
                stop = true;
            }
            if (!checked.contains(indexParent))
            {
                if (indexChildL <= heapSize)
                {
                    if (heap[indexParent].compareTo(heap[indexChildL]) < 0) 
                    {
                        return false;
                    }
                }
                if (indexChildR <= heapSize)
                {
                    if (heap[indexParent].compareTo(heap[indexChildR]) < 0) 
                    {
                        return false;
                    }
                    checked.add(indexParent);
                }
            }
            index--;
            indexParent = index / 2;
            indexChildL = 2 * indexParent;
            indexChildR = 2 * indexParent + 1;
        }
        return true;
    }

    /**
     * Test odd number of nodes.
     */
    private static void test()
    {
        System.out.println("START TEST");
        int heapSize = 11;
        Comparable[] heap = generateRandom(heapSize);
        HeapDisplay display = new HeapDisplay();
        display.displayHeap(heap, heapSize);
        testBuildHeap(heap, heapSize);
        testInsert(heap, new Integer(INSERT_TEST_VALUE), heapSize);
        testRemove(heap, heapSize);

        heap = generateRandom(heapSize);
        HeapDisplay display1 = new HeapDisplay();
        display1.displayHeap(heap, heapSize);
        testHeapSort(heap, heapSize);

        System.out.println("DONE TEST");
    }

    /**
     * Genereate an array of Integer objects with random values
     * in range of [1, 100), starting at index 0.
     * 
     * @param heapSize is the size of the array - 1
     * @return the generated array
     */
    private static Comparable[] generateRandom(int heapSize)
    {
        Integer[] items = new Integer[heapSize + 1];
        Random r = new Random();
        for (int i = 1; i < (heapSize + 1); i++) 
        {
            //get a random integer range[1...100)
            int val = r.nextInt(100 - 1) + 1;
            items[i] = new Integer(val);
        }
        return items;
    }

    /**
     *  The entry method to run the program. 
     *  
     *  @param args command line arguments, not expect any
     */
    public static void main(String[] args)
    {
        test();
        System.out.println("DONE!");
    }
}
