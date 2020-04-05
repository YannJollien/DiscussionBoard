package com.example.discussionboard.ui.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.PostAdapter;
import com.example.discussionboard.adapter.PostAdapterView;
import com.example.discussionboard.adapter.ThreadAdapterView;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.entity.Thread;
import com.example.discussionboard.ui.thread.ThreadAddActivity;
import com.example.discussionboard.ui.thread.ThreadViewActivity;
import com.example.discussionboard.viewmodel.post.PostListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostViewActivity extends AppCompatActivity {

    DatabaseReference reference;

    ArrayList<Post> postList;
    PostAdapterView adapter;

    PostAdapter postAdapter = new PostAdapter();

    PostListViewModel model;

    String threadId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Check if incoming intent
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            threadId = getIntent().getExtras().getString("threadId","defaultKey");
        }



        postList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Post post = dataSnapshot1.getValue(Post.class);
                    if (threadId.equals(post.getThreadId())){
                        postList.add(post);
                    }
                }
                adapter = new PostAdapterView(getApplicationContext(), postList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set Titel of View
        setTitle("Post");


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.post_toolbar);
        setSupportActionBar(myChildToolbar);
        //myChildToolbar.setTitleTextColor(0xFFFFFFFF);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();

                Post post = adapter.getPost(position);

                reference.child(post.getId()).removeValue();
                startActivity(new Intent(PostViewActivity.this, ThreadViewActivity.class));


            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thread_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(PostViewActivity.this, PostAddActivity.class);
                intent.putExtra("threadId", threadId);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
