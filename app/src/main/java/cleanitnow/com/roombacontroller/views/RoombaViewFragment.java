package cleanitnow.com.roombacontroller.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cleanitnow.com.roombacontroller.App;
import cleanitnow.com.roombacontroller.Consts;
import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.Orientation;
import cleanitnow.com.roombacontroller.R;
import cleanitnow.com.roombacontroller.controller.RoombaController;

/**
 * Created by madhur on 19-Jul-14.
 */
public class RoombaViewFragment extends BaseFragment implements CommandDialog.DialogListener
{
    private ImageView roombaImage;
    private LinearLayout frameLayout;
    private RoombaView roombaView;
    private TextView positionTextView;
    private boolean isRunning=false;


    private Animator.AnimatorListener animatorListener, commandanimatorListener;

    private BroadcastReceiver advanceReciever, turnReciever, commandReciever;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        advanceReciever = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(!isRunning)
                {
                    AnimateAdvance(Orientation.values()[intent.getIntExtra(Consts.PARAM_O, -1)]);
                    updateStatus();
                }
                else
                    ShowToast();
            }
        };

        turnReciever = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(!isRunning)
                {
                    AnimateTurn(Orientation.values()[intent.getIntExtra(Consts.PARAM_O, -1)]);
                    updateStatus();
                }
                else
                    ShowToast();

            }
        };

        commandReciever=new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(!isRunning)
                {
                    AnimateCommand(intent.getStringExtra(Consts.PARAM_CMD), Orientation.values()[intent.getIntExtra(Consts.PARAM_O, -1)]);
                    updateStatus();
                }
                else
                   ShowToast();

            }
        };



        animatorListener=new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
                isRunning=true;
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
                AnimationFinished();
                isRunning=false;
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

        commandanimatorListener=new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
                isRunning=true;
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
                AnimationFinished();
                isRunning=false;
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

    private void ShowToast()
    {
        Toast.makeText(getActivity(), "Roomba is busy", Toast.LENGTH_SHORT).show();
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

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(advanceReciever, new IntentFilter(Consts.ACTION_ADVANCE));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(turnReciever, new IntentFilter(Consts.ACTION_LEFT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(turnReciever, new IntentFilter(Consts.ACTION_RIGHT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(commandReciever, new IntentFilter(Consts.ACTION_COMMAND));

        updateStatus();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(advanceReciever);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(turnReciever);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(commandReciever);
    }


    private void AnimateCommand(String command, Orientation orientation)
    {
        AnimatorSet set = new AnimatorSet();
        ArrayList<Animator> items=new ArrayList<Animator>();

        for(char c: command.toCharArray())
        {
            if(c=='l' || c=='L')
            {
                items.add(GetTurnAnimation(orientation));

            }
            else if(c=='r' || c=='R')
            {
                items.add(GetTurnAnimation(orientation));
            }
            else if (c=='a' || c== 'A')
            {
                items.add(GetAdvanceAmination(orientation));

              //  StartRoombaAnimation(GetAdvanceAmination(orientation));
            }

        }

        set.addListener(animatorListener);
        set.setDuration(700);
        set.playSequentially(items);

        set.start();


    }

    private void AnimateAdvance(Orientation orientation)
    {
        ObjectAnimator mover=GetAdvanceAmination(orientation);

       StartRoombaAnimation(mover);

    }

    private ObjectAnimator GetAdvanceAmination(Orientation orientation)
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

    private void AnimateTurn(Orientation orientation)
    {

        ObjectAnimator mover=GetTurnAnimation(orientation);

        StartRoombaAnimation(mover);


    }

    private ObjectAnimator GetTurnAnimation(Orientation orientation)
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

    private void StartRoombaAnimation(ObjectAnimator mover)
    {
        mover.setDuration(Consts.ANIMATION_DURATION);

        mover.addListener(animatorListener);

        mover.start();

    }

    private void AnimationFinished()
    {
        RoombaController controller = ((MainActivity) getActivity()).getRoombaController();
        controller.setBusy(false);
    }

    public void updateStatus()
    {
        RoombaController controller = ((MainActivity) getActivity()).getRoombaController();
        String roombaPosition = controller.toString();

        positionTextView.setText(roombaPosition);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        dialog.dismiss();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {

        RoombaController controller=((MainActivity) getActivity()).getRoombaController();
        EditText commandText= (EditText) dialog.getDialog().findViewById(R.id.command);

        String command= String.valueOf(commandText.getText());

        controller.ProcessCommand(command);


    }
}
