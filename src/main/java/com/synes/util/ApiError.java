package com.synes.util;

public class ApiError {
    private int status;
    private String messages;
    private String errors;


    public ApiError(int status, String messages, String errors) {
        this.status = status;
        this.messages = messages;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
