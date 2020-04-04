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
import com.example.discussionboard.database.entity.ThreadTemp;
import com.example.discussionboard.database.repository.ThreadRepository;
import com.example.discussionboard.database.repository.ThreadTempRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

import java.util.List;

public class ThreadTempListViewModel extends AndroidViewModel {

    private ThreadTempRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ThreadTemp>> observableThreadTemp;

    public ThreadTempListViewModel(@NonNull Application application,
                               ThreadTempRepository repository) {
        super(application);

        this.repository = repository;

        observableThreadTemp = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableThreadTemp.setValue(null);

        LiveData<List<ThreadTemp>> threads = repository.getAllThreadsTemp();

        // observe the changes of the entities from the database and forward them
        //observableEpisodes.addSource(episodes, observableEpisodes::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final ThreadTempRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ((BaseApp) application).getThreadTempRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ThreadTempListViewModel(application,repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<ThreadTemp>> getThreadTemp() {
        return observableThreadTemp;
    }

    public void deleteThreadTemp(ThreadTemp thread, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getThreadTempRepository()
                .delete(thread, callback);
    }

    public void updatePlantationTemp(ThreadTemp thread, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getThreadTempRepository()
                .update(thread, callback);
    }

}
