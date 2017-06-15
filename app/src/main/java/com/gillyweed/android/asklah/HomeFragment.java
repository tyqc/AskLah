package com.gillyweed.android.asklah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
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

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<String> modulesOrMajorsList = new ArrayList<String>();

        modulesOrMajorsList.add("CS1020");
        modulesOrMajorsList.add("CS1010");
        modulesOrMajorsList.add("CS2010");
        modulesOrMajorsList.add("CS2100");
        modulesOrMajorsList.add("CS1231");
        modulesOrMajorsList.add("MA1101R");

        GridView homeGridView = (GridView) rootView.findViewById(R.id.home_grid_view);

        ArrayAdapter<String> modulesOrMajorsAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, modulesOrMajorsList);

        homeGridView.setAdapter(modulesOrMajorsAdapter);

        return rootView;
    }
}
