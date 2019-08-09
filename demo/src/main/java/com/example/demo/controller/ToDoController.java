package com.example.demo.controller;


import com.example.demo.dto.TaskDto;
import com.example.demo.entity.Task;
import com.example.demo.form.IdForm;
import com.example.demo.form.TaskForm;
import com.example.demo.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ToDoController {

    @Autowired TasksService tasksService;

    /**
     * 既存のToDoを取得する
     *
     * @param model　モデル
     * @return トップページ
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Task> tasks = tasksService.findAll();     // List<データ型>　オブジェクト名 =
        List<TaskDto> taskDtos = tasks.stream().map(task -> new TaskDto(task)).collect(Collectors.toList());
        model.addAttribute("taskDtos", taskDtos);
        return "index";
    }


    /**
     * 新規のToDoを作成する
     *
     * @param taskForm 入力された文字列
     * @param result　エラーの確認結果
     * @param model　モデル
     * @return トップページ
     */
    @RequestMapping(value ="/task", method = RequestMethod.POST)
    public String create(@ModelAttribute @Validated TaskForm taskForm,BindingResult result, Model model) {  //BindingResultはエラーの有無を検出
        if(result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            model.addAttribute("allErrors", allErrors);
            List<Task> tasks = tasksService.findAll();     // List<データ型>　オブジェクト名 =
            List<TaskDto> taskDtos = tasks.stream().map(task -> new TaskDto(task)).collect(Collectors.toList());
            model.addAttribute("taskDtos", taskDtos);
            return "index";
        } else if (tasksService.checkTaskName(taskForm.getName()).isPresent()){
            ObjectError sameNameError = new ObjectError("name", "同じ名前のToDoが既に存在しています。");
            List<ObjectError> errors = new ArrayList<>();
            errors.add(sameNameError);
            model.addAttribute("errors", errors);
            List<Task> tasks = tasksService.findAll();     // List<データ型>　オブジェクト名 =
            List<TaskDto> taskDtos = tasks.stream().map(task -> new TaskDto(task)).collect(Collectors.toList());
            model.addAttribute("taskDtos", taskDtos);
            return "index";
        } else {
            tasksService.create(taskForm);
            return "redirect:/";
        }
    }

    @RequestMapping(value ="/create", method = RequestMethod.POST)
    @ResponseBody
    public Task makeToDo(@ModelAttribute @Validated TaskForm taskForm,BindingResult result) {  //BindingResultはエラーの有無を検出
        return tasksService.create(taskForm);
    }




    /**
     * トップ画面から編集画面へ遷移する
     *
     * @param taskId  編集ボタンが押されたToDoのid
     * @param model モデル
     * @return 編集画面
     */
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



    /**
     * 完了・未完了ボタンが押されたToDoの状態を更新(反転)する
     *
     * @param idForm 完了・未完了ボタンが押されたToDoのidフォーム
     * @param result　エラー確認結果
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateStatus(@ModelAttribute IdForm idForm, BindingResult result) {
        Optional<Task> selectTask = tasksService.getSelectTask(idForm.getTaskId()); //optional型であることに注意
        if (selectTask.isPresent()) {    //値の存在をチェック
            Task task = selectTask.get();   //値を取得するgetメソッドは、値が存在していない場合実行時例外を投げる
            tasksService.update(task);
        }
        return "redirect:/";
    }


    /*idを選択し、選択されたidのタスクの名前・期限を更新する*/

    /**
     * 編集画面
     *
     * @param idForm 編集ボタンが押されたToDoのidフォーム
     * @param taskForm　更新後のToDoのフォーム
     * @param result　エラーの確認結果
     * @param model　モデル
     * @return 編集画面
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editToDo(@ModelAttribute IdForm idForm,@Validated TaskForm taskForm, BindingResult result, Model model) {
        Optional<Task> selectTask = tasksService.getSelectTask(idForm.getTaskId()); //optional型であることに注意
        if (selectTask.isPresent()) {
            Task task = selectTask.get();
            if(result.hasErrors()){
                List<ObjectError> allErrors = result.getAllErrors();
                model.addAttribute("allErrors", allErrors);
                TaskDto taskDto = new TaskDto(selectTask.get());   //値を取得するgetメソッドは、値が存在していない場合実行時例外を投げる
                model.addAttribute("taskDto", taskDto);
                return "edit";
            } else if (tasksService.checkTaskName(taskForm.getName()).isPresent()){
                ObjectError sameNameError = new ObjectError("name", "同じ名前のToDoが既に存在しています。");
                List<ObjectError> errors = new ArrayList<>();
                errors.add(sameNameError);
                model.addAttribute("errors", errors);
                TaskDto taskDto = new TaskDto(selectTask.get());   //値を取得するgetメソッドは、値が存在していない場合実行時例外を投げる
                model.addAttribute("taskDto", taskDto);
                return "edit";
            }
            tasksService.edit(taskForm, task);
        }
        return "redirect:/";
    }



    /**
     * 検索画面
     *
     * @param searchText 検索文字列
     * @param model モデル
     * @return 検索画面
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Optional<String> searchText,Model model) {
        List<TaskDto> findTaskDtos = null;
        if (searchText.isPresent()) {
            List<Task> findTasks = tasksService.searchTask(searchText.get());     // List<データ型>　オブジェクト名 =
            findTaskDtos = findTasks.stream().map(task -> new TaskDto(task)).filter(task -> !task.isDone()).collect(Collectors.toList());
        }
        model.addAttribute("findTaskDtos", findTaskDtos);
        return "search";
    }
}