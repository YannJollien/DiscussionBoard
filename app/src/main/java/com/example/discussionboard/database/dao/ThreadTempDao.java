package com.example.discussionboard.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.discussionboard.database.entity.ThreadTemp;

import java.util.List;

@Dao
public interface ThreadTempDao {

    @Insert
    void insert(ThreadTemp threadTemp);

    @Update
    void update(ThreadTemp threadTemp);

    @Delete
    void delete(ThreadTemp threadTemp);

    @Query("DELETE FROM threads_temp")
    void deleteAllThreadsTemp();

    @Query("SELECT * FROM threads_temp")
    LiveData<List<ThreadTemp>> getAllThreadsTemp();


}
