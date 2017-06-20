package com.gillyweed.android.asklah;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class AnnouncementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        // Add back button onto action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Construct the data source
        ArrayList<Announcement> announcementArrayList = new ArrayList<Announcement>();
        announcementArrayList.add(new Announcement("megaNerd", "3 seconds ago", "Rapid Tapper"));
        announcementArrayList.add(new Announcement("holmes", "5 minutes ago", "Question Ninja"));

        // Create the adapter to convert the arraylist to views
        AnnouncementAdapter announcementAdapter = new AnnouncementAdapter(this, announcementArrayList);

        // Attach the adapter to a listview
        ListView announcementsListView = (ListView) findViewById(R.id.announcement_list_view);
        announcementsListView.setAdapter(announcementAdapter);
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
