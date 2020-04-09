package com.example.discussionboard.ui.post;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.discussionboard.R;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.ui.thread.ThreadViewActivity;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.example.discussionboard.viewmodel.post.PostViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class PostAddActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    public EditText submitter;
    public EditText text;
    DatabaseReference databasePost;
    PostViewModel viewModel;
    Button save;
    ArrayList<Post> postList;
    private Button choose;
    private ImageView imageView;
    private StorageReference storageReference;
    private Uri imageUri;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        databasePost = FirebaseDatabase.getInstance().getReference("post");


        setTitle("Add Post");

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.add_post_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        //Get the info by id
        save = (Button) findViewById(R.id.add);
        choose = findViewById(R.id.btnChoose);
        imageView = findViewById(R.id.imgView);

        submitter = (EditText) findViewById(R.id.submitter_in);
        text = (EditText) findViewById(R.id.text_in);

        postList = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        /* on pressing btnSelect SelectImage() is called
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitter.getText().toString().equals("") || text.getText().toString().equals("")) {
                    Toast.makeText(PostAddActivity.this, "empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (mUploadTask != null && mUploadTask.isInProgress()){
                        Toast.makeText(PostAddActivity.this, "Upload in progress",
                                Toast.LENGTH_LONG).show();
                    }else{
                        savePost();
                    }
                }
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        databasePost.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList.clear();

                for (DataSnapshot storageSnapshot : dataSnapshot.getChildren()) {
                    Post post = storageSnapshot.getValue(Post.class);
                    postList.add(post);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void savePost(){
        String submitterString = (submitter.getText().toString());
        String textString = (text.getText().toString());
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //Get current user UID
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentFirebaseUser.getUid();

        //Get thread id
        String threadId = getIntent().getExtras().getString("threadId", "defaultKey");

        String id = databasePost.push().getKey();

        Post post = new Post(id, submitterString, textString, date, threadId, userId);

        PostViewModel.Factory factory = new PostViewModel.Factory(getApplication(), id);
        viewModel = ViewModelProviders.of(this, factory).get(PostViewModel.class);
        viewModel.createPost(post, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        Toast.makeText(PostAddActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        submitter.setText("");
        text.setText("");
        System.out.println(id);
        startActivity(new Intent(PostAddActivity.this, ThreadViewActivity.class));
    }

    /*private void savePostWithImage(String image) {

        String submitterString = (submitter.getText().toString());
        String textString = (text.getText().toString());
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //Get current user UID
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentFirebaseUser.getUid();

        //Get thread id
        String threadId = getIntent().getExtras().getString("threadId", "defaultKey");

        String id = databasePost.push().getKey();

        Post post = new Post(id, submitterString, textString, date, threadId, userId, image);

        PostViewModel.Factory factory = new PostViewModel.Factory(getApplication(), id);
        viewModel = ViewModelProviders.of(this, factory).get(PostViewModel.class);
        viewModel.createPost(post, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        Toast.makeText(PostAddActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        submitter.setText("");
        text.setText("");
        System.out.println(id);
        startActivity(new Intent(PostAddActivity.this, ThreadViewActivity.class));

    }

    public void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            imageView.setImageURI(imageUri);
        }
    }

    public void uploadImage() {

        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() +
                    "." + getFileExtension(imageUri));
            mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            savePostWithImage(taskSnapshot.getUploadSessionUri().toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostAddActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    //Get extension (.jpg(.png)
    private String getFileExtension(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }*/
}
