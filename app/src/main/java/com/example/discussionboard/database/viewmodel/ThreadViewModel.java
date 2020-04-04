package com.example.discussionboard.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.BaseApp;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.repository.ThreadRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

public class ThreadViewModel extends AndroidViewModel {

    private ThreadRepository repository;
    private MediatorLiveData<Thread> observableThread;

    public ThreadViewModel(@NonNull Application application,
                               final String idThread, ThreadRepository repository) {
        super(application);

        this.repository = repository;

        observableThread = new MediatorLiveData<>();
        observableThread.setValue(null);

        if (idThread != null){
            LiveData<Thread> account = repository.getThread(idThread);
            observableThread.addSource(account, observableThread::setValue);

        }

    }


    /* public StorageViewModel(@NonNull Application application, String idEpisode, String showName, PlantationRepository repository) {
         super(application);
     }
 */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String idThread;
        private final ThreadRepository repository;

        public Factory(@NonNull Application application, String idThread) {
            this.application = application;
            this.idThread = idThread;
            repository = ((BaseApp) application).getThreadRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ThreadViewModel(application, idThread, repository);
        }
    }

    public LiveData<Thread> getThread() {
        return observableThread;
    }

    public void createThread(Thread thread, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getThreadRepository()
                .insert(thread, callback);
    }

}
