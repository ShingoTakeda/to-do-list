package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.form.TaskForm;
import com.example.demo.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service  // 業務処理を提供(どんな処理をするか)
public class TasksService {

    @Autowired
    TasksRepository tasksRepository;

    /*Entityから既存のタスクを抽出*/

    /**
     * 全ToDo取得
     *
     */
    public List<Task> findAll() {     // List<データ型>
        return tasksRepository.findAll(new Sort(Sort.Direction.DESC,"createdAt"));   //select * from tasks;と同じ
    }


    /**
     * 入力された新しいタスクを登録
     *
     * @param taskForm 入力フォーム
     */
    public void create(TaskForm taskForm) {
        Task task = Task.createTask(taskForm);    //staticなので大文字の"T"askから引っ張る
        tasksRepository.save(task);       //postしたら保存をする
    }


    /**
     * 選択したidのToDoを取得
     *
     */
    public Optional<Task> getSelectTask(Long id) {

        return tasksRepository.findById(id);
    }


    /**
     * ToDoの完了・未完了状態を更新
     * 　
     * @param task　選択ToDo
     */
    public void update(Task task) {
       task.updateDone();
       tasksRepository.save(task);     //postしたら保存をする
    }


    /**
     * タスクの名前と期限を更新
     * 　
     * @param taskForm　入力フォーム
     * @param selectTask　選択されたToDo
     */
    public void edit(TaskForm taskForm, Task selectTask) {
        Task task = Task.editToDo(taskForm,selectTask);
        tasksRepository.save(task);
    }


    /**
     * ToDo検索
     *
     * @param searchText　検索文字列
     * @return 検索文字列と部分一致した既存ToDo
     */
    public List<Task> searchTask(String searchText){
        List<Task> task = tasksRepository.findByNameContaining(searchText);
        Collections.reverse(task);
        return task;
    }


    /**
     * 同名のタスクを検索
     *
     * @param searchText 検索文字列
     */
    public Optional<Task> checkTaskName(String searchText){

        return tasksRepository.findByNameEquals(searchText);

    }



}
