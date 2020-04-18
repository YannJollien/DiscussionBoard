package com.example.discussionboard.ui.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;

import com.example.discussionboard.adapter.ThreadTempAdapter;
import com.example.discussionboard.adapter.ThreadTempAdapterView;

import com.example.discussionboard.databse.entity.Thread;
import com.example.discussionboard.databse.entity.ThreadTemp;

import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.thread.ThreadViewModel;
import com.example.discussionboard.viewmodel.threadTemp.ThreadTempListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThreadsAdminActivity extends AppCompatActivity {

    DatabaseReference reference;

    ArrayList<ThreadTemp> threadTempList;
    ThreadTempAdapterView adapter;

    ThreadTempAdapter threadTempAdapter = new ThreadTempAdapter();

    ThreadTempListViewModel model;
    ThreadViewModel viewModel;

    String threadId;

    String threadString;
    String categoryString;

    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_temp_thread_view);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_tempThread);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Check if incoming intent
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            threadId = getIntent().getExtras().getString("threadId", "defaultKey");
        }


        threadTempList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("threadTemp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ThreadTemp threadTemp = dataSnapshot1.getValue(ThreadTemp.class);
                    threadTempList.add(threadTemp);

                }
                adapter = new ThreadTempAdapterView(getApplicationContext(), threadTempList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set Titel of View
        setTitle("Pending Threads");


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.admin_thread_toolbar);
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

                ThreadTemp threadTemp = adapter.getThreadTemp(position);

                reference.child(threadTemp.getId()).removeValue();
                startActivity(new Intent(ThreadsAdminActivity.this, AdminActivity.class));


            }
        }).attachToRecyclerView(recyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();

                ThreadTemp threadTemp = adapter.getThreadTemp(position);

                threadId = threadTemp.getId();
                threadString = threadTemp.getThread();
                categoryString = threadTemp.getCategory();
                saveThread();
                reference.child(threadTemp.getId()).removeValue();
                startActivity(new Intent(ThreadsAdminActivity.this, AdminActivity.class));


            }
        }).attachToRecyclerView(recyclerView);

    }

    private void saveThread() {

        Thread thread = new Thread(threadId, threadString, categoryString);

        ThreadViewModel.Factory factory = new ThreadViewModel.Factory(getApplication(), threadId);
        viewModel = ViewModelProviders.of(this, factory).get(ThreadViewModel.class);
        viewModel.createThread(thread, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        Toast.makeText(ThreadsAdminActivity.this, getString(R.string.toast_thread_added),
                Toast.LENGTH_LONG).show();

    }

    //Message start of activity
    @Override
    protected void onResume() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.checkbox_post, null);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipMessage", "NOT checked");


        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);
        adb.setTitle(getString(R.string.alert_info));
        adb.setMessage(Html.fromHtml(getString(R.string.alert_text)+"\n"+(Html.fromHtml(getString(R.string.alert_text1)))));

        adb.setPositiveButton(getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";

                if (dontShowAgain.isChecked()) {
                    checkBoxResult = "checked";
                }

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString("skipMessage", checkBoxResult);
                editor.commit();

                // Do what you want to do on "OK" action

                return;
            }
        });

        adb.setNegativeButton(getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";

                if (dontShowAgain.isChecked()) {
                    checkBoxResult = "checked";
                }

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString("skipMessage", checkBoxResult);
                editor.commit();

                // Do what you want to do on "CANCEL" action

                return;
            }
        });

        if (!skipMessage.equals("checked")) {
            adb.show();
        }

        super.onResume();
    }





}
