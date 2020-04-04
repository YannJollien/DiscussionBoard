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
import com.example.discussionboard.ui.MenuActivity;

import java.util.List;

public class AddThread extends AppCompatActivity {

    MenuActivity menuActivity;
    ThreadTempViewModel threadTempViewModel;
    FeedViewModel feedViewModel;
    UserViewModel userViewModel;
    int userId;
    String submitterString;
    ThreadTemp threadTemp;
    private EditText thread;
    private EditText category;
    private Button addThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thread);
        setTitle(getString(R.string.thread_add_title));


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
                Toast.makeText(getApplicationContext(), getString(R.string.toast_thread_added),
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
        final String threadString = thread.getText().toString();
        final String categoryString = category.getText().toString();

        if (threadString.trim().isEmpty() || categoryString.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_thread_error),
                    Toast.LENGTH_LONG).show();
            return;
        }

        menuActivity = new MenuActivity();
        userId = menuActivity.userId;

        threadTempViewModel = new ViewModelProvider(this).get(ThreadTempViewModel.class);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId() == userId) {
                        submitterString = users.get(i).getFirstName().toString();
                    }
                }
                //Add thread to temporary db
                System.out.println("Submitter " + submitterString);
                threadTemp = new ThreadTemp(threadString, categoryString, submitterString);
                threadTempViewModel.insert(threadTemp);

            }
        });


    }
}
