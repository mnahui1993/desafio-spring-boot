package com.tecnova.task.api.exception;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    TASK_SERVICE_001("TASKSERVICE-API-0001","Ocurrió un error al guardar la tarea"),
    TASK_SERVICE_002("TASKSERVICE-API-0002","Ocurrió un error al actualizar la tarea "),
    TASK_SERVICE_003("TASKSERVICE-API-0003","Ocurrió un error al obtener la tarea por id"),
    TASK_SERVICE_004("TASKSERVICE-API-0004","Ocurrió un error al obtener lista de tareas"),
    TASK_SERVICE_005("TASKSERVICE-API-0005", "Ocurrió un error al intentar eliminar la tarea");
    private final String code;
    private final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
