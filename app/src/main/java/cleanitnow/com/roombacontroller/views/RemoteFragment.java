package cleanitnow.com.roombacontroller.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.views.BaseFragment;

/**
 * Created by madhur on 19-Jul-14.
 * This fragment implements the remote view. This fragment also acts as an observer to be notified of Roomba position changes.
 */
public class RemoteFragment extends BaseFragment
{

    private TextView positionTextView;
    private ImageView roombaImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_main, container, false);
        positionTextView=(TextView)v.findViewById(R.id.roomba_position);
        roombaImageView=(ImageView)v.findViewById(R.id.imgRoomba);

        update();

        return v;
    }


    /***
     * This method first calls the parent class implementation to let make changes to controller according to given command.
     * This implementation only rotates the image according to command.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Call the super implementation to advance the controller. This implementation only roates the image in current fragment
        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        // Currently there are no settings int the application. Do nothing.
        if (id == R.id.action_settings)
        {
            return true;
        }
        else if(id==R.id.action_advance)
        {
            // Do nothing no rotation is required.

        }

        else if(id==R.id.action_left)
        {

            // Rotate left
            roombaImageView.setRotation( ((MainActivity)getActivity()).getRoombaController().getOrientation().getAngle());
            return true;

        }

        else if(id==R.id.action_right)
        {
            // Rotate right
            roombaImageView.setRotation( ((MainActivity)getActivity()).getRoombaController().getOrientation().getAngle());
            return true;
        }
        else if(id==R.id.action_reset)
        {

            // Reset
            roombaImageView.setRotation(0);
            return true;
        }

        return false;
    }

    /**
     * This method will be called by controller as a callback whenever the position changes.
     */
    @Override
    public void update()
    {
        String roombaPosition=((MainActivity)getActivity()).getRoombaController().toString();

        positionTextView.setText(roombaPosition);

    }


}
