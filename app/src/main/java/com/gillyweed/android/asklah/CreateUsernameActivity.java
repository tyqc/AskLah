package com.gillyweed.android.asklah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUsernameActivity extends AppCompatActivity {

    public static final String MyPref = "MyPrefs";
    private static final String TAG = "response";
    SharedPreferences sharedPreferences;
    Button createButton;
    EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);

        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        createButton = (Button) findViewById(R.id.create_button);

        usernameText = (EditText) findViewById(R.id.usernameText);

        // Set a clickListener on that view
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameText.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("usernameTutor", "Dr TKY");

                editor.putString("usernameStudent", "TKY");

                editor.commit();

                Toast.makeText(CreateUsernameActivity.this, "Welcome TKY", Toast.LENGTH_LONG).show();

                // Create a new intent to open the {@link HomeActivity}
                Intent createButtonIntent = new Intent(CreateUsernameActivity.this, HomeActivity.class);

                // Start the new activity
                startActivity(createButtonIntent);
            }
        });
    }
}
