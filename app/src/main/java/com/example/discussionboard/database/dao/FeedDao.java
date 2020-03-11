package com.example.discussionboard.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.discussionboard.database.entity.Feed;

import java.util.List;

@Dao
public interface FeedDao {

    @Insert
    void insert(Feed feed);

    @Update
    void update(Feed feed);

    @Delete
    void delete(Feed feed);

    @Query("DELETE FROM feeds")
    void delteAllFeeds();

    @Query("SELECT * FROM feeds")
    LiveData<List<Feed>> getAllFeeds();

}
