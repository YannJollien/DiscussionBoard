package com.example.discussionboard.viewmodel.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.BaseApp;

import com.example.discussionboard.databse.entity.User;

import com.example.discussionboard.databse.rep.UserRepository;
import com.example.discussionboard.util.OnAsyncEventListener;


public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;
    private MediatorLiveData<User> observableUser;

    public UserViewModel(@NonNull Application application,
                         final String idUser, UserRepository repository) {
        super(application);

        this.repository = repository;

        observableUser = new MediatorLiveData<>();
        observableUser.setValue(null);

        if (idUser != null){
            LiveData<User> account = repository.getUser(idUser);
            observableUser.addSource(account, observableUser::setValue);

        }

    }


    /* public StorageViewModel(@NonNull Application application, String idEpisode, String showName, PlantationRepository repository) {
         super(application);
     }
 */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String idUser;
        private final UserRepository repository;

        public Factory(@NonNull Application application, String idUser) {
            this.application = application;
            this.idUser = idUser;
            repository = ((BaseApp) application).getUserRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, idUser, repository);
        }
    }

    public LiveData<User> getUser() {
        return observableUser;
    }

    public void createUser(User user, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getUserRepository()
                .insert(user, callback);
    }

}
