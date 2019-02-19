import java.util.LinkedList;
import java.util.Queue;

/**
 * Heap display class.
 * 
 * @author Anu Datar 
 * @version 12/22/2018
 */
public class HeapDisplay extends TreeDisplay
{

    /**
     * Constructor.
     */
    public HeapDisplay()
    {
        super();
    }

    /**
     * Display a heap.
     * 
     * @param heapArray the heap array
     * @param heapSize number of elements in heap
     */
    public void displayHeap(Comparable[] heapArray, int heapSize)
    {
        TreeNode root = heapArrayToTree(heapArray, heapSize);
        displayTree(root);
    }

    /**
     * convert a binary tree stored as an array into a TreeNode based tree
     * so that it can be displayed using the TreeDisplay.
     * @param heapArray - the array representation of the tree
     * @param heapSize is the current size of the heap.
     *        It starts at location 1 in
     *        the heapArray and ends at location heapSize
     * @return a TreeNode representation of the tree
     */
    private static TreeNode heapArrayToTree(
    Comparable[] heapArray, int heapSize)
    {
        // scan the array and build the tree using breadth first
        TreeNode root = new TreeNode(heapArray[1]);
        Queue < TreeNode > queue = new LinkedList < TreeNode > ();
        queue.add(root);
        for (int i = 1; i <= (heapSize / 2); i++)
        {
            TreeNode temp = queue.remove();
            int indexLeft = i * 2;
            int indexRight = i * 2 + 1;
            temp.setLeft(new TreeNode(heapArray[indexLeft]));
            queue.add(temp.getLeft());
            if (indexRight <= heapSize) 
            {
                temp.setRight(new TreeNode(heapArray[indexRight]));
                queue.add(temp.getRight());
            }

        }
        return root;

    }
}
