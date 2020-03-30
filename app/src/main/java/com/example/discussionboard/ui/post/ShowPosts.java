package com.example.discussionboard.ui.post;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.discussionboard.adapter.PostAdapter;
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.viewmodel.PostViewModel;
import com.example.discussionboard.ui.MenuActivity;
import com.example.discussionboard.ui.thread.ShowThreads;

import java.util.List;

public class ShowPosts extends AppCompatActivity {

    int idThread;
    int userId;
    MenuActivity menuActivity;
    ShowThreads showThreads;
    private PostViewModel postViewModel;
    private Button delete;
    private Button update;
    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);
        setTitle(getString(R.string.post_title));


        //Show message



        menuActivity = new MenuActivity();

        userId = menuActivity.userId;

        System.out.println("User Id from Show Port " + userId);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PostAdapter adapter = new PostAdapter();

        recyclerView.setAdapter(adapter);


        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                showThreads = new ShowThreads();
                idThread = showThreads.threadId;
                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getThreadId() != idThread) {
                        posts.remove(i);

                    }
                    adapter.setPosts(posts);
                }
            }
        });

        update = findViewById(R.id.update);

        //Delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                System.out.println("ID vom Menu her " + userId);
                System.out.println("ID vom Adapter " + adapter.getPostAt(viewHolder.getAdapterPosition()).getUserId());
                if (adapter.getPostAt(viewHolder.getAdapterPosition()).getUserId() == userId) {
                    postViewModel.delete(adapter.getPostAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_post_delete),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_post_notdelete),
                            Toast.LENGTH_LONG).show();
                    return;
                }

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
                System.out.println("ID vom Adapter " + post.getUserId());
                System.out.println("Submitter " + post.getSubmitter());
                Intent intent = new Intent(ShowPosts.this, ShowPostsDetail.class);
                intent.putExtra("id", post.getId());
                intent.putExtra("submitter", post.getSubmitter());
                intent.putExtra("text", post.getText());
                intent.putExtra("date", post.getDate());
                intent.putExtra("userId", post.getUserId());
                intent.putExtra("postId", post.getId());
                intent.putExtra("threadId", post.getThreadId());
                startActivity(intent);
            }
        });

        //Back
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.post_toolbar);
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
                Intent intent = new Intent(ShowPosts.this, AddPost.class);
                intent.putExtra("threadId", idThread);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
        adb.setMessage(Html.fromHtml(getString(R.string.alert_text_post)));

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
