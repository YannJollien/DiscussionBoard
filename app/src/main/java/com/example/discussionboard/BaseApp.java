package com.example.discussionboard;

import android.app.Application;


import com.example.discussionboard.databse.rep.PostRepository;
import com.example.discussionboard.databse.rep.ThreadRepository;
import com.example.discussionboard.databse.rep.ThreadTempRepository;

public class BaseApp extends Application {

    public ThreadRepository getThreadRepository() {
        return ThreadRepository.getInstance();
    }

    public PostRepository getPostRepository() {
        return PostRepository.getInstance();
    }

    public ThreadTempRepository getThreadTempRepository() {
        return ThreadTempRepository.getInstance();
    }


}
