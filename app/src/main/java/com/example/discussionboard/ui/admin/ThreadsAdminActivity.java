package com.example.discussionboard.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.ThreadTempAdapter;
import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.entity.ThreadTemp;
import com.example.discussionboard.database.viewmodel.FeedViewModel;
import com.example.discussionboard.database.viewmodel.ThreadTempViewModel;
import com.example.discussionboard.database.viewmodel.ThreadViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ThreadsAdminActivity extends AppCompatActivity {

    ThreadTempViewModel threadTempViewModel;
    ThreadViewModel threadViewModel;
    FeedViewModel feedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_admin);
        setTitle("Threads");



        RecyclerView recyclerView = findViewById(R.id.recycler_view_threads_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ThreadTempAdapter adapter = new ThreadTempAdapter();


        recyclerView.setAdapter(adapter);


        threadTempViewModel = new ViewModelProvider(this).get(ThreadTempViewModel.class);
        threadTempViewModel.getAllThreadTemp().observe(this, new Observer<List<ThreadTemp>>() {
            @Override
            public void onChanged(List<ThreadTemp> threadTemps) {
                    adapter.setThreadsTemp(threadTemps);
                }
        });

        threadViewModel = new ViewModelProvider(this).get(ThreadViewModel.class);
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);


        //Delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                threadTempViewModel.delete(adapter.getThreadTempAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Thread deleted",
                        Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        //Accept
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Add to threads
                String threadString = adapter.getThreadTempAt(viewHolder.getAdapterPosition()).getThread();
                String categoryString = adapter.getThreadTempAt(viewHolder.getAdapterPosition()).getCategory();
                String submitterString = adapter.getThreadTempAt(viewHolder.getAdapterPosition()).getSubmitter();
                Thread thread = new Thread(threadString,categoryString);
                threadViewModel.insert(thread);

                //Add to feed
                //Add to feed
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
                String dateFeed = sdfDate.format(new Date());
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                String time = sdfTime.format(new Date());

                Feed feed = new Feed(submitterString,"New thread added",2,dateFeed,time);
                feedViewModel.insert(feed);
                threadTempViewModel.delete(adapter.getThreadTempAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Thread validated",
                        Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.thread_admin_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

    }

}
