
/**
 * Tests the working of heapsort on an array of Integers.
 * 
 * @author Anu Datar 
 * @version 12/22/2018
 */
public class SimpleTester
{
    /**
     * The main method that launches the program.
     * 
     * @param args the command line parameters
     *  
     */
    public static void main(String[ ] args)
    {
        Comparable[] arr = new Comparable[12];
        HeapDisplay display = new HeapDisplay();
        HeapUtils util = new HeapUtils( );
        System.out.println("This is the initial array");
        System.out.println("-----------------------------------");
        System.out.print("|");
        for (int i = 1; i < 12; i++)
        {
            arr[i] = (int)(Math.random() * 100) + 1;
            System.out.print(arr[i] + "|");
        }
        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println();
        display.displayHeap(arr, 11);
        System.out.println("Unsorted array displayed. Invoking Heapsort...");
        util.heapSort(arr, 11);
        System.out.println("This is the sorted array using a max heap");
        System.out.println("-----------------------------------");
        System.out.print("|");
        for (int i = 1; i < 12; i++)
        {
            System.out.print(arr[i] + "|");
            if (i > 1 && ((Integer)arr[i] < (Integer)arr[i - 1]))
            {
                throw new RuntimeException(
                    "Items are not sorted in order from greatest to least"
                    +  arr[i] + " is less than " + arr[i - 1]);
            }
        }
        System.out.println();
        System.out.println("-----------------------------------");
        System.out.println();
        System.out.println("Have a nice day"); 

    }
}
