package com.example.discussionboard.ui.thread;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.ThreadAdapter;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.ui.MenuActivity;
import com.example.discussionboard.ui.post.ShowPosts;

import java.util.List;

public class ShowThreads extends AppCompatActivity {

    private ThreadViewModel threadViewModel;
    private PostViewModel postViewModel;

    private TextView amount;

    int userId;
    public static int threadId;

    MenuActivity menuActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_threads);
        setTitle(getString(R.string.thread_title));

        amount = findViewById(R.id.category_in);

        menuActivity = new MenuActivity();

        userId = menuActivity.userId;

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);

        final ThreadAdapter adapter = new ThreadAdapter();

        recyclerView.setAdapter(adapter);

        threadViewModel = new ViewModelProvider(this).get(ThreadViewModel.class);
        threadViewModel.getAllThread().observe(this, new Observer<List<Thread>>() {
            @Override
            public void onChanged(List<Thread> threads) {
                adapter.setThreads(threads);
            }
        });

        adapter.setOnItemClickListener(new ThreadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Thread thread) {
                Intent intent = new Intent(ShowThreads.this, ShowPosts.class);
                threadId = thread.getId();
                startActivity(intent);
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.thread_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thread_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent inent= new Intent(ShowThreads.this,AddThread.class);
                startActivity(inent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
