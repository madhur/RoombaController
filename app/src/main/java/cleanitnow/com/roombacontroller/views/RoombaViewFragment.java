package cleanitnow.com.roombacontroller.views;

import android.animation.Animator;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
public class RoombaViewFragment extends BaseFragment implements CommandDialog.DialogListener
{
    private ImageView roombaImage;
    private FrameLayout frameLayout;
    private RoombaView roombaView;
    private TextView positionTextView;
    private boolean isRunning=false;


    private String command;
    private int index=0;
    private Orientation previousOrientation;


    private Animator.AnimatorListener animatorListener, commandanimatorListener;

    private BroadcastReceiver advanceReciever, turnReciever, commandReciever;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /**
         *Broadcast Reciever to handle Advance command
          */
        advanceReciever = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(!isRunning)
                {
                    AnimateAdvance(Orientation.values()[intent.getIntExtra(Consts.PARAM_O, -1)]);
                }
                else
                    ShowToast();
            }
        };

        /**
         * Reciever to handle turn command
         */
        turnReciever = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(!isRunning)
                {
                    AnimateTurn(Orientation.values()[intent.getIntExtra(Consts.PARAM_O, -1)]);
                }
                else
                    ShowToast();

            }
        };

        /**
         * Reciever to handle command given from the keyboard
         */
        commandReciever=new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(!isRunning)
                {
                    AnimateCommand(intent.getStringExtra(Consts.PARAM_CMD), Orientation.values()[intent.getIntExtra(Consts.PARAM_O, -1)]);
                }
                else
                   ShowToast();

            }
        };


        /**
         * Listener for roomba animation of single command
         */
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

                updateStatus();
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

        /**
         * Listener for roomba animation of keyboard command
         */
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
                // The complete animation is not finished yet. We process the next character index in command and restart the animation.
                index=index+1;

                if(command.length()> index)
                {
                    Log.d(App.TAG, "Executing command index " + String.valueOf(index) + " for " + String.valueOf(command.charAt(index)) + " orientation " +previousOrientation );
                    ExecuteCharacterCommand(command.charAt(index), previousOrientation);
                }
                else
                {
                    //animation is finished. Reset the index
                    Log.d(App.TAG, "Animation finish");
                    index=0;

                    // Since animation is finished - reset running indicator, hide progress, reset status on controller, update status
                    isRunning=false;
                    ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
                    AnimationFinished();

                    updateStatus();

                }

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

    /**
     * Retrieve the references to views. Setup the initial location of Roomba.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_roombaview, container, false);
        roombaImage = (ImageView) v.findViewById(R.id.imgRoomba);
        frameLayout = (FrameLayout) v.findViewById(R.id.frame_layout);
        roombaView = (RoombaView) v.findViewById(R.id.roombaView);
        positionTextView = (TextView) v.findViewById(R.id.roomba_position);

        ViewTreeObserver observer = frameLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {

            @Override
            public void onGlobalLayout()
            {

                frameLayout.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

               InitRoombaPosition();
            }
        });


        return v;
    }


    private void InitRoombaPosition()
    {



        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(roombaView.getTotalWidth() / Consts.NUM_COLS, roombaView.getTotalHeight() / Consts.NUM_ROWS);

        layoutParams.topMargin = roombaView.getTotalHeight() - roombaView.getSquareHeight();
        roombaImage.setLayoutParams(layoutParams);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(super.onOptionsItemSelected(item))
        {
            return true;
        }
        else if(item.getItemId()==R.id.action_reset)
        {
            ((MainActivity) getActivity()).getRoombaController().Reset();

            roombaImage.setTranslationX(0);
            roombaImage.setTranslationY(0);

            updateStatus();

            return true;
        }

        return false;

    }

    /**
     * We register the broadcast listeners in @onResume
     */
    @Override
    public void onResume()
    {
        super.onResume();

        // Register the recievers to listen for intents
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(advanceReciever, new IntentFilter(Consts.ACTION_ADVANCE));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(turnReciever, new IntentFilter(Consts.ACTION_LEFT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(turnReciever, new IntentFilter(Consts.ACTION_RIGHT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(commandReciever, new IntentFilter(Consts.ACTION_COMMAND));

        updateStatus();
    }

    /**
     * We remove the broadcast listeners in @onPause
     */
    @Override
    public void onPause()
    {
        super.onPause();

        // Remove the recievers when fragment is paused
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(advanceReciever);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(turnReciever);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(commandReciever);
    }


    /**
     * This is the first animate command given from the broadcast reciever. Subsequent commands are handled in the End listener of the Animation
     * @param command
     * @param orientation
     */
    private void AnimateCommand(String command, Orientation orientation)
    {

        this.command=command;
        char c=command.charAt(0);

        Log.d(App.TAG, "Executing command index 0 for " + String.valueOf(c) + " orientation " + String.valueOf(orientation));
        ExecuteCharacterCommand(c, orientation);

    }

    /**
     * Executes a single character command. Called from the End listener of the Animation
     * @param c
     * @param orientation
     */
    private void ExecuteCharacterCommand(char c, Orientation orientation)
    {
        Orientation  end;

        ObjectAnimator mover=null;

        if(c=='l' || c=='L')
        {
            if(previousOrientation==Orientation.NORTH)
                end=Orientation.WEST;
            else if(previousOrientation==Orientation.WEST)
                end=Orientation.SOUTH;
            else if(previousOrientation== Orientation.EAST)
                end=Orientation.NORTH;
            else if(previousOrientation == Orientation.SOUTH)
                end=Orientation.EAST;
            else
                end=orientation;

            mover=AnimatorUtil.GetTurnAnimation(roombaImage, end);
            previousOrientation=end;

        }
        else if(c=='r' || c=='R')
        {
            if(previousOrientation==Orientation.NORTH)
                end=Orientation.EAST;
            else if(previousOrientation==Orientation.WEST)
                end=Orientation.NORTH;
            else if(previousOrientation== Orientation.EAST)
                end=Orientation.SOUTH;
            else if(previousOrientation == Orientation.SOUTH)
                end=Orientation.WEST;
            else
                end=orientation;

            mover=AnimatorUtil.GetTurnAnimation(roombaImage, end);
            previousOrientation=end;
        }
        else if (c=='a' || c== 'A')
        {
            mover=AnimatorUtil.GetAdvanceAmination(roombaImage, roombaView, orientation);
            previousOrientation=orientation;

        }

        mover.setDuration(Consts.ANIMATION_DURATION);

        mover.addListener(commandanimatorListener);

        mover.start();

    }

    /**
     * Gets the animation corresponding to Advance command
     * @param orientation
     */
    private void AnimateAdvance(Orientation orientation)
    {
        ObjectAnimator mover=AnimatorUtil.GetAdvanceAmination(roombaImage, roombaView, orientation);

        StartRoombaAnimation(mover);

    }

    /**
     * Gets the animation corresponding to left or right command
     * @param orientation
     */
    private void AnimateTurn(Orientation orientation)
    {
        ObjectAnimator mover=AnimatorUtil.GetTurnAnimation(roombaImage, orientation);

        StartRoombaAnimation(mover);
    }


    /**
     * Starts the Roomba animation
     * @param mover
     */
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

    public void updateStatus(String status)
    {
        positionTextView.setText(status);
    }

    /**
     * Updates the status of roomba position on fragment
     */
    public void updateStatus()
    {
        RoombaController controller = ((MainActivity) getActivity()).getRoombaController();
        String roombaPosition = controller.toString();

        updateStatus(roombaPosition);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        dialog.dismiss();
    }

    /**
     * Handles Submit button of command dialog box
     * @param dialog
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {

        RoombaController controller=((MainActivity) getActivity()).getRoombaController();
        EditText commandText= (EditText) dialog.getDialog().findViewById(R.id.command);

        // Get the command
        String command= String.valueOf(commandText.getText());

        // Send it to controller for processing. Controller will in turn send the intent to update UI after filtering invalid commands
        if(!TextUtils.isEmpty(command))
            controller.ProcessCommand(command);


    }
}
