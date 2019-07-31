package com.example.demo.dto;

import com.example.demo.entity.Task;

import java.text.SimpleDateFormat;

public class TaskDto {

    private String name;
    private String date;
    private String createdAt;
    private boolean done;


    public TaskDto(Task task) {
        this.name = task.getName();
        this.done = task.isDone();

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
