package com.example.discussionboard.database.entity;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    @Exclude
    private int id;

    private String firstName;

    private String lastName;

    private String email;
    private String password;
    private boolean admin;

    public User(String firstName, String lastName, String email, String password, boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstName);
        result.put("lastname", lastName);
        result.put("email", email);
        result.put("password", password);
        result.put("admin", admin);
        return result;
    }
}

