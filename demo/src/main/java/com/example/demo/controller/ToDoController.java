package com.example.demo.controller;


import com.example.demo.dto.TaskDto;
import com.example.demo.entity.Task;
import com.example.demo.form.IdForm;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TasksService;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ToDoController {

    @Autowired TasksService tasksService;

    /*既存のToDoを取得する*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Task> tasks = tasksService.findAll();     // List<データ型>　オブジェクト名 =
        List<TaskDto> taskDtos = tasks.stream().map(task -> new TaskDto(task)).collect(Collectors.toList());
        model.addAttribute("taskDtos", taskDtos);
        return "index";
    }


    /*新規のToDoを取得する*/
    @RequestMapping(value ="/task", method = RequestMethod.POST)
    public String create(@ModelAttribute TaskForm taskForm, BindingResult result) {  //BindingResultはエラーの有無を検出
        tasksService.create(taskForm);
        return "redirect:/";
    }


    /*Top画面から検索画面へ遷移する*/
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search() {
        return "search";
    }


    /*Top画面から編集画面へ遷移する*/
    @GetMapping(value = "/edit/{taskId}")
    public String edit(@PathVariable(name = "taskId", required = false) Long taskId, Model model) {
        Optional<Task> selectTask = tasksService.getSelectTask(taskId);
        if (selectTask.isPresent()) {    //値の存在をチェック
            TaskDto taskDto = new TaskDto(selectTask.get());   //値を取得するgetメソッドは、値が存在していない場合実行時例外を投げる
            model.addAttribute("taskDto", taskDto);
            return "edit";
        }
        return "redirect:/";
    }


    /*idを選択し、選択されたidのタスクの完了・未完了状態を更新する*/
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateStatus(@ModelAttribute IdForm idForm, BindingResult result) {
        Optional<Task> selectTask = tasksService.getSelectTask(idForm.getTaskId()); //optional型であることに注意
        if (selectTask.isPresent()) {    //値の存在をチェック
            Task task = selectTask.get();   //値を取得するgetメソッドは、値が存在していない場合実行時例外を投げる
            tasksService.update(task);
            return task.getName();
        }
        return "error";
    }


    /*idを選択し、選択されたidのタスクの名前・期限を更新する*/
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editToDo(@ModelAttribute IdForm idForm,TaskForm taskForm, BindingResult result) {
        Optional<Task> selectTask = tasksService.getSelectTask(idForm.getTaskId()); //optional型であることに注意
        if (selectTask.isPresent()) {
            Task task = selectTask.get();
            tasksService.edit(taskForm,task);
            return task.getName();
        }
        return "error";
    }


}
