package com.databps.bigdaf.core.exception;

/**
 * @author haipeng
 * @create 2017-09-28 上午10:55
 */
public class SafeModeException extends RuntimeException{
    private int errorCode;

    public SafeModeException(String message){
        super(message);
    }

    public SafeModeException(String message,int errorCode){
        super(message);
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
