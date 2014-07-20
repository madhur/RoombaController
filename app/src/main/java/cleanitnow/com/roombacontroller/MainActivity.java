package cleanitnow.com.roombacontroller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import cleanitnow.com.roombacontroller.controller.RoombaController;
import cleanitnow.com.roombacontroller.views.RoombaViewFragment;

/**
 * Main Activity of the application. Responsible for hosting one of the two fragments at a time: @MapViewFragment or @RemoteFragment
 */
public class MainActivity extends ActionBarActivity
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
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roombaController=new RoombaController(this);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new RoombaViewFragment()).commit();

    }

    


    public RoombaController getRoombaController()
    {
        return roombaController;
    }



}
