package com.example.discussionboard.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.discussionboard.database.entity.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(Post post);

    @Update
    void update(Post Post);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM posts")
    void delteAllPosts();

    @Query("SELECT * FROM posts")
    LiveData<List<Post>> getAllPosts();


}
