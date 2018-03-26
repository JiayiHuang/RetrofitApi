package com.hjy.retrofitapi.exceptions;

/**
 * Token非法异常, 需要用户重新登录
 */
public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException(String exceptionMsg) {
        super(exceptionMsg);
    }
}
