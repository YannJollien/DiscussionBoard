package com.example.discussionboard.settings;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.discussionboard.R;

public class SettingsAboutActivity extends AppCompatActivity {

    TextView build;
    TextView build1;
    TextView build2;
    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_about);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.info_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        about = (TextView)findViewById(R.id.text_about);

        build = (TextView) findViewById(R.id.about_version);

        build1 = (TextView) findViewById(R.id.about_version1);

        build2 = (TextView) findViewById(R.id.about_version2);

        about.setPaintFlags(about.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


        build1.setText("VersionID " + Build.ID);

        build2.setText("Running on " + Build.DEVICE);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsAboutActivity.this,ContactActivity.class);
                startActivity(intent);
            }
        });
    }


}
