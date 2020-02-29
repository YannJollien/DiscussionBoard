package com.example.discussionboard.ui.post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.PostAdapter;
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.viewmodel.PostViewModel;
import com.example.discussionboard.ui.MenuActivity;
import com.example.discussionboard.ui.thread.ShowThreads;

import java.util.ArrayList;
import java.util.List;

public class ShowPosts extends AppCompatActivity {

    private PostViewModel postViewModel;

    int idThread;
    int userId;

    private Button delete;
    private Button update;

    MenuActivity menuActivity;
    ShowThreads showThreads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);
        setTitle("Posts");

        menuActivity = new MenuActivity();

        userId = menuActivity.userId;

        System.out.println("User Id from Show Port "+userId);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PostAdapter adapter = new PostAdapter();

        recyclerView.setAdapter(adapter);

        ArrayList <Post> postsOk = new ArrayList<>();

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                showThreads = new ShowThreads();
                idThread =  showThreads.threadId;
                for (int i = 0; i < posts.size();i++){
                    if (posts.get(i).getThreadId()!= idThread){
                        posts.remove(i);

                    }
                    adapter.setPosts(posts);
                }
            }
        });

        update = findViewById(R.id.update);

        //Delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                System.out.println("ID vom Menu her "+userId);
                System.out.println("ID vom Adapter "+adapter.getPostAt(viewHolder.getAdapterPosition()).getUserId());
                if (adapter.getPostAt(viewHolder.getAdapterPosition()).getUserId() == userId){
                    postViewModel.delete(adapter.getPostAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getApplicationContext(), "Post deleted",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Can't delete, you are not the owner of the post",
                            Toast.LENGTH_LONG).show();
                }

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
                System.out.println("ID vom Adapter "+post.getUserId());
                System.out.println("Submitter "+post.getSubmitter());
                Intent intent = new Intent(ShowPosts.this,ShowPostsDetail.class);
                intent.putExtra("submitter",post.getSubmitter());
                intent.putExtra("text",post.getText());
                intent.putExtra("date",post.getDate());
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thread_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent= new Intent(ShowPosts.this,AddPost.class);
                intent.putExtra("threadId", idThread);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
