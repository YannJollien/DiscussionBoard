package com.example.discussionboard.database.repository;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.firebase.ThreadListLiveData;
import com.example.discussionboard.database.firebase.ThreadLiveData;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ThreadRepository {

    private static final String TAG = "ThreadRepository";
    private static ThreadRepository instance;

    private ThreadRepository() {}

    public static ThreadRepository getInstance() {
        if (instance == null) {
            synchronized (ThreadRepository.class) {
                if (instance == null) {
                    instance = new ThreadRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Thread> getThread(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(name);
        return new ThreadLiveData(reference);
    }

    public LiveData<List<Thread>> getAllThreads() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("thread");
        return new ThreadListLiveData(reference);
    }

    public void insert(final Thread thread, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("thread")
                .child(thread.getId())
                .setValue(thread, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Thread thread, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("thread")
                .child(thread.getId())
                .updateChildren(thread.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Thread thread, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("thread")
                .child(thread.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<Thread>> getAllThread(String showName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("thread");
        return new ThreadListLiveData(reference);
    }

}
