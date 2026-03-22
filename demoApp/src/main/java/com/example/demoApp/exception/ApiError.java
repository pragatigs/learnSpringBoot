package com.example.demoApp.exception;

import java.time.LocalDateTime;

public class ApiError {

    private LocalDateTime timestamp;
    private String msg;
    private int status;
    private String path;
    public ApiError(LocalDateTime timestamp, int status, String msg, String path){
        this.timestamp = timestamp;
        this.status = status;
        this.msg = msg;
        this.path = path;
    }

    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }

    public int getStatus(){
        return this.status;
    }

    public String getMsg(){
        return this.msg;
    }

    public String getPath(){
        return this.path;
    }
}
