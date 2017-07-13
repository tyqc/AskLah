package com.gillyweed.android.asklah;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SubscribedQuestionsActivity extends AppCompatActivity {

    //TODO: GETPOSTLIST API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_questions);

        Spinner sortBySpinner = (Spinner) findViewById(R.id.sort_by_spinner);

        // List of Sort By options
        ArrayList<String> sortByOptionsList = new ArrayList<>();
        sortByOptionsList.add("Alphabetical");
        sortByOptionsList.add("Module Code");

        // Creating array adapter
        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sortByOptionsList);

        // Apply the adapter to the spinner
        sortBySpinner.setAdapter(sortByAdapter);

        Spinner filterBySpinner = (Spinner) findViewById(R.id.filter_by_spinner);

        // List of Filter By Options
        ArrayList<String> filterByOptionsList = new ArrayList<>();
        filterByOptionsList.add("Level 1000");
        filterByOptionsList.add("Level 2000");
        filterByOptionsList.add("Level 3000");
        filterByOptionsList.add("Level 4000");

        // Creating Array Adapter
        ArrayAdapter<String> filterByAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, filterByOptionsList);

        // Apply the adapter to the spinner
        filterBySpinner.setAdapter(filterByAdapter);

        getPostList();

        // Construct the model source
//        ArrayList<SubscribedQuestion> subscribedQuestionsList = new ArrayList<>();
//        ArrayList<String> tagsList = new ArrayList<>();
//        tagsList.add("#CS1020");
//        subscribedQuestionsList.add(new SubscribedQuestion("CS1020 help", "I was looking through the PYP and chanced upon this question...", tagsList));

        // Create the adapter to convert the arraylist into views
//        SubscribedQnAdapter subscribedQnAdapter = new SubscribedQnAdapter(this, subscribedQuestionsList);

        // Attach the adapter to a listview
//        ListView subscribedQnsListView = (ListView) findViewById(R.id.subscibed_qns_list_view);
//        subscribedQnsListView.setAdapter(subscribedQnAdapter);

//        subscribedQnsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Create a new intent to open Modules List Activity
//                Intent qnThreadActivityIntent = new Intent(SubscribedQuestionsActivity.this, QuestionThreadActivity.class);
//                startActivity(qnThreadActivityIntent);
//            }
//        });

        // Add back button onto action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    // Add a search button in action bar
    // Action bar layout is same as ModulesListActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates the menu. Adds items to action bar if present
        getMenuInflater().inflate(R.menu.menu_modules_list, menu);
        return true;
    }

    // Link back button with Home Fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPostList()
    {

    }
}
