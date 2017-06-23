package com.gillyweed.android.asklah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate layout of home fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<String> modulesOrMajorsList = new ArrayList<String>();

        // Sample of some module codes
        modulesOrMajorsList.add("CS1020");
        modulesOrMajorsList.add("CS1010");
        modulesOrMajorsList.add("CS2010");
        modulesOrMajorsList.add("CS2100");
        modulesOrMajorsList.add("CS1231");
        modulesOrMajorsList.add("MA1101R");
        modulesOrMajorsList.add("Subscribed Questions");

        GridView homeGridView = (GridView) rootView.findViewById(R.id.home_grid_view);

        // Creating a new adapter to store the data
        ArrayAdapter<String> modulesOrMajorsAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, modulesOrMajorsList);

        // Attach adapter to grid view
        homeGridView.setAdapter(modulesOrMajorsAdapter);

        // Clicking on any of the grid items leads to the same activity. :<
        homeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create a new intent to open Modules List Activity
                Intent modulesListIntent = new Intent(getActivity(), ModulesListActivity.class);
                startActivity(modulesListIntent);
            }
        });

        return rootView;
    }
}
