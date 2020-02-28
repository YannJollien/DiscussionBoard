package com.example.discussionboard.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.discussionboard.database.dao.ThreadDao;
import com.example.discussionboard.database.entity.Thread;

@Database(entities = {Thread.class}, version = 1)
public abstract class ThreadDatabase extends RoomDatabase {

    private static ThreadDatabase instance;

    public abstract ThreadDao threadDao();

    public static synchronized ThreadDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ThreadDatabase.class, "thread_database").fallbackToDestructiveMigration().
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

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private ThreadDao threadDao;

        private PopulateDbAsyncTask(ThreadDatabase db){
            threadDao = db.threadDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            threadDao.insert(new Thread("Fortnite","Gaming"));
            return null;
        }
    }

}
