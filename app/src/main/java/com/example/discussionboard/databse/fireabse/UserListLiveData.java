package com.example.discussionboard.databse.fireabse;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserListLiveData extends LiveData<List<User>> {

    private static final String TAG = "UserListLiveData";

    private final DatabaseReference reference;
    private final UserListLiveData.MyValueEventListener listener = new UserListLiveData.MyValueEventListener();

    public UserListLiveData(DatabaseReference reference, String showName) {
        this.reference= reference;
    }

    public UserListLiveData(DatabaseReference reference) {
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
            setValue(toUsers(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<User> toUsers(DataSnapshot snapshot) {
        List<User> users = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            User entity = childSnapshot.getValue(User.class);
            entity.setId(childSnapshot.getKey());
            users.add(entity);
        }
        return users;
    }

}
