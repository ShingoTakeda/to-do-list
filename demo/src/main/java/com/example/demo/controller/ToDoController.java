package com.example.demo.controller;


import com.example.demo.dto.TaskDto;
import com.example.demo.entity.Task;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ToDoController {

    @Autowired TasksService tasksService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Task> tasks = tasksService.findAll();     //List<データ型>　オブジェクト名 =
        List<TaskDto> taskDtos = tasks.stream().map(task -> new TaskDto(task)).collect(Collectors.toList());
        model.addAttribute("taskDtos", taskDtos);
        return "index";
    }

    @RequestMapping(value ="/task", method = RequestMethod.POST)
    public String create(@ModelAttribute TaskForm taskForm, BindingResult result) {
        tasksService.create(taskForm);
        return "redirect:/";
    }

}
