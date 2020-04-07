package com.example.discussionboard.databse.entity;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;

    private String firstname;
    private String lastname;


    public User(){

    }
    //Constructor
    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    //Constructor
    public User(String id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstname);
        result.put("lastname", lastname);
        result.put("id", id);

        return result;
    }

}
