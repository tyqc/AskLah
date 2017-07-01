package com.gillyweed.android.asklah;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Envy 15 on 25/6/2017.
 */

public class ChangeUsernameDialogFragment extends DialogFragment {

    public static final String MyPref = "MyPrefs";
    SharedPreferences sharedPreferences;
    EditText new_usernameText;
    TextView newUsernameText;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_username, null);
        new_usernameText = (EditText) view.findViewById(R.id.new_usernameText);
        sharedPreferences = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        builder.setView(inflater.inflate(R.layout.dialog_change_username, null))
                .setPositiveButton("Save", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String newUsername = new_usernameText.getText().toString();


                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("studentUName", newUsername);
                        editor.putString("tutorUName", newUsername);
                        editor.commit();

                        ProfileFragment profileFragment = (ProfileFragment) getTargetFragment();

                        profileFragment.setUsername();
                        
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                   public void onClick(DialogInterface dialog, int id)
                   {
                       ChangeUsernameDialogFragment.this.getDialog().cancel();
                   }
                });

        return builder.create();
    }


}
