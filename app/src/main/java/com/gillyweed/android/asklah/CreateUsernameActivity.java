package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.AccessToken;
import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateUsernameActivity extends AppCompatActivity {

    private static final String TAG = "create username";
    Button createButton;
    EditText usernameText;
    ProgressBar spinner;
    AccessToken currentUserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);

        createButton = (Button) findViewById(R.id.create_button);

        usernameText = (EditText) findViewById(R.id.usernameText);

        spinner = (ProgressBar) findViewById(R.id.progressBarCreateUsername);

        spinner.setVisibility(View.GONE);

        //api client instance
        ApiClient apiClient = new ApiClient();

        Retrofit retrofit = apiClient.getClient();

        final ApiInterface apiService = retrofit.create(ApiInterface.class);

        // Set a clickListener on that view
        //send create username request
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner.setVisibility(View.VISIBLE);

                String username = usernameText.getText().toString();

                //get access token in parcelable
                currentUserToken = getIntent().getParcelableExtra("accessToken");

                //get current user detail in parcelable
                User currentUser = getIntent().getParcelableExtra("user");

                currentUser.setUsername(username);

                //make call to send request
                Call<User> call = apiService.update(currentUserToken.getToken(),currentUser);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        int responseCode = response.code();

                        if(response.isSuccessful())
                        {
                            if(responseCode == 200)
                            {
                                //direct to home view
                                goHomeActivity(response);
                            }

                        }
                        else
                        {
                            spinner.setVisibility(View.GONE);
                            switch (responseCode)
                            {
                                case 400:
                                    Toast.makeText(CreateUsernameActivity.this, "Username field cannot be empty :(", Toast.LENGTH_LONG).show();
                                    break;
                                case 409:
                                    Toast.makeText(CreateUsernameActivity.this, "Your new unsername has been taken by others :(", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(CreateUsernameActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                        spinner.setVisibility(View.GONE);

                        if(call.isCanceled())
                        {
                            Toast.makeText(CreateUsernameActivity.this, "Login failed, request has been canceled", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(CreateUsernameActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    //direct to home view
    public void goHomeActivity(Response<User> response)
    {
        User currentUser = response.body();

        Toast.makeText(CreateUsernameActivity.this, "Welcome " + currentUser.getUsername(), Toast.LENGTH_LONG).show();

        // Create a new intent to open the {@link HomeActivity}
        //direct to home activity
        Intent homeIntent = new Intent(CreateUsernameActivity.this, HomeActivity.class);

        homeIntent.putExtra("user", currentUser);

        homeIntent.putExtra("accessToken", currentUserToken);

        // Start the new activity
        startActivity(homeIntent);
    }

    //hide keyboard when edit text is not focus
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        return true;
    }
}
