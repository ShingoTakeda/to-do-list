package com.example.demo.controller;


import com.example.demo.entity.Task;
import com.example.demo.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ToDoController {

    @Autowired TasksService tasksService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Task> tasks = tasksService.findAll();

        model.addAttribute("tasks", tasks);
        return "index";
    }

    @RequestMapping(value ="/", method = RequestMethod.POST)


}
