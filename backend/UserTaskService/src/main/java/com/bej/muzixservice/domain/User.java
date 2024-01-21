package com.bej.muzixservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class User {

    @Id
    private String email;
    private String username;
    private String password;
    private List<Task> taskList;

    public User() {
    }

    public User(String email, String username, String password, List<Task> taskList) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.taskList = taskList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", taskList=" + taskList +
                '}';
    }
}