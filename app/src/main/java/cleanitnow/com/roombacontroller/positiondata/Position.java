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

    public boolean Advance()
    {
        switch (getOrientation())
        {
            case NORTH:
               return IncrementX();

            case SOUTH:
                return DecrementX();


            case EAST:
                return IncrementY();


            case WEST:
                return DecrementY();

            default:
                return false;

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


    public boolean IncrementX()
    {
        if(getxPosition() == Consts.NUM_ROWS -1)
            return false;

        xPosition=getxPosition()+1;

        return true;
    }

    public boolean DecrementX()
    {
        if(getxPosition()==0)
            return false;

        xPosition=getxPosition()-1;

        return true;
    }

    public boolean IncrementY()
    {
        if(getyPosition() == Consts.NUM_COLS-1)
            return false;

        yPosition=getyPosition()+1;

        return true;

    }

    public boolean DecrementY()
    {
        if(getyPosition()==0)
            return false;

        yPosition=getyPosition()-1;

        return true;

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
