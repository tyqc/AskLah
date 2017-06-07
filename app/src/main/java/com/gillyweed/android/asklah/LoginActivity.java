package com.gillyweed.android.asklah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.start;
import static com.gillyweed.android.asklah.R.string.login;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.login_button);

        // Set a clickListener on that view
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent to open the {@link CreateUsernameActivity}
                Intent loginIntent = new Intent(LoginActivity.this, CreateUsernameActivity.class);

                // Start the new activity
                startActivity(loginIntent);
            }
        });
    }
}
