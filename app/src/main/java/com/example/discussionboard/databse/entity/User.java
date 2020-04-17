package com.example.discussionboard.databse.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;

    private String firstname;
    private String lastname;
    private boolean admin;


    public User(){

    }
    //Constructor
    public User(String firstname, String lastname, boolean admin) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.admin = admin;
    }

    //Constructor
    public User(String id, String firstname, String lastname, boolean admin) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public boolean isAdmin() {
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
        result.put("id", id);

        return result;
    }

}
