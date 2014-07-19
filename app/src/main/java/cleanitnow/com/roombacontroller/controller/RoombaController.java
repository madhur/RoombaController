package cleanitnow.com.roombacontroller.controller;

import java.util.ArrayList;

import cleanitnow.com.roombacontroller.Orientation;

/**
 * Created by madhur on 19-Jul-14.
 */
public class RoombaController implements  IController
{
    private int xPosition;
    private int yPosition;
    private Orientation orientation;
    private ArrayList<IObserver> observers=new ArrayList<IObserver>();

    public RoombaController()
    {
        Reset();
    }

    @Override
    public void Advance()
    {
        switch (getOrientation())
        {
            case NORTH:
                xPosition= getxPosition() +1;
                break;

            case SOUTH:
                xPosition= getxPosition() -1;
                break;

            case EAST:
                yPosition= getyPosition() +1;
                break;

            case WEST:
                yPosition= getyPosition() -1;
                break;

        }

        positionChanged();

    }

    @Override
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

        positionChanged();

    }

    @Override
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

        positionChanged();

    }

    @Override
    public void setObserver(IObserver observer)
    {
        if(!observers.contains(observer))
        {
            observers.add(observer);
        }

    }

    @Override
    public void positionChanged()
    {
        for(IObserver observer: observers)
        {
            observer.update();
        }

    }

    @Override
    public void removeObserver(IObserver observer)
    {
        if(observer!=null && observers.contains(observer))
            observers.remove(observer);
    }

    public void Reset()
    {
        xPosition=0;
        yPosition=0;
        orientation=Orientation.NORTH;
    }

    @Override
    public String toString()
    {
        return String.format("%d, %d : %s", getxPosition(), getyPosition(), getOrientation());
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
