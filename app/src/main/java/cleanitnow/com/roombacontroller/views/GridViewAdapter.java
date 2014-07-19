package cleanitnow.com.roombacontroller.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import cleanitnow.com.roombacontroller.Consts;
import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.controller.RoombaController;

/**
 * Created by madhur on 19-Jul-14.
 * The @GridViewAdapter is responsible for laying out grid and rendering the Roomba at appropriate position.
 */
public class GridViewAdapter extends BaseAdapter
{
    private RoombaController roombaController;
    private Context context;
    private int numCols;

    public GridViewAdapter(Context context, RoombaController roombaController)
    {
        this.roombaController=roombaController;
        this.context=context;

    }

    @Override
    public int getCount()
    {
        return Consts.NUM_GRIDS;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * This method implements the View holder pattern to render the grid. Roomba position is calculated by using X,Y co-ordinates and origentation
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        View v;
        if(convertView==null)
        {
            v=LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);

            holder=new ViewHolder();
            holder.roombaView=(ImageView)v.findViewById(R.id.imgRoomba);
            holder.position=(TextView)v.findViewById(R.id.position);

            v.setTag(holder);

        }
        else
        {
            v=convertView;
            holder= (ViewHolder) v.getTag();
        }


        int origin=Consts.NUM_GRIDS- getNumCols();

        int xDispacement=origin - ((roombaController.getxPosition())* getNumCols());
        int yDisplacement=roombaController.getyPosition();

        int roombaPosition=xDispacement+yDisplacement;



        holder.position.setText(String.valueOf(position));
        if(position==roombaPosition)
        {

            holder.roombaView.setVisibility(View.VISIBLE);
        }
        else
        {

            holder.roombaView.setVisibility(View.INVISIBLE);
        }

        holder.roombaView.setRotation( roombaController.getOrientation().getAngle());

        return v;
    }


    public int getNumCols()
    {
        return 8;
    }

    public void setNumCols(int numCols)
    {
        this.numCols = numCols;
    }


    public static class ViewHolder
    {
        ImageView roombaView;
        TextView position;
    }
}
