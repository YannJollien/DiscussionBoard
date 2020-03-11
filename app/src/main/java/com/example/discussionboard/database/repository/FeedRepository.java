package com.example.discussionboard.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.FeedDatabase;
import com.example.discussionboard.database.dao.FeedDao;
import com.example.discussionboard.database.entity.Feed;

import java.util.List;

public class FeedRepository {

    private FeedDao feeDao;
    private LiveData<List<Feed>> allFeed;

    public FeedRepository(Application application){

        FeedDatabase database = FeedDatabase.getInstance(application);
        feeDao = database.feedDao();
        allFeed = feeDao.getAllFeeds();

    }

    public void insert(Feed feed){
        new InsertFeedAsyncTask(feeDao).execute(feed);
    }

    public void update(Feed feed){
        new UpdateFeedAsyncTask(feeDao).execute(feed);
    }

    public void delete(Feed feed){
        new DeleteFeedAsyncTask(feeDao).execute(feed);
    }

    public void deleteAllFeeds(){
        new DeleteAllFeedAsyncTask(feeDao).execute();
    }

    public LiveData<List<Feed>> getAllFeed(){
        return allFeed;
    }

    private static class InsertFeedAsyncTask extends AsyncTask<Feed, Void, Void> {

        private FeedDao feedDao;

        private InsertFeedAsyncTask(FeedDao feedDao){
            this.feedDao =feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.insert(feeds[0]);
            return null;
        }
    }

    private static class UpdateFeedAsyncTask extends AsyncTask<Feed, Void, Void>{

        private FeedDao feedDao;

        private UpdateFeedAsyncTask(FeedDao feedDao){
            this.feedDao=feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.update(feeds[0]);
            return null;
        }
    }

    private static class DeleteFeedAsyncTask extends AsyncTask<Feed, Void, Void>{

        private FeedDao feedDao;

        private DeleteFeedAsyncTask(FeedDao postDao){
            this.feedDao=feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.delete(feeds[0]);
            return null;
        }
    }

    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void>{

        private FeedDao feedDao;

        private DeleteAllFeedAsyncTask(FeedDao feedDao){
            this.feedDao=feedDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            feedDao.delteAllFeeds();
            return null;
        }
    }

}
