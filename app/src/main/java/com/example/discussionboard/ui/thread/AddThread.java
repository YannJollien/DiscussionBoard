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
import androidx.lifecycle.ViewModelProviders;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.ThreadTemp;
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.ThreadTempViewModel;
import com.example.discussionboard.ui.MenuActivity;
import com.example.discussionboard.ui.admin.AdminActivity;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddThread extends AppCompatActivity {

    MenuActivity menuActivity;


    int userId;
    String submitterString;
    ThreadTemp threadTemp;
    private EditText thread;
    private EditText category;
    private Button addThread;

    DatabaseReference databaseThreadTemp;
    ThreadTempViewModel threadTempViewModel;


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

        databaseThreadTemp = FirebaseDatabase.getInstance().getReference("threadTemp");

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
        String id = databaseThreadTemp.push().getKey();

        if (threadString.trim().isEmpty() || categoryString.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_thread_error),
                    Toast.LENGTH_LONG).show();
            return;
        }

        ThreadTemp threadTemp = new ThreadTemp(id,threadString,categoryString,"Yann");

        menuActivity = new MenuActivity();
        userId = menuActivity.userId;

        threadTempViewModel = new ViewModelProvider(this).get(ThreadTempViewModel.class);


        ThreadTempViewModel.Factory factory = new ThreadTempViewModel.Factory(getApplication(), id);
        threadTempViewModel = ViewModelProviders.of(this,factory).get(ThreadTempViewModel.class);
        threadTempViewModel.createThreadTemp(threadTemp, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        Toast.makeText(AddThread.this, "Saved",
                Toast.LENGTH_LONG).show();
        thread.setText("");
        category.setText("");
        startActivity(new Intent(getApplicationContext(), AdminActivity.class));


    }
}
