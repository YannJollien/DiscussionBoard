package com.example.discussionboard.database.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.discussionboard.database.entity.Post;

import com.example.discussionboard.database.repository.PostRepository;



import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private LiveData<List<Post>> allPosts;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);
        allPosts = postRepository.getAllPost();
    }


    public void insert(Post post){
        postRepository.insert(post);
    }

    public void update(Post post){
        postRepository.update(post);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

    public void delelteAllPost(){
        postRepository.deleteAllPosts();
    }

    public LiveData<List<Post>> getAllPosts(){
        return allPosts;
    }

}
