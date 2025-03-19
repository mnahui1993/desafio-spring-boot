package com.tecnova.task.api.exception;


import com.tecnova.task.api.exception.NotFoundTaskException;
import com.tecnova.task.api.exception.ServiceException;
import com.tecnova.task.dto.generated.ErrorResponse;
import com.tecnova.task.dto.generated.Notification;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException exception) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(buildNotification(
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        ));
        return  buildErrorResponse(notifications);
    }



    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccountStatusException(AccountStatusException exception) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(buildNotification(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.value()
        ));
        return buildErrorResponse(notifications);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(buildNotification(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.value()
        ));
        return buildErrorResponse(notifications);
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleSignatureException(SignatureException exception) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(buildNotification(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.value()
        ));
        return buildErrorResponse(notifications);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException exception) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(buildNotification(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.value()
        ));
        return buildErrorResponse(notifications);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception exception) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(buildNotification(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ));
        return buildErrorResponse(notifications);
    }

    @ExceptionHandler(NotFoundTaskException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundTask(NotFoundTaskException exception) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(buildNotification(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value()
        ));
        return buildErrorResponse(notifications);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServiceException(ServiceException exception) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(buildNotification(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ));
        return buildErrorResponse(notifications);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Notification> notifications = new ArrayList<>();
        StringBuilder errores = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
           errores.append(fieldName)
                    .append(" ")
                    .append(errorMessage)
                    .append(" ");
        });
        notifications.add(buildNotification(
                errores.toString().trim(),
                HttpStatus.BAD_REQUEST.value()
        ));
        return buildErrorResponse(notifications);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        List<Notification> notifications = new ArrayList<>();

        notifications.add(buildNotification(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        ));
        return buildErrorResponse(notifications);
    }

    private Notification buildNotification(String message,int httpCode){
        return Notification.builder()
                .message(message)
                .statusCode(httpCode).build();
    }
    private ErrorResponse buildErrorResponse(List<Notification> notification){
        return ErrorResponse.builder()
                .notifications(notification)
                .build();

    }


}
