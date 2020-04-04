package com.example.discussionboard.database.entity;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ThreadTemp {

    @Exclude
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("thread", thread);
        result.put("category", category);
        result.put("submitter", submitter);

        return result;
    }
}
