package com.example.discussionboard.ui.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.discussionboard.R;

public class AdminActivity extends AppCompatActivity {

    Button user;
    Button thread1;
    Button thread2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle(getString(R.string.admin_tile));

        user = findViewById(R.id.button_users);
        thread1 = findViewById(R.id.button_threads);
        thread2 = findViewById(R.id.button_threads1);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, UsersAdminActivity.class);
                startActivity(intent);
            }
        });

        thread1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, ThreadsAdminActivity.class);
                startActivity(intent);
            }
        });

        thread2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, ThreadsAdminActivity1.class);
                startActivity(intent);
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }
}
