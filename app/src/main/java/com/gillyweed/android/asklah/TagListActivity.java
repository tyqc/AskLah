package com.gillyweed.android.asklah;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.GetPost;
import com.gillyweed.android.asklah.data.model.Tag;
import com.gillyweed.android.asklah.data.model.TagArray;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by Envy 15 on 23/7/2017.
 */

public class TagListActivity extends AppCompatActivity {

    public static final String MyPref = "MyPrefs";
    User currentUser = null;

    AccessToken currentUserToken = null;

    ApiClient apiClient = null;

    Retrofit retrofit = null;

    ApiInterface apiService = null;
    SwipeMenuListView tagListView = null;
    TagAdapter tagAdapter;
    ArrayList<Tag> tagArrayList;
    private String TAG = "tag list";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tag_list);

        tagListView = (SwipeMenuListView) findViewById(R.id.tag_list_view);

        currentUser = getIntent().getParcelableExtra("user");

        currentUserToken = getIntent().getParcelableExtra("accessToken");

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        Call< TagArray > call = apiService.getTags(currentUserToken.getToken());

        call.enqueue(new Callback<TagArray>() {
            @Override
            public void onResponse(Call<TagArray> call, Response<TagArray> response) {
                final int responseCode = response.code();

                if(response.isSuccessful())
                {
                    tagArrayList = response.body().getTagArrayList();

                    tagAdapter = new TagAdapter(TagListActivity.this, tagArrayList);

                    tagListView.setAdapter(tagAdapter);

                    tagListView.setCloseInterpolator(new BounceInterpolator());

                    SwipeMenuCreator creator = new SwipeMenuCreator() {
                        @Override
                        public void create(SwipeMenu menu) {
                            SwipeMenuItem subscribeBtn = new SwipeMenuItem(getApplicationContext());
                            subscribeBtn.setBackground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.bg_color)));

                            subscribeBtn.setWidth(400);
                            subscribeBtn.setTitle("Subscribe");
                            subscribeBtn.setTitleSize(18);
                            subscribeBtn.setTitleColor(ContextCompat.getColor(getApplicationContext(), R.color.secondary_text));

                            menu.addMenuItem(subscribeBtn);

                        }
                    };

                    tagListView.setMenuCreator(creator);

                    tagListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(int position, final SwipeMenu menu, int index) {
                            switch (index)
                            {
                                case 0:
                                    Call<Tag> call = apiService.subscribeTag(currentUserToken.getToken(), tagArrayList.get(position).getTagId());

                                    call.enqueue(new Callback<Tag>() {
                                        @Override
                                        public void onResponse(Call<Tag> call, Response<Tag> response) {

                                            int responseCode = response.code();

                                            if(response.isSuccessful())
                                            {
                                                menu.getMenuItem(0).setTitle("Unsubscribe");
                                            }
                                            else
                                            {
                                                switch (responseCode)
                                                {
                                                    case 404:
                                                        Toast.makeText(TagListActivity.this, "Post cannot be found from the database", Toast.LENGTH_LONG).show();
                                                        break;
                                                    default:
                                                        Toast.makeText(TagListActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                                        break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Tag> call, Throwable t) {
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
                                    break;
                            }
                            return false;
                        }
                    });
                }
                else
                {
                    switch (responseCode)
                    {
                        default:
                            Toast.makeText(TagListActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<TagArray> call, Throwable t) {
                if(call.isCanceled())
                {
                    Log.e(TAG, "request was aborted");
                }
                else
                {
                    Log.e(TAG, t.getMessage());
                }
                Log.i(TAG, "response code 3: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                setResult(RESULT_OK, null);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
