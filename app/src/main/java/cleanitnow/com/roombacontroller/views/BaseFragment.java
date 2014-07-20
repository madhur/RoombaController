package cleanitnow.com.roombacontroller.views;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

import cleanitnow.com.roombacontroller.App;
import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.controller.RoombaController;

/**
 * Created by madhur on 19-Jul-14.
 */
public abstract class BaseFragment extends Fragment
{

    protected  int screenWidth, screenHeight;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);



        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();

        screenWidth = metrics.widthPixels ;
        screenHeight = metrics.heightPixels;

        Rect rect = new Rect();
        Window win =getActivity().getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusHeight = rect.top;
        int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleHeight = contentViewTop - statusHeight;

        screenHeight=screenHeight - (titleHeight + contentViewTop);

        Log.d(App.TAG, "Screen width: " + screenWidth);
        Log.d(App.TAG, "Screen height: " + screenHeight);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_advance)
        {

            ((MainActivity) getActivity()).getRoombaController().Advance();
        }

        else if (id == R.id.action_left)
        {
            ((MainActivity) getActivity()).getRoombaController().TurnLeft();

            return true;

        }

        else if (id == R.id.action_right)
        {
            ((MainActivity) getActivity()).getRoombaController().TurnRight();

            return true;
        }
        else if (id == R.id.action_reset)
        {
            ((MainActivity) getActivity()).getRoombaController().Reset();

            return true;
        }
        else if (id == R.id.action_command)
        {

            new CommandDialog().show(getFragmentManager(), "tag");
        }


        return false;
    }


}
