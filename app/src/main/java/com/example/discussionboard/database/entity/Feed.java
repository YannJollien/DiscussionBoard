package com.example.discussionboard.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Feed {

    @Exclude
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("submitter", submitter);
        result.put("context", context);
        result.put("imageCode", imageCode);
        result.put("date", date);
        result.put("time", time);

        return result;
    }
}
