package com.example.discussionboard.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.discussionboard.database.entity.Thread;

import java.util.List;

@Dao
public interface ThreadDao {

    @Insert
    void insert(Thread thread);

    @Update
    void update(Thread thread);

    @Delete
    void delete(Thread thread);

    @Query("DELETE FROM threads")
    void deleteAllThreads();

    @Query("SELECT * FROM threads")
    LiveData<List<Thread>> getAllThreads();


}
