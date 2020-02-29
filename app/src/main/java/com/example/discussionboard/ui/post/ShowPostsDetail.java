package com.example.discussionboard.ui.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.database.viewmodel.PostViewModel;

public class ShowPostsDetail extends AppCompatActivity {

    private TextView submitter;
    private TextView text;
    private TextView date;


    String sub;
    String txt;
    String dt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts_detail);

        submitter = findViewById(R.id.submitter_details);
        text = findViewById(R.id.text_details);
        date = findViewById(R.id.date_details);

        sub = getIntent().getExtras().getString("submitter");
        txt = getIntent().getExtras().getString("text");
        dt = getIntent().getExtras().getString("date");

        submitter.setText(sub);
        text.setText(txt);
        date.setText(dt);

    }



}
