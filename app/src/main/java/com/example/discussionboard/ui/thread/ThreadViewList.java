package com.example.discussionboard.ui.thread;


import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.discussionboard.R;
import com.example.discussionboard.adapter.ThreadAdapter;
import com.example.discussionboard.adapter.ThreadAdapterView;
import com.example.discussionboard.databse.entity.Thread;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.thread.ThreadListViewModel;
import com.google.firebase.auth.FirebaseAuth;
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();

                Thread thread = adapter.getThread(position);

                reference.child(thread.getId()).removeValue();
                startActivity(new Intent(ThreadViewList.this, ThreadViewActivity.class));
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);


    }

    public void deletePlantation(Thread thread){
        ThreadListViewModel.Factory factory = new ThreadListViewModel.Factory(
                getApplication(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        model = ViewModelProviders.of(this, factory).get(ThreadListViewModel.class);
        model.deleteThread(thread, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        startActivity(new Intent(ThreadViewList.this, ThreadViewActivity.class));
        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
    }


}
