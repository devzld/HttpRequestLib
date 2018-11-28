package com.zld.httplib.exception;

public class ApiException extends Exception {
    public int code;
    public String message;
    public Throwable throwable;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.throwable = throwable;
    }
}