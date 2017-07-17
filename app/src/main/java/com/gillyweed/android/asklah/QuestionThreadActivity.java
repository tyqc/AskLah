package com.gillyweed.android.asklah;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.GetPost;
import com.gillyweed.android.asklah.data.model.PostTags;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuestionThreadActivity extends AppCompatActivity {

    private String TAG = "question thread";

    User currentUser = null;

    AccessToken currentUserToken = null;

    int postId;

    ApiClient apiClient = null;

    Retrofit retrofit = null;

    ApiInterface apiService = null;

    GetPost questionThread = null;

    TextView postTitleText;

    TextView postDescriptionText;

    TextView postTagText;

    TextView postDateText;

    TextView postUpdateDateText;

    String postOwnerNusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_thread);

        postTitleText = (TextView) findViewById(R.id.question_title);

        postDescriptionText = (TextView) findViewById(R.id.question_description);

        postTagText = (TextView) findViewById(R.id.question_tag);

        postDateText = (TextView) findViewById(R.id.question_owner);

        postUpdateDateText = (TextView) findViewById(R.id.question_update);

        currentUser = getIntent().getParcelableExtra("user");

        currentUserToken = getIntent().getParcelableExtra("accessToken");

        postId = getIntent().getIntExtra("postId", -1);

        postOwnerNusId = getIntent().getStringExtra("postOwnerNusId");

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        Call<GetPost> call = apiService.getPostThread(currentUserToken.getToken(), postId);

        call.enqueue(new Callback<GetPost>() {
            @Override
            public void onResponse(Call<GetPost> call, Response<GetPost> response) {

                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    questionThread = response.body();

                    postTitleText.setText(questionThread.getPostTitle());

                    postDescriptionText.setText(questionThread.getPostDescription());

                    getTagText(questionThread);

                    String postOwnerText = "";

                    if(currentUser.getRole() == "student")
                    {
                        postOwnerText = "Posted by " + questionThread.getOwner().getUsername() + " on " + ConvertDateTime.convertTime(questionThread.getDateAdded().getDate());
                    }
                    else
                    {
                        postOwnerText = "Posted by " + questionThread.getOwner().getUsername() + " on " + ConvertDateTime.convertTime(questionThread.getDateAdded().getDate());

                    }

                    postDateText.setText(postOwnerText);

                    postUpdateDateText.setText("Updated on " + ConvertDateTime.convertTime(questionThread.getDateUpdated().getDate()));
                }
                else
                {
                    switch (responseCode)
                    {
                        case 404:
                            Toast.makeText(QuestionThreadActivity.this, "Post cannot be found from the database", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(QuestionThreadActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                            break;
                    }
                }

            }

            @Override
            public void onFailure(Call<GetPost> call, Throwable t) {
                if(call.isCanceled())
                {
                    Log.e(TAG, "request was aborted");
                }
                else
                {
                    Log.e(TAG, t.getMessage());
                }
            }
        });

        // Lookup the recycler view in activity layout
        RecyclerView commentsRV = (RecyclerView) findViewById(R.id.thread_recycler_view);

        // Add a sample comment
        ArrayList<Comment> sampleCommentsList = new ArrayList<>();
        sampleCommentsList.add(new Comment("Bonjour Kaiyong !", getString(R.string.comment_op, "toh", "13/4/13", "1233h")));
        sampleCommentsList.add(new Comment("Salut Toh !", getString(R.string.comment_op, "holmes", "14/3/14", "1233h")));

        // Create a CommentAdapter to pass in sample model
        CommentAdapter commentAdapter = new CommentAdapter(this, sampleCommentsList);

        // Attach the adapter to the RecyclerView to populate the items
        commentsRV.setAdapter(commentAdapter);

        // Set layout manager to position the items
        commentsRV.setLayoutManager(new LinearLayoutManager(this));

        // Add back button onto action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // Add bookmark button to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(postOwnerNusId.equalsIgnoreCase(currentUser.getNusId()))
        {
            getMenuInflater().inflate(R.menu.menu_question_owner, menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.menu_question, menu);
        }

        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
//                Intent homeIntent = getParentActivityIntent();
//                homeIntent.putExtra("user", currentUser);
//                homeIntent.putExtra("accessToken", currentUserToken);
//                NavUtils.navigateUpTo(this, homeIntent);
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;

            case R.id.action_delete:
                new AlertDialog.Builder(QuestionThreadActivity.this)
                        .setTitle("Delete Tag")
                        .setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Call<ResponseBody> call = apiService.deletePost(currentUserToken.getToken(), postId, currentUser.getNusId());
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        int responseCode = response.code();

                                        if(response.isSuccessful())
                                        {
                                            Toast.makeText(QuestionThreadActivity.this, "Post has been deleted", Toast.LENGTH_LONG).show();
                                            setResult(RESULT_OK, null);
                                            finish();
                                        }
                                        else
                                        {
                                            switch (responseCode)
                                            {
                                                case 404:
                                                    Toast.makeText(QuestionThreadActivity.this, "Post cannot be found from the database", Toast.LENGTH_LONG).show();
                                                    break;
                                                default:
                                                    Toast.makeText(QuestionThreadActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                                    break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        if(call.isCanceled())
                                        {
                                            Log.e(TAG, "request was aborted");
                                        }
                                        else
                                        {
                                            Log.e(TAG, t.getMessage());
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

                return true;

            case R.id.action_edit:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getTagText(GetPost questionThread)
    {
        String tagText = "Tag: ";

        ArrayList<PostTags> tagList = questionThread.getPostTagArray().getTags();

        for(int i = 0; i < tagList.size(); i++)
        {
            tagText += tagList.get(i).getTagName();

            tagText += " ";
        }

        postTagText.setText(tagText);
    }
}
