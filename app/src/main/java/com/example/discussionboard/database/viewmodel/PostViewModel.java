package com.example.discussionboard.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.BaseApp;
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.entity.Thread;
import com.example.discussionboard.database.repository.PostRepository;
import com.example.discussionboard.database.repository.ThreadRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

public class PostViewModel extends AndroidViewModel {

    private PostRepository repository;
    private MediatorLiveData<Post> observablePost;

    public PostViewModel(@NonNull Application application,
                           final String idPost, PostRepository repository) {
        super(application);

        this.repository = repository;

        observablePost = new MediatorLiveData<>();
        observablePost.setValue(null);

        if (idPost != null){
            LiveData<Post> account = repository.getPost();
            observablePost.addSource(account, observablePost::setValue);

        }

    }


    /* public StorageViewModel(@NonNull Application application, String idEpisode, String showName, PlantationRepository repository) {
         super(application);
     }
 */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String idPost;
        private final PostRepository repository;

        public Factory(@NonNull Application application, String idPost) {
            this.application = application;
            this.idPost = idPost;
            repository = ((BaseApp) application).getPostRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PostViewModel(application, idPost, repository);
        }
    }

    public LiveData<Post> getPost() {
        return observablePost;
    }

    public void createPost(Post post, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getPostRepository()
                .insert(post, callback);
    }

}
