package com.example.opendoor.data.model;

public class UserModel {
    private String username;
    private String email;

    public UserModel() {
        // Required empty constructor
    }

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}