package cleanitnow.com.roombacontroller.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.controller.IController;

/**
 * Created by madhur on 19-Jul-14.
 */
public abstract class BaseFragment extends Fragment
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /***
     * Inflate the menu
     * @param menu
     * @param inflater
     */


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
    }


    /***
     * This method is called by framework whenver the menu option is selected from the fragment
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        IController controller=((MainActivity) getActivity()).getRoombaController();

        if (id == R.id.action_advance)
        {

             controller.Advance();
            return true;
        }

        else if (id == R.id.action_left)
        {
            controller.TurnLeft();

            return true;

        }

        else if (id == R.id.action_right)
        {
            controller.TurnRight();

            return true;
        }
        else if (id == R.id.action_command)
        {

            new CommandDialog().show(getFragmentManager(), "tag");
            return true;
        }


        return false;
    }


}
