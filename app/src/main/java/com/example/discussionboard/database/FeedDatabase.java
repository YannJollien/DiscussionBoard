package com.example.discussionboard.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.discussionboard.database.dao.FeedDao;
import com.example.discussionboard.database.entity.Feed;

@Database(entities = {Feed.class}, version = 1)
public abstract class FeedDatabase extends RoomDatabase {

    private static FeedDatabase instance;

    public abstract FeedDao feedDao();

    public static synchronized FeedDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FeedDatabase.class, "feed_database").fallbackToDestructiveMigration().
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
            new FeedDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private FeedDao feedDao;

        private PopulateDbAsyncTask(FeedDatabase db){
            feedDao = db.feedDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            feedDao.insert(new Feed("Yann","New post added",1));
            feedDao.insert(new Feed("Hans","New post added",1));
            return null;
        }
    }



}
