package com.example.discussionboard.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.repository.FeedRepository;

import java.util.List;

public class FeedViewModel extends AndroidViewModel {

    private FeedRepository feedRepository;
    private LiveData<List<Feed>> allFeed;

    public FeedViewModel(@NonNull Application application) {
        super(application);
        feedRepository = new FeedRepository(application);
        allFeed = feedRepository.getAllFeed();
    }


    public void insert(Feed feed){
        feedRepository.insert(feed);
    }

    public void update(Feed feed){
        feedRepository.update(feed);
    }

    public void delete(Feed feed){
        feedRepository.delete(feed);
    }

    public void delelteAllFeed(){
        feedRepository.deleteAllFeeds();
    }

    public LiveData<List<Feed>> getAllFeed(){
        return allFeed;
    }

}
