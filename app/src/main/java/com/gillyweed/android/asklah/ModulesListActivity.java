package com.gillyweed.android.asklah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ModulesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_list);

        Spinner filterSpinner = (Spinner) findViewById(R.id.filter_spinner);

        // List of filter options
        ArrayList<String> filterOptionsList = new ArrayList<>();
        filterOptionsList.add("Alphabetical");
        filterOptionsList.add("Module Code");
        filterOptionsList.add("Level");

        // Creating arrayadapter
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, filterOptionsList);

        // Apply the adapter to the spinner
        filterSpinner.setAdapter(filterAdapter);
    }
}
