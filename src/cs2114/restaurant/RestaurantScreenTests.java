package cs2114.restaurant;

import android.widget.*;
import android.content.Intent;
import realtimeweb.yelp.exceptions.BusinessSearchException;

// -------------------------------------------------------------------------
/**
 * This is the Restaurant screen test class.
 *
 * @author Leoul Yiheyis (leoul16)
 * @version (2014.04.16)
 */
public class RestaurantScreenTests
    extends student.AndroidTestCase<RestaurantScreen>
{
    // ~ Fields ................................................................
    /**
     * the searchField EditText
     */
    private EditText  searchField;

    /**
     * the restaurantName TextView
     */
    private TextView  restaurantName;

    /**
     * the ratingbar RatingBar
     */
    private RatingBar ratingBar;

    /**
     * the next button
     */
    private Button    next;

    /**
     * the previous button
     */
    private Button    previous;

    /**
     * the viewMap button
     */
    private Button    viewMap;


    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public RestaurantScreenTests()
    {
        super(RestaurantScreen.class);
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    public void setUp()
    {
        enterText(searchField, "Blacksburg, VA");
    }


    /**
     * tests the searchField
     */
    public void testSearchField()
    {
        enterText(searchField, "Blacksburg, VA");
        assertEquals(20, getScreen().list.size());
    }



    /**
     * tests the next button
     */
    public void testNextClicked()
    {
        click(next);
        assertEquals(5.0, ratingBar.getRating(), 0.01);
        assertEquals("Carol Lee Donuts", restaurantName.getText());
    }


    /**
     * tests the previous button
     */
    public void testPreviousClicked()
    {
        click(previous);
        click(previous);
        assertEquals(5.0, ratingBar.getRating(), 0.01);
        assertEquals("Lyric Theatre", restaurantName.getText());
    }


    /**
     * tests to see if the app searches correctly
     */
    public void testBusinessSearchComplete()
    {
        assertEquals(5.0, ratingBar.getRating(), 0.01);
    }



    /**
     * tests the buisnessSearchFailed() method
     */
    public void testBusinessSearchFailed()
    {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run()
            {
                getScreen().businessSearchFailed(
                    new BusinessSearchException("", "", ""));
            }
        });

        assertEquals(false, next.isEnabled());
        assertEquals(false, previous.isEnabled());
        assertEquals(false, viewMap.isEnabled());
    }



    /**
     * tests the viewMap button
     */
    public void testViewMap()
    {
        enterText(searchField, "Blacksburg, VA");
        prepareForUpcomingActivity(Intent.ACTION_VIEW);
        click(viewMap);
        assertEquals(getScreen().list.size(), 20);

    }



}
