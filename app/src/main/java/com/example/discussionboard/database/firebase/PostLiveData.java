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

public class PostLiveData extends LiveData<Post> {

    private static final String TAG = "PostLiveData";

    private final DatabaseReference reference;
    private final PostLiveData.MyValueEventListener listener = new PostLiveData.MyValueEventListener();

    public PostLiveData(DatabaseReference reference) {
        this.reference = reference;
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
            Post entity = dataSnapshot.getValue(Post.class);
            try {
                entity.setId(dataSnapshot.getKey());
                setValue(entity);
            }
            catch (NullPointerException e){}
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

}
