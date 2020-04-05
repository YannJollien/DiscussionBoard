package com.example.discussionboard.viewmodel.post;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.BaseApp;
import com.example.discussionboard.databse.entity.Post;
import com.example.discussionboard.databse.rep.PostRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

import java.util.List;

public class PostListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Post>> observablePost;
    private PostRepository repository;

    public PostListViewModel(@NonNull Application application,
                             final String showName, PostRepository repository) {
        super(application);

        this.repository = repository;

        observablePost = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observablePost.setValue(null);

        LiveData<List<Post>> posts = repository.getAllPost(showName);

        // observe the changes of the entities from the database and forward them
        //observableEpisodes.addSource(episodes, observableEpisodes::setValue);
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Post>> getPost() {
        return observablePost;
    }

    public void deletePost(Post post, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getPostRepository()
                .delete(post, callback);
    }

    public void updatePost(Post post, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getPostRepository()
                .update(post, callback);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final PostRepository repository;
        private final String showName;

        public Factory(@NonNull Application application, String showName) {
            this.application = application;
            this.showName = showName;
            repository = ((BaseApp) application).getPostRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PostListViewModel(application, showName, repository);
        }
    }

}
