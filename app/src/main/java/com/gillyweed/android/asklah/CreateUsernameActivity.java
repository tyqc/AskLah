package com.gillyweed.android.asklah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateUsernameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_username);

        Button createButton = (Button) findViewById(R.id.create_button);
        // Set a clickListener on that view
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent to open the {@link HomeActivity}
                Intent createButtonIntent = new Intent(CreateUsernameActivity.this, HomeActivity.class);

                // Start the new activity
                startActivity(createButtonIntent);
            }
        });
    }
}
