package com.example.discussionboard.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.discussionboard.database.dao.ThreadTempDao;
import com.example.discussionboard.database.entity.ThreadTemp;

@Database(entities = {ThreadTemp.class}, version = 1)
public abstract class ThreadTempDatabase extends RoomDatabase {

    private static ThreadTempDatabase instance;

    public abstract ThreadTempDao threadTempDao();

    public static synchronized ThreadTempDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ThreadTempDatabase.class, "thread_temp_database").fallbackToDestructiveMigration().
                    addCallback(roomCallback).
                    build();
        }
        return instance;
    }

    //Populate SAmple Data to Database
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ThreadTempDao threadTempDao;

        private PopulateDbAsyncTask(ThreadTempDatabase db){
            threadTempDao = db.threadTempDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            threadTempDao.insert(new ThreadTemp("Kali Linux","Virtual Machine","Jean"));
            threadTempDao.insert(new ThreadTemp("Fifa19","Gaming","Michel"));
            return null;
        }
    }

}
