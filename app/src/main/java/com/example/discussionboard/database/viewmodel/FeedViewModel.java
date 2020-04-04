package com.example.discussionboard.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discussionboard.BaseApp;
import com.example.discussionboard.database.entity.Feed;
import com.example.discussionboard.database.entity.Post;
import com.example.discussionboard.database.repository.FeedRepository;
import com.example.discussionboard.database.repository.PostRepository;
import com.example.discussionboard.util.OnAsyncEventListener;

public class FeedViewModel extends AndroidViewModel {

    private FeedRepository repository;
    private MediatorLiveData<Feed> observableFeed;

    public FeedViewModel(@NonNull Application application,
                         final String idPost, FeedRepository repository) {
        super(application);

        this.repository = repository;

        observableFeed = new MediatorLiveData<>();
        observableFeed.setValue(null);

        if (idPost != null){
            LiveData<Feed> account = repository.getFeed();
            observableFeed.addSource(account, observableFeed::setValue);

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
        private final FeedRepository repository;

        public Factory(@NonNull Application application, String idPost) {
            this.application = application;
            this.idPost = idPost;
            repository = ((BaseApp) application).getFeedRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new FeedViewModel(application, idPost, repository);
        }
    }

    public LiveData<Feed> getFeed() {
        return observableFeed;
    }

    public void createFeed(Feed feed, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getFeedRepository()
                .insert(feed, callback);
    }

}
