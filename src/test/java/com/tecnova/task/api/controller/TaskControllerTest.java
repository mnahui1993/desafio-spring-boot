package com.tecnova.task.api.controller;

import com.tecnova.task.api.model.User;
import com.tecnova.task.api.service.TaskService;
import com.tecnova.task.api.service.authentication.AuthenticationService;
import com.tecnova.task.api.util.TaskUtilTest;
import com.tecnova.task.dto.generated.TaskRequest;
import com.tecnova.task.dto.generated.TaskResponse;
import com.tecnova.task.dto.generated.TaskSaveResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    @Mock
    private TaskService taskService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private TaskController taskController;




    private TaskResponse taskResponse;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        taskResponse= TaskUtilTest.buildTaskResponse(1,1,1,"nueva tarea","marco");
    }

    @Test
    void createTaskShouldReturnCreated() throws Exception {

        var taskSaveResponse=TaskSaveResponse.builder()
                                .id(1)
                                .description("nueva tarea")
                                .build();
        when(authenticationService.getCurrentUser()).thenReturn(User.builder().userId(1).userName("marco").build());
        when(taskService.save(any(),anyInt())).thenReturn(taskSaveResponse);

        String request= Files.readString(Path.of("src/test/resources/task/request/task_request_create.json"));
        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskResponse.getId()));


    }

    @Test
    void deleteTaskByIdShouldReturnOk() throws Exception {

        doNothing().when(taskService).delete(anyInt());
        mockMvc.perform(delete("/task/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTasksShouldReturnListOfTasks() throws Exception {

        when(taskService.getAllTasks()).thenReturn(List.of(taskResponse));

        mockMvc.perform(get("/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskResponse.getId()));



    }

    @Test
    void getTaskByIdShouldReturnTask() throws Exception {
        when(taskService.findTaskById(1)).thenReturn(taskResponse);
        mockMvc.perform(get("/task/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskResponse.getId()));


    }

    @Test
    void updateTaskByIdShouldReturnOk() throws Exception {
        when(authenticationService.getCurrentUser()).thenReturn(User.builder().userId(1).userName("marco").build());
        doNothing().when(taskService).update(any(TaskRequest.class), anyInt(), anyInt());
        String request= Files.readString(Path.of("src/test/resources/task/request/task_request_update.json"));
        mockMvc.perform(put("/task/{id}",1).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk());

    }

}
