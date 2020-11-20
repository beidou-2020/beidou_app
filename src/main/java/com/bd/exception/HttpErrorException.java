package com.bd.exception;

/**
 * 请求异常类
 */
public class HttpErrorException extends RuntimeException {
    public HttpErrorException(String message) {
        super(message);
    }

    public HttpErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
