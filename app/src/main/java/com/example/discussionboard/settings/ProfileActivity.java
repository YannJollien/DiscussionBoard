package com.example.discussionboard.settings;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.discussionboard.R;
import com.example.discussionboard.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    TextView name;
    LoginActivity main = new LoginActivity();

    String email;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);




        name = (TextView) findViewById(R.id.text_name);


        if(user != null) {
            // Name, email address, and profile photo Url
            email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }

        name.setText("Logged in as " + email);



    }



}
