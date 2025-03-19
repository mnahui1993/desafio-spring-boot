package com.tecnova.task.api.service.impl;


import com.tecnova.task.api.exception.ErrorCodes;
import com.tecnova.task.api.exception.NotFoundTaskException;
import com.tecnova.task.api.exception.ServiceException;
import com.tecnova.task.api.mapper.TaskDtoMapper;
import com.tecnova.task.api.mapper.TaskEntityMapper;
import com.tecnova.task.api.repository.TaskRepository;
import com.tecnova.task.api.service.TaskService;
import com.tecnova.task.dto.generated.TaskRequest;
import com.tecnova.task.dto.generated.TaskResponse;
import com.tecnova.task.dto.generated.TaskSaveResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }


    @Override
    public TaskSaveResponse save(TaskRequest taskRequest, Integer userId) {
        try {
            var task = TaskEntityMapper.toTask(taskRequest, userId);
            var result=taskRepository.save(task);
            log.info("Tarea con ID {} guardada exitosamente.", result.getId());
            return TaskDtoMapper.toTaskSaveResponse(result);

        } catch (Exception e) {
            log.error("Error al guardar la tarea para el usuario con ID {}. Detalles del error: {}", userId, e.getMessage(), e);
            throw new ServiceException(ErrorCodes.TASK_SERVICE_001);
        }

    }

    @Override
    public void update(TaskRequest taskRequest, Integer userId, Integer taskId) {

        try {
            taskRepository.findById(taskId).orElseThrow(() -> new NotFoundTaskException("no se encontro registro"));
            var task = TaskEntityMapper.toTask(taskRequest, userId, taskId);
            taskRepository.save(task);
            log.info("Tarea con ID {} actualizada exitosamente.", task.getId());

        } catch (NotFoundTaskException ex) {
            log.error("Error: Tarea con ID {} no encontrada para actualización.", taskId);
            throw ex;
        } catch (Exception e) {
            log.error("Error al actualizar la tarea con ID {}. Detalles: {}", taskId, e.getMessage(), e);
            throw new ServiceException(ErrorCodes.TASK_SERVICE_002);
        }

    }


    @Override
    public TaskResponse findTaskById(Integer id) {

        try {
            var result = taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("no se encontro registro"));
            return TaskDtoMapper.toTaskResponse(result);
        } catch (NotFoundTaskException ex) {
            log.error("Error: Tarea con ID {} no encontrada.", id);
            throw ex;
        } catch (Exception e) {
            log.error("Error al obtener tarea con ID {}. Detalles: {}", id, e.getMessage(), e);

            throw new ServiceException(ErrorCodes.TASK_SERVICE_003);
        }

    }

    @Override
    public List<TaskResponse> getAllTasks() {
        try {

            var result = StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                    .map(TaskDtoMapper::toTaskResponse)
                    .collect(Collectors.toList());

            return result.isEmpty() ? Collections.emptyList() : result;
        } catch (Exception e) {
            log.error("Error al obtener tareas.  detalle:{}", e.getMessage(), e);
            throw new ServiceException(ErrorCodes.TASK_SERVICE_004);
        }
    }

    @Override
    public void delete(Integer taskId) {
        try {
            var task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundTaskException("no se encontro registro"));
            taskRepository.deleteById(taskId);
            log.info("Tarea con ID {} eliminada exitosamente.", task.getId());

        } catch (NotFoundTaskException ex) {
            log.error("Error: Tarea con ID {} no encontrada para eliminación.", taskId);
            throw ex;
        } catch (Exception e) {

            log.error("Error al eliminar la tarea con ID {}. Detalles: {}", taskId, e.getMessage(), e);
            throw new ServiceException(ErrorCodes.TASK_SERVICE_005);


        }
    }


}
