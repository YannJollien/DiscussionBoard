package com.example.discussionboard.databse.fireabse;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.databse.entity.ThreadTemp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThreadTempListLiveData extends LiveData<List<ThreadTemp>> {

    private static final String TAG = "ThreadTempListLiveData";

    private final DatabaseReference reference;
    private final ThreadTempListLiveData.MyValueEventListener listener = new ThreadTempListLiveData.MyValueEventListener();

    public ThreadTempListLiveData(DatabaseReference reference, String showName) {
        this.reference= reference;
    }

    public ThreadTempListLiveData(DatabaseReference reference) {
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

    private List<ThreadTemp> toThreads(DataSnapshot snapshot) {
        List<ThreadTemp> threadsTemp = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ThreadTemp entity = childSnapshot.getValue(ThreadTemp.class);
            entity.setId(childSnapshot.getKey());
            threadsTemp.add(entity);
        }
        return threadsTemp;
    }

}
