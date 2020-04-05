package com.example.discussionboard.databse.fireabse;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.databse.entity.Thread;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThreadListLiveData extends LiveData<List<Thread>> {

    private static final String TAG = "ThreadListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ThreadListLiveData(DatabaseReference reference, String showName) {
        this.reference= reference;
    }

    public ThreadListLiveData(DatabaseReference reference) {
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
            setValue(toThreads(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Thread> toThreads(DataSnapshot snapshot) {
        List<Thread> threads = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Thread entity = childSnapshot.getValue(Thread.class);
            entity.setId(childSnapshot.getKey());
            threads.add(entity);
        }
        return threads;
    }
}
