package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.attr.apiKey;
import static android.R.attr.defaultHeight;
import static android.R.attr.start;
import static com.gillyweed.android.asklah.R.string.login;

public class LoginActivity extends AppCompatActivity {

    public static final String MyPref = "MyPrefs";
    private final static String API_KEY = "08006c47-d0b9-4990-adb1-7d76610a4536";
    private static final String TAG = "response";
    SharedPreferences sharedPreferences;
    Button loginButton;
    EditText nus_idText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login_button);
        nus_idText = (EditText) findViewById(R.id.nus_idText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        //check api key
        if(API_KEY.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first", Toast.LENGTH_LONG).show();
            return;
        }

        ApiClient apiClient = new ApiClient();

        Retrofit retrofit = apiClient.getClient();

        final ApiInterface apiService = retrofit.create(ApiInterface.class);

        // Set a clickListener on that view
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nus_id = nus_idText.getText().toString();
                String password = passwordText.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                User loginUser = new User();

                loginUser.setNusId(nus_id);

                loginUser.setPassword(password);

                Call<User> call = apiService.login(loginUser, "access_token,subscription_tag");

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(response.isSuccessful())
                        {
                            int responseCode = response.code();

                            Log.i(TAG, "response code 1: " + response.code());

                            switch (responseCode)
                            {
                                case 200:
                                    checkUsernameExist(response);
                                    Log.i(TAG, "why not work!!!");
                                    break;
                                case 400:
                                    Toast.makeText(LoginActivity.this, "Wrong NUS ID or password", Toast.LENGTH_LONG).show();
                                    break;
                                case 404:
                                    Toast.makeText(LoginActivity.this, "Wrong NUS ID or password", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                        if(response.body() == null)
                        {
                            Toast.makeText(LoginActivity.this, "Wrong NUS ID or password", Toast.LENGTH_LONG).show();
                        }
                        Log.i(TAG, "response code 2: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
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
        });
    }

    public void checkUsernameExist(Response<User> response)
    {

        User userData = response.body();

        if(userData.getUsername() == null || userData.getUsername() == "" || userData.getUsername().isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Hi " + userData.getName() + " ! Let's create a username!", Toast.LENGTH_LONG).show();

            // Create a new intent to open the {@link CreateUsernameActivity}
            Intent usernameIntent = new Intent(LoginActivity.this, CreateUsernameActivity.class);

            usernameIntent.putExtra("user", userData);

            usernameIntent.putExtra("accessToken", userData.getAccessToken());

            // Start the new activity
            startActivity(usernameIntent);
        }
        else
        {
            Log.i(TAG, userData.getAccessToken().getToken());

            Toast.makeText(LoginActivity.this, "Welcome " + userData.getUsername(), Toast.LENGTH_LONG).show();

            // Create a new intent to open the {@link CreateUsernameActivity}
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);

            homeIntent.putExtra("user", userData);

            homeIntent.putExtra("accessToken", userData.getAccessToken());

            // Start the new activity
            startActivity(homeIntent);
        }
    }
}
