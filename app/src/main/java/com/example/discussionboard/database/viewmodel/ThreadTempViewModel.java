package com.example.discussionboard.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.ThreadTemp;
import com.example.discussionboard.database.repository.ThreadTempRepository;

import java.util.List;

public class ThreadTempViewModel extends AndroidViewModel {

    private ThreadTempRepository threadTempRepository;
    private LiveData<List<ThreadTemp>> allThreadsTemp;

    public ThreadTempViewModel(@NonNull Application application) {
        super(application);
        threadTempRepository = new ThreadTempRepository(application);
        allThreadsTemp = threadTempRepository.getAllThreadTemp();
    }


    public void insert(ThreadTemp threadTemp){
        threadTempRepository.insert(threadTemp);
    }

    public void update(ThreadTemp threadTemp){
        threadTempRepository.update(threadTemp);
    }

    public void delete(ThreadTemp threadTemp){
        threadTempRepository.delete(threadTemp);
    }

    public void delelteAllThreadTemp(){
        threadTempRepository.deleteAllThreadsTemp();
    }

    public LiveData<List<ThreadTemp>> getAllThreadTemp(){
        return allThreadsTemp;
    }

}
