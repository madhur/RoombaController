package cleanitnow.com.roombacontroller.views;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.controller.IObserver;
import cleanitnow.com.roombacontroller.controller.RoombaController;

/**
 * Created by madhur on 19-Jul-14.
 */
public abstract class BaseFragment extends Fragment implements IObserver, CommandDialog.DialogListener
{


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        ((MainActivity) getActivity()).getRoombaController().setObserver(this);


    }

    @Override
    public void onDetach()
    {
        super.onDetach();

        ((MainActivity) getActivity()).getRoombaController().removeObserver(this);


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
        if (id == R.id.action_settings)
        {
            return true;
        }
        else if (id == R.id.action_advance)
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


    @Override
    public abstract void update();


    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        dialog.dismiss();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {

        RoombaController controller=((MainActivity) getActivity()).getRoombaController();
        EditText commandText= (EditText) dialog.getDialog().findViewById(R.id.command);

        String command= String.valueOf(commandText.getText());

        for(char c: command.toCharArray())
        {
            if(c=='l' || c=='L')
            {
                controller.TurnLeft();
            }
            else if(c=='r' || c=='R')
            {
                controller.TurnRight();
            }
            else if (c=='a' || c== 'A')
            {
                controller.Advance();
            }


        }


    }
}
