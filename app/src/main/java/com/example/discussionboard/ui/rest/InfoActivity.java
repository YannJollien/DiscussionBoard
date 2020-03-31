package com.example.discussionboard.ui.rest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.discussionboard.BuildConfig;
import com.example.discussionboard.R;

public class InfoActivity extends AppCompatActivity {

    TextView creator;
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setTitle("Info");

        creator = findViewById(R.id.creator);
        version = findViewById(R.id.version);

        version.setText(BuildConfig.VERSION_NAME);

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.info_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

    }
}
