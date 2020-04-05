package com.example.discussionboard.ui.thread;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.ThreadAdapterView;
import com.example.discussionboard.databse.entity.Thread;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThreadViewActivity extends AppCompatActivity {

    DatabaseReference reference;

    Button bAdd;

    ArrayList<Thread> threadList;
    ThreadAdapterView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_view);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_thread);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        threadList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("plantation");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Thread thread = dataSnapshot1.getValue(Thread.class);
                    threadList.add(thread);
                }
                adapter = new ThreadAdapterView(getApplicationContext(), threadList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bAdd = findViewById(R.id.btn_add_thread);


        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThreadViewActivity.this, ThreadAddActivity.class));
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

    }

}
