package com.example.discussionboard.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "threads")
public class Thread {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String thread;
    private String category;

    public Thread(String thread, String category) {
        this.thread = thread;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getThread() {
        return thread;
    }

    public String getCategory() {
        return category;
    }
}

