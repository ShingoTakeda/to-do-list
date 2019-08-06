package com.example.demo.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TaskForm {
    @NotEmpty(message = "ToDo名を入力してください。")
    @Size(max = 30,message = "ToDo名は30文字以内で入力してください。")
    private String name;

    @NotEmpty(message = "期限を入力してください。")
    private String date;


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
}
