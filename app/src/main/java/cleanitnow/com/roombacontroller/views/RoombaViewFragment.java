package cleanitnow.com.roombacontroller.views;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.controller.RoombaController;
import cleanitnow.com.roombacontroller.views.BaseFragment;

/**
 * Created by madhur on 19-Jul-14.
 */
public class RoombaViewFragment extends BaseFragment
{
    private GridView gridView;
    private RoombaController roomBaController;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_roombaview, container, false);
        gridView=(GridView)v.findViewById(R.id.gridview);

        this.roomBaController=((MainActivity)getActivity()).getRoombaController();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Log.d("TAG", String.valueOf(width));
        Log.d("TAG", String.valueOf(height));


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        GridViewAdapter gridViewAdapter=new GridViewAdapter(getActivity(), roomBaController);
        gridView.setAdapter(gridViewAdapter);

        gridViewAdapter.setNumCols(gridView.getNumColumns());

        gridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        GridViewAdapter gridViewAdapter= (GridViewAdapter) gridView.getAdapter();
        if(gridViewAdapter!=null)
            gridViewAdapter.notifyDataSetChanged();


        return true;

    }

    @Override
    public void update()
    {

    }
}
