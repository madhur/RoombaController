package cleanitnow.com.roombacontroller.controller;

/**
 * Created by madhur on 19-Jul-14.
 *  Interface which defines the operations of Roomba Controller: Advance, Turn Left, Turn Right
    The interface also acts as a subscriber which notifies the observers whenever the roomba position changes
 */


public interface IController
{

    void Advance();

    void TurnLeft();

    void TurnRight();

    void setObserver(IObserver observer);

    void positionChanged();

    void removeObserver(IObserver observer);
}
