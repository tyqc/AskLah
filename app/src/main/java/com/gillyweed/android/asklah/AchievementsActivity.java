package com.gillyweed.android.asklah;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AchievementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        // Construct the data source
        ArrayList<Achievement> achievementArrayList = new ArrayList<Achievement>();
        achievementArrayList.add(new Achievement("Question Ninja", true));

        // Create the adapter to convert the array list to views
        AchievementAdapter achievementAdapter = new AchievementAdapter(this, achievementArrayList);

        // Attach the adapter to a list view
        ListView achievementsListView = (ListView) findViewById(R.id.achievement_list_view);
        achievementsListView.setAdapter(achievementAdapter);

        // Add back button onto action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
