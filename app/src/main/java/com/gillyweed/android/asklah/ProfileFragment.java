package com.gillyweed.android.asklah;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView usernameTextView = (TextView) rootView.findViewById(R.id.username_text_view);
        usernameTextView.setText("@kyholmes");

        TextView yourAchievements = (TextView) rootView.findViewById(R.id.your_achievements);

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
}
