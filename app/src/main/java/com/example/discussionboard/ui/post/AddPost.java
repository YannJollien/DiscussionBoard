package com.example.discussionboard.ui.post;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.viewmodel.FeedViewModel;
import com.example.discussionboard.database.viewmodel.PostViewModel;
import com.example.discussionboard.ui.MenuActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPost extends AppCompatActivity {

    private EditText submitter;
    private EditText text;
    private Button addPost;

    PostViewModel postViewModel;
    FeedViewModel feedViewModel;

    String dateFeed;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        submitter = findViewById(R.id.submitter_in);
        text = findViewById(R.id.text_in);
        addPost = findViewById(R.id.add);

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
                Toast.makeText(getApplicationContext(), "Post Added",
                        Toast.LENGTH_LONG).show();
                Intent inent= new Intent(AddPost.this, ShowPosts.class);
                startActivity(inent);
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.add_post_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

    }

    public void savePost(){
        MenuActivity m = new MenuActivity();
        String submitterString = submitter.getText().toString();
        String textString = text.getText().toString();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        int idThread = getIntent().getExtras().getInt("threadId");
        int idUser = m.getUserId();

        if (submitterString.trim().isEmpty() ||textString.trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter all informations",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Post post = new Post(submitterString,textString,date,idThread,idUser);

        //Add to post
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.insert(post);

        //Add to feed
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
        dateFeed = sdfDate.format(new Date());
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        time = sdfTime.format(new Date());

        Feed feed = new Feed(submitterString,"New post added",1,dateFeed,time);

        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        feedViewModel.insert(feed);

    }
}
