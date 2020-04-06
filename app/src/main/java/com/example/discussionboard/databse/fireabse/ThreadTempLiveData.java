package com.example.discussionboard.databse.fireabse;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.databse.entity.ThreadTemp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ThreadTempLiveData extends LiveData<ThreadTemp> {

    private static final String TAG = "ThreadTempLiveData";

    private final DatabaseReference reference;
    private final ThreadTempLiveData.MyValueEventListener listener = new ThreadTempLiveData.MyValueEventListener();

    public ThreadTempLiveData(DatabaseReference reference) {
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
            ThreadTemp entity = dataSnapshot.getValue(ThreadTemp.class);
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
