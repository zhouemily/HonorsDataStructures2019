import java.util.Iterator;
import java.util.ListIterator;

/**
 * MyLinkedList provides a subset of fundamental methods that
 * can be performed on a linked list. It is implemented as a
 * double linked list. 
 *
 * @author Emily Zhou
 * @version 10-13-2018
 */
public class MyLinkedList<E>
{
	private DoubleNode first;
	private DoubleNode last;
	private int size;

       /**
        * The constructor to create MyLinkedList
        */
	public MyLinkedList()
	{
		first = null;
		last = null;
		size = 0;
	}

       /**
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
		if (index < size/2)
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
		addLast(obj);
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
	* @postcondition inserts obj at index by adjust the 'previous'
        *                and 'next' variables of the nodes: the new node,
        *                the current index node and the node right before
        *                the index node, and also adjust first and last if affected
        * @param index the position where to insert the element
        * @param obj the object to be inserted
	*/
	public void add(int index, E obj)
	{
		DoubleNode newNode = new DoubleNode(obj);
		DoubleNode indexNode = getNode(index);
                DoubleNode prevNode = null;

                //take care the node at front of newNode
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
		if (size == 0)
		{
			addFirst(obj);
		} else {
			add(size-1, obj);
		}
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
		return remove(size-1);
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
		return new MyLinkedListIterator();
	}

        /**
         * An inner class that implements java.util.Iterator.
         */
	private class MyLinkedListIterator implements Iterator<E>
	{
		private DoubleNode nextNode;

               /**
                * The constructor of the MyLinkedListIterator
                */
		public MyLinkedListIterator()
		{
			nextNode = MyLinkedList.this.first;
		}

               /**
                * @return true if has more element otherwise false
                */
		public boolean hasNext()
		{
			return (nextNode != null);
		}

               /**
                * @return the next element
                */
		public E next()
		{
			if (nextNode == null) return null;
			nextNode = nextNode.getNext();	
			return (E)nextNode.getPrevious().getValue();
			//(You will need to promise the return value is of type E.)
		}

               /** 
                * Remove the last element that was returned by next()
                *
		* @postcondition removes the last element that was returned by next
                */
		public void remove()
		{
			if (nextNode == null) {
				int s = MyLinkedList.this.size();
				if (s == 0) return;
				MyLinkedList.this.remove(s-1);
			}
			MyLinkedList.this.removeNode(nextNode.getPrevious());
		}
	}
}
