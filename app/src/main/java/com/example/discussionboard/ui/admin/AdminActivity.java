package com.example.discussionboard.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.discussionboard.R;

public class AdminActivity extends AppCompatActivity {

    Button user;
    Button thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        user = findViewById(R.id.button_users);
        thread = findViewById(R.id.button_threads);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, UsersAdminActivity.class);
                startActivity(intent);
            }
        });

        thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, ThreadsAdminActivity.class);
                startActivity(intent);
            }
        });
    }
}
