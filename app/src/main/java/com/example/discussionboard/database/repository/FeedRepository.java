package com.example.discussionboard.database.repository;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.firebase.FeedListLiveData;
import com.example.discussionboard.database.firebase.FeedLiveData;
import com.example.discussionboard.database.firebase.PostListLiveData;
import com.example.discussionboard.database.firebase.PostLiveData;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FeedRepository {

    private static final String TAG = "FeedRepository";
    private static FeedRepository instance;

    private FeedRepository() {}

    public static FeedRepository getInstance() {
        if (instance == null) {
            synchronized (FeedRepository.class) {
                if (instance == null) {
                    instance = new FeedRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Feed> getFeed() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("feed");
        return new FeedLiveData(reference);
    }

    public LiveData<List<Feed>> getAllFeed() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("feed");
        return new FeedListLiveData(reference);
    }

    public void insert(final Feed feed, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("feed")
                .child(feed.getId())
                .setValue(feed, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Feed feed, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("feed")
                .child(feed.getId())
                .updateChildren(feed.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Feed feed, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("feed")
                .child(feed.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<Feed>> getAllFeed(String showName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("feed");
        return new FeedListLiveData(reference);
    }

}
