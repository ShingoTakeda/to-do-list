package com.example.demo.entity;

import com.example.demo.form.TaskForm;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    /**
     * 特殊文字のエスケープ
     *
     * @param str
     * @return
     */
    public static String htmlEscape(String str){
        StringBuffer result = new StringBuffer();
        for(char c : str.toCharArray()) {
            switch (c) {
                case '&' :
                    result.append("&amp;");
                    break;
                case '<' :
                    result.append("&lt;");
                    break;
                case '>' :
                    result.append("&gt;");
                    break;
                case '"' :
                    result.append("&quot;");
                    break;
                case '\'' :
                    result.append("&#39;");
                    break;
                case ' ' :
                    result.append("&nbsp;");
                    break;
                default :
                    result.append(c);
                    break;
            }
        }
        return result.toString();
    }


    /**
     * 新規ToDo作成
     *
     * @param taskForm　　入力フォーム
     * @return  Task Entity
     */
    public static Task createTask(TaskForm taskForm){
        Date now = new Date();
        Task task = new Task();
        String escape = htmlEscape(taskForm.getName());
        task.setName(escape);
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

   /**/

    /**
     *現在の完了・未完了状態を更新(反転)する
     */
    public void updateDone() {
        this.setDone(!this.isDone());
    }



    /**
     * ToDoの名前と期限を変更
     *
     * @param taskForm 入力フォーム
     * @param task　変更したいToDo
     * @return
     */
    public static Task editToDo(TaskForm taskForm, Task task) {
        String escape = htmlEscape(taskForm.getName());
        task.setName(escape);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(taskForm.getDate());
            task.setDate(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return task;
    }


}
