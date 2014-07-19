package cleanitnow.com.roombacontroller.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import cleanitnow.com.roombacontroller.MainActivity;
import cleanitnow.com.roombacontroller.R;

/**
 * Created by madhur on 19-Jul-14.
 * This dialog class implements a dialog box where user can type the commands to be given to controller through keyboard
 */
public class CommandDialog extends DialogFragment
{
    public interface DialogListener
    {
        public void onDialogPositiveClick(DialogFragment dialog);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    DialogListener mListener;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);


            MainActivity mainActivity=(MainActivity)activity;
            mainActivity.getSupportFragmentManager().getFragments().get(0);
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (CommandDialog.DialogListener) mainActivity.getSupportFragmentManager().getFragments().get(0);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_command, null))
                // Add action buttons
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        mListener.onDialogPositiveClick(CommandDialog.this);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        mListener.onDialogNegativeClick(CommandDialog.this);
                    }
                }).setTitle(R.string.give_command);
        return builder.create();
    }
}
