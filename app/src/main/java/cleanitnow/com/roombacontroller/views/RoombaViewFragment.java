package cleanitnow.com.roombacontroller.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cleanitnow.com.roombacontroller.App;
import cleanitnow.com.roombacontroller.Consts;
import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.Orientation;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.controller.RoombaController;

/**
 * Created by madhur on 19-Jul-14.
 */
public class RoombaViewFragment extends BaseFragment
{
    private ImageView roombaImage;
    private LinearLayout frameLayout;
    private RoombaView roombaView;
    private TextView positionTextView;

    private ObjectAnimator mover = null;
    private Animator.AnimatorListener animatorListener;

    BroadcastReceiver advanceReciever, turnReciever, rightReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        advanceReciever = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(!mover.isRunning())
                {
                    AnimateAdvance(Orientation.values()[intent.getIntExtra(Consts.PARAM_O, -1)]);
                    update();
                }
                else
                    Toast.makeText(getActivity(), "Roomba is busy", Toast.LENGTH_SHORT).show();
            }
        };

        turnReciever = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(!mover.isRunning())
                {
                    AnimateTurn(Orientation.values()[intent.getIntExtra(Consts.PARAM_O, -1)]);
                    update();
                }
                else
                    Toast.makeText(getActivity(), "Roomba is busy", Toast.LENGTH_SHORT).show();

            }
        };

        mover=new ObjectAnimator();

        animatorListener=new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
                AnimationFinished();
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        };


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_roombaview, container, false);
        roombaImage = (ImageView) v.findViewById(R.id.imgRoomba);
        frameLayout = (LinearLayout) v.findViewById(R.id.frame_layout);
        roombaView = (RoombaView) v.findViewById(R.id.roombaView);
        positionTextView = (TextView) v.findViewById(R.id.roomba_position);

        Log.d(App.TAG, String.valueOf(roombaView.getTotalWidth()));
        Log.d(App.TAG, String.valueOf(roombaView.getTotalHeight()));

        ViewTreeObserver observer = frameLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {

            @Override
            public void onGlobalLayout()
            {


                Log.d(App.TAG, String.valueOf(roombaView.getTotalWidth()));
                Log.d(App.TAG, String.valueOf(roombaView.getTotalHeight()));

                frameLayout.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(roombaView.getTotalWidth() / Consts.NUM_COLS, roombaView.getTotalHeight() / Consts.NUM_ROWS);

                layoutParams.topMargin = roombaView.getTotalHeight() - roombaView.getSquareHeight();
                roombaImage.setLayoutParams(layoutParams);
            }
        });


        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Log.d(App.TAG, String.valueOf(roombaView.getTotalWidth()));
        Log.d(App.TAG, String.valueOf(roombaView.getTotalHeight()));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(advanceReciever, new IntentFilter(Consts.ACTION_ADVANCE));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(turnReciever, new IntentFilter(Consts.ACTION_LEFT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(turnReciever, new IntentFilter(Consts.ACTION_RIGHT));

        update();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(advanceReciever);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(turnReciever);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


    }

    private void AnimateAdvance(Orientation orientation)
    {
        Log.d(App.TAG, "getTranslationY: " + String.valueOf(roombaImage.getTranslationY()));
        Log.d(App.TAG, "Y: " + String.valueOf(roombaImage.getY()));

        Log.d(App.TAG, "getSquareHeight: " + String.valueOf(roombaView.getSquareHeight()));
        Log.d(App.TAG, "getSquareWidth: " + String.valueOf(roombaView.getSquareWidth()));




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

        mover.addListener(animatorListener);

        mover.setDuration(700);

        mover.start();

    }

    private void AnimateTurn(Orientation orientation)
    {

        ObjectAnimator mover = null;
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

        mover.setDuration(700);

        mover.addListener(animatorListener);

        mover.start();



    }

    private void AnimationFinished()
    {
        RoombaController controller = ((MainActivity) getActivity()).getRoombaController();
        controller.setBusy(false);
    }

    public void update()
    {
        RoombaController controller = ((MainActivity) getActivity()).getRoombaController();
        String roombaPosition = controller.toString();

        positionTextView.setText(roombaPosition);
    }
}
