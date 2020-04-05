package com.example.discussionboard.databse.rep;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.fireabse.PostListLiveData;
import com.example.discussionboard.databse.fireabse.PostLiveData;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PostRepository {

    private static final String TAG = "PostRepository";
    private static PostRepository instance;

    private PostRepository() {}

    public static PostRepository getInstance() {
        if (instance == null) {
            synchronized (PostRepository.class) {
                if (instance == null) {
                    instance = new PostRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Post> getPost(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("post")
                .child(name);
        return new PostLiveData(reference);
    }

    public LiveData<List<Post>> getAllPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("post");
        return new PostListLiveData(reference);
    }

    public void insert(final Post post, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("post")
                .child(post.getId())
                .setValue(post, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Post post, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("post")
                .child(post.getId())
                .updateChildren(post.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Post post, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("post")
                .child(post.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<Post>> getAllPost(String showName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("post");
        return new PostListLiveData(reference);
    }

}
