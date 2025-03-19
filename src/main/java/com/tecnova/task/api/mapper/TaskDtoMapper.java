package com.tecnova.task.api.mapper;



import com.tecnova.task.api.model.Task;
import com.tecnova.task.api.model.User;
import com.tecnova.task.dto.generated.TaskResponse;
import com.tecnova.task.dto.generated.TaskSaveResponse;
import com.tecnova.task.dto.generated.UserResponse;


public class TaskDtoMapper {
    private TaskDtoMapper() {

    }


    public static TaskResponse toTaskResponse(Task task) {

        return TaskResponse.builder()
                .id(task.getId())
                .description(task.getDescription())
                .user(toUserResponse(task.getUser()))
                .state(task.getState().getDescription())
                .build();

    }

    public static TaskSaveResponse toTaskSaveResponse(Task task){
        return TaskSaveResponse.builder()
                .id(task.getId())
                .description(task.getDescription())
                .build();
    }

    private static UserResponse toUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getUserId())
                .name(user.getUsername())
                .build();

    }

}
