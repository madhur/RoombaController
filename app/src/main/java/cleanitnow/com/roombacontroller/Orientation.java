package cleanitnow.com.roombacontroller;

/**
 * Created by madhur on 19-Jul-14.
 * This enum lists the various orientations which Roomba can have.
 */
public enum Orientation
{
    NORTH(0), EAST(90), WEST(270), SOUTH(180);

    private final int angle;

    Orientation(int angle)
    {
        this.angle = angle;
    }

    public int getAngle()
    {
        return angle;
    }
}
