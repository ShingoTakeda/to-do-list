package com.example.demo.dto;

import com.example.demo.entity.Task;

import java.text.SimpleDateFormat;

public class TaskDto {

    private String name;
    private String date;
    private String createdAt;

    public TaskDto(Task task) {
        this.name = task.getName();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = dateFormat.format(task.getDate());
        this.createdAt = dateFormat.format(task.getCreatedAt());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
