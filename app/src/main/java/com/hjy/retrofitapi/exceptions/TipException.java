package com.hjy.retrofitapi.exceptions;

/**
 * 需要提示用户的异常
 */
public class TipException extends RuntimeException {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public TipException(String message) {
        super(message);
    }

    public TipException(String message, int code) {
        super(message);
        this.code = code;
    }
}