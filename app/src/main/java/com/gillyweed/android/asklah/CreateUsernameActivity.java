package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

//    public static final String MyPref = "MyPrefs";
    private static final String TAG = "response";
    Button createButton;
    EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);


        createButton = (Button) findViewById(R.id.create_button);

        usernameText = (EditText) findViewById(R.id.usernameText);

        ApiClient apiClient = new ApiClient();

        Retrofit retrofit = apiClient.getClient();

        final ApiInterface apiService = retrofit.create(ApiInterface.class);

        // Set a clickListener on that view
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameText.getText().toString();

//                Log.i(TAG, username);

                AccessToken currentUserToken = getIntent().getParcelableExtra("accessToken");

                User currentUser = getIntent().getParcelableExtra("user");

                currentUser.setUsername(username);

                Call<User> call = apiService.update(currentUserToken.getToken(),currentUser);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        int responseCode = response.code();

                        if(response.isSuccessful())
                        {
                            if(responseCode == 200)
                            {
                                goHomeActivity(response);
//                                Log.i(TAG, "why not work!!!");
                            }

                        }
                        else
                        {
//                            Log.i(TAG, "error code: " + response.errorBody().toString());
//                            Log.i(TAG, "error code: " + response.code());
                            switch (responseCode)
                            {
                                case 400:
                                    Toast.makeText(CreateUsernameActivity.this, "Username field cannot be empty :(", Toast.LENGTH_LONG).show();
                                    break;
                                case 404:
                                    Toast.makeText(CreateUsernameActivity.this, "Your data cannot be found in database", Toast.LENGTH_LONG).show();
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
                        if(call.isCanceled())
                        {
//                            Log.e(TAG, "request was aborted");
                        }
                        else
                        {
//                            Log.e(TAG, t.getMessage());
                        }
//                        Log.i(TAG, "response code 3: " + t.getMessage());
                    }
                });
            }
        });
    }

    public void goHomeActivity(Response<User> response)
    {
        User currentUser = response.body();

        Toast.makeText(CreateUsernameActivity.this, "Welcome " + currentUser.getUsername(), Toast.LENGTH_LONG).show();

        // Create a new intent to open the {@link HomeActivity}
        Intent homeIntent = new Intent(CreateUsernameActivity.this, HomeActivity.class);

        homeIntent.putExtra("user", currentUser);

        // Start the new activity
        startActivity(homeIntent);
    }
}
