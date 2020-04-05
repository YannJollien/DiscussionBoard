package com.example.discussionboard.viewmodel.plantation;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.example.discussionboard.BaseApp;
import com.example.discussionboard.databse.entity.Thread;
import com.example.discussionboard.databse.rep.ThreadRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

import java.util.List;

public class ThreadListViewModel extends AndroidViewModel {

    private ThreadRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Thread>> observableThread;

    public ThreadListViewModel(@NonNull Application application,
                               final String showName, ThreadRepository repository) {
        super(application);

        this.repository = repository;

        observableThread = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableThread.setValue(null);

        LiveData<List<Thread>> threads = repository.getAllThread(showName);

        // observe the changes of the entities from the database and forward them
        //observableEpisodes.addSource(episodes, observableEpisodes::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final ThreadRepository repository;
        private final String showName;

        public Factory(@NonNull Application application, String showName) {
            this.application = application;
            this.showName = showName;
            repository = ((BaseApp) application).getThreadRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ThreadListViewModel(application, showName, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Thread>> getThread() {
        return observableThread;
    }

    public void deleteThread(Thread thread, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getThreadRepository()
                .delete(thread, callback);
    }

    public void updateThread(Thread thread, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getThreadRepository()
                .update(thread, callback);
    }


}
