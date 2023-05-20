package com.synes.util;

import java.util.Arrays;
import java.util.List;

public class ApiError {
    private int status;
    private List<String> messages;
    private String errors;

    public ApiError(int status, List<String> messages, String errors) {
        this.status = status;
        this.messages = messages;
        this.errors = errors;
    }
    public ApiError(int status, String message, String errors) {
        this.status = status;
        messages = Arrays.asList(message);
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
