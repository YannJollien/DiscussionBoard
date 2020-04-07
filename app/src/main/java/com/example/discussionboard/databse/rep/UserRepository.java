package com.example.discussionboard.databse.rep;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.databse.entity.User;

import com.example.discussionboard.databse.fireabse.UserListLiveData;
import com.example.discussionboard.databse.fireabse.UserLiveData;
import com.example.discussionboard.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserRepository {

    private static final String TAG = "UserRepository";
    private static UserRepository instance;

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<User> getUser(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(name);
        return new UserLiveData(reference);
    }

    public LiveData<List<User>> getAllUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users");
        return new UserListLiveData(reference);
    }

    public void insert(final User user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .setValue(user, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final User user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .updateChildren(user.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final User user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<User>> getAllPost(String showName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users");
        return new UserListLiveData(reference);
    }

}
