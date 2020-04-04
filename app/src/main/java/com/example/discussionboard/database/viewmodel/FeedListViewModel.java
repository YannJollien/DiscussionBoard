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

import java.util.List;

public class FeedListViewModel extends AndroidViewModel {

    private FeedRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Feed>> observableFeed;

    public FeedListViewModel(@NonNull Application application,
                             FeedRepository repository) {
        super(application);

        this.repository = repository;

        observableFeed = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableFeed.setValue(null);

        LiveData<List<Feed>> feeds = repository.getAllFeed();

        // observe the changes of the entities from the database and forward them
        //observableEpisodes.addSource(episodes, observableEpisodes::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final FeedRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ((BaseApp) application).getFeedRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new FeedListViewModel(application,repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Feed>> getFeed() {
        return observableFeed;
    }

    public void deleteFeed(Feed feed, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getFeedRepository()
                .delete(feed, callback);
    }

    public void updateFeed(Feed feed, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getFeedRepository()
                .update(feed, callback);
    }


}
