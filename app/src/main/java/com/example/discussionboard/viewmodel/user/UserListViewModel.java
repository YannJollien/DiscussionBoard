package com.example.discussionboard.viewmodel.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.BaseApp;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.entity.User;
import com.example.discussionboard.databse.rep.PostRepository;
import com.example.discussionboard.databse.rep.UserRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<User>> observableUser;
    private UserRepository repository;

    public UserListViewModel(@NonNull Application application,
                             final String showName, UserRepository repository) {
        super(application);

        this.repository = repository;

        observableUser = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableUser.setValue(null);

        LiveData<List<User>> users = repository.getAllUSer(showName);

        // observe the changes of the entities from the database and forward them
        //observableEpisodes.addSource(episodes, observableEpisodes::setValue);
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<User>> getUser() {
        return observableUser;
    }

    public void deleteUser(User user, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getUserRepository()
                .delete(user, callback);
    }

    public void updateUser(User user, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getUserRepository()
                .update(user, callback);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final UserRepository repository;
        private final String showName;

        public Factory(@NonNull Application application, String showName) {
            this.application = application;
            this.showName = showName;
            repository = ((BaseApp) application).getUserRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserListViewModel(application, showName, repository);
        }
    }

}
