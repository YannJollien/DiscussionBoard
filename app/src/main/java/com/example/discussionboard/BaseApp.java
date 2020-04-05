package com.example.discussionboard;

import android.app.Application;


import com.example.discussionboard.databse.rep.ThreadRepository;

public class BaseApp extends Application {

    public ThreadRepository getThreadRepository() {
        return ThreadRepository.getInstance();
    }


}
