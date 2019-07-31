package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.form.IdForm;
import com.example.demo.form.TaskForm;
import com.example.demo.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // 業務処理を提供(どんな処理をするか)
public class TasksService {

    @Autowired
    TasksRepository tasksRepository;

    public List<Task> findAll() {     // List<データ型>
        return tasksRepository.findAll();   //select * from tasks;と同じ
    }

    public void create(TaskForm taskForm) {
        Task task = Task.createTask(taskForm);
        tasksRepository.save(task);
    }

    public Optional<Task> getSelectTask(Long id) {

        return tasksRepository.findById(id);
    }

    public void update(Task task) {
       task.updateDone();
       tasksRepository.save(task);
    }

    


}
