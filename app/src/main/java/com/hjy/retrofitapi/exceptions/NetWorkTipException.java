package com.hjy.retrofitapi.exceptions;

/**
 * 网络异常
 */
public class NetWorkTipException extends RuntimeException {
    public NetWorkTipException(String message) {
        super(message);
    }
}