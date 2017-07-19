package com.gillyweed.android.asklah;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.EditPost;
import com.gillyweed.android.asklah.data.model.GetPost;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Envy 15 on 17/7/2017.
 */

public class EditPostActivity extends AppCompatActivity {

    private String TAG = "add post";

    EditText postTitleText;

    EditText postTagText;

    EditText postDescripText;

    ApiClient apiClient = null;

    Retrofit retrofit = null;

    ApiInterface apiService = null;

    User currentUser = null;

    AccessToken currentUserToken = null;

    GetPost postThread = null;

    EditPost editPost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        getSupportActionBar().setTitle("Edit Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiClient = new ApiClient();

        retrofit = apiClient.getClient();

        apiService = retrofit.create(ApiInterface.class);

        currentUser = getIntent().getParcelableExtra("user");

        currentUserToken = getIntent().getParcelableExtra("accessToken");

        postThread = getIntent().getParcelableExtra("post");

        postTitleText = (EditText) findViewById(R.id.newPostTitleText);

        postDescripText = (EditText) findViewById(R.id.newPostDescriptText);

        postTagText = (EditText) findViewById(R.id.tagsText);

        postTagText.setVisibility(View.INVISIBLE);

        editPost = new EditPost();

        editPost.setPostId(postThread.getPostId());

        editPost.setPostTitle(postThread.getPostTitle());

        editPost.setPostDescription(postThread.getPostDescription());

        postTitleText.setText(postThread.getPostTitle());

        postDescripText.setText(postThread.getPostDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.edit_post_action, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_save:

                String title = postTitleText.getText().toString();
                String description = postDescripText.getText().toString();

                editPost.setPostTitle(title);
                editPost.setPostDescription(description);

                Call<ResponseBody> call = apiService.editPost(currentUserToken.getToken(), editPost);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        int responseCode = response.code();

                        if(response.isSuccessful())
                        {
                            Toast.makeText(EditPostActivity.this, "Post has been updated", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK, null);
                            finish();
                        }
                        else
                        {
                            switch (responseCode)
                            {
                                case 400:
                                    Toast.makeText(EditPostActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                                    break;
                                case 404:
                                    Toast.makeText(EditPostActivity.this, "Post cannot be found from the database", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(EditPostActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
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
//                homeIntent = getParentActivityIntent();
//                homeIntent.putExtra("user", currentUser);
//                homeIntent.putExtra("accessToken", currentUserToken);
//                NavUtils.navigateUpTo(this, homeIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
