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
import com.example.discussionboard.databse.entity.ThreadTemp;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.thread.ThreadViewModel;
import com.example.discussionboard.viewmodel.threadTemp.ThreadTempViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    ThreadTempViewModel viewModel;
    Button save;
    Button date;
    ArrayList<ThreadTemp> threadTempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_add);

        databaseThread = FirebaseDatabase.getInstance().getReference("thread");


        setTitle(getString(R.string.thread_add_title));

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

        threadTempList = new ArrayList<>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread.getText().toString().equals("") || category.getText().toString().equals("")) {
                    Toast.makeText(ThreadAddActivity.this, getString(R.string.toast_thread_error),
                            Toast.LENGTH_LONG).show();
                } else {
                    saveThreadTemp();
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

                threadTempList.clear();

                for (DataSnapshot storageSnapshot : dataSnapshot.getChildren()) {
                    ThreadTemp threadTemp = storageSnapshot.getValue(ThreadTemp.class);
                    threadTempList.add(threadTemp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveThreadTemp() {

        String threadString = (thread.getText().toString());
        String categoryString = (category.getText().toString());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String submitterString = user.getEmail();

        String id = databaseThread.push().getKey();

        ThreadTemp threadTemp = new ThreadTemp(id, threadString, categoryString,submitterString);

        ThreadTempViewModel.Factory factory = new ThreadTempViewModel.Factory(getApplication(), id);
        viewModel = ViewModelProviders.of(this, factory).get(ThreadTempViewModel.class);
        viewModel.createThreadTemp(threadTemp, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        startActivity(new Intent(ThreadAddActivity.this, ThreadViewActivity.class));
        Toast.makeText(ThreadAddActivity.this, getString(R.string.toast_thread_added),
                Toast.LENGTH_LONG).show();
        thread.setText("");
        category.setText("");
        System.out.println(id);
        startActivity(new Intent(getApplicationContext(), ThreadViewActivity.class));

    }


}
