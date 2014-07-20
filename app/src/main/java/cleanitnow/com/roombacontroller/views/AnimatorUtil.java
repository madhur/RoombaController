package cleanitnow.com.roombacontroller.views;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

import cleanitnow.com.roombacontroller.Orientation;

/**
 * Created by madhur on 20-Jul-14.
 * Utility class for calculating animations
 */
class AnimatorUtil
{

    /**
     * Gets the animation corresponding to advance command
     * @param roombaImage
     * @param roombaView
     * @param orientation
     * @return
     */
    public static ObjectAnimator GetAdvanceAmination(ImageView roombaImage, RoombaView roombaView,  Orientation orientation)
    {
        ObjectAnimator mover = null;

        switch (orientation)
        {
            case NORTH:
                mover = ObjectAnimator.ofFloat(roombaImage, "Y", roombaImage.getY(), roombaImage.getY() - roombaView.getSquareHeight());
                break;

            case SOUTH:
                mover = ObjectAnimator.ofFloat(roombaImage, "Y", roombaImage.getY(), roombaImage.getY() + roombaView.getSquareHeight());
                break;

            case EAST:
                mover = ObjectAnimator.ofFloat(roombaImage, "X", roombaImage.getX(), roombaImage.getX() + roombaView.getSquareWidth());
                break;

            case WEST:
                mover = ObjectAnimator.ofFloat(roombaImage, "X", roombaImage.getX(), roombaImage.getX() - roombaView.getSquareWidth());
                break;

        }

        return mover;

    }


    /**
     * Creates the Turn Animation
     * @param orientation
     * @return
     */
    public static ObjectAnimator GetTurnAnimation(ImageView roombaImage, Orientation orientation)
    {

        ObjectAnimator mover=null;

        float prevAngle = roombaImage.getRotation();

        int angle = orientation.getAngle();

        if (prevAngle == 360)
            prevAngle = 0;


        switch (orientation)
        {

            case NORTH:

                if (prevAngle == 270)
                    angle = 360;

                mover = ObjectAnimator.ofFloat(roombaImage, "Rotation", prevAngle, angle);
                break;

            case SOUTH:


                mover = ObjectAnimator.ofFloat(roombaImage, "Rotation", prevAngle, angle);
                break;

            case EAST:


                mover = ObjectAnimator.ofFloat(roombaImage, "Rotation", prevAngle, angle);
                break;

            case WEST:

                if (prevAngle == 0)
                    prevAngle = 360;


                mover = ObjectAnimator.ofFloat(roombaImage, "Rotation", prevAngle, angle);
                break;

        }

        return mover;

    }



}
