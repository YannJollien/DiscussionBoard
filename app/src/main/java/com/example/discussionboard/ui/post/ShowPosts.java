package com.example.discussionboard.ui.post;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.example.discussionboard.database.entity.User;
import com.example.discussionboard.database.viewmodel.PostViewModel;
import com.example.discussionboard.ui.MenuActivity;
import com.example.discussionboard.ui.rest.ProfileActivity;
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
    private CheckBox show;

    boolean showMessage = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);
        setTitle("Posts");

        System.out.println("Status "+showMessage);

        //Show message
        if(showMessage==true){
            infoAtStart();
        }


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
                    Toast.makeText(getApplicationContext(), "Post deleted",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Can't delete, you are not the owner of the post",
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

    public void infoAtStart(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowPosts.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_start_post,null);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(true);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
        show = dialogView.findViewById(R.id.dialog_checkbox);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Set positive/yes button click listener
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                if (show.isChecked()){
                    showMessage = false;
                }
                dialog.cancel();
            }
        });
    }

}
