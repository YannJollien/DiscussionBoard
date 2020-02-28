package com.example.discussionboard.ui.thread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.viewmodel.ThreadViewModel;

public class AddThread extends AppCompatActivity {

    private EditText thread;
    private EditText category;
    private Button addThread;

    ThreadViewModel threadViewModel;

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

    }
}
