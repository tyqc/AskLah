package com.gillyweed.android.asklah;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ModulesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_list);

        Spinner filterSpinner = (Spinner) findViewById(R.id.filter_spinner);

        // List of filter options
        ArrayList<String> filterOptionsList = new ArrayList<>();
        filterOptionsList.add("Module Code");
        filterOptionsList.add("Level");

        // Creating array adapter
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, filterOptionsList);

        // Apply the adapter to the spinner
        filterSpinner.setAdapter(filterAdapter);

        ListView modulesListView = (ListView) findViewById(R.id.modules_list_view);

        // Creating array list of modules
        ArrayList<String> modulesList = new ArrayList<>();
        modulesList.add("CS1010 Progamming Methodology");
        modulesList.add("CS1020 Data Structures and Algorithms I");
        modulesList.add("CS2010 Date Structures and Algorithms II");

        // Creating array adapter
        ArrayAdapter<String> modulesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modulesList);

        // Apply the adapter to the list view
        modulesListView.setAdapter(modulesAdapter);


        // Attach the adapter to list view
        ListView subscribedQnsListView = (ListView) findViewById(R.id.subscibed_qns_list_view);

        // Add back button onto action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates the menu. Adds items to action bar if present
        getMenuInflater().inflate(R.menu.menu_modules_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handles action of action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
