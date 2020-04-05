package com.example.discussionboard.databse.entity;

import java.util.HashMap;
import java.util.Map;

public class Post {

    private String id;

    private String submitter;
    private String text;
    private String date;

    private String threadId;
    private String userId;



    public Post(){

    }
    //Constructor
    public Post(String submitter, String text, String date) {
        this.submitter = submitter;
        this.text = text;
        this.date = date;
    }

    //Constructor
    public Post(String id, String submitter, String text, String date, String threadId, String userId) {
        this.id = id;
        this.submitter = submitter;
        this.text = text;
        this.date = date;
        this.threadId = threadId;
        this.userId = userId;
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

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("submitter", submitter);
        result.put("text", text);
        result.put("date", date);
        result.put("threadId", threadId);
        result.put("userId", userId);
        result.put("id", id);

        return result;
    }

}
