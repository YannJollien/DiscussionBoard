package com.example.discussionboard.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.entity.Thread;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostListLiveData extends LiveData<List<Post>> {

    private static final String TAG = "PostListLiveData";

    private final DatabaseReference reference;
    private final PostListLiveData.MyValueEventListener listener = new PostListLiveData.MyValueEventListener();

    public PostListLiveData(DatabaseReference reference, String showName) {
        this.reference= reference;
    }

    public PostListLiveData(DatabaseReference reference) {
        this.reference= reference;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toPosts(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Post> toPosts(DataSnapshot snapshot) {
        List<Post> posts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Post entity = childSnapshot.getValue(Post.class);
            entity.setId(childSnapshot.getKey());
            posts.add(entity);
        }
        return posts;
    }

}
