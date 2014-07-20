package cleanitnow.com.roombacontroller.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;

import cleanitnow.com.roombacontroller.App;
import cleanitnow.com.roombacontroller.Consts;
import cleanitnow.com.roombacontroller.Orientation;
import cleanitnow.com.roombacontroller.positiondata.Position;

/**
 * Created by madhur on 19-Jul-14.
 */


/**
 * Implementation of Roomba Controller
 */
public class RoombaController implements IController
{

    /**
     * X, Y , orientation are encapsulated in Position class
     */
    private Position position;

    private Context context;


    public RoombaController(Context context)
    {

        position = new Position();
        this.context = context;
    }

    /**
     * Advance the roomba position. Notify the observers
     */
    @Override
    public void Advance()
    {

        if (position.Advance())
        {


            LocalBroadcastManager.getInstance(context).sendBroadcast(PrepareIntent(Consts.ACTION_ADVANCE));
        }

    }

    /**
     * Advance the roomba position. Notify the observers
     */
    @Override
    public void TurnLeft()
    {

        position.TurnLeft();


        LocalBroadcastManager.getInstance(context).sendBroadcast(PrepareIntent(Consts.ACTION_LEFT));

    }

    /**
     * Turn right. Notify the observers.
     */
    @Override
    public void TurnRight()
    {

        position.TurnRight();


        LocalBroadcastManager.getInstance(context).sendBroadcast(PrepareIntent(Consts.ACTION_RIGHT));

    }

    /**
     * Prepare the intent based on Controller action. Put the parameters as extras
     *
     * @param action
     * @return
     */
    private Intent PrepareIntent(String action)
    {

        Intent intent = new Intent();

        intent.setAction(action);
        intent.putExtra(Consts.PARAM_X, getxPosition());
        intent.putExtra(Consts.PARAM_Y, getyPosition());
        intent.putExtra(Consts.PARAM_O, getOrientation().ordinal());

        return intent;

    }

    /**
     * Reset the Roomba position
     */
    public void Reset()
    {
        position.Reset();
    }

    /**
     * Print the string representation of Controller
     *
     * @return
     */
    @Override
    public String toString()
    {
        return String.format("%d, %d : %s", getxPosition(), getyPosition(), getOrientation());
    }

    public int getxPosition()
    {
        return position.getxPosition();
    }

    public int getyPosition()
    {
        return position.getyPosition();
    }

    public Orientation getOrientation()
    {
        return position.getOrientation();
    }
}
