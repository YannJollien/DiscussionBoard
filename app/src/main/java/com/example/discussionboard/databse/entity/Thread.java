package com.example.discussionboard.databse.entity;

import java.util.HashMap;
import java.util.Map;


public class Thread {

    private String id;

    private String thread;

    String category;


    public Thread(){

    }
    //Constructor
    public Thread(String thread, String category) {
        this.thread = thread;
        this.category = category;
    }

    //Constructor
    public Thread(String id, String thread, String category) {
        this.id = id;
        this.thread = thread;
        this.category = category;
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("thread", thread);
        result.put("category", category);
        result.put("id", id);

        return result;
    }
}
