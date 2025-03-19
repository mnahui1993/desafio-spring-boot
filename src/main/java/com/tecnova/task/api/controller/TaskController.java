package com.tecnova.task.api.controller;


import com.tecnova.task.api.generated.TaskApi;
import com.tecnova.task.api.service.TaskService;
import com.tecnova.task.api.service.authentication.AuthenticationService;

import com.tecnova.task.dto.generated.TaskRequest;
import com.tecnova.task.dto.generated.TaskResponse;
import com.tecnova.task.dto.generated.TaskSaveResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TaskController implements TaskApi {

    private final TaskService taskService;
    private final AuthenticationService authenticationService;

    public TaskController(TaskService taskService, AuthenticationService authenticationService) {
        this.taskService = taskService;
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<TaskSaveResponse> createTask(TaskRequest taskRequest) {
        var userId = authenticationService.getCurrentUser().getUserId();
        var task=taskService.save(taskRequest,userId);
        return ResponseEntity.ok().body(task);
    }

    @Override
    public ResponseEntity<Void> deleteTaskById(Integer id) {
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.OK ).build();
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getAllTasks() {

        var tasks = taskService.getAllTasks();
        return ResponseEntity.ok().body(tasks);

    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById(Integer id) {

        var task = taskService.findTaskById(id);
        return ResponseEntity.ok().body(task);

    }

    @Override
    public ResponseEntity<Void> updateTaskById(Integer id,  TaskRequest taskRequest) {
        var userId = authenticationService.getCurrentUser().getUserId();
        taskService.update(taskRequest,userId,id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
