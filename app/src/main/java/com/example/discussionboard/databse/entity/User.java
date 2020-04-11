package com.example.discussionboard.databse.entity;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;

    private String firstname;
    private String lastname;
    public String url;
    private boolean admin;


    public User(){

    }
    //Constructor
    public User(String firstname, String lastname, String url, boolean admin) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.url = url;
        this.admin = admin;
    }

    //Constructor
    public User(String id, String firstname, String lastname, String url, boolean admin) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.url = url;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstname);
        result.put("lastname", lastname);
        result.put("admin", admin);
        result.put("url", url);
        result.put("id", id);

        return result;
    }

}
