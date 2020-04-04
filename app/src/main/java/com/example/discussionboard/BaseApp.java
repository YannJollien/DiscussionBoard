package com.example.discussionboard;

import android.app.Application;

import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.repository.FeedRepository;
import com.example.discussionboard.database.repository.PostRepository;
import com.example.discussionboard.database.repository.ThreadRepository;
import com.example.discussionboard.database.repository.ThreadTempRepository;

public class BaseApp extends Application {

    public ThreadRepository getThreadRepository() {
        return ThreadRepository.getInstance();
    }

    public ThreadTempRepository getThreadTempRepository() {
        return ThreadTempRepository.getInstance();
    }

    public PostRepository getPostRepository() {
        return PostRepository.getInstance();
    }

    public FeedRepository getFeedRepository() {
        return FeedRepository.getInstance();
    }

}
