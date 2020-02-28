package com.example.discussionboard.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.discussionboard.database.dao.PostDao;
import com.example.discussionboard.database.entity.Post;

@Database(entities = {Post.class}, version = 1)
public abstract class PostDatabase extends RoomDatabase{

    private static PostDatabase instance;

    public abstract PostDao postDao();

    public static synchronized PostDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PostDatabase.class, "post_database").fallbackToDestructiveMigration().
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
            new PostDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private PostDao postDao;

        private PopulateDbAsyncTask(PostDatabase db){
            postDao = db.postDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            postDao.insert(new Post("Yann","Nice Game","13.02.2020",1,1));
            postDao.insert(new Post("Peter","Hello there","14.02.2020",2,2));
            return null;
        }
    }

}
