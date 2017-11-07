package com.databps.bigdaf.core.exception;

/**
 * @author haipeng
 * @create 2017-09-28 上午10:55
 */
public class StandbyException extends RuntimeException{

    private int errorCode;

    public StandbyException(String message){
        super(message);
    }

    public StandbyException(String message,int errorCode){
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
