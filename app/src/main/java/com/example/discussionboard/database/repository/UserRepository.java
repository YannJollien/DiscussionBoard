package com.example.discussionboard.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.UserDatabase;
import com.example.discussionboard.database.dao.UserDao;
import com.example.discussionboard.database.entity.User;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUser;

    public UserRepository(Application application){

        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUser = userDao.getAllUser();

    }



    public void insert(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user){
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public void deleteAllUsers(){
        new DeleteAllUserAsyncTask(userDao).execute();
    }

    public LiveData<List<User>> getAllUser(){
        return allUser;
    }

    //Inner class Insert
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>{

        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    //Inner class Update
    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>{

        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    //Inner class Delete
    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>{

        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    //Inner class Delete all
    private static class DeleteAllUserAsyncTask extends AsyncTask<Void, Void, Void>{

        private UserDao userDao;

        private DeleteAllUserAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAllUsers();
            return null;
        }
    }

}
