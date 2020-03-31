package com.example.discussionboard.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.PostDatabase;
import com.example.discussionboard.database.dao.PostDao;
import com.example.discussionboard.database.entity.Post;

import java.util.List;

public class PostRepository {

    private PostDao postDao;
    private LiveData<List<Post>> allPost;

    public PostRepository(Application application){

        PostDatabase database = PostDatabase.getInstance(application);
        postDao = database.postDao();
        allPost = postDao.getAllPosts();

    }

    public void insert(Post post){
        new InsertPostAsyncTask(postDao).execute(post);
    }

    public void update(Post post){
        new UpdatePostAsyncTask(postDao).execute(post);
    }

    public void delete(Post post){
        new DeletePostAsyncTask(postDao).execute(post);
    }

    public void deleteAllPosts(){
        new DeleteAllPostAsyncTask(postDao).execute();
    }

    public LiveData<List<Post>> getAllPost(){
        return allPost;
    }

    //Inner class Insert
    private static class InsertPostAsyncTask extends AsyncTask<Post, Void, Void>{

        private PostDao postDao;

        private InsertPostAsyncTask(PostDao postDao){
            this.postDao=postDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDao.insert(posts[0]);
            return null;
        }
    }

    //Inner class Update
    private static class UpdatePostAsyncTask extends AsyncTask<Post, Void, Void>{

        private PostDao postDao;

        private UpdatePostAsyncTask(PostDao postDao){
            this.postDao=postDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDao.update(posts[0]);
            return null;
        }
    }

    //Inner class Delete
    private static class DeletePostAsyncTask extends AsyncTask<Post, Void, Void>{

        private PostDao postDao;

        private DeletePostAsyncTask(PostDao postDao){
            this.postDao=postDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDao.delete(posts[0]);
            return null;
        }
    }

    //Inner class DElete all
    private static class DeleteAllPostAsyncTask extends AsyncTask<Void, Void, Void>{

        private PostDao postDao;

        private DeleteAllPostAsyncTask(PostDao postDao){
            this.postDao=postDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            postDao.delteAllPosts();
            return null;
        }
    }

}
