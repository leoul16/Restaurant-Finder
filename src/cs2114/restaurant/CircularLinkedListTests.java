package cs2114.restaurant;

import java.util.NoSuchElementException;
import java.util.Iterator;

//-------------------------------------------------------------------------
/**
 * Example unit tests for the CircularLinkedList class.
 *
 * @author <Leoul Yiheyis> (leoul16)
 * @version (2014.03.30)
 */
public class CircularLinkedListTests extends student.TestCase
{
    //~ Fields ................................................................

    private CircularLinkedList<String> list;


    //~ Public methods ........................................................

    /**
     * constructor
     */
    public CircularLinkedListTests()
    {
        //should be empty for testing
    }


    /**
     * Creates a brand new, empty CircularLinkedList for each test method.
     */
    public void setUp()
    {
        list = new CircularLinkedList<String>();
    }


    /**
     * tests the add() method
     */
    public void testAdd()
    {
        Exception occurred = null;
        try
        {
            list.getCurrent();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals(
            "This list is empty.",
            occurred.getMessage());

        list.add("one");
        list.add("two");
        list.add("three");
        assertEquals("three", list.getCurrent());
        list.next();
        assertEquals("two", list.getCurrent());
        list.next();
        assertEquals("one", list.getCurrent());
        list.next();
    }


    /**
     * tests the clear() method
     */
    public void testClear()
    {
        Exception occurred = null;
        try
        {
            list.removeCurrent();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals(
            "The list is empty.",
            occurred.getMessage());

        list.add("one");
        list.add("two");
        list.add("three");
        list.clear();
        assertEquals(0, list.size());
    }


    /**
     * tests the previous() method
     */
    public void testPrevious()
    {
        list.add("one");
        list.add("two");
        list.add("three");
        list.previous();
        assertEquals("one", list.getCurrent());
        list.previous();
        assertEquals("two", list.getCurrent());
        list.removeCurrent();
        assertEquals("one", list.getCurrent());
        list.previous();
        assertEquals("three", list.getCurrent());
    }


    /**
     * tests the next() method
     */
    public void testNext()
    {
        list.add("one");
        list.add("two");
        list.add("three");
        list.next();
        assertEquals("two", list.getCurrent());
        list.next();
        assertEquals("one", list.getCurrent());
        list.next();
        assertEquals("three", list.getCurrent());

        list.add("four");
        list.add("five");
        list.add("six");
        list.next();
        assertEquals("five", list.getCurrent());
        list.next();
        assertEquals("four", list.getCurrent());
        list.next();
        list.next();
        list.next();
        list.next();
        assertEquals("six", list.getCurrent());
    }


    /**
     * tests the toString() method
     */
    public void testToString()
    {
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");

        assertEquals("[six, five, four, three, two, one]", list.toString());
        list.next();
        assertEquals("[five, four, three, two, one, six]", list.toString());
    }


    /**
     * tests the hasNext() method
     */
    public void testHasNext()
    {
        Iterator<String> iter = list.iterator();
        assertFalse(iter.hasNext());

        list.add("one");
        list.add("two");
        list.add("three");
        list.next();
        iter = list.iterator();
        assertTrue(iter.hasNext());
    }


    /**
     * tests the iterator's next() method
     */
    public void testIteratorNext()
    {
        Exception occurred = null;
        try
        {
            Iterator<String> iter = list.iterator();
            iter.next();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals(
            "This list is empty.",
            occurred.getMessage());

        list.add("one");
        list.add("two");
        list.add("three");
        Iterator<String> iter = list.iterator();
        assertEquals("three", iter.next());
        assertEquals("two", iter.next());
        assertEquals("one", iter.next());

    }


    /**
     * tests the Iterator's remove() method
     */
    public void testIteratorRemove()
    {
        Exception occurred = null;
        try
        {
            Iterator<String> iter = list.iterator();
            iter.remove();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof UnsupportedOperationException);
        assertEquals("This method isn't supported.", occurred.getMessage());

    }
}
