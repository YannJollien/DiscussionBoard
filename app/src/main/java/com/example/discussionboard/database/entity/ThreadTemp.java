package com.example.discussionboard.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "threads_temp")
public class ThreadTemp {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String thread;
    private String category;
    private String submitter;

    public ThreadTemp(String thread, String category, String submitter) {
        this.thread = thread;
        this.category = category;
        this.submitter = submitter;
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

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }
}
