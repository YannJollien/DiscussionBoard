package com.example.discussionboard.ui.rest;

import androidx.appcompat.app.AppCompatActivity;

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

        creator = findViewById(R.id.creator);
        version = findViewById(R.id.version);

        version.setText(BuildConfig.VERSION_NAME);

    }
}
