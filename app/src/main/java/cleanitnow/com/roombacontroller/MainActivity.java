package cleanitnow.com.roombacontroller;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import cleanitnow.com.roombacontroller.controller.IController;
import cleanitnow.com.roombacontroller.controller.RoombaController;
import cleanitnow.com.roombacontroller.views.RoombaViewFragment;

/**
 * Main Activity of the application. Responsible for hosting @RoombaViewFragment
 */
public class MainActivity extends ActionBarActivity
{
    /***
     * The instance of the RoombaController which implements IController interface.
     */
    private IController roombaController;

    /***
     * Initialize and setup the action bar with the spinner
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);


        roombaController=new RoombaController(this);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new RoombaViewFragment()).commit();

    }

    


    public IController getRoombaController()
    {
        return roombaController;
    }



}
