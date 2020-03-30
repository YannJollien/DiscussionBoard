package com.example.discussionboard.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feeds")
public class Feed {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "submitter_name")
    private String submitter;

    private String context;
    private int imageCode;
    private String date;
    private String time;

    public Feed(String submitter, String context, int imageCode, String date, String time) {
        this.submitter = submitter;
        this.context = context;
        this.imageCode = imageCode;
        this.date = date;
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
