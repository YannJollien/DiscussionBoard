package com.example.discussionboard.databse.entity;

import java.util.HashMap;
import java.util.Map;

public class ThreadTemp {

    private String id;

    private String thread;

    private String category;

    private String submitter;


    public ThreadTemp(){

    }
    //Constructor
    public ThreadTemp(String thread, String category, String submitter) {
        this.thread = thread;
        this.category = category;
        this.submitter = submitter;
    }

    //Constructor
    public ThreadTemp(String id, String thread, String category, String submitter) {
        this.id = id;
        this.thread = thread;
        this.category = category;
        this.submitter = submitter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        result.put("id", id);

        return result;
    }

}
