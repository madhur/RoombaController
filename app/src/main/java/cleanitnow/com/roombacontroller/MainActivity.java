package cleanitnow.com.roombacontroller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import cleanitnow.com.roombacontroller.controller.RoombaController;
import cleanitnow.com.roombacontroller.views.MapViewFragment;
import cleanitnow.com.roombacontroller.views.RemoteFragment;

/**
 * Main Activity of the application. Responsible for hosting one of the two fragments at a time: @MapViewFragment or @RemoteFragment
 */
public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener
{
    /***
     * The instance of the RoombaController which implements IController interface.
     */
    private RoombaController roombaController;

    /***
     * Initialize and setup the action bar with the spinner
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_dropdown_item);

        getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, this);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setContentView(R.layout.activity_main);



        roombaController=new RoombaController();

    }


    public RoombaController getRoombaController()
    {
        return roombaController;
    }


    /***
     * Load the desired fragment based on the selected spinner
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(int position, long id)
    {
        if(position==0)
        {
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new RemoteFragment()).commit();
            return true;
        }
        else if(position ==1)
        {
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new MapViewFragment()).commit();
            return true;
        }

        return false;

    }
}
