package com.example.discussionboard.database.entity;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Thread {

    @Exclude
    private String id;

    private String thread;
    private String category;

    public Thread(String thread, String category) {
        this.thread = thread;
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getThread() {
        return thread;
    }

    public String getCategory() {
        return category;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("thread", thread);
        result.put("category", category);

        return result;
    }
}

