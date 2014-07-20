package cleanitnow.com.roombacontroller.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

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

    private boolean isBusy;


    public RoombaController(Context context)
    {

        position = new Position();
        this.context = context;
        setBusy(false);
    }

    /**
     * Advance the roomba position. Notify the observers
     */
    @Override
    public void Advance()
    {
        if( !isBusy)
        {
            if (position.Advance())
            {

                LocalBroadcastManager.getInstance(context).sendBroadcast(PrepareIntent(Consts.ACTION_ADVANCE));
                isBusy = true;
            }
        }

    }

    /**
     * Advance the roomba position. Notify the observers
     */
    @Override
    public void TurnLeft()
    {

        if(!isBusy)
        {
            position.TurnLeft();
            LocalBroadcastManager.getInstance(context).sendBroadcast(PrepareIntent(Consts.ACTION_LEFT));
            isBusy=true;
        }


    }

    /**
     * Turn right. Notify the observers.
     */
    @Override
    public void TurnRight()
    {
        if(!isBusy)
        {

            position.TurnRight();
            LocalBroadcastManager.getInstance(context).sendBroadcast(PrepareIntent(Consts.ACTION_RIGHT));
            isBusy=true;
        }


    }

    public void ProcessCommand(String command)
    {
        // Cannot process command while roomba is busy.
        if(isBusy)
            return;

        isBusy=true;

        // We prepare the intent first, because we want to pass the initial orientation, not the final one.
        Intent intent=PrepareIntent(Consts.ACTION_COMMAND);
        intent.putExtra(Consts.PARAM_CMD, command);

        for(char c: command.toCharArray())
        {
            if(c=='l' || c=='L')
            {
                position.TurnLeft();
            }
            else if(c=='r' || c=='R')
            {
                position.TurnRight();
            }
            else if (c=='a' || c== 'A')
            {
                position.Advance();
            }

        }



        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


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

    public void setBusy(boolean isBusy)
    {
        this.isBusy = isBusy;
    }
}
