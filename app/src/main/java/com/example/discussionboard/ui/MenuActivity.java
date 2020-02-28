package com.example.discussionboard.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.discussionboard.R;
import com.example.discussionboard.ui.login.LoginActivity;
import com.example.discussionboard.ui.thread.ShowThreads;

public class MenuActivity extends AppCompatActivity {

    private Button thread;

    public int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        thread = findViewById(R.id.threads);

        userId = getIntent().getExtras().getInt("userId");

        thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inent= new Intent(MenuActivity.this, ShowThreads.class);
                inent.putExtra("userId",userId);
                startActivity(inent);
            }
        });

        System.out.println("User id :"+userId);

    }

    public int getUserId() {
        return userId;
    }
}
