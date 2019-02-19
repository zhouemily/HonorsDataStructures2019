import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * MyLinkedList provides a subset of fundamental methods that
 * can be performed on a linked list. It is implemented as a
 * double linked list. 
 *
 * @param <E> the type of elements in this list
 * 
 * @author Emily Zhou
 * @version 10-13-2018
 */
public class MyLinkedList < E >
{
    private DoubleNode first;
    private DoubleNode last;
    private int size;

    /**
     * The constructor to create MyLinkedList.
     */
    public MyLinkedList()
    {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Get a string presentation of the object.
     * 
     * @return a string representation of the MyLinkedList object
     */
    @Override
    public String toString()
    {
        DoubleNode node = first;
        if (node == null)
            return "[]";
        String s = "[";
        while (node.getNext() != null)
        {
            s += node.getValue() + ", ";
            node = node.getNext();
        }
        return s + node.getValue() + "]";
    }

    /** 
     * Get the node at the specified index by traversing
     * from the first node.
     *
     * @precondition  0 <= index <= size / 2
     * @postcondition starting from first, returns the node
     *               with given index (where index 0
     *               returns first)
     * @param index the position where to get the node
     * @return the node at the specified position
     */
    private DoubleNode getNodeFromFirst(int index)
    {
        int i = 0;	
        DoubleNode node = first;		
        while (i != index && node != null)
        {
            node = node.getNext();
            i++;
        }
        return node;
    }

    /** 
     * Get the node at the specified index by traversing
     * from the last node.
     *
     * @precondition  size / 2 <= index < size
     * @postcondition starting from last, returns the node
     *               with given index (where index size-1
     *               returns last)
     * @param index the position where to get the node
     * @return the node at the specified position
     */
    private DoubleNode getNodeFromLast(int index)
    {
        int i = size - 1;	
        DoubleNode node = last;
        while (i != index && node != null) 
        {
            node = node.getPrevious();
            i--;
        }
        return node;

    }

    /** 
     * Get the node at specified index.
     *
     * @precondition  0 <= index < size
     * @postcondition starting from first or last (whichever
     *               is closer), returns the node with given
     *                index
     * @param index the position where to get the node
     * @return the node at the specified position
     */
    private DoubleNode getNode(int index)
    {
        if (index <= size / 2)
        {
            return getNodeFromFirst(index);
        }
        return getNodeFromLast(index);
    }

    /**
     * Get the number of elements in the list.
     *
     * @return the size of the list
     */
    public int size()
    {
        return size;
    }

    /**
     * Get the element at the specified index.
     *
     * @precondition  0 <= index < size
     * @param index the position where to get the element
     * @return the element at index
     */
    public E get(int index)
    {
        DoubleNode node = getNode(index);
        return (E)node.getValue();
        //(You will need to promise the return value is of type E.)
    }

    /** 
     * Set the element at the specified index to the specified object.
     *
     * @precondition  0 <= index < size
     * @postcondition replaces the element at position index with obj
     * @param index the position where to replace the element
     * @param obj the object as the replacement 
     * @return the element formerly at the specified position
     */
    public E set(int index, E obj)
    {
        DoubleNode node = getNode(index);
        Object o = node.getValue();
        node.setValue(obj);
        return (E)o;
        //(You will need to promise the return value is of type E.)
    }

    /**
     * Append the specified object to the end of the list.
     *
     * @postcondition appends obj to the end of list
     * @param obj the object to be appended
     * @return true
     */
    public boolean add(E obj)
    {

        DoubleNode newNode = new DoubleNode(obj);

        //take care newNode
        newNode.setPrevious(last);
        newNode.setNext(null);

        //take care last and first
        if (last != null) last.setNext(newNode);
        last = newNode;
        if (first == null) first = newNode;
        size++;
        return true;
    }

    /** 
     * Removes the element at the specified index.
     *
     * @precondition  0 <= index < size
     * @postcondition removes element at index by adjust 'previous'
     *                and 'next' variables of the nodes: the node
     *                right before the index node and the node right
     *                after the index node and also adjust first
     *                and last variables if affected
     * @param index the position where the element to be removed
     * @return the element formerly at the specified position
     */
    public E remove(int index)
    {
        DoubleNode indexNode = getNode(index);
        if (indexNode == null) return null;
        DoubleNode prevNode = indexNode.getPrevious();
        DoubleNode nextNode = indexNode.getNext();

        //take care the node right behind
        if (nextNode != null) nextNode.setPrevious(prevNode);

        //take care the node right front
        if (prevNode != null) prevNode.setNext(nextNode);

        //take care first and last
        if (first == indexNode) first = nextNode;
        if (last == indexNode) last = prevNode;

        Object o = indexNode.getValue();
        size--;
        return (E)o;
        //(You will need to promise the return value is of type E.)
    }

    /** 
     * Insert an element at specified index.
     *
     * @precondition  0 <= index <= size
     * @postcondition inserts obj at front of current index node by
     *                adjust the 'previous' and 'next' variables of
     *                the nodes: the new node, the current index node
     *                and the node right before the curent index node,
     *                and also adjust first and last if affected
     * @param index the position where to insert the element
     * @param obj the object to be inserted
     */
    public void add(int index, E obj)
    {
        if (index == size) {
            add(obj);
            return;
        }
        DoubleNode newNode = new DoubleNode(obj);
        DoubleNode indexNode = getNode(index);

        //take care the node at front of newNode
        DoubleNode prevNode = null;
        if (indexNode != null)
        {
            prevNode = indexNode.getPrevious();
            if (prevNode != null) prevNode.setNext(newNode);
        }

        //take care newNode
        newNode.setPrevious(prevNode);
        newNode.setNext(indexNode);

        //take care current indexNode
        if (indexNode != null) indexNode.setPrevious(newNode);

        //take care first and last
        if (first == indexNode) first = newNode;
        if (indexNode == null) 
        {
            first = newNode;
            last = newNode;
        }
        size++;
    }

    /** 
     * Add the specified element at the beginning of the list.
     *
     * @postcondition inserts the element at position 0
     * @param obj the object to be added
     */
    public void addFirst(E obj)
    {
        add(0, obj);
    }

    /** 
     * Add the specified element at the end of the list.
     *
     * @postcondition inserts the element the last position of the list
     * @param obj the object to be added
     */
    public void addLast(E obj)
    {
        add(obj);
    }

    /** 
     * Get the first element.
     *
     * @return the first element in the list
     */
    public E getFirst()
    {
        if (first == null) return null;
        return (E)first.getValue();
        //(You will need to promise the return value is of type E.)
    }

    /** 
     * Get the last element.
     *
     * @return the last element in the list
     */
    public E getLast()
    {
        if (last == null) return null;
        return (E)last.getValue();
        //(You will need to promise the return value is of type E.)
    }

    /** 
     * Remove the first element.
     *
     * @return the element that is removed
     */
    public E removeFirst()
    {
        return remove(0);
        //(You will need to promise the return value is of type E.)
    }

    /** 
     * Remove the last element.
     *
     * @return the element that is removed
     */
    public E removeLast()
    {
        if (size == 0) return null;
        return remove(size - 1);
        //(You will need to promise the return value is of type E.)
    }

    /** 
     * Remove a specific node in the list.
     *
     * @param node the node to be removed
     */
    private void removeNode(DoubleNode node)
    {
        DoubleNode mynode = first;
        int i = 0;
        while (mynode != null)
        {
            if (mynode == node)
            {
                remove(i);
                return;
            }
            mynode = mynode.getNext();
            i++;
        }
    }

    /**
     * Ensure this list is same as the copy.
     * 
     * @throws RuntimeException if not same
     */
    private void ensureListNotModified(List copy)
    {
        if (size() != copy.size()) 
        {
            throw new RuntimeException("List modified: size=" + size() +
                ", expected size=" + copy.size());
        }

        DoubleNode node = first;
        int index = 0;
        while (node != null)
        {
            if (node != copy.get(index)) 
            {
                throw new RuntimeException("List modified !");
            }
            node =  node.getNext();
            index++;
        }
    }

    /**
     * Make a copy of the list.
     * 
     * @return a MyLinkedList that is a copy of this list object
     */
    private List makeCopy()
    {
        ArrayList copy = new ArrayList();
        DoubleNode node = first;
        while (node != null)
        {
            copy.add(node);
            node = node.getNext();
        }
        return copy;
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
    public Iterator < E > iterator()
    {
        return new MyLinkedListIterator();
    }

    public ListIterator < E > listIterator()
    {
        return new MyLinkedListListIterator();
    }

    /**
     * An inner class that implements java.util.Iterator.
     */
    private class MyLinkedListIterator implements Iterator < E >
    {
        private DoubleNode nextNode;
        private DoubleNode lastReturnedNode;
        private int expectedModificationCount;
        private List listCopy;

        /**
         * The constructor of the MyLinkedListIterator.
         */
        public MyLinkedListIterator()
        {
            nextNode = MyLinkedList.this.first;
            lastReturnedNode = null;
            listCopy = MyLinkedList.this.makeCopy();
            System.out.println("Created Iterator with list size " + MyLinkedList.this.size());

        }

        /**
         * Check if has next element.
         * 
         * @return true if has more element otherwise false
         * @throws RuntimeException if the list has been modified (add or remove)
         */
        public boolean hasNext()
        {
            return (nextNode != null);
        }

        /**
         * Get the next element.
         * 
         * @return the next element
         */
        public E next()
        {
            MyLinkedList.this.ensureListNotModified(listCopy);
            if (nextNode == null) return null;

            Object o = nextNode.getValue();
            lastReturnedNode = nextNode;
            nextNode = nextNode.getNext();
            return (E)o;
            //(You will need to promise the return value is of type E.)
        }

        /** 
         * Remove the last element that was returned by next().
         *
         * @postcondition removes the last element that was returned by next
         * @throws RuntimeException if the list has been modified 
         *       by adding or removal except using iterator.remove()
         */
        public void remove()
        {
            MyLinkedList.this.ensureListNotModified(listCopy);
            if (lastReturnedNode ==  null) return;
            MyLinkedList.this.removeNode(lastReturnedNode);
            listCopy.remove(lastReturnedNode);
            lastReturnedNode = null;
        }
    }

    /**
     * An inner class that implements java.util.ListIterator.
     */
    private class MyLinkedListListIterator implements ListIterator < E >
    {
        private DoubleNode cursorNode;
        private int cursorIndex;
        private DoubleNode lastReturnedNode;
        
        /**
         * Used to initialize cursorNode and cursorIndex
         * based on intention of iteration direction on 
         * first call of this ListIterator object.
         */
        private boolean iteratorStarted;
        
        /**
         * Used to compare if the MyLinkedList has been modified.
         */
        private List listCopy;

        /**
         * The constructor of the MyLinkedListListIterator.
         */
        public MyLinkedListListIterator()
        {
            cursorNode = null;
            lastReturnedNode = null;
            iteratorStarted = false;
            listCopy = MyLinkedList.this.makeCopy();
        }

        /**
         * Check if has next element when traversing forward the list.
         * 
         * @return true if has more element when traversing forward otherwise false
         */
        public boolean hasNext()
        {
            if (!iteratorStarted) 
            {
                iteratorStarted = true;
                cursorNode = MyLinkedList.this.first;
                cursorIndex = 0;

            }

            return cursorIndex < MyLinkedList.this.size();
        }

        /**
         * Check if has next element when traversing backward the list.
         * 
         * @return true if has more element when traversing backard otherwise false
         */
        public boolean hasPrevious()
        {
            if (!iteratorStarted) 
            {
                iteratorStarted = true;
                cursorNode = null;
                if (size == 0) 
                {
                    cursorIndex = 0;
                } else {
                    cursorIndex = size -1;
                }
            }
            return cursorIndex > 0;
        }

        /**
         * Get the next element and moves the cursor forward.
         * 
         * @return the next element
         * @throws RuntimeException if the list has been modified
         *       by adding or removal except using listIterator.remove()
         *       or listIterator.add()
         */
        public E next()
        {
            if (!iteratorStarted) hasNext();
            MyLinkedList.this.ensureListNotModified(listCopy);
            if (cursorNode == null) return null;

            lastReturnedNode = cursorNode;
            cursorNode = cursorNode.getNext();
            cursorIndex++;
            return (E)lastReturnedNode.getValue();
        }

        /**
         * Return the previous element and moves the cursor backward.
         * 
         * @return the previous element
         * @throws RuntimeException if the list has been modified
         *       by adding or removal except using listIterator.remove()
         *       or listIterator.add()
         */
        public E previous()
        {
            if (!iteratorStarted) hasPrevious();
            MyLinkedList.this.ensureListNotModified(listCopy);
            if (cursorNode == null) 
            {
                cursorNode = MyLinkedList.this.last;
            } else {
                cursorNode = cursorNode.getPrevious();

            }
            cursorIndex--;
            lastReturnedNode = cursorNode;
            return (E)lastReturnedNode.getValue();   

        }

        /**
         * Return the index of the element that would be
         * returned by next call next().
         * 
         * @precondition hasNext() must have called and returned true
         * @return the next index
         */
        public int nextIndex()
        {
            if (!iteratorStarted) hasNext();
            return cursorIndex;
        }

        /**
         * Return the index of the element that would be
         * returned by next call previous().
         * 
         * @precondition hasPrevious() must have called and returned true
         * @return the previous index
         */
        public int previousIndex()
        {
            if (!iteratorStarted) hasPrevious();
            return cursorIndex - 1;
        }

        /** 
         * Remove the last element that was returned by next().
         *
         * @postcondition removes the last element that was returned by next
         * @throws RuntimeException if the list has been modified
         *       by adding or removal except using listIterator.remove()
         *       or listIterator.add()
         */
        public void remove()
        {
            MyLinkedList.this.ensureListNotModified(listCopy);
            System.out.println("latsReturned:"+lastReturnedNode.hashCode());
            if (lastReturnedNode ==  null) return;
            MyLinkedList.this.removeNode(lastReturnedNode);
            listCopy.remove(lastReturnedNode);
            lastReturnedNode = null;
        }

        /**
         * Insert the specified element into the list.
         * The new element is inserted right before the element
         * that would be return by next() and right after the element
         * that would be return by previous().  The operation does not
         * affect the next next() call.  The next previous() call will
         * return the new element.
         * 
         * @precondition either hasNext() or hasPrevious() must called
         * @throws RuntimeException if the list has been modified
         *       by adding or removal except using listIterator.remove()
         *       or listIterator.add()
         */
        public void add(E e)
        {
            MyLinkedList.this.ensureListNotModified(listCopy);
            if (!iteratorStarted) 
            {
                MyLinkedList.this.add(e); 
                listCopy.add(e);
                return;
            }
            MyLinkedList.this.add(cursorIndex, e);
            listCopy.add(cursorIndex, e);
        }

        /**
         * Replaces last element returned by next() or
         * previous() with the specified element.
         * 
         * @param e the element to be used for replacement
         * @throws RuntimeException if the list has been modified
         *       by adding or removal except using listIterator.remove()
         *       or listIterator.add()
         */
        public void set(E e)
        {
            MyLinkedList.this.ensureListNotModified(listCopy);
            if (lastReturnedNode == null) return;
            lastReturnedNode.setValue(e);
        }
    }
}
