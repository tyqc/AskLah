package com.gillyweed.android.asklah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Add_Post extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__post);
        getSupportActionBar().setTitle("Add New Post");
    }
}
