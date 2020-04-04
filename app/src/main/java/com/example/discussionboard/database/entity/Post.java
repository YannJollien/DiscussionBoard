package com.example.discussionboard.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {

    @Exclude
    private String id;

    private String submitter;

    private String text;
    private String date;
    private int threadId;
    private int userId;

    public Post(String submitter, String text, String date, int threadId, int userId) {
        this.submitter = submitter;
        this.text = text;
        this.date = date;
        this.threadId = threadId;
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("submitter", submitter);
        result.put("text", text);
        result.put("date", date);
        result.put("threadId", threadId);
        result.put("userId", userId);
        return result;
    }
}

