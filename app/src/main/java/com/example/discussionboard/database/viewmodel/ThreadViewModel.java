package com.example.discussionboard.database.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.Thread;

import com.example.discussionboard.database.repository.ThreadRepository;


import java.util.List;

public class ThreadViewModel extends AndroidViewModel {

    private ThreadRepository threadRepository;
    private LiveData<List<Thread>> allThreads;

    public ThreadViewModel(@NonNull Application application) {
        super(application);
        threadRepository = new ThreadRepository(application);
        allThreads = threadRepository.getAllThread();
    }


    public void insert(Thread thread){
        threadRepository.insert(thread);
    }

    public void update(Thread thread){
        threadRepository.update(thread);
    }

    public void delete(Thread thread){
        threadRepository.delete(thread);
    }

    public void delelteAllThread(){
        threadRepository.deleteAllThreads();
    }

    public LiveData<List<Thread>> getAllThread(){
        return allThreads;
    }

}
