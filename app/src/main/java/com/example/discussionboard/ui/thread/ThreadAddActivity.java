package com.example.discussionboard.ui.thread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;


import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Thread;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.plantation.ThreadViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ThreadAddActivity extends AppCompatActivity {

    public static EditText thread;
    public static EditText category;
    public static TextView result;
    DatabaseReference databaseThread;
    ThreadViewModel viewModel;
    Button save;
    Button date;
    ArrayList<Thread> threadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_add);

        databaseThread = FirebaseDatabase.getInstance().getReference("plantation");


        setTitle("Add Thread");

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.thread_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        //Get the info by id
        save = (Button) findViewById(R.id.save_add_thread);

        thread = (EditText) findViewById(R.id.add_thread);
        category = (EditText) findViewById(R.id.add_category);

        threadList = new ArrayList<>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread.getText().toString().equals("") || category.getText().toString().equals("")) {
                    Toast.makeText(ThreadAddActivity.this, "empty fields",
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

        databaseThread.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                threadList.clear();

                for (DataSnapshot storageSnapshot : dataSnapshot.getChildren()) {
                    Thread thread = storageSnapshot.getValue(Thread.class);
                    threadList.add(thread);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void savePlantation() {

        String threadString = (thread.getText().toString());
        String categoryString = (category.getText().toString());

        String id = databaseThread.push().getKey();

        Thread plantation = new Thread(id, threadString, categoryString);

        ThreadViewModel.Factory factory = new ThreadViewModel.Factory(getApplication(), id);
        viewModel = ViewModelProviders.of(this, factory).get(ThreadViewModel.class);
        viewModel.createThread(plantation, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        startActivity(new Intent(ThreadAddActivity.this, ThreadViewActivity.class));
        Toast.makeText(ThreadAddActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        thread.setText("");
        category.setText("");
        System.out.println(id);
        startActivity(new Intent(getApplicationContext(), ThreadViewActivity.class));

    }


}
