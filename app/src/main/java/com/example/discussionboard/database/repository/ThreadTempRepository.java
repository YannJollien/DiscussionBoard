package com.example.discussionboard.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.ThreadTempDatabase;
import com.example.discussionboard.database.dao.ThreadTempDao;
import com.example.discussionboard.database.entity.ThreadTemp;

import java.util.List;

public class ThreadTempRepository {

    private ThreadTempDao threadTempDao;
    private LiveData<List<ThreadTemp>> allThreadTemp;

    public ThreadTempRepository(Application application){

        ThreadTempDatabase database = ThreadTempDatabase.getInstance(application);
        threadTempDao = database.threadTempDao();
        allThreadTemp = threadTempDao.getAllThreadsTemp();

    }

    public void insert(ThreadTemp threadTemp){
        new InsertThreadTempAsyncTask(threadTempDao).execute(threadTemp);
    }

    public void update(ThreadTemp threadTemp){
        new UpdateThreadTempAsyncTask(threadTempDao).execute(threadTemp);
    }

    public void delete(ThreadTemp threadTemp){
        new DeleteThreadTempAsyncTask(threadTempDao).execute(threadTemp);
    }

    public void deleteAllThreadsTemp(){
        new DeleteAllThreadTempAsyncTask(threadTempDao).execute();
    }

    public LiveData<List<ThreadTemp>> getAllThreadTemp(){
        return allThreadTemp;
    }

    private static class InsertThreadTempAsyncTask extends AsyncTask<ThreadTemp, Void, Void> {

        private ThreadTempDao threadTempDao;

        private InsertThreadTempAsyncTask(ThreadTempDao threadTempDao){
            this.threadTempDao=threadTempDao;
        }

        @Override
        protected Void doInBackground(ThreadTemp... threadsTemp) {
            threadTempDao.insert(threadsTemp[0]);
            return null;
        }
    }

    private static class UpdateThreadTempAsyncTask extends AsyncTask<ThreadTemp, Void, Void>{

        private ThreadTempDao threadTempDao;

        private UpdateThreadTempAsyncTask(ThreadTempDao threadTempDao){
            this.threadTempDao=threadTempDao;
        }

        @Override
        protected Void doInBackground(ThreadTemp... threadsTemp) {
            threadTempDao.update(threadsTemp[0]);
            return null;
        }
    }

    private static class DeleteThreadTempAsyncTask extends AsyncTask<ThreadTemp, Void, Void>{

        private ThreadTempDao threadTempDao;

        private DeleteThreadTempAsyncTask(ThreadTempDao threadTempDao){
            this.threadTempDao=threadTempDao;
        }

        @Override
        protected Void doInBackground(ThreadTemp... threadsTemp) {
            threadTempDao.delete(threadsTemp[0]);
            return null;
        }
    }

    private static class DeleteAllThreadTempAsyncTask extends AsyncTask<Void, Void, Void>{

        private ThreadTempDao threadTempDao;

        private DeleteAllThreadTempAsyncTask(ThreadTempDao threadTempDao){
            this.threadTempDao=threadTempDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            threadTempDao.deleteAllThreadsTemp();
            return null;
        }
    }

}
