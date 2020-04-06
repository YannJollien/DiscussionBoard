package com.example.discussionboard.databse.rep;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.databse.entity.ThreadTemp;

import com.example.discussionboard.databse.fireabse.ThreadTempListLiveData;
import com.example.discussionboard.databse.fireabse.ThreadTempLiveData;
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

    public LiveData<ThreadTemp> getThreadTemp(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("threadTemp")
                .child(name);
        return new ThreadTempLiveData(reference);
    }

    public LiveData<List<ThreadTemp>> getAllThreadsTemp() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("threadTemp");
        return new ThreadTempListLiveData(reference);
    }

    public void insert(final ThreadTemp threadTemp, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("threadTemp")
                .child(threadTemp.getId())
                .setValue(threadTemp, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ThreadTemp threadTemp, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("threadTemp")
                .child(threadTemp.getId())
                .updateChildren(threadTemp.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ThreadTemp threadTemp, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("threadTemp")
                .child(threadTemp.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<ThreadTemp>> getAllThreadTemp(String showName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("threadTemp");
        return new ThreadTempListLiveData(reference);
    }

}
