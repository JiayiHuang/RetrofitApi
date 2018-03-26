package com.hjy.retrofitapi.exceptions;

/**
 * 已经提示用户的异常
 */
public class NoneTipException extends RuntimeException {

    public NoneTipException(String message) {
        super(message);
    }

}
