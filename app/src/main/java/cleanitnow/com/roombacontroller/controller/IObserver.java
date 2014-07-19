package cleanitnow.com.roombacontroller.controller;

/**
 * Created by madhur on 19-Jul-14.
 * Interface definition of a Roomba position observer.
   This interface will be implemented by anyone which wishes to notified of change in roomba position
 */

public interface IObserver
{
    void update();
}
