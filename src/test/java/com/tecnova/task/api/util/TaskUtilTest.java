package com.tecnova.task.api.util;

import com.tecnova.task.api.model.StateTask;
import com.tecnova.task.api.model.Task;
import com.tecnova.task.api.model.User;
import com.tecnova.task.dto.generated.TaskRequest;
import com.tecnova.task.dto.generated.TaskResponse;
import com.tecnova.task.dto.generated.UserRequest;
import com.tecnova.task.dto.generated.UserResponse;


public class TaskUtilTest {

    private TaskUtilTest(){

    }

    public static UserRequest buildUserRequest(){
        UserRequest userRequest= new UserRequest();
        userRequest.setUserName("marco");
        userRequest.setPassword("123");
        return userRequest;
    }

    public static User buildUser(){
        return User.builder()
                .userId(1)
                .userName("marco").build();
    }
    public static TaskResponse buildTaskResponse(Integer taskId, Integer userId, Integer stateId, String description, String userName){
        TaskResponse taskResponse= new TaskResponse();
        taskResponse.setId(taskId);
        taskResponse.setDescription(description);
        taskResponse.setUser(buildUserResponse(userId,userName));
        taskResponse.setState("COMPLETED");
        return  taskResponse;

    }
    public static Task  buildTask(Integer taskId,Integer userId,Integer stateId,String description,String userName,String stateDescription){
       return Task.builder()
                .id(taskId)
                .user(buildUser(userId,userName))
                .state(buildStateTask(stateId,stateDescription))
                .description(description)
                .build();

    }
    public static TaskRequest buildTaskRequest(Integer stateId, String description){

        TaskRequest taskRequest= new TaskRequest();
        taskRequest.setStateId(stateId);
        taskRequest.setDescription(description);
        return taskRequest;
    }



    private static User buildUser(Integer userId,String userName){
        return User.builder().userId(userId).userName(userName).build();
    }

    private static UserResponse buildUserResponse(Integer userId, String name){
        UserResponse userResponse= new UserResponse();
        userResponse.setId(userId);
        userResponse.setName(name);
        return userResponse;
    }

    private static StateTask buildStateTask(Integer stateId,String stateDescription){
        return StateTask.builder().stateTaskId(stateId).description(stateDescription).build();
    }
}
