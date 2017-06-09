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
    }
}
