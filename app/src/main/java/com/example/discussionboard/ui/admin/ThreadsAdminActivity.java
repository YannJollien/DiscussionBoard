package com.example.discussionboard.ui.admin;

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

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.ThreadTempAdapter;
import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.entity.ThreadTemp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ThreadsAdminActivity extends AppCompatActivity {

    ThreadTempViewModel threadTempViewModel;
    ThreadViewModel threadViewModel;
    FeedViewModel feedViewModel;

    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads_admin);
        setTitle(getString(R.string.thread_title));



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
                Toast.makeText(getApplicationContext(), getString(R.string.toast_thread_delete),
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

                Feed feed = new Feed(submitterString,getString(R.string.feed_thread_added),2,dateFeed,time);
                feedViewModel.insert(feed);
                threadTempViewModel.delete(adapter.getThreadTempAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), getString(R.string.toast_thread_ok),
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
