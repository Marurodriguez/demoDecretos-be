package com.sm.admDecretos.Exceptions;



public class SubError {
    private String name;
    private String value;
    private String message;

    public SubError(){}
    public SubError(String name, String value, String message){
        this.name = name;
        this.value = value;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
