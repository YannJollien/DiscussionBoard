package com.example.discussionboard.ui.admin;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.ThreadAdapter;
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.viewmodel.PostViewModel;
import com.example.discussionboard.database.viewmodel.ThreadViewModel;

import java.util.ArrayList;
import java.util.List;

public class ThreadsAdminActivity1 extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;
    ThreadViewModel threadViewModel;
    PostViewModel postViewModel;
    RecyclerView recyclerView;
    ThreadAdapter adapter;
    int threadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_admin1);
        setTitle(getString(R.string.thread_title));


        recyclerView = findViewById(R.id.recycler_view_threads_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new ThreadAdapter();

        recyclerView.setAdapter(adapter);

        threadViewModel = new ViewModelProvider(this).get(ThreadViewModel.class);
        threadViewModel.getAllThread().observe(this, new Observer<List<Thread>>() {
            @Override
            public void onChanged(List<Thread> threads) {
                adapter.setThreads(threads);
            }
        });

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);


        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.thread_admin_toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        delete();

    }

    public void delete() {

        final ArrayList<Post> listToDelete = new ArrayList<Post>();
        postViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                System.out.println("Thread Id im post " + threadId);
                for (int i = 0; i < posts.size(); i++) {
                    listToDelete.add(posts.get(i));
                }
            }
        });

        //Delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //Delete thread
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                threadViewModel.delete(adapter.getThreadAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), getString(R.string.toast_thread_delete),
                        Toast.LENGTH_LONG).show();
                threadId = adapter.getThreadAt(viewHolder.getAdapterPosition()).getId();
                for (int i = 0; i < listToDelete.size(); i++) {
                    if (listToDelete.get(i).getThreadId() == threadId) {
                        postViewModel.delete(listToDelete.get(i));
                        System.out.println("Post deleted");
                    }
                }
                System.out.println(threadId);
            }
        }).attachToRecyclerView(recyclerView);

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
        adb.setMessage(Html.fromHtml(getString(R.string.alert_text)));

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
