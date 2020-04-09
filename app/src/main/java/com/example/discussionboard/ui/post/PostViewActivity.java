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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discussionboard.R;
import com.example.discussionboard.adapter.PostAdapter;
import com.example.discussionboard.adapter.PostAdapterView;
import com.example.discussionboard.adapter.ThreadAdapter;
import com.example.discussionboard.adapter.ThreadAdapterView;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.entity.Thread;
import com.example.discussionboard.ui.thread.ThreadAddActivity;
import com.example.discussionboard.ui.thread.ThreadViewActivity;
import com.example.discussionboard.viewmodel.post.PostListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostViewActivity extends AppCompatActivity {

    DatabaseReference reference;

    ArrayList<Post> postList;
    PostAdapterView adapter;

    PostAdapter postAdapter = new PostAdapter();

    PostListViewModel model;

    String threadId;
    String currentUserId;

    FirebaseUser currentFirebaseUser;

    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final PostAdapter postAdapter = new PostAdapter();
        recyclerView.setAdapter(postAdapter);

        //Get current user uid
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        currentUserId = currentFirebaseUser.getUid();

        //Check if incoming intent
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            threadId = getIntent().getExtras().getString("threadId","defaultKey");
        }



        postList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Post post = dataSnapshot1.getValue(Post.class);
                    if (threadId.equals(post.getThreadId()) && intent.getExtras() != null){
                        postList.add(post);
                    }
                }
                postAdapter.setPost(postList);
                adapter = new PostAdapterView(getApplicationContext(), postList);
                recyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //set Titel of View
        setTitle("Post");


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.post_toolbar);
        setSupportActionBar(myChildToolbar);
        //myChildToolbar.setTitleTextColor(0xFFFFFFFF);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();

                Post post = adapter.getPost(position);

                if (post.getId().equals(currentUserId)){
                    reference.child(post.getId()).removeValue();
                    startActivity(new Intent(PostViewActivity.this, ThreadViewActivity.class));
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_post_delete),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_post_notdelete),
                            Toast.LENGTH_LONG).show();
                    return;
                }




            }
        }).attachToRecyclerView(recyclerView);

        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
                if (post.getImageUrl() != null){
                    Intent intent = new Intent(PostViewActivity.this, PostViewDetailActivity.class);
                    intent.putExtra("postId", post.getId());
                    intent.putExtra("submitter", post.getSubmitter());
                    intent.putExtra("text", post.getText());
                    intent.putExtra("date", post.getDate());
                    intent.putExtra("imageUrl", post.getImageUrl());
                    intent.putExtra("userId", post.getUserId());
                    intent.putExtra("threadId", post.getThreadId());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(PostViewActivity.this, PostViewDetailActivity.class);
                    intent.putExtra("postId", post.getId());
                    intent.putExtra("submitter", post.getSubmitter());
                    intent.putExtra("text", post.getText());
                    intent.putExtra("date", post.getDate());
                    intent.putExtra("userId", post.getUserId());
                    intent.putExtra("threadId", post.getThreadId());
                    startActivity(intent);
                }
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
                Intent intent = new Intent(PostViewActivity.this, PostAddActivity.class);
                intent.putExtra("threadId", threadId);
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
