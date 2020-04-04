package com.example.discussionboard.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.BaseApp;
import com.example.discussionboard.database.entity.ThreadTemp;
import com.example.discussionboard.database.repository.ThreadTempRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

public class ThreadTempViewModel extends AndroidViewModel {

    private ThreadTempRepository repository;
    private MediatorLiveData<ThreadTemp> observableThreadTemp;

    public ThreadTempViewModel(@NonNull Application application,
                           final String idThread, ThreadTempRepository repository) {
        super(application);

        this.repository = repository;

        observableThreadTemp = new MediatorLiveData<>();
        observableThreadTemp.setValue(null);

        if (idThread != null){
            LiveData<ThreadTemp> account = repository.getThreadTemp();
            observableThreadTemp.addSource(account, observableThreadTemp::setValue);

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
        private final ThreadTempRepository repository;

        public Factory(@NonNull Application application, String idThread) {
            this.application = application;
            this.idThread = idThread;
            repository = ((BaseApp) application).getThreadTempRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ThreadTempViewModel(application, idThread, repository);
        }
    }

    public LiveData<ThreadTemp> getThreadTemp() {
        return observableThreadTemp;
    }

    public void createThreadTemp(ThreadTemp thread, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getThreadTempRepository()
                .insert(thread, callback);
    }

}
