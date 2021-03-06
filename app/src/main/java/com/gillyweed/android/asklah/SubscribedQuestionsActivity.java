package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.PostList;
import com.gillyweed.android.asklah.data.model.SubscriptionPosts;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Envy 15 on 22/7/2017.
 */

public class SubscribedQuestionsActivity extends AppCompatActivity {

    public static final String MyPref = "MyPrefs";
    User currentUser = null;

    AccessToken currentUserToken = null;

    ApiClient apiClient = null;

    Retrofit retrofit = null;

    ApiInterface apiService = null;

    int tagId;
    ArrayList<PostList> postLists;
    private String TAG = "subscribed question list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subscribed_questions);

        currentUser = getIntent().getParcelableExtra("user");

        currentUserToken = getIntent().getParcelableExtra("accessToken");

        tagId = getSharedPreferences(MyPref, Context.MODE_PRIVATE).getInt("tagId", -1);

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

//        getPostOverview();

        Spinner sortBySpinner = (Spinner) findViewById(R.id.sort_by_spinner);

        // List of Sort By options
        ArrayList<String> sortByOptionsList = new ArrayList<>();
        sortByOptionsList.add("Default");
        sortByOptionsList.add("Most Updated");


        // Creating array adapter
        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, sortByOptionsList);

        // Apply the adapter to the spinner
        sortBySpinner.setAdapter(sortByAdapter);

        Spinner filterBySpinner = (Spinner) findViewById(R.id.filter_by_spinner);

        // List of Filter By Options
        ArrayList<String> filterByOptionsList = new ArrayList<>();
        filterByOptionsList.add("Default");
        filterByOptionsList.add("Solved");
        filterByOptionsList.add("Pending");

        // Creating Array Adapter
        ArrayAdapter<String> filterByAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, filterByOptionsList);

        // Apply the adapter to the spinner
        filterBySpinner.setAdapter(filterByAdapter);

        Call<SubscriptionPosts> call = apiService.getSubscribedPost(currentUserToken.getToken());

            call.enqueue(new Callback<SubscriptionPosts>() {
                @Override
                public void onResponse(Call<SubscriptionPosts> call, Response<SubscriptionPosts> response) {
                    int responseCode = response.code();

                    if(response.isSuccessful())
                    {
                        postLists = response.body().getSubscriptionPosts();

                        // Create the adapter to convert the arraylist into views
                        SubscribedQnAdapter subscribedQnAdapter = new SubscribedQnAdapter(SubscribedQuestionsActivity.this, postLists);

                        // Attach the adapter to a listview
                        ListView subscribedQnsListView = (ListView) findViewById(R.id.subscibed_qns_list_view);

                        subscribedQnsListView.setAdapter(subscribedQnAdapter);

                        subscribedQnsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Create a new intent to open Modules List Activity
                                Intent qnThreadActivityIntent = new Intent(SubscribedQuestionsActivity.this, QuestionThreadActivity.class);
                                qnThreadActivityIntent.putExtra("user", currentUser);
                                qnThreadActivityIntent.putExtra("accessToken", currentUserToken);
                                qnThreadActivityIntent.putExtra("postId", postLists.get(position).getPost().getPostId());
                                qnThreadActivityIntent.putExtra("postOwnerNusId",  postLists.get(position).getPost().getNusId());
//                            startActivity(qnThreadActivityIntent);
                                startActivityForResult(qnThreadActivityIntent, 1000);
                            }
                        });
                    }
                    else
                    {
                        switch (responseCode)
                        {
                            case 400:
                                Toast.makeText(SubscribedQuestionsActivity.this, "Please provide tag id", Toast.LENGTH_LONG).show();
                                break;
                            case 404:
                                Toast.makeText(SubscribedQuestionsActivity.this, "Tag cannot be found from the database", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(SubscribedQuestionsActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<SubscriptionPosts> call, Throwable t) {
                    if(call.isCanceled())
                    {
                        Toast.makeText(SubscribedQuestionsActivity.this, "Request has been canceled", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(SubscribedQuestionsActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                    }
                }
            });

        // Add back button onto action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000)
        {
            if(resultCode == RESULT_OK)
            {
                finish();
                startActivity(getIntent());
            }
        }
    }

    // Add a search button in action bar
    // Action bar layout is same as ModulesListActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates the menu. Adds items to action bar if present
        getMenuInflater().inflate(R.menu.menu_modules_list_owner, menu);
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
}
