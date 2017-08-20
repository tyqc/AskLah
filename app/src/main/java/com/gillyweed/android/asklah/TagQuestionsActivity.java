package com.gillyweed.android.asklah;

import android.content.Context;
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
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.PostList;
import com.gillyweed.android.asklah.data.model.Tag;
import com.gillyweed.android.asklah.data.model.TagDescrip;
import com.gillyweed.android.asklah.data.model.TagPostList;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TagQuestionsActivity extends AppCompatActivity {

    public static final String MyPref = "MyPrefs";
    User currentUser = null;

    AccessToken currentUserToken = null;

    ApiClient apiClient = null;

    Retrofit retrofit = null;

    ApiInterface apiService = null;

    int tagId;
    ArrayList<PostList> postLists;
    private String TAG = "question list";
    String tagName;
    Boolean tagSub;
    private Menu getMenu;
    Boolean tagOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_questions);

        currentUser = getIntent().getParcelableExtra("user");

        currentUserToken = getIntent().getParcelableExtra("accessToken");

        tagId = getSharedPreferences(MyPref, Context.MODE_PRIVATE).getInt("tagId", -1);

        tagName = getSharedPreferences(MyPref, Context.MODE_PRIVATE).getString("module_tag","");

        tagSub = getSharedPreferences(MyPref, Context.MODE_PRIVATE).getBoolean("tag_subscribed", false);

        tagOwner = getSharedPreferences(MyPref, Context.MODE_PRIVATE).getBoolean("owner", false);

        // Add back button onto action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(tagName);
        actionBar.setDisplayHomeAsUpEnabled(true);

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        final Spinner sortBySpinner = (Spinner) findViewById(R.id.sort_by_spinner);

        // List of Sort By options
        ArrayList<String> sortByOptionsList = new ArrayList<>();
        sortByOptionsList.add("Default");
        sortByOptionsList.add("Most Updated");

        // Creating array adapter
        final ArrayAdapter<String> sortByAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, sortByOptionsList);

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

        Call<TagPostList> call = apiService.getPostList(currentUserToken.getToken(), tagId);

        call.enqueue(new Callback<TagPostList>() {
            @Override
            public void onResponse(Call<TagPostList> call, Response<TagPostList> response) {
                int responseCode = response.code();

                if(response.isSuccessful())
                {
                    postLists = response.body().getPostList();

                    // Create the adapter to convert the arraylist into views
                    SubscribedQnAdapter subscribedQnAdapter = new SubscribedQnAdapter(TagQuestionsActivity.this, postLists);

                    // Attach the adapter to a listview
                    ListView subscribedQnsListView = (ListView) findViewById(R.id.subscibed_qns_list_view);

                    subscribedQnsListView.setAdapter(subscribedQnAdapter);

                    subscribedQnsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Create a new intent to open Modules List Activity
                            Intent qnThreadActivityIntent = new Intent(TagQuestionsActivity.this, QuestionThreadActivity.class);
                            qnThreadActivityIntent.putExtra("user", currentUser);
                            qnThreadActivityIntent.putExtra("accessToken", currentUserToken);
                            qnThreadActivityIntent.putExtra("postId", postLists.get(position).getPost().getPostId());
                            qnThreadActivityIntent.putExtra("postOwnerNusId",  postLists.get(position).getPost().getNusId());
                            startActivityForResult(qnThreadActivityIntent, 1000);
                        }
                    });

//                    sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            switch (position)
//                            {
//                                case 0:
//                                    Collections.sort(postLists, new SortPostDefaultComparator());
//                                    break;
//                                case 1:
//                                    Collections.sort(postLists, new SortPostUpdatedComparator());
//                                    break;
//                            }
//
//                            sortByAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
                }
                else
                {
                    switch (responseCode)
                    {
                        case 400:
                            Toast.makeText(TagQuestionsActivity.this, "Please provide tag id", Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Toast.makeText(TagQuestionsActivity.this, "Tag cannot be found from the database", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(TagQuestionsActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<TagPostList> call, Throwable t) {
                if(call.isCanceled())
                {
                    Toast.makeText(TagQuestionsActivity.this, "Request has been canceled", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(TagQuestionsActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });


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

        if(tagOwner)
        {
            getMenuInflater().inflate(R.menu.menu_modules_list_owner, menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.menu_modules_list, menu);

            getMenu = menu;

            MenuItem menuItem = menu.findItem(R.id.action_bookmark);

            if(tagSub)
            {
                menuItem.setIcon(R.drawable.ic_bookmark_black_24dp);
            }
            else
            {
                menuItem.setIcon(R.drawable.ic_bookmark_white_24dp);
            }
        }

        return true;
    }

    // Link back button with Home Fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_bookmark:
                addDeleteTagSubscription(tagSub);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addDeleteTagSubscription(Boolean tagSub)
    {
        if(tagSub)
        {
            Call<ResponseBody> call = apiService.unsubscribeTag(currentUserToken.getToken(), tagId);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    int responseCode = response.code();

                    if(response.isSuccessful())
                    {
                        MenuItem menuItem = getMenu.findItem(R.id.action_bookmark);

                        menuItem.setIcon(R.drawable.ic_bookmark_white_24dp);

                        Toast.makeText(TagQuestionsActivity.this, "You have unsubscribed this module tag", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        switch (responseCode)
                        {
                            case 400:
                                Toast.makeText(TagQuestionsActivity.this, "Please provide tag id", Toast.LENGTH_LONG).show();
                                break;
                            case 404:
                                Toast.makeText(TagQuestionsActivity.this, "Tag cannot be found from the database", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(TagQuestionsActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(call.isCanceled())
                    {
                        Toast.makeText(TagQuestionsActivity.this, "Request has been canceled", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(TagQuestionsActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            Call<Tag> call = apiService.subscribeTag(currentUserToken.getToken(), tagId);

            call.enqueue(new Callback<Tag>() {
                @Override
                public void onResponse(Call<Tag> call, Response<Tag> response) {
                    int responseCode = response.code();

                    if(response.isSuccessful())
                    {
                        MenuItem menuItem = getMenu.findItem(R.id.action_bookmark);

                        menuItem.setIcon(R.drawable.ic_bookmark_black_24dp);

                        Toast.makeText(TagQuestionsActivity.this, "You have subscribed this module tag", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        switch (responseCode)
                        {
                            case 400:
                                Toast.makeText(TagQuestionsActivity.this, "Please provide tag id", Toast.LENGTH_LONG).show();
                                break;
                            case 404:
                                Toast.makeText(TagQuestionsActivity.this, "Tag cannot be found from the database", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(TagQuestionsActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tag> call, Throwable t) {
                    if(call.isCanceled())
                    {
                        Toast.makeText(TagQuestionsActivity.this, "Request has been canceled", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(TagQuestionsActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, null);
        finish();
    }
}

//class SortPostDefaultComparator implements Comparator<PostList>
//{
//    @Override
//    public int compare(PostList o1, PostList o2) {
//
//        return ConvertDateTime.compareDate(o1.getPost().getDateAdded().getDate(), o2.getPost().getDateAdded().getDate());
//    }
//
//}
//
//class SortPostUpdatedComparator implements Comparator<PostList>
//{
//    @Override
//    public int compare(PostList o1, PostList o2) {
//
//        return ConvertDateTime.compareDate(o2.getPost().getDateAdded().getDate(), o1.getPost().getDateAdded().getDate());
//    }
//
//}
