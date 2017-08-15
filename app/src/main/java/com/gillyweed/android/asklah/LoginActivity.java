package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gillyweed.android.asklah.data.model.User;
import com.gillyweed.android.asklah.rest.ApiClient;
import com.gillyweed.android.asklah.rest.ApiInterface;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    public static final String MyPref = "MyPrefs";
    private final static String API_KEY = "08006c47-d0b9-4990-adb1-7d76610a4536";
    private static final String TAG = "response";
    Button loginButton;
    EditText nus_idText;
    EditText passwordText;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // setting the font of the title of the login page
        TextView loginTitle = (TextView)findViewById(R.id.login_title);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/shorelines_script_bold.otf");
        loginTitle.setTypeface(customFont);

        loginButton = (Button) findViewById(R.id.login_button);
        nus_idText = (EditText) findViewById(R.id.nus_idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        spinner = (ProgressBar) findViewById(R.id.progressBarLogin);
        spinner.setVisibility(View.GONE);

        //check api key
        if(API_KEY.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first", Toast.LENGTH_LONG).show();
            return;
        }

        ApiClient apiClient = new ApiClient();

        final Retrofit retrofit = apiClient.getClient();

        final ApiInterface apiService = retrofit.create(ApiInterface.class);

        // Set a clickListener on login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //make spinner visible
                spinner.setVisibility(View.VISIBLE);

                String nus_id = nus_idText.getText().toString();
                String password = passwordText.getText().toString();

                User loginUser = new User();

                loginUser.setNusId(nus_id);

                loginUser.setPassword(password);

                Call<User> call = apiService.login(loginUser, "access_token");

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        int responseCode = response.code();

                        if(response.isSuccessful())
                        {

                            if(responseCode == 200)
                            {
                                //check if username exist
                                //if yes, direct to create username view
                                //if not, direct to home page
                                checkUsernameExist(response);
                            }
                        }
                        else
                        {
                            switch (responseCode)
                            {
                                case 400:
                                    Toast.makeText(LoginActivity.this, "Wrong NUS ID or password", Toast.LENGTH_LONG).show();
                                    break;
                                case 401:
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

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        if(call.isCanceled())
                        {
                            Toast.makeText(LoginActivity.this, "Login failed, request has been canceled", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Some errors occur, please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void checkUsernameExist(Response<User> response)
    {

        User userData = response.body();

        SharedPreferences sharedPref = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("access_token", userData.getAccessToken().getToken());
        editor.putString("user_role", userData.getRole());
        editor.commit();

        if(userData.getUsername() == null || userData.getUsername() == "" || userData.getUsername().isEmpty())
        {
            spinner.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "Hi " + userData.getName() + " ! Let's create a username!", Toast.LENGTH_LONG).show();

            // Create a new intent to open the {@link CreateUsernameActivity}
            //direct to create username view
            Intent usernameIntent = new Intent(LoginActivity.this, CreateUsernameActivity.class);

            //pass current user's detail to next intent
            usernameIntent.putExtra("user", userData);

            usernameIntent.putExtra("accessToken", userData.getAccessToken());

            // Start the new activity
            startActivity(usernameIntent);
        }
        else
        {
            spinner.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "Welcome " + userData.getUsername(), Toast.LENGTH_LONG).show();

            // Create a new intent to open the {@link CreateUsernameActivity}
            //direct to home page
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);

            //pass current user's detail to next intent
            homeIntent.putExtra("user", userData);

            homeIntent.putExtra("accessToken", userData.getAccessToken());

            // Start the new activity
            startActivity(homeIntent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        return true;
    }
}
