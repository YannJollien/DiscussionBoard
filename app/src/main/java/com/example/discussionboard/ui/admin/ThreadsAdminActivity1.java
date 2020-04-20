package com.example.discussionboard.ui.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.ThreadAdapterAdmin;
import com.example.discussionboard.adapter.ThreadAdapterView;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.entity.Thread;
import com.example.discussionboard.viewmodel.thread.ThreadListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThreadsAdminActivity1 extends AppCompatActivity {

    DatabaseReference referenceThread;
    DatabaseReference referencePost;

    ArrayList<Thread> threadList;
    ArrayList<Post> postList;
    ThreadAdapterView adapter;

    ThreadListViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_view_admin);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_thread);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final ThreadAdapterAdmin threadAdapter = new ThreadAdapterAdmin();
        recyclerView.setAdapter(threadAdapter);

        threadList = new ArrayList<>();
        postList = new ArrayList<>();

        referencePost = FirebaseDatabase.getInstance().getReference().child("post");
        referencePost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Post post = dataSnapshot1.getValue(Post.class);
                    postList.add(post);
                    System.out.println("PostList "+postList.get(0).getThreadId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        referenceThread = FirebaseDatabase.getInstance().getReference().child("thread");
        referenceThread.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Thread thread = dataSnapshot1.getValue(Thread.class);
                    threadList.add(thread);
                }
                threadAdapter.setThread(threadList);
                adapter = new ThreadAdapterView(getApplicationContext(), threadList);
                recyclerView.setAdapter(threadAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set Titel of View
        setTitle("Thread");


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.thread_toolbar);
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

                for (int j = 0; i < postList.size(); j++) {
                    System.out.println("Current ID"+adapter.getThread(position).getId());
                    System.out.println(" ID Thread from PostList "+postList.get(j).getThreadId());
                    if (adapter.getThread(position).getId().equals(postList.get(j).getThreadId())) {
                        referencePost.child(postList.get(j).getId()).removeValue();
                    }
                }

                Thread thread = adapter.getThread(position);

                referenceThread.child(thread.getId()).removeValue();

                Toast.makeText(getApplicationContext(), getString(R.string.toast_thread_delete),
                        Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);


    }

}
