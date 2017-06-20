package com.gillyweed.android.asklah;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SubscribedQuestionsActivity extends AppCompatActivity {

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

        // Construct the data source
        ArrayList<SubscribedQuestion> subscribedQuestionsList = new ArrayList<>();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add("#CS1020");
        subscribedQuestionsList.add(new SubscribedQuestion("CS1020 help", "I was looking through the PYP and chanced upon this question", tagsList));

        // Create the adapter to convert the arraylist into views
        SubscribedQnAdapter subscribedQnAdapter = new SubscribedQnAdapter(this, subscribedQuestionsList);

        // Attach the adapter to a listview
        ListView subscribedQnsListView = (ListView) findViewById(R.id.subscibed_qns_list_view);
        subscribedQnsListView.setAdapter(subscribedQnAdapter);

        subscribedQnsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create a new intent to open Modules List Activity
                Intent qnThreadActivityIntent = new Intent(SubscribedQuestionsActivity.this, QuestionThreadActivity.class);
                startActivity(qnThreadActivityIntent);
            }
        });
    }
}
