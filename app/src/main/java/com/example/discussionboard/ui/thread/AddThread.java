package com.example.discussionboard.ui.thread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.ThreadTemp;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.FeedViewModel;
import com.example.discussionboard.database.viewmodel.ThreadTempViewModel;
import com.example.discussionboard.database.viewmodel.UserViewModel;
import com.example.discussionboard.ui.MenuActivity;

import java.util.List;

public class AddThread extends AppCompatActivity {

    MenuActivity menuActivity;
    ThreadTempViewModel threadTempViewModel;
    FeedViewModel feedViewModel;
    UserViewModel userViewModel;
    int userId;
    String submitterString;
    String date;
    String time;
    private EditText thread;
    private EditText category;
    private Button addThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thread);
        setTitle("Add Thread");


        startActivity();


    }

    public void startActivity() {
        thread = findViewById(R.id.thread_in);
        category = findViewById(R.id.category_in);
        addThread = findViewById(R.id.add);

        addThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveThread();
                Toast.makeText(getApplicationContext(), "Thread Added",
                        Toast.LENGTH_LONG).show();
                Intent inent = new Intent(AddThread.this, ShowThreads.class);
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

    public void saveThread() {
        String threadString = thread.getText().toString();
        String categoryString = category.getText().toString();

        if (threadString.trim().isEmpty() || categoryString.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter all informations",
                    Toast.LENGTH_LONG).show();
            return;
        }

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

        ThreadTemp threadTemp = new ThreadTemp(threadString, categoryString,submitterString);

        threadTempViewModel = new ViewModelProvider(this).get(ThreadTempViewModel.class);
        threadTempViewModel.insert(threadTemp);
    }
}
