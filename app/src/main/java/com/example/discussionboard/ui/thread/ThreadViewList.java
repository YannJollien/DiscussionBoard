package com.example.discussionboard.ui.thread;


import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.discussionboard.R;
import com.example.discussionboard.adapter.ThreadAdapter;
import com.example.discussionboard.adapter.ThreadAdapterView;
import com.example.discussionboard.databse.entity.Thread;

import com.example.discussionboard.viewmodel.thread.ThreadListViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ThreadViewList extends AppCompatActivity {
    public final static int ADD_NOTE_REQUEST = 1;

    DatabaseReference reference;

    ArrayList<Thread> threadList;
    ThreadAdapterView adapter;

    DatabaseReference databaseStorage;
    ThreadAdapter threadAdapter = new ThreadAdapter();

    ThreadListViewModel model;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_view_2);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_thread);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final ThreadAdapter threadAdapter = new ThreadAdapter();
        recyclerView.setAdapter(threadAdapter);

        threadList = new ArrayList<>();

        databaseStorage = FirebaseDatabase.getInstance().getReference("thread");

        reference = FirebaseDatabase.getInstance().getReference().child("thread");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    System.out.println("Datasnapshot1 get Key----->:" + dataSnapshot1.getKey());
                    System.out.println("Datasnapshot1 get Key----->:" + dataSnapshot1.getValue());
                    Thread thread = dataSnapshot1.getValue(Thread.class);
                    thread.setId(dataSnapshot1.getKey());
                    System.out.println("ID von Thread nach snapshot:" + thread);
                    threadList.add(thread);
                    for (int i = 0; i < threadList.size(); i++){
                        System.out.println("ID von ViewList Thread: " + threadList.get(i).getId());
                    }
                }
                threadAdapter.setThread(threadList);
                adapter = new ThreadAdapterView(getApplicationContext(), threadList);
                recyclerView.setAdapter(threadAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.thread_toolbar);
        setSupportActionBar(myChildToolbar);
        //myChildToolbar.setTitleTextColor(0xFFFFFFFF);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }


}
