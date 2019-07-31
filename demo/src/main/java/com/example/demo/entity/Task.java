package com.example.demo.entity;

import com.example.demo.form.IdForm;
import com.example.demo.form.TaskForm;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "tasks") // DB:todo_list のテーブル名と一致する
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String name;
    private Date date;
    private Date createdAt;
    private boolean done;

    // getterとsetterの設定(cmd+nから選択)
    public Long getTaskId() {
        return taskId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }


    public static Task createTask(TaskForm taskForm){
        Date now = new Date();
        Task task = new Task();
        task.setName(taskForm.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(taskForm.getDate());
            task.setDate(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        task.setCreatedAt(now);
        task.setDone(false);

        return task;
    }

   public void updateDone() {
        this.setDone(!this.isDone());
   }

}
