package com.example.discussionboard.ui.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.post.PostListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PostViewDetailActivity extends AppCompatActivity {

    TextView submitter;
    TextView text;
    TextView date;
    ImageView image;

    Button edit;
    Button save;

    DatabaseReference reference;
    DatabaseReference databasePost;
    ArrayList<Post> postList;

    PostListViewModel model;

    String sub;
    String txt;
    String dt;
    String userId;
    String postId;
    String threadId;


    String currentUserId;

    FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_detail);

        startActivity();

    }

    public void startActivity() {
        submitter = findViewById(R.id.submitter_details);
        text = findViewById(R.id.text_details);
        date = findViewById(R.id.date_details);
        image = findViewById(R.id.imgView);
        edit = findViewById(R.id.update);
        save = findViewById(R.id.save_changes);

        save.setVisibility(View.GONE);

        //Disable the editText for the view only
        submitter.setEnabled(false);
        text.setEnabled(false);
        date.setEnabled(false);

        sub = getIntent().getExtras().getString("submitter");
        txt = getIntent().getExtras().getString("text");
        dt = getIntent().getExtras().getString("date");
        userId = getIntent().getExtras().getString("userId");
        postId = getIntent().getExtras().getString("postId");
        threadId = getIntent().getExtras().getString("threadId");

        System.out.println("PostId " + postId);

        submitter.setText(sub);
        text.setText(txt);
        date.setText(dt);

        //Show image
        // Load the image using Glide
        //Glide.with(this).load(imageUrl).into(image);


        databasePost = FirebaseDatabase.getInstance().getReference("post");
        reference = FirebaseDatabase.getInstance().getReference().child("post");

        postList = new ArrayList<>();

        //Get current user uid
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = currentFirebaseUser.getUid();


        //Set Button visible when id ok
        setVisible(edit);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setVisibility(View.VISIBLE);
                //Make text editable
                submitter.setEnabled(true);
                text.setEnabled(true);
                date.setEnabled(true);
                //Make save button visible to save changes
                save.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Click");
                updatePost();
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.show_post_details_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void updatePost() {
        String submitterString = submitter.getText().toString();
        String textString = text.getText().toString();
        String dateString = date.getText().toString();


        Post post = new Post(postId, submitterString, textString, dateString, threadId, userId);
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getId().equals(postId)) {
                postList.set(i, post);
            }
        }

        //reference.child(post.getId()).setValue(post);
        PostListViewModel.Factory factory = new PostListViewModel.Factory(getApplication(), postId);
        model = ViewModelProviders.of(this, factory).get(PostListViewModel.class);
        model.updatePost(post, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        startActivity(new Intent(PostViewDetailActivity.this, PostViewActivity.class));
        Toast.makeText(PostViewDetailActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        submitter.setText("");
        text.setText("");
        text.setText("");
        finish();

    }

    public void setVisible(Button button) {
        if (!currentUserId.equals(userId)) {
            //Hide button
            button.setVisibility(View.GONE);
        } else {
            //Enable button
            button.setVisibility(View.VISIBLE);
        }


    }

}
