package cleanitnow.com.roombacontroller.positiondata;

import cleanitnow.com.roombacontroller.Consts;
import cleanitnow.com.roombacontroller.Orientation;

/**
 * Created by madhur on 19-Jul-14.
 * This class encapsulates the X,Y co-ordinates and orientation.
 * Implements low level function to move the Roomba controller.
 */
public class Position
{

    private int xPosition;
    private int yPosition;
    private Orientation orientation;

    public Position()
    {
        Reset();
    }

    public void Advance()
    {
        switch (getOrientation())
        {
            case NORTH:
               IncrementX();
                break;

            case SOUTH:
                DecrementX();
                break;

            case EAST:
                IncrementY();
                break;

            case WEST:
                DecrementY();
                break;

        }
    }

    public void TurnLeft()
    {
        switch (getOrientation())
        {
            case NORTH:
                orientation=Orientation.WEST;
                break;

            case SOUTH:
                orientation=Orientation.EAST;
                break;

            case EAST:
                orientation=Orientation.NORTH;
                break;

            case WEST:
                orientation=Orientation.SOUTH;
                break;

        }

    }

    public void TurnRight()
    {
        switch (getOrientation())
        {
            case NORTH:
                orientation=Orientation.EAST;
                break;

            case SOUTH:
                orientation=Orientation.WEST;
                break;

            case EAST:
                orientation=Orientation.SOUTH;
                break;

            case WEST:
                orientation=Orientation.NORTH;
                break;

        }

    }

    public void Reset()
    {
        xPosition=0;
        yPosition=0;
        orientation=Orientation.NORTH;
    }


    public void IncrementX()
    {
        if(getxPosition() == Consts.MAX_X)
            return;

        xPosition=getxPosition()+1;
    }

    public void DecrementX()
    {
        if(getxPosition()==0)
            return;

        xPosition=getxPosition()-1;
    }

    public void IncrementY()
    {
        if(getyPosition() == Consts.MAX_Y)
            return;

        yPosition=getyPosition()+1;

    }

    public void DecrementY()
    {
        if(getyPosition()==0)
            return;

        yPosition=getyPosition()-1;

    }

    public int getxPosition()
    {
        return xPosition;
    }

    public int getyPosition()
    {
        return yPosition;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }


}
