package com.tecnova.task.api.mapper;



import com.tecnova.task.api.model.StateTask;
import com.tecnova.task.api.model.Task;
import com.tecnova.task.api.model.User;
import com.tecnova.task.dto.generated.TaskRequest;


public class TaskEntityMapper {
    private TaskEntityMapper(){

    }

    public static Task toTask(TaskRequest taskRequest, Integer userId){
        return Task.builder()
                .description(taskRequest.getDescription())
                .user(buildUser(userId))
                .state(buildState(taskRequest.getStateId()))
                .build();
    }

    public static Task toTask(TaskRequest taskRequest, Integer userId,Integer taskId){
        return Task.builder()
                .id(taskId)
                .description(taskRequest.getDescription())
                .user(buildUser(userId))
                .state(buildState(taskRequest.getStateId()))
                .build();
    }

    private static StateTask buildState(Integer stateId) {
        return StateTask.builder()
                .stateTaskId(stateId)
                .build();
    }


    private static User buildUser(Integer userId){
        return User.builder()
                .userId(userId)
                .build();
    }
}
