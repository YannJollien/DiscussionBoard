package com.example.discussionboard.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feeds")
public class Feed {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String submitter;
    private String context;
    private int imageCode;

    public Feed(String submitter, String context, int imageCode) {
        this.submitter = submitter;
        this.context = context;
        this.imageCode = imageCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getImageCode() {
        return imageCode;
    }

    public void setImageCode(int imageCode) {
        this.imageCode = imageCode;
    }
}
