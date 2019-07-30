package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.form.TaskForm;
import com.example.demo.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {
    @Autowired
    TasksRepository tasksRepository;

    public List<Task> findAll() {
        return tasksRepository.findAll();
    }

    public void create(TaskForm taskForm) {
        Task task = Task.createTask(taskForm);
        tasksRepository.save(task);
    }





}
