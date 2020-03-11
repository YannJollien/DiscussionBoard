package com.example.discussionboard.ui.thread;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.FeedViewModel;
import com.example.discussionboard.database.viewmodel.ThreadViewModel;
import com.example.discussionboard.database.viewmodel.UserViewModel;
import com.example.discussionboard.ui.MenuActivity;

import java.util.List;

public class AddThread extends AppCompatActivity {

    private EditText thread;
    private EditText category;
    private Button addThread;

    MenuActivity menuActivity;

    ThreadViewModel threadViewModel;
    FeedViewModel feedViewModel;
    UserViewModel userViewModel;

    int userId;
    String submitterString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thread);
        setTitle("Add Thread");

        thread = findViewById(R.id.thread_in);
        category = findViewById(R.id.category_in);
        addThread = findViewById(R.id.add);

        addThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveThread();
                Toast.makeText(getApplicationContext(), "Thread Added",
                        Toast.LENGTH_LONG).show();
                Intent inent= new Intent(AddThread.this,ShowThreads.class);
                startActivity(inent);
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.add_thread_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);



    }

    public void saveThread(){
        String threadString = thread.getText().toString();
        String categoryString = category.getText().toString();

        if (threadString.trim().isEmpty() ||categoryString.trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter all informations",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Thread thread = new Thread(threadString,categoryString);

        threadViewModel = new ViewModelProvider(this).get(ThreadViewModel.class);
        threadViewModel.insert(thread);

        //Add to feed

        menuActivity = new MenuActivity();
        userId = menuActivity.userId;

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId() == userId) {
                        submitterString = users.get(i).getFirstName().toString();
                    }
                }
            }
        });



        Feed feed = new Feed(submitterString,"New thread added",2);

        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        feedViewModel.insert(feed);

    }
}
