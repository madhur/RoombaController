package cleanitnow.com.roombacontroller.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.controller.RoombaController;

/**
 * Created by madhur on 19-Jul-14.
 * This fragment implements the map view. This fragment also acts as an observer to be notified of Roomba position changes.
 */
public class MapViewFragment extends BaseFragment
{
    private GridView gridView;
    private RoombaController roomBaController;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_roombaview, container, false);
        gridView=(GridView)v.findViewById(R.id.gridview);

        this.roomBaController=((MainActivity)getActivity()).getRoombaController();

        return v;
    }

    /**
     *Renders the grid.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        GridViewAdapter gridViewAdapter=new GridViewAdapter(getActivity(), roomBaController);
        gridView.setAdapter(gridViewAdapter);

        gridViewAdapter.setNumCols(gridView.getNumColumns());

        gridViewAdapter.notifyDataSetChanged();
    }

    /**
     * This method calls the super class implementation to do most of its work. This implementation simply notifies the adapter to update itself.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        GridViewAdapter gridViewAdapter= (GridViewAdapter) gridView.getAdapter();
        if(gridViewAdapter!=null)
            gridViewAdapter.notifyDataSetChanged();


        return true;

    }

    /**
     * Map View does not render the position. Hence this method is not used. It can be used in future.
     */
    @Override
    public void update()
    {

    }
}
