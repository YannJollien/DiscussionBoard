package com.example.discussionboard.viewmodel.threadTemp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.BaseApp;
import com.example.discussionboard.databse.entity.ThreadTemp;
import com.example.discussionboard.databse.rep.ThreadTempRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

public class ThreadTempViewModel extends AndroidViewModel {

    private ThreadTempRepository repository;
    private MediatorLiveData<ThreadTemp> observableThreadTemp;

    public ThreadTempViewModel(@NonNull Application application,
                           final String idThreadTemp, ThreadTempRepository repository) {
        super(application);

        this.repository = repository;

        observableThreadTemp = new MediatorLiveData<>();
        observableThreadTemp.setValue(null);

        if (idThreadTemp != null){
            LiveData<ThreadTemp> account = repository.getThreadTemp(idThreadTemp);
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
        private final String idThreadTemp;
        private final ThreadTempRepository repository;

        public Factory(@NonNull Application application, String idThreadTemp) {
            this.application = application;
            this.idThreadTemp = idThreadTemp;
            repository = ((BaseApp) application).getThreadTempRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ThreadTempViewModel(application, idThreadTemp, repository);
        }
    }

    public LiveData<ThreadTemp> getThreadTemp() {
        return observableThreadTemp;
    }

    public void createThreadTemp(ThreadTemp threadTemp, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getThreadTempRepository()
                .insert(threadTemp, callback);
    }

}
