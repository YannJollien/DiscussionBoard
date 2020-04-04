package com.example.discussionboard.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.entity.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedListLiveData extends LiveData<List<Feed>> {

    private static final String TAG = "FeedListLiveData";

    private final DatabaseReference reference;
    private final FeedListLiveData.MyValueEventListener listener = new FeedListLiveData.MyValueEventListener();

    public FeedListLiveData(DatabaseReference reference, String showName) {
        this.reference= reference;
    }

    public FeedListLiveData(DatabaseReference reference) {
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
            setValue(toFeeds(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Feed> toFeeds(DataSnapshot snapshot) {
        List<Feed> feeds = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Feed entity = childSnapshot.getValue(Feed.class);
            entity.setId(childSnapshot.getKey());
            feeds.add(entity);
        }
        return feeds;
    }
}
