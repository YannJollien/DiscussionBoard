package com.example.discussionboard.ui.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.entity.Thread;
import com.example.discussionboard.ui.thread.ThreadViewActivity;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.post.PostViewModel;
import com.example.discussionboard.viewmodel.thread.ThreadViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class PostAddActivity extends AppCompatActivity {

    public static EditText submitter;
    public static EditText text;

    DatabaseReference databasePost;
    PostViewModel viewModel;
    Button save;

    ArrayList<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        databasePost = FirebaseDatabase.getInstance().getReference("post");


        setTitle("Add Post");

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.add_post_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);



        //Get the info by id
        save = (Button) findViewById(R.id.add);

        submitter = (EditText) findViewById(R.id.submitter_in);
        text = (EditText) findViewById(R.id.text_in);

        postList = new ArrayList<>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitter.getText().toString().equals("") || text.getText().toString().equals("")) {
                    Toast.makeText(PostAddActivity.this, "empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    savePlantation();
                }

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        databasePost.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList.clear();

                for (DataSnapshot storageSnapshot : dataSnapshot.getChildren()) {
                    Post post = storageSnapshot.getValue(Post.class);
                    postList.add(post);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void savePlantation() {

        String submitterString = (submitter.getText().toString());
        String textString = (text.getText().toString());
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //Get current user UID
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String userId = currentFirebaseUser.getUid();

        //Get thread id
        String threadId = getIntent().getExtras().getString("threadId","defaultKey");

        String id = databasePost.push().getKey();

        Post post = new Post(id, submitterString, textString,date,threadId,userId);

        PostViewModel.Factory factory = new PostViewModel.Factory(getApplication(), id);
        viewModel = ViewModelProviders.of(this, factory).get(PostViewModel.class);
        viewModel.createPost(post, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        Toast.makeText(PostAddActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        submitter.setText("");
        text.setText("");
        System.out.println(id);
        startActivity(new Intent(PostAddActivity.this, ThreadViewActivity.class));

    }


}