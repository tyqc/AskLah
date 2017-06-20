package com.gillyweed.android.asklah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_thread);

        // Lookup the recycler view in activity layout
        RecyclerView commentsRV = (RecyclerView) findViewById(R.id.thread_recycler_view);

        // Add a sample comment
        ArrayList<Comment> sampleCommentsList = new ArrayList<>();
        sampleCommentsList.add(new Comment("Bonjour Kaiyong !", getString(R.string.comment_op, "toh", "13/4/13", "1233h")));
        sampleCommentsList.add(new Comment("Salut Toh !", getString(R.string.comment_op, "holmes", "14/3/14", "1233h")));

        // Create a CommentAdapter to pass in sample data
        CommentAdapter commentAdapter = new CommentAdapter(this, sampleCommentsList);

        // Attach the adapter to the RecyclerView to populate the items
        commentsRV.setAdapter(commentAdapter);

        // Set layout manager to position the items
        commentsRV.setLayoutManager(new LinearLayoutManager(this));
    }
}
