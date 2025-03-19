package com.tecnova.task.api.service;






import com.tecnova.task.dto.generated.TaskRequest;
import com.tecnova.task.dto.generated.TaskResponse;
import com.tecnova.task.dto.generated.TaskSaveResponse;

import java.util.List;

public interface TaskService {
    TaskSaveResponse save(TaskRequest taskRequest, Integer userId);
    void update(TaskRequest taskRequest, Integer userId ,Integer taskId);

    TaskResponse findTaskById(Integer id);
    List<TaskResponse> getAllTasks();

    void delete(Integer tastkId);
}
