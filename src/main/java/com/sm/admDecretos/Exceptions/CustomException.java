package com.sm.admDecretos.Exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomException extends RuntimeException {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime localTime;
    private HttpStatus status;
    private String userMessage;
    private String developerMessage;
    private List<SubError> errores;

    private void setInitValues(){
        this.errores = new ArrayList<>();
        this.localTime = LocalDateTime.now();

    }

    public void newSubError(String name, String value, String message){
        SubError subError = new SubError();
        subError.setName(name);
        subError.setMessage(message);
        subError.setValue(value);

        this.errores.add(subError);
    }

    public CustomException(String userMessage, String developerMessage) {
        super(userMessage);
        this.setInitValues();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

    public CustomException(String userMessage) {
        super(userMessage);
        this.setInitValues();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.userMessage = userMessage;
    }

    public CustomException(HttpStatus status, String userMessage, String developerMessage) {
        super(userMessage);
        this.setInitValues();
        this.status = status;
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }

    public CustomException(HttpStatus status, String userMessage, String developerMessage, List<SubError> errores) {
        super(userMessage);
        this.setInitValues();
        this.status = status;
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
        this.errores = errores;
    }

    public LocalDateTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalDateTime localTime) {
        this.localTime = localTime;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public List<SubError> getErrores() {
        return errores;
    }

    public void setErrores(List<SubError> errores) {
        this.errores = errores;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
//        int max = this.getStackTrace().length;
//        if(max > 1) {
//            StackTraceElement[] stackTraceElements = new StackTraceElement[1];
//            stackTraceElements[0] = this.getStackTrace()[0];
//            stackTraceElements[1] = this.getStackTrace()[1];
//            this.setStackTrace(stackTraceElements);
//        }else{
//            return null;
//        }
//        return this.fillInStackTrace();
        return null;
    }
}