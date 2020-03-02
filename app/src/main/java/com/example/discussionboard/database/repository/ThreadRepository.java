package com.example.discussionboard.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.ThreadDatabase;
import com.example.discussionboard.database.UserDatabase;
import com.example.discussionboard.database.dao.ThreadDao;
import com.example.discussionboard.database.entity.Thread;

import java.util.List;

public class ThreadRepository {

    private ThreadDao threadDao;
    private LiveData<List<Thread>> allThread;

    public ThreadRepository(Application application){

        ThreadDatabase database = ThreadDatabase.getInstance(application);
        threadDao = database.threadDao();
        allThread = threadDao.getAllThreads();

    }

    public void insert(Thread thread){
        new InsertThreadAsyncTask(threadDao).execute(thread);
    }

    public void update(Thread thread){
        new UpdateThreadAsyncTask(threadDao).execute(thread);
    }

    public void delete(Thread thread){
        new DeleteThreadAsyncTask(threadDao).execute(thread);
    }

    public void deleteAllThreads(){
        new DeleteAllThreadAsyncTask(threadDao).execute();
    }

    public LiveData<List<Thread>> getAllThread(){
        return allThread;
    }

    private static class InsertThreadAsyncTask extends AsyncTask<Thread, Void, Void>{

        private ThreadDao threadDao;

        private InsertThreadAsyncTask(ThreadDao threadDao){
            this.threadDao=threadDao;
        }

        @Override
        protected Void doInBackground(Thread... threads) {
            threadDao.insert(threads[0]);
            return null;
        }
    }

    private static class UpdateThreadAsyncTask extends AsyncTask<Thread, Void, Void>{

        private ThreadDao threadDao;

        private UpdateThreadAsyncTask(ThreadDao threadDao){
            this.threadDao=threadDao;
        }

        @Override
        protected Void doInBackground(Thread... threads) {
            threadDao.update(threads[0]);
            return null;
        }
    }

    private static class DeleteThreadAsyncTask extends AsyncTask<Thread, Void, Void>{

        private ThreadDao threadDao;

        private DeleteThreadAsyncTask(ThreadDao threadDao){
            this.threadDao=threadDao;
        }

        @Override
        protected Void doInBackground(Thread... threads) {
            threadDao.delete(threads[0]);
            return null;
        }
    }

    private static class DeleteAllThreadAsyncTask extends AsyncTask<Void, Void, Void>{

        private ThreadDao threadDao;

        private DeleteAllThreadAsyncTask(ThreadDao threadDao){
            this.threadDao=threadDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            threadDao.deleteAllThreads();
            return null;
        }
    }

}
