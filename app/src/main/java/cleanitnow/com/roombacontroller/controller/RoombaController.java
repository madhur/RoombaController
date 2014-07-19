package cleanitnow.com.roombacontroller.controller;

import java.util.ArrayList;

import cleanitnow.com.roombacontroller.Orientation;
import cleanitnow.com.roombacontroller.positiondata.Position;

/**
 * Created by madhur on 19-Jul-14.
 */



/**
 * Implementation of Roomba Controller
  */
public class RoombaController implements  IController
{

    /**
     * X, Y , orientation are encapsulated in Position class
     */
    private Position position;


    /**
     * ArrayList to hold the observers
     */
    private ArrayList<IObserver> observers=new ArrayList<IObserver>();

    public RoombaController()
    {

        position=new Position();
    }

    /**
     *  Advance the roomba position. Notify the observers
     */
    @Override
    public void Advance()
    {

        position.Advance();

        positionChanged();

    }

    /**
     *Advance the roomba position. Notify the observers
      */
    @Override
    public void TurnLeft()
    {

        position.TurnLeft();
        positionChanged();

    }

    /**
     * Turn right. Notify the observers.
     */
    @Override
    public void TurnRight()
    {

        position.TurnRight();
        positionChanged();

    }

    /**
     * Reset the Roomba position
     */
    public void Reset()
    {
        position.Reset();

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


    /**
     * Print the string representation of Controller
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
