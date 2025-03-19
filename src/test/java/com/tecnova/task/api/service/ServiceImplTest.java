package com.tecnova.task.api.service;

import com.tecnova.task.api.exception.NotFoundTaskException;
import com.tecnova.task.api.exception.ServiceException;
import com.tecnova.task.api.repository.TaskRepository;
import com.tecnova.task.api.model.Task;
import com.tecnova.task.api.service.impl.TaskServiceImpl;
import com.tecnova.task.api.util.TaskUtilTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
 class ServiceImplTest {
    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;


    @Test
    void shouldSaveTaskSuccessfully() {

        given(taskRepository.save(any(Task.class)))
                .willReturn(TaskUtilTest.buildTask(1, 1, 1, "nueva tarea","",""));

        taskService.save(TaskUtilTest.buildTaskRequest(1, "nueva tarea"), 1);

    }

    @Test
    public void testSaveTaskThrowsServiceExceptionWhenErrorOccurs() {

        doThrow(new RuntimeException()).when(taskRepository).save(any(Task.class));
        assertThrows(ServiceException.class, ()
                -> taskService.save(TaskUtilTest.buildTaskRequest(0, "")
                , 0));

    }

    @Test
    void shouldUpdateTaskSuccessfully() {

        given(taskRepository.findById(any())).willReturn(Optional.of(TaskUtilTest.buildTask(1, 1, 1, "tarea nueva","","")));

        given(taskRepository.save(any(Task.class)))
                .willReturn(TaskUtilTest.buildTask(1, 2, 2, "nueva tarea modificada","",""));

        taskService.update(TaskUtilTest.buildTaskRequest(2, "nueva tarea modificada"), 1, 1);

    }

    @Test
    public void testUpdateTaskThrowsNotFoundTaskExceptionWhenErrorOccurs() {

        given(taskRepository.findById(any())).willReturn(Optional.empty());
        assertThrows(NotFoundTaskException.class, ()
                -> taskService.update(TaskUtilTest.buildTaskRequest(1, "tarea")
                , 1, 0));

    }


    @Test
    public void testUpdateTaskThrowsServiceExceptionWhenErrorOccurs() {

        given(taskRepository.findById(any())).willReturn(Optional.of(TaskUtilTest.buildTask(1, 1, 1, "tarea nueva","","")));
        given(taskRepository.save(any(Task.class))).willThrow(new RuntimeException());


        assertThrows(ServiceException.class, () -> taskService.update(TaskUtilTest.buildTaskRequest(1, "tarea modificada")
                , 1, 1));

    }

    @Test
    void shouldFindTaskByIdSuccessfully() {

        given(taskRepository.findById(any())).willReturn(Optional.of(TaskUtilTest.buildTask(1, 1, 1, "tarea nueva","marco","COMPLETED")));
        assertEquals(taskService.findTaskById(1),TaskUtilTest.buildTaskResponse(1,1,1,"tarea nueva","marco"));

    }
    @Test
    public void testFindTaskByIdThrowsNotFoundTaskExceptionWhenErrorOccurs() {

        given(taskRepository.findById(any())).willReturn(Optional.empty());
        assertThrows(NotFoundTaskException.class, ()
                -> taskService.findTaskById(1));

    }
    @Test
    public void testFindTaskByIdThrowsServiceExceptionWhenErrorOccurs() {

        given(taskRepository.findById(any())).willThrow(new RuntimeException());
        assertThrows(ServiceException.class, () -> taskService.findTaskById(1));

    }
    @Test
    void shouldGetAllTasksSuccessfully() {

        given(taskRepository.findAll()).willReturn(Collections.singletonList(TaskUtilTest.buildTask(1,1,1,"tarea nueva","marco","COMPLETED")));
        assertEquals(taskService.getAllTasks(), List.of(TaskUtilTest.buildTaskResponse(1,1,1,"tarea nueva","marco")));

    }

    @Test
    public void testGetAllTasksThrowsServiceExceptionWhenErrorOccurs() {


        given(taskRepository.findAll()).willThrow(new RuntimeException());
        assertThrows(ServiceException.class, () -> taskService.getAllTasks());

    }


    @Test
    void shouldDeleteTaskSuccessfully() {

        given(taskRepository.findById(any())).willReturn(Optional.of(TaskUtilTest.buildTask(1,1,1,"tarea nueva","marco","COMPLETED")));
        doNothing().when(taskRepository).deleteById(1);
        taskService.delete(1);

    }

    @Test
    public void testDeleteTaskThrowsNotFoundTaskExceptionWhenErrorOccurs() {

        given(taskRepository.findById(any())).willReturn(Optional.empty());
        assertThrows(NotFoundTaskException.class, ()
                -> taskService.delete(1));

    }


    @Test
    public void testDeleteTaskThrowsServiceExceptionWhenErrorOccurs() {

        given(taskRepository.findById(any())).willReturn(Optional.of(TaskUtilTest.buildTask(1, 1, 1, "tarea nueva","","")));

        doThrow(new RuntimeException()).when(taskRepository).deleteById(1);


        assertThrows(ServiceException.class, () -> taskService.delete(1));



    }


}
