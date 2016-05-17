package cs2114.restaurant;

//import realtimeweb.yelp.BusinessSearch;
//import realtimeweb.yelp.SearchResponse;
import java.util.List;
import android.widget.*;
import android.net.Uri;
import sofia.app.Screen;
import sofia.widget.ImageView;
import sofia.content.ContentViewer;
import realtimeweb.yelp.*;
import realtimeweb.yelp.exceptions.BusinessSearchException;

// -------------------------------------------------------------------------
/**
 * This is the Restaurant screen class. This controls the GUI
 *
 * @author Leoul Yiheyis (leoul16)
 * @version (2014.04.16)
 */
public class RestaurantScreen
    extends Screen
    implements BusinessSearchListener

{

    // ~ Fields ................................................................

    /**
     * the circular linked list
     */
    public CircularLinkedList<Business> list;

    /**
     * the business search field
     */
    public BusinessSearch               bus;

    /**
     * the searchField EditText
     */
    private EditText                    searchField;

    /**
     * the restaurantName TextView
     */
    private TextView                    restaurantName;

    /**
     * the numRatings TextView
     */
    private TextView                    numRatings;

    /**
     * the ratingbar RatingBar
     */
    private RatingBar                   ratingBar;

    /**
     * the next button
     */
    private Button                      next;

    /**
     * the previous button
     */
    private Button                      previous;

    /**
     * the viewMap button
     */
    private Button                      viewMap;

    /**
     * the imageView
     */
    private ImageView                   imageView;


    // ~ Public methods ........................................................

    /**
     * Initializes the screen.
     */
    public void initialize()
    {
        list = new CircularLinkedList<Business>();
        bus = BusinessSearch.getInstance();
        button(false);
    }


    /**
     * searches the location inputed to the searchField
     */
    public void searchFieldEditingDone()
    {
        String location = searchField.getText().toString();
        bus.searchBusinesses(
            new BusinessQuery(location),
            new BusinessSearchGUIAdapter(this));
    }


    @Override
    public void businessSearchCompleted(SearchResponse result)
    {
        if (result.getBusinesses().isEmpty())
        {
            button(false);
        }
        else
        {
            button(true);
            list.clear();
            List<Business> anotherList = result.getBusinesses();

            int size = anotherList.size() - 1;
            for (int i = size; i >= 0; i--)
            {
                list.add(anotherList.get(i));
            }
            updateInfo();
        }

    }


    /**
     * updates the information labels on the GUI
     */
    public void updateInfo()
    {
        if (list.getCurrent().getImageUrl() != null)
        {
            imageView.setImageURI(Uri.parse(list.getCurrent().getImageUrl()));
        }
        else
        {
            imageView.setImageURI(null);
        }
        ratingBar.setRating(list.getCurrent().getRating());
        restaurantName.setText(list.getCurrent().getName());
        // numRatings.setText(list.getCurrent().getReviewCount() + " Ratings");
        numRatings
            .setText(Integer.toString(list.getCurrent().getReviewCount()));
    }


    /**
     * method for when next button is pressed
     */
    public void nextClicked()
    {
        list.next();
        updateInfo();
    }


    /**
     * method for when previous button is pressed
     */
    public void prevClicked()
    {
        list.previous();
        updateInfo();
    }


    /**
     * acts when the viewMap button is clicked
     */
    public void viewMapClicked()
    {
        double longitude = list.getCurrent().getLocation().getLongitude();
        double latitude = list.getCurrent().getLocation().getLatitude();

        Uri uri =
           (Uri.parse("http://maps.google.com/maps?q=" + latitude + longitude));
        new ContentViewer(uri).start(this);

    }


    @Override
    public void businessSearchFailed(BusinessSearchException result)
    {
        button(false);

    }



    /**
     * changes the states of the buttons
     *
     * @param on
     *            - decides whether buttons are on or off
     */
    public void button(boolean on)
    {
        previous.setEnabled(on);
        next.setEnabled(on);
        viewMap.setEnabled(on);
    }

}
