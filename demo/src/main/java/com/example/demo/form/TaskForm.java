package com.example.demo.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TaskForm {
    @NotEmpty(message = "{empty.name}")
    @Size(max = 30,message = "{max.name}")
    @Pattern(regexp="[a-zA-Z0-9]*", message = "{invalid.name}")   //*：任意の文字(ここでは[]内の文字)が0個以上続く
    private String name;

    @NotEmpty(message = "{date}")
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
