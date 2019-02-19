import java.util.Iterator;
import java.util.ListIterator;

/**
 * MyArrayList provides a subset of fundamental methods that
 * can be performed on a list. It is implemented using an array
 * and allows auto-expansion of the list capacity.  It is a 
 * light-weight and easy to use class.
 *
 * @author Emily Zhou
 * @version 10-7-2018
 */
public class MyArrayList<E>
{
    //track the number of elements in the array
    private int size;

    private Object[] values;  //(Java doesn't let us make an array of type E)

    /**
     * The constructor to create MyArrayList object
     */
    public MyArrayList()
    {
        size = 0;
        values = new Object[1];
    }

   /**
    * @return a string representation of the MyArrayList object 
    */
    @Override
    public String toString()
    {
        if (size == 0)
            return "[]";

        String s = "[";
        for (int i = 0; i < size - 1; i++)
            s += values[i] + ", ";
        return s + values[size - 1] + "]";
    }

   /**
    * @precondition values not null and has capacity at least 1
    *               and size < values.length
    * @postcondition replaces the array with one that is
    *               twice as long, and copies all of the
    *               old elements into it
    */
    private void doubleCapacity()
    {   
        int count = values.length;
        Object[] temp = new Object[count*2];
        for (int i = 0; i < size(); i++)
        {
            temp[i] = values[i];
        }
        values = temp;
    }

   /**
    * @postcondition returns the length of the array
    *
    * @return the length of the array
    */
    public int getCapacity()
    {
        return values.length;
    }

   /**
    * @postcondition returns the number of elements in the array
    *
    * @return the number of elements in the list
    */
    public int size()
    {
        return size;
    }

   /**
    * Return the element at the specified index.
    *
    * @precondition 0 <= index < size
    * @param index the index where to get the element
    * @return the element at the index 
    */
    public E get(int index)
    {
        return (E)values[index];
        //(You will need to promise the return value is of type E.)
    }

   /** 
    * Replaces the element at the specified index and return
    * the original element at the index. 
    *
    * @precondition  0 <= index < size
    * @postcondition replaces the element at position index with obj
    *               returns the element formerly at the specified position
    * @param index the index where to replace element
    * @param obj the new object to be used to replace the old
    * @return the element at the index before the replacement
    */
    public E set(int index, E obj)
    {
        Object temp = values[index];
        values[index] = obj;
        return (E)temp;
        //(You will need to promise the return value is of type E.)
    }

   /**
    * Add an element at the end of list
    *
    * @postcondition appends obj to the end of the list; returns true
    *
    * @param obj the object as the element to add
    * @return true
    */
    public boolean add(E obj)
    {
        /* if values is already full, call doubleCapacity before adding */
        if (getCapacity() == size())
            doubleCapacity();
        set(size(), obj);
        size++;
        return true;
    }

   /**
    * Removes the element at specified index.
    * 
    * @precondition 0 <= index < size
    * @postcondition removes element from position index, moving elements
    *               at position index + 1 and higher to the left
    *               (subtracts 1 from their indices) and adjusts size
    *               returns the element formerly at the specified position
    * @param index the index where the element to be removed
    * @return the removed element
    */
    public E remove(int index)
    {
        Object temp = values[index];
        for (int i = index; i < size()-1; i++)
        {
            values[i] = values[i+1];
        }
        size--;
        return (E)temp;
        //(You will need to promise the return value is of type E.)
    }

    /**
     * Get an Iterator object to be used to iterate the list.
     *
     * Once the returned Iterator object is used, no modifition
     * operations should be performned on the list until the
     * returned Iterator object is no longer to be used.
     *
     * @return an Iterator object 
     */
    public Iterator<E> iterator()
    {
        return new MyArrayListIterator();
    }

   /**
    * Add an element at specified index
    *
    * @precondition  0 <= index <= size
    * @postcondition inserts obj at position index,
    *               moving elements at position index and higher
    *               to the right (adds 1 to their indices) and adjusts size
    * @param index the index where to add the element 
    * @param obj the object to add
    */
    public void add(int index, E obj)
    {
        if (getCapacity() == size())
            doubleCapacity();
        for (int i = size()-1; i >= index; i--)
        {
            values[i+1] = values[i];
        }
        values[index] = obj;
        size++;
    }

    /**
     * An inner class that implements java.util.Iterator interface
     */
    private class MyArrayListIterator implements Iterator<E>
    {
        //the index of the value that will be returned by next()
        private int nextIndex;

       /**
        * The constructor to create MyArrayListIterator object
        */
        public MyArrayListIterator()
        {
            nextIndex = 0;
        }

       /**
        * @return true if has more element otherwise false
        */
        public boolean hasNext()
        {
            return (nextIndex < size());
        }

       /**
        * @return the next element
        */
        public E next()
        {
            nextIndex++;
            return (E)values[nextIndex-1];
            //(You will need to promise the return value is of type E.)
        }

       /*
        * Remove the last element that was returned by next()
        *
        * @postcondition removes the last element that was returned by next
        */
        public void remove()
        {
            MyArrayList.this.remove(nextIndex-1);
        }
    }
}
