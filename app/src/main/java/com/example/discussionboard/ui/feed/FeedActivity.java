package com.example.discussionboard.ui.feed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.FeedAdapter;
import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.viewmodel.FeedViewModel;

import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private TextView feed;
    private FeedViewModel feedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setTitle(getString(R.string.feed_title));

        startAcitivty();

    }


    public void startAcitivty(){
        //Setting up the recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final FeedAdapter adapter = new FeedAdapter();

        recyclerView.setAdapter(adapter);


        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        feedViewModel.getAllFeed().observe(this, new Observer<List<Feed>>() {
            @Override
            public void onChanged(List<Feed> feeds) {
                adapter.setFeeds(feeds);
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.feed_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }
}
