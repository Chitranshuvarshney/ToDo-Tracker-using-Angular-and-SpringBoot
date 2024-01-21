package com.bej.muzixservice.domain;

import org.springframework.data.annotation.Id;


public class Task {
    @Id
    private String taskId;
    private String taskName;
    private String description;
    private String category;
    private String dueDate;
    private String priority;
    private String status;
    private String comments;

    public Task() {
    }

    public Task(String taskId, String taskName, String description, String category, String dueDate, String priority, String status, String comments) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.category = category;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.comments = comments;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
