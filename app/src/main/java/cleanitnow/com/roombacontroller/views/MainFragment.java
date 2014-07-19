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
 */
public class MainFragment extends BaseFragment
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         super.onOptionsItemSelected(item);

        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        else if(id==R.id.action_advance)
        {


        }

        else if(id==R.id.action_left)
        {

            roombaImageView.setRotation( ((MainActivity)getActivity()).getRoombaController().getOrientation().getAngle());
            return true;

        }

        else if(id==R.id.action_right)
        {

            roombaImageView.setRotation( ((MainActivity)getActivity()).getRoombaController().getOrientation().getAngle());
            return true;
        }
        else if(id==R.id.action_reset)
        {

            roombaImageView.setRotation(0);
            return true;
        }

        return false;
    }

    @Override
    public void update()
    {
        String roombaPosition=((MainActivity)getActivity()).getRoombaController().toString();

        positionTextView.setText(roombaPosition);

    }


}
