package com.example.discussionboard.ui.post;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.viewmodel.PostViewModel;
import com.example.discussionboard.ui.MenuActivity;
import com.example.discussionboard.ui.thread.ShowThreads;

import java.util.List;

public class ShowPostsDetail extends AppCompatActivity {

    private EditText submitter;
    private EditText text;
    private EditText date;

    private Button edit;
    private Button save_changes;

    MenuActivity menuActivity;
    PostViewModel postViewModel;


    int id;
    String sub;
    String txt;
    String dt;
    int userId;
    int postId;
    int threadId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts_detail);
        setTitle(getString(R.string.post_detail_title));

        startActivity();

    }

    public void startActivity(){
        submitter = findViewById(R.id.submitter_details);
        text = findViewById(R.id.text_details);
        date = findViewById(R.id.date_details);
        edit = findViewById(R.id.update);
        save_changes = findViewById(R.id.save_changes);

        save_changes.setVisibility(View.GONE);

        //Disable the editText for the view only
        submitter.setEnabled(false);
        text.setEnabled(false);
        date.setEnabled(false);

        id = getIntent().getExtras().getInt("id");
        sub = getIntent().getExtras().getString("submitter");
        txt = getIntent().getExtras().getString("text");
        dt = getIntent().getExtras().getString("date");
        userId = getIntent().getExtras().getInt("userId");
        postId = getIntent().getExtras().getInt("postId");
        threadId = getIntent().getExtras().getInt("threadId");

        submitter.setText(sub);
        text.setText(txt);
        date.setText(dt);

        //Set Button visible when id ok
        setVisible(edit);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_changes.setVisibility(View.VISIBLE);
                //Make text editable
                submitter.setEnabled(true);
                text.setEnabled(true);
                date.setEnabled(true);
                //Make save button visible to save changes
                save_changes.setVisibility(View.VISIBLE);
            }
        });

        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subString = submitter.getText().toString();
                String textString = text.getText().toString();
                String dateText = date.getText().toString();
                System.out.println("New text: "+textString);
                Post post = new Post(subString,textString,dateText,threadId,userId);
                post.setId(id);
                postViewModel.update(post);
                Toast.makeText(getApplicationContext(), getString(R.string.toast_post_update),
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ShowPostsDetail.this,ShowPosts.class);
                startActivity(intent);
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.show_post_details_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void setVisible(Button button){
        menuActivity = new MenuActivity();
        int idFromLogin = menuActivity.userId;
        if (idFromLogin != userId){
            //Hide button
            button.setVisibility(View.GONE);
        }else {
            //Enable button
            button.setVisibility(View.VISIBLE);
        }


    }



}
