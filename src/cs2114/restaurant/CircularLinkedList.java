package cs2114.restaurant;

import java.util.Iterator;
import java.util.NoSuchElementException;

//-------------------------------------------------------------------------
/**
 * A circular, doubly-linked list.
 *
 * @param <E> the type of element stored in the list
 *
 * @author  Leoul Yiheyis (leoul16)
 * @version (2014.03.30)
 */
public class CircularLinkedList<E> implements CircularList<E>
{
    //~ Fields ................................................................

    private Node<E> current;
    private Node<E> next;
    private Node<E> previous;

    private int size;


    //~ Constructors ..........................................................

    /**
     * this is the constructor for the circular linked list
     */
    public CircularLinkedList()
    {
        size = 0;
        current = new Node<E>(null);
        next = new Node<E>(null);
        previous = new Node<E>(null);
    }


    //~ Public methods ........................................................

    /**
     * the list's next method
     */
    public void next()
    {
        if (size > 1)
        {
            previous = current;
            current  = next;
            next = current.next();
        }
    }


    /**
     * the list's previous method
     */
    public void previous()
    {
        if (size > 2)
        {
            current  = previous;
            previous = previous.previous();
            next = current.next();
        }
        else if (size > 1)
        {
            next();
        }
    }


    /**
     * returns current node
     * @return E - node's data
     * @throws NoSuchElementException
     */
    public E getCurrent()
    {
        if (size == 0)
        {
            throw new NoSuchElementException("This list is empty.");
        }
        else
        {
            return current.data();
        }
    }



    /**
     * tells user the size of the list
     * @return size - the size of the list
     */
    public int size()
    {
        return size;
    }



    /**
     * add's a node to the list
     * @param data - the data in the node
     */
    public void add(E data)
    {
        Node<E> newNode = new Node<E>(data);
        next = current;
        current = newNode;
        if (size == 0)
        {
            previous = newNode;
        }
        if (size == 1)
        {
            current.join(next);
            previous.join(current);
        }
        if (size > 1)
        {
            current.join(previous.split());
            previous.join(current);
        }
        size++;
    }


    /**
     * remove's the current node
     * @return E the data in the node
     */
    public E removeCurrent()
    {
        Node<E> it = current;
        if (size == 0)
        {
            throw new NoSuchElementException("The list is empty.");
        }
        else if (size == 1)
        {
            current = null;
            previous = null;
            next = null;
        }
        else
        {
            previous.split();
            previous.join(current.split());
            current = next;
        }
        size--;

        if (size == 2)
        {
            next = previous;
        }
        return it.data();
    }


    /**
     * completely clear's the list
     */
    public void clear()
    {
        while (size > 0)
        {
            removeCurrent();
        }
    }


    /**
     * prints the list in a readable formal
     * @return str - the list in a string
     */
    public String toString()
    {
        String str = "[" + getCurrent();
        next();
        for (int i = 0; i < size - 1; i++)
        {
            str += ", " + getCurrent();
            next();
        }
        str += "]";
        return str;
    }


    /**
     * the lists iterator
     * @return new Iterator
     */
    public Iterator<E> iterator()
    {
        return new CircularLinkedListIterator();
    }


    //~ Inner classes .........................................................

    /**
     * This is the circular linked lists iterator
     *
     *  @author leoul_y
     *  @version Apr 4, 2014
     */
    private class CircularLinkedListIterator implements Iterator<E>
    {
        //~ Fields ............................................................

        private Node<E> curr;

        private boolean next;

        private int index;

        //~ Constructors ......................................................

        /**
         * the iterator's constructor
         */
        public CircularLinkedListIterator()
        {
            curr = current;
            next = false;
            index = 0;
        }


        /**
         * iterator's hasNext() method
         * tells if current node has a node after it
         */
        public boolean hasNext()
        {
            return index < size;
        }


        /**
         * the iterator's next method
         * @return E - node's data
         * @throws NoSuchElementException
         */
        public E next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("This list is empty.");
            }
            else if (!next)
            {
                next = true;
                index++;
                return curr.data();
            }
            curr = curr.next();
            index++;
            return curr.data();
        }


        /**
         * the iterator's remove method
         * @throws UnsupportedOperationException
         */
        public void remove()
        {
            throw new UnsupportedOperationException("This method isn't " +
                "supported.");
        }
    }
}
