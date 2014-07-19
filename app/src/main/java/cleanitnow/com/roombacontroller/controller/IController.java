package cleanitnow.com.roombacontroller.controller;

/**
 * Created by madhur on 19-Jul-14.
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
