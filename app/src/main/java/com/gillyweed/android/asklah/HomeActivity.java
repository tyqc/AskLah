package com.gillyweed.android.asklah;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Button that links to achievements
        Button achievementButton = (Button) findViewById(R.id.achievement_button);
        // Set a clickListener on that view
        achievementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent to open the {@link AchievementsActivity}
                Intent achievementIntent = new Intent(HomeActivity.this, AchievementsActivity.class);

                // Start the new activity
                startActivity(achievementIntent);
            }
        });

        // Button that links to settings
        Button settingsButton = (Button) findViewById(R.id.settings_button);
        // Set a clickListener on that view
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent to open the {@link AchievementsActivity}
                Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);

                // Start the new activity
                startActivity(settingsIntent);
            }
        });

        // Button that links to announcements
        Button announcementsButton = (Button) findViewById(R.id.announcements_button);
        // Set a clickListener on that view
        announcementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent to open the {@link AchievementsActivity}
                Intent announcementsIntent = new Intent(HomeActivity.this, AnnouncementsActivity.class);

                // Start the new activity
                startActivity(announcementsIntent);
            }
        });
    }
}
