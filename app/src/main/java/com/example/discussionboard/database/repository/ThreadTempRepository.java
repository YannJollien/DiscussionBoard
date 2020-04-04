package com.example.discussionboard.database.repository;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.entity.ThreadTemp;
import com.example.discussionboard.database.firebase.ThreadListLiveData;
import com.example.discussionboard.database.firebase.ThreadLiveData;
import com.example.discussionboard.database.firebase.ThreadTempListLiveData;
import com.example.discussionboard.database.firebase.ThreadTempLiveData;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ThreadTempRepository {

    private static final String TAG = "ThreadTempRepository";
    private static ThreadTempRepository instance;

    private ThreadTempRepository() {}

    public static ThreadTempRepository getInstance() {
        if (instance == null) {
            synchronized (ThreadTempRepository.class) {
                if (instance == null) {
                    instance = new ThreadTempRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ThreadTemp> getThreadTemp() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("threadTemp");
        return new ThreadTempLiveData(reference);
    }

    public LiveData<List<ThreadTemp>> getAllThreadsTemp() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("threadTemp");
        return new ThreadTempListLiveData(reference);
    }

    public void insert(final ThreadTemp thread, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("threadTemp")
                .child(thread.getId())
                .setValue(thread, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ThreadTemp thread, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("threadTemp")
                .child(thread.getId())
                .updateChildren(thread.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ThreadTemp thread, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("threadTemp")
                .child(thread.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<ThreadTemp>> getAllThreadTemp() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("thread");
        return new ThreadTempListLiveData(reference);
    }

}
