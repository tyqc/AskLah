package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    public static final String MyPref = "MyPrefs";
    SharedPreferences sharedPreferences;
    TextView usernameTextView;
    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        usernameTextView = (TextView) rootView.findViewById(R.id.username_text_view);
        sharedPreferences = getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        usernameTextView.setText("@" + sharedPreferences.getString("usernameStudent", ""));

        TextView yourAchievements = (TextView) rootView.findViewById(R.id.your_achievements);

        TextView changeUsername = (TextView) rootView.findViewById(R.id.change_usernameText);

        changeUsername.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new ChangeUsernameDialogFragment();
//                newFragment.setTargetFragment(ProfileFragment.this, 1000);
                newFragment.show(getFragmentManager(), "dialog");

//                usernameTextView.setText("@" + sharedPreferences.getString("usernameStudent", ""));
            }
        });

        // Set a ClickListener on that view
        yourAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent to open the {@link AchievementsAcitivty}
                Intent achievementsIntent = new Intent(getActivity(), AchievementsActivity.class);

                // Start the new activity
                startActivity(achievementsIntent);
            }
        });
        return rootView;
    }

    public void setUsername()
    {
        usernameTextView.setText("@" + sharedPreferences.getString("usernameStudent", ""));
    }
}
